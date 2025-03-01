###################
# Build container #
###################
FROM azul/zulu-openjdk-alpine:23 AS build

WORKDIR /build
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./

RUN ./gradlew build -x generateJooq || return 0

COPY . .
RUN ./gradlew build -x test -x spotlessJava -x generateJooq

#####################
# Runtime container #
#####################
FROM azul/zulu-openjdk-alpine:23-jre

WORKDIR /app

COPY --from=build /build/build/libs/boot-app.jar /app/

ENTRYPOINT ["java", "-jar", "/app/boot-app.jar"]
