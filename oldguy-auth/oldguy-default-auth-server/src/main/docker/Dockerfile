FROM livingobjects/jre8
VOLUME /tmp
ADD oldguy-default-auth-server-0.0.1-SNAPSHOT.jar app.jar
RUN ["/bin/bash","-c","touch /app.jar"]
EXPOSE 9000
ENTRYPOINT ["java","-jar","/app.jar"]