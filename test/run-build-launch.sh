cd $(dirname $0)
cd ../
mvn clean package

rm -rf target

./gradlew build
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi

$JAVA_HOME/bin/java -jar build/libs/palindrome-rest-service-0.1.0.jar
exit
