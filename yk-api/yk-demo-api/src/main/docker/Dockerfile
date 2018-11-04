FROM registry.cn-hangzhou.aliyuncs.com/yk-cloud/yk-java8:8-jdk-alpine
ADD yk-demo-api*.jar app.jar
VOLUME ["/yk"]
ENTRYPOINT ["java","-jar","/app.jar"]