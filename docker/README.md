Streaming Data Platform
=======================

A simplified proof of concept for data streaming.

Not recommended for production - only for research and development.


Prerequisites
=============

Install Docker Toolbox: https://www.docker.com/products/docker-toolbox


Starting the Platform
=====================

*NOTE*: This will download quote a bit of Docker images and Alpine Linux packages.

Within a "Docker Quickstart Terminal"" start up the platform:

`docker-compose up`

Optionally the number number of kafka instances can be scaled up, for example three:

`docker-compose scale kafka=3`


Creating a Topic
================

Obtain a bash login shell within a Kafka container:

`docker exec -it docker_kafka_1 bash --login`

Then within the container create a topic "Topic1":

`bin/kafka-topics.sh --create --zookeeper $KAFKA_ZOOKEEPER_CONNECT --replication-factor 1 --partitions 1 --topic Topic1`

We can see this new topic in the list of topics:

`bin/kafka-topics.sh --list --zookeeper $KAFKA_ZOOKEEPER_CONNECT`


Command line producers and consumers
====================================

Now generate some messages on the console by running the following command, after which you can enter messages separated
by new lines, end with Ctrl+C:

`bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Topic1`

Now display these messages from the beginning:

`bin/kafka-console-consumer.sh --zookeeper $KAFKA_ZOOKEEPER_CONNECT --topic Topic1 --from-beginning`

Similar commands can be used to show real-time streaming of messages, however this setup will not be as performant as a
productionised system which can have latencies around 2ms.
