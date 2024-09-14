# Tour Reservation Sample Application (Spring Boot)


* Java 21+ is required
* Docker is required for testing

```
./mvnw clean package
```

```
docker compose up -d
java -jar ./target/tourreservation-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase --spring.datasource.username=myuser --spring.datasource.password=secret
```

Go to http://localhost:8080

Login user: `00000001`:`password`