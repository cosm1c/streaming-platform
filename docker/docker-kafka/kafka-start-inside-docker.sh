#!/bin/bash
set -eu
set -o pipefail

# Capture kill requests to stop properly
trap "$KAFKA_HOME/bin/kafka-server-stop.sh; echo 'Kafka stopped.'; exit" SIGHUP SIGINT SIGTERM

KAFKA_ADVERTISED_PORT=$(docker port `hostname` 9092 | sed -r "s/.*:(.*)/\1/g")

source /etc/profile.d/kafka-profile.sh

LOG_DIR=$KAFKA_HOME/logs/host-$HOSTNAME

sed -i -r "s|log.dirs=.*|log.dirs=$LOG_DIR|g" $KAFKA_HOME/config/server.properties && \
sed -i -r "s|zookeeper.connect=.*|zookeeper.connect=$KAFKA_ZOOKEEPER_CONNECT|g" $KAFKA_HOME/config/server.properties && \
sed -i -r "s|#advertised.listeners=.*|advertised.listeners=PLAINTEXT://$KAFKA_ADVERTISED_HOST_NAME:$KAFKA_ADVERTISED_PORT|g" $KAFKA_HOME/config/server.properties && \

${KAFKA_HOME}/bin/kafka-server-start.sh ${KAFKA_HOME}/config/server.properties