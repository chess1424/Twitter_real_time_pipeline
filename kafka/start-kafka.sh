#start zookeper
$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties &

#start kafka broker
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties &

#create kafka topics
/scripts/create-kafka-topics.sh &

#keep container running
tail -f /dev/null
