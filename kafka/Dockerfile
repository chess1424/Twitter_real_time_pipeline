FROM ubuntu:16.04

ENV KAFKA_HOME /usr/local/kafka
ADD ./start-kafka.sh /scripts/

# install java 
RUN apt-get update && apt-get install -y \
  wget \
  openjdk-8-jdk

# install kafka
RUN wget http://apache.cs.utah.edu/kafka/0.11.0.2/kafka_2.12-0.11.0.2.tgz && \
  tar -xzvf kafka_2.12-0.11.0.2.tgz && \
  mv kafka_2.12-0.11.0.2 $KAFKA_HOME

CMD ["/scripts/start-kafka.sh"]