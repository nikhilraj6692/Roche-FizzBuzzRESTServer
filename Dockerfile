FROM openjdk:18
WORKDIR /app
COPY ./target/fizz-buzz-0.0.1-SNAPSHOT.jar /app
EXPOSE 8085
CMD ["java", "-jar", "fizz-buzz-0.0.1-SNAPSHOT.jar"]