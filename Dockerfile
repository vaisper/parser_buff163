# builder image
FROM eclipse-temurin:17-jdk-jammy as builder
RUN mkdir /build
COPY . /build/
WORKDIR /build/
RUN ["ls", "-l", "."]
RUN ["chmod", "+x", "mvnw"]
RUN ["./mvnw", "dependency:resolve"]
RUN ["./mvnw", "clean"]
RUN ["./mvnw", "install"]

# generate clean, final image for end users
FROM eclipse-temurin:17-jdk-jammy
COPY --from=builder /build/target/parser.jar .

# executable
ENTRYPOINT [ "java", "-jar", "parser.jar" ]