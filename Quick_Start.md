📘 Apache Beam – Essential Design Patterns & Concepts

Apache Beam does not force specific design patterns, but knowing the right ones helps you build clean, maintainable, and scalable pipelines.
This guide summarizes all critical design patterns needed for real-world Beam development.


# 1. Pipeline Pattern (Core Beam Concept)

## Apache Beam itself follows the pipeline architecture:
```css
Input → Transform → Output
```

## Beam uses three main abstractions:

- Pipeline – workflow container

- PCollection – dataset

- PTransform – operation applied to data

   ```java
  pipeline
    .apply(Read.from(source))
    .apply(Transform.of(...))
    .apply(Write.to(sink));
  ```

# 2. Functional Programming Patterns

Beam heavily uses functional programming concepts.

## ✔ Map Pattern (ParDo / DoFn)

### Applies logic to each element:

 ```java
  .apply(ParDo.of(new DoFn<String, String>() {
    @ProcessElement
    public void process(ProcessContext c) {
        c.output(c.element().toUpperCase());
    }
}));

 ```
## ✔ Filter Pattern
 ```java
  .apply(Filter.by(x -> x.isValid()));
 ```
## ✔ FlatMap Pattern
  ```
  .apply(ParDo.of(new DoFn<List<String>, String>() {
    @ProcessElement
    public void process(ProcessContext c) {
        for (String s : c.element()) c.output(s);
    }
}));
```

## ✔ Reduce Pattern (Combine / GroupByKey)
 ```
  .apply(Combine.globally(Sum.ofIntegers()));
  OR
  .apply(Combine.perKey(new Sum.CombineFn()));
```

# 3. Builder Pattern
 ## Beam IOs mostly use builder syntax:

 ```java
JdbcIO.<User>read()
    .withDataSourceConfiguration(
        JdbcIO.DataSourceConfiguration.create(
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://localhost:3306/db"
        )
        .withUsername("root")
        .withPassword("root")
    )
    .withQuery("SELECT * FROM users")
    .withRowMapper((rs) -> new User(rs.getInt("id"), rs.getString("name")));
```
# 4. Strategy Pattern

## Beam provides MANY interchangeable strategies:

### ✔ Windowing Strategies

- Fixed

- Sliding

- Session

### ✔ Trigger Strategies

- AfterWatermark

- AfterProcessingTime

- Composite triggers

### ✔ Coder Strategies

- AvroCoder

- ByteArrayCoder

- SerializableCoder

### ✔ Runner Strategies

 - DirectRunner

- Dataflow

- Flink

- Spark

# 5. Factory Pattern

## Beam uses factories to generate coders, transforms, and IOs.
```java
Coder<String> coder = StringUtf8Coder.of();
```
# 6. Adapter Pattern

## Used to convert external data formats into Beam-friendly objects.
```java
.withRowMapper(rs -> new Student(rs.getInt("id"), rs.getString("name")));
```

# 7. Observer Pattern (Internal Beam Behavior)

## Beam runners “observe” and react to:

- Watermarks

- Bundles

- Trigger firing

- Progress

- Backpressure

### Good to understand for streaming pipeline debugging.

# 8. Decorator Pattern

## Each PTransform decorates the PCollection with new behavior:
 ```java
  input
    .apply(WithTimestamps.of(x -> x.getEventTime()))
    .apply(Window.into(FixedWindows.of(Duration.standardSeconds(30))));
 ```


 # 9. Template Method Pattern (DoFn Lifecycle)

##  Beam calls these callback hooks:
 ```java
 @Setup
 @StartBundle
 @ProcessElement
 @FinishBundle
 @Teardown
```
```
public class MyFn extends DoFn<String, String> {

    @Setup
    public void init() {}

    @ProcessElement
    public void process(ProcessContext c) {
        c.output(c.element().toUpperCase());
    }
}
```
