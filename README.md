
# Marvel Client
Application provides rest interface to interact with Marvel Server.
### Test Data
example - characterId = 1011106,1009693
## Get All marvel character ids.
- http://localhost:8080/characters

## Get character by id
This is real time data from Marvel server no caching involved.
Give it a try.
- http://localhost:8080/characters/1011106?language=fr
- http://localhost:8080/characters/1009693?language=fr

First time call to http://localhost:8080/characters will take few seconds
saves result into cache, consecutive calls will be delivered from cache.

### Swagger UI
- http://localhost:8080/swagger-ui.html

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
   visit - https://redis.io/topics/quickstart
5. maven setup: copy the settings.xml from the root to your local.m2 directory
6. Mandatory environment vars: add ```PRIVATE_KEY```,```PUBLIC_KEY```,```REDIS_PORT```
7. compile:  ```mvn clean install``` from root directory
8. start application ```java -jar target/marvelService-1.0.jar```