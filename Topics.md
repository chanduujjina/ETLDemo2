# Apache Beam – Complete Topic List (Java Focused)

## 1. Core Concepts
- What is Apache Beam?
- Beam programming model
- Batch vs Streaming
- Bounded vs Unbounded PCollection
- Pipeline & PipelineOptions
- PCollection
- PTransform
- Runner abstraction
- DirectRunner vs Production Runners

---

## 2. Time Concepts
- Event Time
- Processing Time
- Ingestion Time
- Timestamp assignment
- Timestamp skew
- Watermarks
- Late data handling

---

## 3. Windowing
- Why windowing is needed
- Fixed Windows
- Sliding Windows
- Session Windows
- Global Window
- Window merging
- Windowing with batch pipelines

---

## 4. Triggers & Lateness
- Default triggers
- Event-time triggers
- Processing-time triggers
- Repeated triggers
- Allowed lateness
- Accumulating vs Discarding panes
- Late data strategies

---

## 5. Basic Transforms
- Create
- MapElements
- FlatMapElements
- Filter
- ParDo
- DoFn lifecycle
  - Constructor
  - @Setup
  - @StartBundle
  - @ProcessElement
  - @FinishBundle
  - @Teardown

---

## 6. Keying & Grouping
- KV<K, V>
- WithKeys
- GroupByKey (why discouraged)
- Combine.perKey
- Combine.globally
- CombineFn
- Lambda-based Combine
- GroupIntoBatches
- CoGroupByKey

---

## 7. Aggregations
- Count.perKey
- Sum.perKey
- Min / Max
- Mean
- Stats.perKey
- Top.perKey
- Approximate.Unique
- Approximate.Quantiles
- Custom aggregations
- Windowed aggregations

---

## 8. ORDER BY Patterns
- Sorting within a key
- Sorting within a window
- Top N per key
- Why global ORDER BY is impossible
- ORDER BY vs Top
- SQL vs Core SDK ordering

---

## 9. Multiple Outputs & Routing
- TupleTag
- PCollectionTuple
- Side outputs
- Conditional routing
- Error handling
- Dead-letter queue (DLQ) pattern

---

## 10. Side Inputs
- What is Side Input?
- View.asList
- View.asMap
- View.asMultimap
- View.asSingleton
- Side input with default values
- Windowed side inputs
- Side input vs Join

---

## 11. Joins & Multi-Stream Processing
- CoGroupByKey
- Inner Join pattern
- Left / Right Join pattern
- Stream–stream joins
- Stream–batch joins
- Side input joins
- Join using Beam SQL

---

## 12. State & Timers (Advanced)
- Stateful DoFn
- ValueState
- BagState
- MapState
- CombiningState
- Event-time timers
- Processing-time timers
- Per-key state usage
- Common state patterns

---

## 13. I/O Connectors
- TextIO
- FileIO
- KafkaIO
- PubSubIO
- BigQueryIO
- JDBCIO
- Custom IOs
- Exactly-once sinks

---

## 14. Beam SQL
- Beam SQL overview
- SQL pipelines in Java
- GROUP BY in Beam SQL
- ORDER BY + LIMIT
- Windowing with SQL
- JOINs with SQL
- User Defined Functions (UDFs)
- When to use SQL vs SDK

---

## 15. Metrics & Monitoring
- Beam Metrics API
- Counters
- Distributions
- Gauges
- Logging best practices
- Pipeline monitoring

---

## 16. Performance & Optimization
- Fusion
- Combiner lifting
- Shuffle reduction
- Hot key problem
- Data skew handling
- Parallelism tuning
- Memory optimization

---

## 17. Fault Tolerance & Reliability
- Exactly-once semantics
- At-least-once vs exactly-once
- Checkpointing
- Retries
- Idempotent writes
- Deduplication strategies
- Replay behavior

---

## 18. Runners
- DirectRunner
- DataflowRunner
- FlinkRunner
- SparkRunner
- Runner-specific differences
- Portability framework

---

## 19. Testing
- TestPipeline
- PAssert
- Unit testing DoFn
- Testing windowed pipelines
- Integration testing

---

## 20. Deployment & Production
- Pipeline templates
- Flex templates
- Parameterized pipelines
- Configuration management
- Versioning pipelines
- Backfills
- Rollbacks

---

## 21. Design Patterns (Real World)
- Deduplication pattern
- Enrichment pattern
- Fan-out / Fan-in
- Retry & DLQ pattern
- Slowly changing dimensions
- Exactly-once database writes
- Streaming ETL patterns

---

## 22. Interview-Focused Topics
- Why GroupByKey is discouraged
- Side Input vs Join
- Watermark vs Trigger
- Window vs Key grouping
- Why global ORDER BY is impossible
- Stateful vs Stateless DoFn
- Batch vs Streaming differences
- Beam vs Spark/Flink
