Backend – Spring Boot

Tech Stack

- Spring Boot 3.x
- Spring Data JPA
- Spring Cache (Redis + Bucket4j)
- PostgreSQL
- Gradle

Features

- Redis caching (`otp-cache`, `upcomingEvents`)
- Rate limiting with Bucket4j
- Event hosting and attendance APIs
- DTO mapping and pagination support

Setup Instructions

1. Configure `application.properties`:

spring.application.name=eventm
server.port=8082
server.servlet.context-path=/api

logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.boot.web.embedded.tomcat=DEBUG
logging.level.org.springframework.boot=TRACE

jwt.secret-key=cVY+h702RX8awGFIRc05YnBHEdxLjOKa7Kfau8SU9O0=
app.jwt.expiration-ms=86400000

spring.datasource.url=jdbc:postgresql://localhost:5432/eventm
spring.datasource.username=postgres
spring.datasource.password=12345
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.liquibase.enabled=false

spring.redis.host=localhost
spring.redis.port=6379

2. Start Redis:

docker run -p 6379:6379 redis


3. Start the backend:
./gradlew bootRun


Dependencies (Gradle)

    implementation 'com.bucket4j:bucket4j-core:8.9.0'
    implementation 'com.bucket4j:bucket4j-redis:8.9.0'
    implementation 'io.lettuce:lettuce-core:6.3.1.RELEASE'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    implementation 'org.springframework.boot:spring-boot-starter-cache'


    implementation 'io.jsonwebtoken:jjwt-api:0.12.6'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.6'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.6'

    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.h2database:h2'
    runtimeOnly 'org.postgresql:postgresql'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.6.3'


