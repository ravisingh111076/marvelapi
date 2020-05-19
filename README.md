**TODO**
# Marvel Client

### Stack
- Java 8
- Spring Boot
- Netflix Hystrix
- Redis (cache)
- Maven

### Local Setup
1. Install Java 8+
2. Install Maven
3. Install Redis: from Docker container or install locally
4. checkout: repository from Github
5. maven setup: copy the settings.xml from the root to your local.m2 directory
6. Mandatory environment vars: add ```PRIVATE_KEY```,```PUBLIC_KEY```,```REDIS_PORT```
7. compile:  ```mvn clean install``` from root directory
8. start application ```java -jar target/marvelService-1.0.jar```

### Test Data
example - characterId = 1011106,1009693

- http://localhost:8080/characters/1011106?language=fr
- http://localhost:8080/characters/1009693?language=fr

