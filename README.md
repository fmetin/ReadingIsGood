# ReadingIsGood

Online book retail demo application.

## Requirements

For building and running the application you need:

- [JDK 17](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Redis](https://redis.io/)

## Running the application locally

Application and redis can be started easily with the help of the following commands over the docker-compose.yml file.

First step is creating jar file with maven.

```shell
mvn clean install
```
Second step is docker compose

```shell
docker-compose up
```
After started the service we are done! We can start to consume services.

## Tech Stack
- Java 17
- Hibernate
- Spring Boot Web, Security, Jpa
- H2 database
- Lombok
- Redis

