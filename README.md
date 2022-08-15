# Building URL Shortening project by using Java 8, Spring Boot and Redis.
###Created by Alireza Gholamzadeh Lahroodi
####AlirezaGholamzadehLahrudi@gmail.com
####https://www.linkedin.com/in/alireza-gholamzadeh-lahroodi/

This Product requires Redis server that should be installed. 
Install Docker For Windows/Linux

### Docker Commands
#### Start redis-stack-server (downloads image if not found)
To start Redis Stack server using the redis-stack-server image, run the following command in your terminal:
``
docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server:latest
``
You can that the Redis Stack server database to your ``RedisInsight`` desktop application.

###Ports

If you want to expose Redis Stack server or RedisInsight on a different port, update the left hand of portion of the ``-p`` argument. 
This command exposes Redis Stack server on port ``10001`` and RedisInsight on port ``13333`` :

``$ docker run -p 10001:6379 -p 13333:8001 redis/redis-stack:latest``

##### view all images
``
docker images
``
##### view all containers (running or not)
``
docker ps -a
``
#### Startup with Profile settings
Spring boot Gradle contains task bootRun which help us to compile and run spring boot application:

    Go to the root of the application where build.gradle is available and
    run this command : gradle bootRun

#### Dockerize project
##### Build jar, image, set default profile
> gradle build\
> java -jar .\build\libs\shortening-0.0.1-SNAPSHOT.jar

###### container with default property set in Dockerfile
#####
##### Push image to Docker hub
######Login to Docker hub locally
``docker login``
###### Upload image
``
docker tag <image id> <docker hub repository>/lahroodi.shorteningURL:latest
``
###### Download image
``
docker pull <docker hub repository>/lahroodi.shorteningURL
``
##### Run Container from docker hub image
``
docker run --name shortening-app-shorteningURL -p 8080:8080  -d <docker hub repository>/lahroodi.shorteningURL``
###View documentation
Use the following path to view the documentation produced by open-api and 
swagger use the following path, after the execution
####http://localhost:8080/documentation.html

#### The Server will run on http://localhost:8080 address
For producing shorten url, send POST requesting to http://localhost:8080/generateShortening 
with json body content :

```
{
  'originalUrl' : '<INSERT URL>'
}
```

For redirecting based on shorten URL, send POST requesting to http://localhost:8080/redirection
with json body content :

```
shortenUrl='<shorten URL>'
example : http://localhost:8080/redirection?shortenUrl=http://merced.es/icP4le
```
"# ShurteningUrl" 
