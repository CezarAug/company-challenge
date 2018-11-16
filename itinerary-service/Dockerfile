FROM openjdk:alpine
ARG version=0.0.1-SNAPSHOT
COPY target/itinerary-service-${version}.jar /itinerary-service.jar
ENV JAVA_OPTS ""
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /itinerary-service.jar