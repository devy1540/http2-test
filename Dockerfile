FROM eclipse-temurin:11.0.16.1_1-jdk
VOLUME /tmp
ENV TZ=Asia/Seoul
#COPY ./target/*.jar app.jar
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]