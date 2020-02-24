FROM ubuntu:16.04

MAINTAINER carlos.1424@live.com.mx

ENV DRUID_HOME /usr/local/druid

# install java
RUN apt-get update && apt-get install -y \
  vim \
  net-tools \
  wget \
  openjdk-8-jdk \
  perl

# install druid
RUN wget https://downloads.apache.org/druid/0.17.0/apache-druid-0.17.0-bin.tar.gz && \
  tar -xzvf apache-druid-0.17.0-bin.tar.gz && \
  mv apache-druid-0.17.0 $DRUID_HOME

ADD config/common.runtime.properties /usr/local/druid/single-server/micro-quickstart/_common/common.runtime.properties

ENV DRUID_SKIP_JAVA_CHECK=1

ENTRYPOINT ./$DRUID_HOME/bin/micro-quickstart


