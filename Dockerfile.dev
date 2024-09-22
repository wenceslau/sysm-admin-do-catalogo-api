FROM eclipse-temurin:17.0.5_8-jre-alpine

COPY infrastructure/build/*.jar /opt/app/application.jar

# Create a group called spring and a user called spring
# and add the user to the group
RUN addgroup -S spring && adduser -S spring -G spring

# Change the owner of the application.jar file to spring
# To avoid use root user
USER spring:spring

CMD java -jar /opt/app/application.jar