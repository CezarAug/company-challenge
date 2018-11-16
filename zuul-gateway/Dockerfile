FROM openjdk:alpine
ARG version=0.0.1-SNAPSHOT
COPY target/zuul-gateway-${version}.jar /zuul-gateway.jar
ENV JAVA_OPTS ""
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /zuul-gateway.jar