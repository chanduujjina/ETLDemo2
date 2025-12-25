
## Configuration
### Depedencies and plugin

```xml
 <dependency>
            <groupId>org.apache.avro</groupId>
            <artifactId>avro</artifactId>
            <version>1.11.3</version>
        </dependency>

```

Plugin

```xml
<plugins>
            <plugin>
                <groupId>org.apache.avro</groupId>
                <artifactId>avro-maven-plugin</artifactId>
                <version>1.11.3</version>
                <executions>
                    <execution>
                        <id>generate-avro-sources</id>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>schema</goal>
                        </goals>
                        <configuration>
                            <sourceDirectory>${project.basedir}/src/main/avro</sourceDirectory>
                            <outputDirectory>${project.basedir}/target/generated-sources/avro</outputDirectory>
                            <stringType>String</stringType>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
```

### Sample avsc file

```avsc
{
  "type": "record",
  "name": "ClassName",
  "namespace": "com.demo.avro",
  "fields": [
    { "name": "field1", "type": "int" },
    { "name": "field2", "type": "string" },
    { "name": "field3", "type": "int" },
    { "name": "field4", "type": "string" }
   
  ]
}
```
