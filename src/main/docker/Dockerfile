FROM netflixoss/java:8
VOLUME /tmp
ADD auth-server-*.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=docker"]
EXPOSE 5021