FROM adoptopenjdk/openjdk11:latest
RUN mkdir -p /app/questionpro/groceryapp
RUN mkdir -p /app/questionpro/groceryapp/config

COPY target/groceryapp-0.0.1-SNAPSHOT.jar  /app/questionpro/groceryapp
ADD ./src/main/resources /app/questionpro/groceryapp/config

WORKDIR /app/questionpro/groceryapp

# ENTRYPOINT command to execute the jar
CMD ["java","-Dspring.config.location=/app/questionpro/groceryapp/config/","-jar","/app/questionpro/groceryapp/groceryapp-0.0.1-SNAPSHOT.jar"]