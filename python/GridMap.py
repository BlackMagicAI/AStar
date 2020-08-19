# Program
# Filename:     GridMap.py

# Title:		Map Class (version 1.0)
# Created on: 	July 29, 2020

# Last Date
# Modified:	

# Author:	    Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/

# Target
# Compilers:	python 3

# Description:	Class that represents a map divided into a X x Y grid of gridCell objects.

from GridCell import GridCell, CellConstants
from Point import Point

class GridMap:

    def __init__(self, mapWidth, mapHeight):
        
        self.mapWidth = mapWidth
        self.mapHeight = mapHeight
        self.gridCellMap = [[GridCell(Point(col,row)) for col in range(self.mapWidth)] for row in range(self.mapHeight)]        

    # Set default value for grid map cells.
    def initializeMap(self):
        for idRow in range(self.mapHeight):
            for idCol in range(self.mapWidth):
                # TODO figure out why row & col are reversed
                self.setGridCell(idRow, idCol, CellConstants.NORMAL, CellConstants.NORMAL_CELL)                

	# Set a gridcell to type of obstacle it represents.
	# @param xPosition - x grid coordinate of the cell to set.
	# @param yPosition - y grid coordinate of the cell to set.
	# @param gridCellCost - Cost value of the cell to be set.
	# @param cellType - Type of cell.
    def setGridCell(self, xPosition, yPosition, gridCellCost, cellType):
        self.gridCellMap[xPosition][yPosition].cost = gridCellCost
        self.gridCellMap[xPosition][yPosition].position = Point(xPosition, yPosition)
        if cellType == CellConstants.START_CELL:
            # START CELL case
            self.gridCellMap[xPosition][yPosition].isStart = True
            self.gridCellMap[xPosition][yPosition].cost = CellConstants.NORMAL
        elif cellType == CellConstants.FINISH_CELL:
            # FINISH CELL case
            self.gridCellMap[xPosition][yPosition].isFinish = True
            self.gridCellMap[xPosition][yPosition].cost = CellConstants.NORMAL

    # Return a grid cell with the specified type: Start or finish cell in the map grid.
    def getCellOfType(self, typeOfCell):
        for rows in self.gridCellMap:
            for cell in rows:
                if cell.isStart and typeOfCell == CellConstants.START_CELL:
                    return cell
                elif cell.isFinish and typeOfCell == CellConstants.FINISH_CELL:
                    return cell
        return None

	# Finds surrounding 4-way gridcells that are adjacent to this one.
    # Ref: https://www.redblobgames.com/pathfinding/grids/graphs.html
	# @param g - GridCell object to find adjacent cells.
	# @return - GridCell array of the adjacent cells found.
    def getNeighbors(self, current):
        dirs = [[1, 0], [0, 1], [-1, 0], [0, -1]]
        result = []
        for dir in dirs:
            # 4-way neighbor search
            if 0 <= (current.position.x + dir[0]) < self.mapHeight and 0 <= (current.position.y + dir[1]) < self.mapWidth:
                node = self.gridCellMap[current.position.x + dir[0]][current.position.y + dir[1]]
                result.append(node)
        return result        