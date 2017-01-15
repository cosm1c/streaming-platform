FROM frolvlad/alpine-oraclejdk8

MAINTAINER Cory Prowse <cory@prowse.com>

# See: https://hub.docker.com/r/ZOOKEEPER/zookeeper/~/dockerfile/
# See: https://hub.docker.com/r/wurstmeister/zookeeper/~/dockerfile/

# TODO: support docker-compose scale - need to set myid file

ENV ZK_VERSION="3.4.9" \
    ZK_USER=zookeeper \
    ZK_GROUP=zookeeper \
    TERM=xterm-256color

ENV ZK_HOME=/opt/zookeeper-${ZK_VERSION}

COPY download-zookeeper.sh /tmp/download-zookeeper.sh

RUN apk add --update bash wget curl jq gnupg docker coreutils && \
    addgroup ${ZK_GROUP} && \
    adduser -D ${ZK_USER} -G ${ZK_GROUP} && \
    adduser ${ZK_USER} users && \
    mkdir /opt && \
    sh /tmp/download-zookeeper.sh && \
    tar -xzf /tmp/zookeeper-${ZK_VERSION}.tgz -C /opt && \
    mv /opt/zookeeper-${ZK_VERSION}/conf/zoo_sample.cfg /opt/zookeeper-${ZK_VERSION}/conf/zoo.cfg && \
    sed -i -r "s|/tmp/zookeeper|$ZK_HOME/data|g" $ZK_HOME/conf/zoo.cfg && \
    sed -i -r 's|#autopurge|autopurge|g' $ZK_HOME/conf/zoo.cfg && \
    sed -i -r 's|#(log4j.appender.ROLLINGFILE.MaxBackupIndex.*)|\1|g' $ZK_HOME/conf/log4j.properties && \
    mkdir $ZK_HOME/data && \
    chown $ZK_USER:$ZK_GROUP $ZK_HOME/data && \
    rm -f /tmp/zookeeper-${ZK_VERSION}.tgz

USER ${ZK_USER}

# Expose client port (2188/tcp), peer connection port (2888/tcp), leader election port (3888/tcp)
EXPOSE 2181 2888 3888

WORKDIR ${ZK_HOME}

VOLUME ["${ZK_HOME}/conf", "${ZK_HOME}/data"]

CMD ${ZK_HOME}/bin/zkServer.sh start-foreground