

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
  --topic studentInfo-topic
```

## Press Ctrl + D to exit.
6️⃣ Consume Messages (Optional Check)

```bash
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic studentInfo-topic \
  --from-beginning
```

## Sample payload

```json
{
    "payLoad": [
        {
            "id": 1,
            "name": "Ravi Kumar",
            "gender": "M",
            "barnch": "CSE",
            "eventTime": "2025-01-20T10:15:30Z"
        },
        {
            "id": 2,
            "name": "Rani",
            "gender": "F",
            "barnch": "ECE",
            "eventTime": "2025-01-20T10:15:40Z"
        },
        {
            "id": 3,
            "name": "Raju",
            "gender": "M",
            "barnch": "MECH",
            "eventTime": "2025-01-20T10:15:55Z"
        }
    ]
}
```
