This project is controlled by [Maven](https://maven.apache.org/).
#
Project  includes maven wrapper set to v3.6.3  
To build the project use `mvn clean install` or `mvnw clean install` from the root directory.  
#
To run the game use `mvn exec:java -pl client` from the root directory.
#
Default window parameters:
```
field.width=30
field.height=30
frog.scale=1
frog.number=3
snake.part.scale=1
snake.length=3
snake.movement.speed=50
logging.level.root=info
```
Parameters can be changed using arguments `mvn exec:java -pl client -Dsnake.length=100 -Dfrog.number=100`.
