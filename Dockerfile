# 使用 OpenJDK 8 作为基础镜像
FROM openjdk:8-jdk

# 复制构建好的 JAR 文件到容器中
COPY ProgrammingPracticePlatform.jar /app/ProgrammingPracticePlatform.jar

# 暴露容器的端口
EXPOSE 8080

# 启动应用程序
ENTRYPOINT ["java", "-jar", "/app/ProgrammingPracticePlatform.jar"]
