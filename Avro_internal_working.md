## What is Avro ?
- Apache Avro is a schema-based binary data format used to efficiently store and transfer large amounts of structured data in big-data and streaming systems.

 ##  Why Avro is used in streaming pipelines ?
###  Avro is chosen because it provides:
 - Compact binary encoding

-  Schema evolution support

-  Block-level compression

-  Streaming-friendly writes

## What is a codec? 

### A codec is a compression algorithm used by Avro to compress blocks of records inside an Avro file.

### Common codecs:

- snappy (default for streaming)

- deflate

- bzip2

- zstandard

## Conceptual Guide: Avro in Streaming & Big-Data Systems


![alt-text](Avro_file_format.png)
