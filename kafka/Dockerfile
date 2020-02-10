FROM ubuntu:16.04

MAINTAINER carlos.1424@live.com.mx

ENV KAFKA_HOME /usr/local/kafka
COPY ./start-kafka.sh /scripts/
COPY ./create-kafka-topics.sh /scripts/

RUN chmod +x /scripts/start-kafka.sh
RUN chmod +x /scripts/create-kafka-topics.sh

# install java
RUN apt-get update && apt-get install -y \
  vim \
  net-tools \
  wget \
  openjdk-8-jdk

# install kafka
RUN wget https://archive.apache.org/dist/kafka/0.11.0.2/kafka_2.12-0.11.0.2.tgz && \
  tar -xzvf kafka_2.12-0.11.0.2.tgz && \
  mv kafka_2.12-0.11.0.2 $KAFKA_HOME

CMD "/scripts/start-kafka.sh"