FROM eclipse-temurin:21
WORKDIR /app

COPY target/quarkus-app /app/quarkus-app

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx64m", "-Xms64m", "-jar"]
CMD ["quarkus-app/quarkus-run.jar"]
