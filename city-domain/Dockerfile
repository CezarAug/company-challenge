FROM openjdk:alpine
ARG version=0.0.1-SNAPSHOT
COPY target/city-domain-${version}.jar /city-domain.jar
ENV JAVA_OPTS ""
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /city-domain.jar