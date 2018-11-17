# Code Challenge - Itinerary Application

##### Quick links: 
- [Postman collection](documentations/Company%20challenge.postman_collection) 
- [City contract](documentations/city-domain.yml)
- [Itinerary contract](documentations/itinerary-service.yml)
- [Pipeline proposal](documentations/pipeline.pptx)

![alt text](https://raw.githubusercontent.com/CezarAug/company-challenge/master/documentations/services-diagram.png)

There are two main services:
 * City: responsible for providing all routes data and communicate with mongoDb, after a search the results will be cached.
 * Itinerary: requests data from city and selects the best route.

All of them are registered with eureka service discovery and looks up for their configuration on config service during startup. 
Zuul will does the basic auth and proxy service. 

# Available endpoints

| Service | Endpoint | Methods |  
| ------ | ------ | -------- |
| City | /route | GET, POST, DELETE |
| City | /route/{cityName} | GET |
| Itinerary | /itinerary/{originCity}/{destinationCity} | GET |

For further details please check the swagger contracts
 - [City](documentations/city-domain.yml)
 - [Itinerary](documentations/itinerary-service.yml)


# How To Build

#### Clean/Install all applications
```sh
mvn clean install
```

#### Build docker images
```sh
docker build -t challenge/config-server:1.0.0 ./config-server
docker build -t challenge/city-domain:1.0.0 ./city-domain
docker build -t challenge/eureka-server:1.0.0 ./eureka-server
docker build -t challenge/itinerary-service:1.0.0 ./itinerary-service
docker build -t challenge/zuul-gateway:1.0.0 ./zuul-gateway
```

#### Compose docker images:

```sh
docker-compose up --no-start
```
And then startup the services, configuration must start first.

```sh
docker-compose up -d config-server
docker-compose up -d eureka-server
docker-compose up -d zuul-gateway
docker-compose up -d city-domain
docker-compose up -d itinerary-service
```

# How to run:

After all services running and registered to Eureka, everything will be set. 
But since there is no info in the database there isn't pretty much to do with it.
There is a suggestion of initial set of data to test it (Don't forget the authorization :)):

```json
- POST to http://localhost:8060/city-service/city (Suggested initial list available in postman.)

[
	{
		"city": "Sao Paulo",
		"destination": "Recife",
		"departureTime": "00:00",
		"arrivalTime": "06:00"
	},
	{
		"city": "Guarulhos",
		"destination": "Recife",
		"departureTime": "00:00",
		"arrivalTime": "07:00"
	},
	{
		"city": "Guarulhos",
		"destination": "Belo Horizonte",
		"departureTime": "00:00",
		"arrivalTime": "02:00"
	},
	{
		"city": "Belo Horizonte",
		"destination": "Recife",
		"departureTime": "00:00",
		"arrivalTime": "01:00"
	},
	{
		"city": "Recife",
		"destination": "Guarulhos",
		"departureTime": "04:00",
		"arrivalTime": "06:00"
	},
	{
		"city": "Recife",
		"destination": "Sao Paulo",
		"departureTime": "04:00",
		"arrivalTime": "06:00"
	}
]
```

#### All requests requires a Basic authorization:

- City service: the user must have an admin profile
- Itinerary: available for all authenticated users

##### Available Users:

 - Admin profile:
   - user: admin
   - password: admin

 - User profile:
    - user: user
    - password: password

There is also a [Postman](documentations/Company%20challenge.postman_collection) project available for all endpoints.

# Used libraries and Frameworks

### Spring libraries
 - **Web**: Provides out of the box functions and annotations for web communication and REST controller creations without having to do manual configurations.
 - **Actuator**: Adds endpoints for monitoring the state of the application.
 - **Cache**: Adds a basic Cache function managed by Spring, it is used to Cache the routes in the database for a fastest return when requesting it several times.

### Spring Cloud libraries
 - **Eureka**: Implements a service discovery, making it possible to have our gateway, declarative web clients and load balancing working.
 - **Zuul**: Gateway service responsible for receiving all requests, validate the user and then proxy it to the right service.
 - **Hystrix**: Cirtcuit breaker, in this project Hystrix is used to monitor some of the route functions and the itinerary search function. If something fails, it will take over and provide a pre defined set of data instead of just let the application fail.
 - **Hystrix Dashboard**: Each city and itinerary services comes up with a Hystrix dashboard to monitor failiures and the current state of the circuit breaker.
 - **Config**: Provides configurations for all services, in this project it provides eureka and endpoint info. It can be used for managing environment-based info without having to configure a file for each one of them on the project.
 - **Feign**: Declarative web clients, by using Feign the itinerary service can request data from the city service searching only by the application name. Also, when it does that the requests are load balanced between instances, with that Ribbon wasn't explcitally implemented but is working under the hood.

### Other libraries
 - **Springfox Swagger2 and SwaggerUi:** Automatic swagger and documentation generation. It was used to create the swagger files and also enables the /swagger-ui endpoint in both city and itineraries service. With that every new developer can just build and run the service to get all documentation without having to check any other source than the code itself.
 - **Lombok**: Faster java default functions development - Used for generating Getters, Setters and constructors.

# Future Improvements

 - The route filtering/API can follow a more reactive approach, making it possible to have a WebFlux sending back chunks containing new routes found
 - Use a Graph approach for searching routes with a Graph database
 - Provide a less dependent service from the configuration server and use Spring cloud bus for providing configurations
 - Add a Spring security auth provider to the Zuul gateway.
 - Switch spring default cache to redis.
