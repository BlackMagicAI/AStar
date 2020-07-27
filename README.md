##  A* (AStar) Path Planning Algorithm

Java implementation of the A* (A Star) path planning algorithm. Includes example test driver command line program.

 - To compile, type the following command in command line and type enter to run:
```java
    javac AStarDriver.java
```
 - To run the AStarDrive program, type the following command in command line and type enter to run:
```java
    java AStarDriver
```
Follow the command prompts to create a grid manually or read from a text file generated using this [online ascii maze generator tool](https://www.dcode.fr/maze-generator).
### Core Code
The main class you need to add the A* algorithm to your Java programs is the AStar.java class and the associated Map.java and GridCell.java classes in the com.bmai.astar package.

### How to use the A* AStar.java class

 1. Create a Grid object passing in the number of rows and columns as parameters.
```java
Map gridMap = new  Map(rows, cols);// grid cell map object
```
 2. Set grid cell location (row and column index), cost (EASY, NORMAL, TOUGH, VERY_TOUGH, BLOCK) and type (NORMAL_CELL, START_CELL or FINISH_CELL).
```java
    gridMap.setGridCell(row, col, Map.NORMAL, Map.START_CELL);
```
 3. Create an AStar object.
  ```java
AStar  aStar  =  new  AStar();//shortest path algorithm object
```
 4.  Call the AStar object findPath(gridMap) method, passing in the grid object from the previous steps.
 ```java
Object[] pathWaypoints  =  aStar.findPath(gridMap);
```
 5. Process the waypoints array returned by the findPath method which contains the GridCell objects in the path solution.
```java
for(Object gridCell: pathWaypoints){             
	waypointXCoordinate = (int) (((GridCell) gridCell).position.x);
	waypointYCoordinate = (int) (((GridCell) gridCell).position.y);
	//TODO - something with the path point coordinates               
}
```

***Disclaimer**
*I know this code contains bad code but the main AStar classes were written in 2005 and will be optimized later.*

Code written by Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/

  

* Informaion about A* algorithm by: Patel, Amits. Amit's A* Pages. Retrieved on February 2005 from http://theory.stanford.edu/~amitp/GameProgramming.

> Written with [StackEdit](https://stackedit.io/).
