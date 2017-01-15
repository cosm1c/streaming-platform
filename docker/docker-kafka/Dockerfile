FROM frolvlad/alpine-oraclejdk8

MAINTAINER Cory Prowse <cory@prowse.com>

# See: https://github.com/wurstmeister/kafka-docker

ENV KAFKA_VERSION="0.10.1.1" \
    SCALA_VERSION="2.12" \
    KAFKA_USER=kafka \
    KAFKA_GROUP=kafka \
    TERM=xterm-256color

ENV KAFKA_HOME=/opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION}

COPY download-kafka.sh /tmp/download-kafka.sh

COPY kafka-start-inside-docker.sh $KAFKA_HOME/kafka-start-inside-docker.sh

COPY kafka-profile.sh /etc/profile.d/kafka-profile.sh

RUN apk add --update bash wget curl jq gnupg docker coreutils && \
    addgroup ${KAFKA_GROUP} && \
    adduser -D ${KAFKA_USER} -G ${KAFKA_GROUP} && \
    adduser ${KAFKA_USER} users && \
    sh /tmp/download-kafka.sh && \
    tar -xzf /tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz -C /opt && \
    sed -i -r 's|broker.id=.*|broker.id=-1|g' $KAFKA_HOME/config/server.properties && \
    mkdir $KAFKA_HOME/logs && \
    chown $KAFKA_USER:$KAFKA_GROUP $KAFKA_HOME/logs && \
    chown -R $KAFKA_USER:$KAFKA_GROUP $KAFKA_HOME/config && \
    rm -f /tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz

EXPOSE 9092

USER ${KAFKA_USER}

WORKDIR ${KAFKA_HOME}

# TODO: Volumes
VOLUME ["${KAFKA_HOME}/logs"]

CMD sh ${KAFKA_HOME}/kafka-start-inside-docker.sh