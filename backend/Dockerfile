FROM inovus/openjdk:14-jdk
RUN apt update
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-Dsun.jnu.encoding=UTF-8","-jar","app.jar"]
EXPOSE 8080