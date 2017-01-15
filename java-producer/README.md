Example Kafka Producer in Java
==============================

Modify producer.properties so that the `bootstrap.server` property points to your instance of Kafka
running within Docker.

When using docker-machine the IP address is obtained with:

`docker-machine ip`

The port that is redirected to the Kafka 9092 port:

`docker-compose ps`

Gives:

           Name                     Command               State                      Ports
     ---------------------------------------------------------------------------------------------------------
     docker_kafka_1       /bin/sh -c sh ${KAFKA_HOME ...   Up      0.0.0.0:32869->9092/tcp
     docker_zookeeper_1   /bin/sh -c ${ZK_HOME}/bin/ ...   Up      0.0.0.0:32868->2181/tcp, 2888/tcp, 3888/tcp

Indicating port 32869 is redirected to 9092 on the kafka container.
