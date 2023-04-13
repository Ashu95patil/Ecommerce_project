FROM openjdk:8
EXPOSE 8484
ADD target/Electronic-store.jar Electronic-store.jar
ENTRYPOINT ["java","-jar","/Electronic-store.jar"]