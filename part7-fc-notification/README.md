## Kafka
### docker-compose kafka 구성
### Kafka producer, consumer 테스트
* terminal1
```
docker exec -it infra-kafka-1 /bin/bash
kafka-topics --create --topic test --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-console-producer --topic test --bootstrap-server localhost:9092
> hello
```
* terminal2
```
kafka-console-consumer --topic test --bootstrap-server localhost:9092 --from-beginning
hello
```

### spring cloud stream
* 실무에서는 토픽 자동 생성을 끄는 경우가 많다

* 토픽 리스트
```
kafka-topics --list --bootstrap-server localhost:9092
```
* comment topic produce
```
echo '{"type":"ADD","postId":1,"userId":2,"commentId":1}' | 
\kafka-console-producer --broker-list localhost:9092 --topic comment
```
* like topic produce
```
echo '{"type":"ADD","postId":1,"userId":2,"createdAt":"2024-01-20T18:35:24.01Z"}' | 
\kafka-console-producer --broker-list localhost:9092 --topic like
```
* follow topic produce
```
echo '{"type":"ADD","userId":1,"targetUserId":2,"createdAt":"2024-01-20T18:35:24.01Z"}' | 
\kafka-console-producer --broker-list localhost:9092 --topic follow
```