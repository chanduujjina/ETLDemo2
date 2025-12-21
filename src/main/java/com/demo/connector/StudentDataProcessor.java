package com.demo.connector;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO.DataSourceConfiguration;
import org.apache.beam.sdk.io.jdbc.JdbcIO.Write;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.demo.dto.Student;
import com.demo.dto.StudentRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentDataProcessor {
	
	private static final String INSERT_QUERY = "insert into student_info(id,name,gender,branch) values(?,?,?,?)";

	public static void main(String[] args) {
		
		
		//Create a pipeline with StreamOptions
		
		
		StreamingOptions streamingOptions = PipelineOptionsFactory.fromArgs(args).withValidation().as(StreamingOptions.class);
		
		streamingOptions.setStreaming(true);
		
	
		Pipeline pipeLine = Pipeline.create(streamingOptions);
		
		
		pipeLine.apply("Connect to Kafka",
				KafkaIO.<String, String>read().withBootstrapServers("localhost:9092").withTopic("studentInfo-topic")
						.withKeyDeserializer(StringDeserializer.class).withValueDeserializer(StringDeserializer.class)
						.withoutMetadata())
				.apply("Read Data From From Kafka", ParDo.of(new StudentInfoDesrilizer())).
				 apply("Save student details",saveStudentDetail());
		
		pipeLine.run().waitUntilFinish();
	}

	private static Write<Student> saveStudentDetail() {
		return JdbcIO.<Student>write().
				 withDataSourceConfiguration(DataSourceConfiguration.create("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/etl_db").
						 withUsername("root").withPassword("root")).withStatement(StudentDataProcessor.INSERT_QUERY).
				 withPreparedStatementSetter((std,ps)-> {
					 ps.setInt(1, std.getId());
					 ps.setString(2, std.getName());
					 ps.setString(3, std.getGender());
					 ps.setString(4, std.getBarnch());
				 });
	}
	
	static class StudentInfoDesrilizer extends DoFn<KV<String,String>, Student> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final ObjectMapper MAPPER = new ObjectMapper();
		
		@ProcessElement
		public void processData(ProcessContext ctx) throws Exception
		
		{
			
			String payload = ctx.element().getValue();
			
			System.out.println("payload : "+payload);
			
			StudentRequestDto studentRequestDto = MAPPER.readValue(payload, StudentRequestDto.class);
			
			System.out.println("studentRequestDto : "+studentRequestDto);
			
			studentRequestDto.getPayLoad().forEach(std-> {
				ctx.output(std);
			});
		}
	}
	
	

}
