
# Marvel Client
Application provides rest interface to interact with Marvel Server.

## Get character by id
This is real time data from Marvel server no caching involved.

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
6. Environment vars: add ```PRIVATE_KEY```,```PUBLIC_KEY```,```TRANS_API_KEY```
   by default my key will be used for TRANS_API_KEY,
   please visit https://rapidapi.com/ for your key
   `REDIS_PORT``` - if you are running redis on other than 6379.
   
7.
8. compile:  ```mvn clean install``` from root directory
9. start application from command line ```java -jar target/marvelService-1.0.jar```

### Test Data
example - characterId = 1011106,1009693
## Get All marvel character ids.
- http://localhost:8080/characters
Give it a try.
- http://localhost:8080/characters/1011106?language=fr
- http://localhost:8080/characters/1009693?language=fr