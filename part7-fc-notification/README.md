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