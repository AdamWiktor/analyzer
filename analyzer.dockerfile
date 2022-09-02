FROM openjdk:17

COPY target/analyzer-0.0.1-SNAPSHOT.jar analyzer.jar

CMD java -jar analyzer.jar
