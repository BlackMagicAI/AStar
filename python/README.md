# Python
## A* (AStar) Path Planning Algorithm

  

Python implementation of the A* (A Star) path planning algorithm. Includes example test driver command line program.

- To run the AStarDrive program, type the following command in command line and type enter to run:

```python

python3 AStarDriver

```

Follow the command prompts to create a grid manually or read from a text file generated using this [online ascii maze generator tool](https://www.dcode.fr/maze-generator).

### Video
YouTube Tutorial Video is [here](https://youtu.be/xqHJxILaVrw) for Python.

### Core Code

The main class you need to add the A* algorithm to your Python programs is the AStar.py class and the associated GridMap.py, GridCell.py and Point.py classes in the python folder.

### How to use the A* AStar.py class

  

1. Create a Grid object passing in the number of rows and columns as parameters.

```python

gridMap = GridMap(int(cols), int(rows))

```
Optional if manually creating gridMap
```python
gridMap.initializeMap()
```

2. Set grid cell location (row and column index), cost (EASY, NORMAL, TOUGH, VERY_TOUGH, BLOCK) and type (NORMAL_CELL, START_CELL or FINISH_CELL).

```python

gridMap.setGridCell(row, col, CellConstants.NORMAL, CellConstants.START_CELL)

```

3. Create an AStar object.

```python

aStar = AStar() # shortest path algorithm class

```

4. Call the AStar object findPath(gridMap) method, passing in the gridMap object created in the previous steps.

```python

pathWaypoints = aStar.findPath(gridMap)

```

5. Process the waypoints array returned by the findPath method which contains the GridCell objects in the path solution.

```python

for gridCell in pathWaypoints:
	if not gridCell == None:
		#TODO - something with the path waypoint point coordinates

```

Code written by Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/

and the following references.

* Information about A* algorithm by: Patel, Amits. Amit's A* Pages. Retrieved on February 2005 from http://theory.stanford.edu/~amitp/GameProgramming.
* https://www.redblobgames.com/pathfinding/a-star/introduction.html

* https://en.wikipedia.org/wiki/A*_search_algorithm

* https://www.redblobgames.com/pathfinding/a-star/implementation.html#optimize-queue

* https://www.redblobgames.com/pathfinding/grids/graphs.html

> Written with [StackEdit](https://stackedit.io/).