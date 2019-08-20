FROM livingobjects/jre8
VOLUME /tmp
ADD oldguy-base-0.0.1-SNAPSHOT.jar app.jar
RUN ["/bin/bash","-c","touch /app.jar"]
EXPOSE 9013
ENTRYPOINT ["java","-jar","/app.jar"]