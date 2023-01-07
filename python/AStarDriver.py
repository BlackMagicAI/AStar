from GridCell import GridCell, CellConstants
from GridMap import GridMap
from utilities import *

#  Filename:	AStarDriver.py
 
#  Title:		AStarDriver Class (version 1.0)
#  Created on: 	July 29, 2020
 
#  Last Date
#  Modified:	
 
#  Author:		Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/
#   			Informaion about A* algorithm by: Patel, Amits. Amit's A* Pages. Retrieved on February 2005 from
#   			http://theory.stanford.edu/~amitp/GameProgramming.
# Ref:
#               https://www.redblobgames.com/pathfinding/grids/graphs.html
#               https://www.redblobgames.com/pathfinding/a-star/introduction.html
#               https://en.wikipedia.org/wiki/A*_search_algorithm
# Target
# Compilers:	python3

# Description:	Driver test class command line user interface for the AStar
#               path planning algorithm.
#
#

option = int(input("1. Manually Create Grid\n" +
"2. Load from file\n"))

if option == 1:
    # Get user input for grid size
    cols = input("Enter number of Grid Map Columns: ")
    rows = input("Enter number of Grid Map Rows: ")

    print("You entered " + cols + " cols and " + rows + " rows")

    #initialize empty gridmap
    gridMap = GridMap(int(cols), int(rows))
    gridMap.initializeMap()

elif option == 2:
    fileName = input("Enter file name or press enter to accept the default (maze.txt): ")
    if not fileName:# use default if filename is empty
        fileName = "maze.txt"
    gridMap = gridFromFile(fileName)

displayGrid(gridMap)

repeat = True
while repeat:
    print("*** In Edit Cell Map Mode ***")

    #get user input to add obstacle to grid
    col = int(input("Cell col: "))

    row = int(input("Cell row: "))

    cellType = int(input("Type of cell:\n" + "1. Start Cell\n" + "2. Easy Cell\n" + "3. Normal Cell\n"
            + "4. Tough Cell\n" + "5. Very Tough Cell\n" + "6. Block Cell\n" + "7. Finish Cell" + "\n: "))

    print("\n")

    if cellType == 1:
        gridMap.setGridCell(row, col, CellConstants.NORMAL, CellConstants.START_CELL)
    elif cellType == 2:
        gridMap.setGridCell(row, col, CellConstants.EASY, CellConstants.NORMAL_CELL)
    elif cellType == 3:
        gridMap.setGridCell(row, col, CellConstants.NORMAL, CellConstants.NORMAL_CELL)
    elif cellType == 4:
        gridMap.setGridCell(row, col, CellConstants.TOUGH, CellConstants.NORMAL_CELL)
    elif cellType == 5:
        gridMap.setGridCell(row, col, CellConstants.VERY_TOUGH, CellConstants.NORMAL_CELL)
    elif cellType == 6:
        gridMap.setGridCell(row, col, CellConstants.BLOCK, CellConstants.NORMAL_CELL)
    elif cellType == 7:
        gridMap.setGridCell(row, col, CellConstants.NORMAL, CellConstants.FINISH_CELL)

    displayGrid(gridMap)
    #Continue entering data/loop?
    choice = input("Add another cell? y or n: ")
    if choice == "n":
        repeat = False

# Calculate A* path
calcAStar(gridMap)
displayGrid(gridMap)
