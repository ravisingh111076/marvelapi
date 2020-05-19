
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
4. API Key acquisition from rapidapi.com , (you can skip this by default my key will be used)
   please visit https://rapidapi.com/ for your key.See in the last section for quick rapidapi steps
5. Environment vars: add ```PRIVATE_KEY```,```PUBLIC_KEY```,```TRANS_API_KEY```
   by default my key will be used for TRANS_API_KEY,
   set ```REDIS_PORT``` - if you are running redis on other than 6379.
7. compile:  ```mvn clean install``` from root directory
8. Export env variables
   - export PUBLIC_KEY=yourMarvelkey
   - export PRIVATE_KEY=yourMarvelPrivateKey
   - export TRANS_API_KEY=KEY_FROM_RAPID_API (optional for test run you can use my key)
   OR Follow appropriate step based on Operating system.
9. start application from command line ```java -jar target/marvelService-1.0.jar```

### Test Data
example - characterId = 1011106,1009693
## Get All marvel character ids.
- http://localhost:8080/characters
- Give it a try.
    - http://localhost:8080/characters/1011106?language=fr
    - http://localhost:8080/characters/1009693?language=fr

### Step to create TRANS_API_KEY
- Open https://rapidapi.com/
- After sign up go to API Marketplace , search for translator , 
select Microsoft Text Translation(https://rapidapi.com/microsoft-azure-admin/api/microsoft-text-translation)
subscribe it. 
you can see X-RapidAPI-Key after subscription is done.