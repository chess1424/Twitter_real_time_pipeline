KAFKA_PORT="2181"

while netstat -lnt | awk '$4 ~ /:'$KAFKA_PORT'/ {exit 1}'; do
	echo "waiting for kafka to be ready"
	sleep 10;
done

#Able to create a kafka topic
echo "Creating kafka topics"
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic twitter-streamer