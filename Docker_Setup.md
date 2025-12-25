

▶️ Run Kafka & Zookeeper
```bash
docker-compose up -d
```
✅ Verify It's Running
```bash
docker ps
```
3. ✅ Restart Everything
- Restart Docker Compose:
```bash
docker-compose down
docker-compose up -d
```

## Docker Kafka commands
1️⃣ Enter Kafka Container

```bash
docker exec -it etldemo2-kafka-1 bash
```

2️⃣ Verify Kafka CLI tools

```bash
ls /usr/bin | grep kafka
```

3️⃣ List Topics
```bash
kafka-topics \
  --bootstrap-server localhost:9092 \
  --list
```
4️⃣ Create Topic
```bash
kafka-topics \
  --bootstrap-server localhost:9092 \
  --create \
  --topic studentInfo-topic \
  --partitions 1 \
  --replication-factor 1
```
5️⃣ Produce JSON Messages

```bash
kafka-console-producer \
  --bootstrap-server localhost:9092 \
  --topic employee-topic
```

## Press Ctrl + D to exit.
6️⃣ Consume Messages (Optional Check)

```bash
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic employee-topic \
  --from-beginning
```
