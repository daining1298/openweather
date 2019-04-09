# openweather
fetch local weather from open weather map
only tested with MAC. should also works on other platform. 
Seems test cases are not running on Linux, probably maven configuration needs to be changed.

Compile & Run
- Service
cd implementation/Service
mvn package
java -jar target/OpenWeatherService-0.0.1-SNAPSHOT.jar

- Client
cd implementation/Client
mvn package
java -jar target/OpenWeatherClient-0.0.1-SNAPSHOT.jar
