FROM livingobjects/jre8
VOLUME /tmp
ADD oldguy-log-server-0.0.1-SNAPSHOT.jar app.jar
RUN ["/bin/bash","-c","touch /app.jar"]
EXPOSE 9014
ENTRYPOINT ["java","-jar","/app.jar"]