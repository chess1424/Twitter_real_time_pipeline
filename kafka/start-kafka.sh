#start zookeper
$KAFKA_HOME/bin/zookeper-server-start.sh $KAFKA_HOME/config/zookeper.properties &


#start kafka broker
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties