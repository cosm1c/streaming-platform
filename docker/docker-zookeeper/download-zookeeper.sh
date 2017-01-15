#!/bin/bash
set -eu
set -o pipefail

# Locate nearest mirror
mirror=$(curl --stderr /dev/null https://www.apache.org/dyn/closer.cgi\?as_json\=1 | jq -r '.preferred')

# Download all required files to tmp
wget -q "${mirror}/zookeeper/zookeeper-${ZK_VERSION}/zookeeper-${ZK_VERSION}.tar.gz" -O "/tmp/zookeeper-${ZK_VERSION}.tgz"
wget -q https://www.apache.org/dist/zookeeper/KEYS -O /tmp/zookeeper-KEYS
wget -q "https://www.apache.org/dist/zookeeper/zookeeper-${ZK_VERSION}/zookeeper-${ZK_VERSION}.tar.gz.asc" -O "/tmp/zookeeper-${ZK_VERSION}.tar.gz.asc"

# Confirm integrity of download
gpg --import /tmp/zookeeper-KEYS
gpg --verify "/tmp/zookeeper-${ZK_VERSION}.tar.gz.asc" "/tmp/zookeeper-${ZK_VERSION}.tgz"
