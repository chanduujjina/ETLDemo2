
## More Beam Characteristics (Extra Comparison)

| Spring Boot Concept    | Apache Beam Equivalent                              |
| ---------------------- | --------------------------------------------------- |
| Controller             | Transform class (DoFn)                              |
| Request Mapping        | PCollection input edges                             |
| Dependency Injection   | PipelineOptions + external configs                  |
| Filters / Interceptors | Composite transforms                                |
| Application Properties | ValueProviders (runtime configs)                    |
| Session                | State + Timers                                      |
| Database               | IO connectors (JDBCIO, PubSubIO, KafkaIO, S3, etc.) |
| Thread-pool            | Runner-managed workers                              |
| Transactions           | Exactly-once semantics, checkpointing               |
