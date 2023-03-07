
## Create a topic
docker compose exec broker kafka-topics --bootstrap-server broker:9092 --create --topic quickstart

## Write msg to topic
docker compose exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic my-first-topic

this is my first kafka message
hello world!
this is my third kafka message. I’m on a roll :-D


## Read msg from topic
docker compose exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic my-first-topic --from-beginning