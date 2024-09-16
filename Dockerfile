FROM maven:3.8-amazoncorretto-17
WORKDIR /tictactoe
COPY . .
RUN mvn clean jar:jar
CMD mvn javafx:run