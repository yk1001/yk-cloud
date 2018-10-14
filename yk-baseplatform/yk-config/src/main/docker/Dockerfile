FROM registry.cn-hangzhou.aliyuncs.com/yk-cloud/yk-java8:8-jdk-alpine
ADD yk-config*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]