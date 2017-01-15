#!/bin/bash
set -eu
set -o pipefail

# Locate nearest mirror
mirror=$(curl --stderr /dev/null https://www.apache.org/dyn/closer.cgi\?as_json\=1 | jq -r '.preferred')

# Download all required files to tmp
wget -q "${mirror}kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz" -O "/tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz"
wget -q https://www.apache.org/dist/kafka/KEYS -O /tmp/kafka-KEYS
wget -q "https://www.apache.org/dist/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz.asc" -O "/tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz.asc"

# Confirm integrity of download
gpg --import /tmp/kafka-KEYS
gpg --verify "/tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz.asc" "/tmp/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz"
