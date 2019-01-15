FROM java:8
ADD /build/libs/captain-0.0.1-SNAPSHOT.jar captain-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "captain-0.0.1-SNAPSHOT.jar"]
