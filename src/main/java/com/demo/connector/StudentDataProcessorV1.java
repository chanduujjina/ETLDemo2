package com.demo.connector;

import java.time.Instant;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.joda.time.Duration;

import com.demo.dto.Student;
import com.demo.dto.StudentRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

public class StudentDataProcessorV1 {

    // ✅ UPSERT to avoid duplicates
    private static final String UPSERT_QUERY =
        "INSERT INTO student_info(id, name, gender, branch) " +
        "VALUES (?, ?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE " +
        "name = VALUES(name), " +
        "gender = VALUES(gender), " +
        "branch = VALUES(branch)";

    public static void main(String[] args) {

        StreamingOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(StreamingOptions.class);

        options.setStreaming(true);

        Pipeline pipeline = Pipeline.create(options);

        pipeline
            // =======================
            // 1️⃣ READ FROM KAFKA
            // =======================
            .apply("ReadFromKafka",
                KafkaIO.<String, String>read()
                    .withBootstrapServers("localhost:9092")
                    .withTopic("CC-Test1")
                    .withKeyDeserializer(StringDeserializer.class)
                    .withValueDeserializer(StringDeserializer.class)
                    .withConsumerConfigUpdates(
                        ImmutableMap.of(
                            "group.id", "CC-Group1",
                            "auto.offset.reset", "earliest"
                        )
                    )
                    .withoutMetadata()
            )

            // =======================
            // 2️⃣ EXTRACT VALUE
            // =======================
            .apply("ExtractValue",
                MapElements.into(TypeDescriptors.strings())
                    .via(KV::getValue)
            )

            // =======================
            // 3️⃣ DESERIALIZE + FAN-OUT
            // =======================
            .apply("DeserializeStudents",
                ParDo.of(new DoFn<String, Student>() {

                    private static final ObjectMapper MAPPER = new ObjectMapper();

                    @ProcessElement
                    public void processElement(
                            @Element String json,
                            OutputReceiver<Student> out) throws Exception {

                        StudentRequestDto dto =
                            MAPPER.readValue(json, StudentRequestDto.class);

                        for (Student s : dto.getPayLoad()) {
                            out.output(s);
                        }
                    }
                })
            )

            // =======================
            // 4️⃣ ASSIGN EVENT TIME
            // =======================
            .apply("AssignEventTime",
            	    WithTimestamps.of(
            	        (Student s) ->
            	            new org.joda.time.Instant(
            	                java.time.Instant.parse(s.getEventTime()).toEpochMilli()
            	            )
            	    ).withAllowedTimestampSkew(org.joda.time.Duration.standardSeconds(5))
            	)

            // =======================
            // 5️⃣ WINDOWING + TRIGGERS
            // =======================
            .apply("Windowing",
                Window.<Student>into(FixedWindows.of(Duration.standardSeconds(30)))
                    .withAllowedLateness(Duration.standardSeconds(30))
                    .triggering(
                        AfterWatermark.pastEndOfWindow()
                            .withEarlyFirings(
                                AfterProcessingTime
                                    .pastFirstElementInPane()
                                    .plusDelayOf(Duration.standardSeconds(10))
                            )
                    )
                    .discardingFiredPanes()
            )

            // =======================
            // 6️⃣ LOG WINDOW + PANE (DEBUG)
            // =======================
            .apply("LogWindowInfo",
                ParDo.of(new DoFn<Student, Student>() {

                    @ProcessElement
                    public void process(
                        @Element Student student,
                        OutputReceiver<Student> out,
                        BoundedWindow window,
                        PaneInfo pane) {

                        System.out.println(
                            "WINDOW=" + window +
                            ", PANE=" + pane.getTiming() +
                            ", FIRST=" + pane.isFirst() +
                            ", LAST=" + pane.isLast() +
                            ", STUDENT=" + student
                        );

                        out.output(student);
                    }
                })
            )

            // =======================
            // 7️⃣ WRITE TO MYSQL
            // =======================
            .apply("WriteToMySQL",
                JdbcIO.<Student>write()
                    .withDataSourceConfiguration(
                        JdbcIO.DataSourceConfiguration.create(
                            "com.mysql.cj.jdbc.Driver",
                            "jdbc:mysql://localhost:3306/etl_db"
                        )
                        .withUsername("root")
                        .withPassword("root")
                    )
                    .withStatement(UPSERT_QUERY)
                    .withPreparedStatementSetter((student, ps) -> {
                        ps.setInt(1, student.getId());
                        ps.setString(2, student.getName());
                        ps.setString(3, student.getGender());
                        ps.setString(4, student.getBarnch());
                    })
            );

        pipeline.run().waitUntilFinish();
    }
}
