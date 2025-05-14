FROM openjdk:21-jdk-slim

RUN apt-get update && \
    apt-get install -y netcat-openbsd ffmpeg && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY target/audio-to-text-converter-1.0.0-SNAPSHOT.jar app.jar
COPY vosk-model-small-en-us-0.15 vosk-model-small-en-us-0.15
EXPOSE 8080
ENV LOGGING_FILE_NAME=app.log
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]
