FROM openjdk:17-jdk-slim
WORKDIR /irumorder
# JAR 파일 복사
COPY build/libs/IrumOrder-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]