cd $(dirname $0)
cd ../
mvn clean package

rm -rf target logs

./gradlew build
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi

rm -rf build logs
exit
