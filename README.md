# ADPS Palindrome & Dictionary Word Test

Contains a REST service that will handle `GET` requests at `/palindrome`, with a `word` parameter in the query string. The `GET` request should return a `200 OK` response with JSON in the body that represents the results of a palindrome and dictionary check. It should look something like this:

```
{
    "id": 2,
    "word": "diligent"
    "reversed": "tnegilid",
    "palindrome": false,
    "defined": true,
    "serviceHistory": "[id=1,word=Mom,reverse=moM,palindrome=true,defined=true][id=2,word=diligent,reverse=tnegilid,palindrome=false,defined=true]",
}
```

The `id` field is a unique request id for the word test
The `word` field is the user-provided word
The `reversed` field is the user-provided word reversed by the REST service
The `palindrome` field indicates whether the word is a palindrome or not (note that the check is case-insensitive)
The `defined` field indicates whether the word is defined in the dictionary or not. The REST service uses the DictionaryAPI as an Http client
     to look up this information.
The `serviceHistory` is a field that contains previous word submissions processed by the REST service. To limit the output returned back, the last 100 results are returned.

**This software is written in Java and builds with either maven or gradle**

**Note**
Although it is possible to package this service as a traditional WAR file for deployment to an external application server, the simpler approach demonstrated here creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring's support for embedding the Tomcat servlet container as the HTTP runtime, instead of deploying to an external instance.

# Building the application

### Dependencies
1) Java 1.7 or higher  (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html?ssSourceSiteId=otnpt)
2) Maven 3.3+ (I used Maven 3.5.4) https://maven.apache.org/download.cgi

### Build the Application

#### Option 1 (Clean build and unit tests)
cd to the `<REPO_ROOT>/test` folder and run the following command:
```
./run-tests.sh
```
The built jar can be found under `<REPO_ROOT>/build/lib/palindrome-rest-service-0.1.0.jar` folder

#### Option 2 (Build and unit tests)
From the root of the repo, run the following command
```
./mvnw package
```
The built jar can be found under `<REPO_ROOT>/target/palindrome-rest-service-0.1.0.jar` folder

# Launching the REST Service

## Option 1 - Build and Launch

Under the `<ROOT>/test` folder, launch the following command.  It will compile the application, run unit tests, package the code into a jar file, and then launch the service. 

```
<REPO_ROOT>/test/run-build-launch.sh
```

The service will be accessible at the following REST endpoint:

http://localhost:8080/palindrome

## Option 2 - Manually launching the service 

The service can be launched manually from the command line with the following command:

```
$JAVA_HOME/bin/java -jar <PATH_TO>/palindrome-rest-service-0.1.0.jar

```

# Testing the Service

First make sure the service is up and running by following the above instructions for **Launching the Service**

## Using webpage to test service

Enter localhost:8080/index.html onto your browser. It will contain a simple web page where you can enter a word in the text box.  

Click on the `Test my word` **button** which will issue an HTTP GET request to the service and post the results back on the same web page.


## Testing the REST Service on browser URL section


## Testing with cURL


Testing with word=`hello`
```
$ curl -isS http://localhost:8080/palindrome?word=hello
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 19 Aug 2018 21:21:50 GMT

{"id":1,"word":"hello","reversed":"olleh","serviceHistory":"[id=1,word=hello,reverse=olleh,palindrome=false,defined=true]","palindrome":false,"defined":true}
```

Testing with word=`racecar`
```
$ curl -i http://localhost:8080/palindrome?word=racecar
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 19 Aug 2018 21:23:15 GMT

{"id":2,"word":"racecar","reversed":"racecar","serviceHistory":"[id=1,word=hello,reverse=olleh,palindrome=false,defined=true][id=2,word=racecar,reverse=racecar,palindrome=true,defined=false]","palindrome":true,"defined":false}
```

