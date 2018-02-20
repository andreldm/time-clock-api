# time-clock-api

A sample application for time-clock management demonstrating how simple it is to get started with Spring Boot.

## Specs

- Language: Java 8
- Build System: Gradle 4.5
- Framework: Spring Boot 1.5
- HTTP Server: Jetty 9.4
- Database: H2

## Build and run
Open up the terminal and execute the following commands:

- `./gradlew build`
- `java -jar -Xmx256m build/libs/time-clock-api-0.0.1-SNAPSHOT.jar`

Take a look at src/test/resources/test.sh for a reference of how to consume the API.

## Possible Improvements

- Use caching to validate clock in requests against conflicts
  - An in memory expiring Map for single instance app
  - A distributed cache, e.g. Redis, for load balanced instances
- Store daily data (work time, rest time, etc...) in order to speed up report generation
  - Update them accordingly every time a new entry is created
- Setup a benchmark suite (e.g. JMeter) to measure the impact of the above improvements
  - Also to measure how well the solution scales
