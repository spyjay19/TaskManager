FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew clean bootJar -x test

RUN JAR=$(ls build/libs/*.jar | head -n 1) && cp $JAR app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]