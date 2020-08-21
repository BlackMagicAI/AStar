
#  Filename:	AStar.py
 
#  Title:		AStar Class (version 1.0)
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

# Description:	Class that represents implements the A Start shortest path 
# 				algorithm for a grid map divided into a X x Y grid of gridcells.
from GridCell import GridCell, CellConstants
import math
from PriorityQueue import PriorityQueue

class AStar:

    def __init__(self):
        pass

    # Calculates the weighted Manhattan distance from a to b.
    # @param a - Start point.
    # @param b - Finish point.
    # @param low - Scaling factor.
    # @return - return Manhatten distance.
    def cbDist(self, a, b, low):
	    return low * (math.fabs(a.x - b.x) + math.fabs(a.y - b.y) - 1)

    # Assemble A* breadcrumbs in to waypoints list
    def reconstructPath(self, cameFrom, current):
        #print("len:" + str(len(cameFrom)) + "," + str(current.position.x) + ":" + str(current.position.y))
        total_path = [current]
        while current in cameFrom.keys():
            #print("+")
            current = cameFrom[current]
            if current is not None:
                total_path.insert(0, current)
                #print(current.position)
        return total_path

# Finds the shortest path from the start location to finish location
# using the A* algorithm and returns the waypoints of the path found
# as an array of objects.
# @param gridMap - Gridmap to perform the A star search on to find the shortest path.
# @return - Gridcell object list of waypoints of the shortest path found by the A start algorithm.
    def findPath(self, gridMap):
        print("A*.findpath()")
        
        startCell = gridMap.getCellOfType(CellConstants.START_CELL)
        goal = gridMap.getCellOfType(CellConstants.FINISH_CELL)

        openSet = PriorityQueue()
        openSet.put(startCell, 0)

        gScore = {} #cost so far
        gScore[startCell] = 0.0
        #
        fScore = {}
        fScore[startCell] = self.cbDist(startCell.position, goal.position, 1.0) #calculate huristic (h()) and add to g() to get f()

        cameFrom = {}
        cameFrom[startCell] = None

        while not openSet.empty():
            current = openSet.get()

            if current == goal:
                print("Goal reached")
                waypoints = self.reconstructPath(cameFrom, current)
                return waypoints
            
            #Get neighboring cells
            t = gridMap.getNeighbors(current)
            for next in t:

                tentative_gScore = gScore[current] + next.cost
                if next not in gScore or tentative_gScore < gScore.setdefault(next, float('inf')):                    
                   # print(str(tentative_gScore) + "," + str(gScore.setdefault(next, float('inf'))))
                    #print(next.position)
                    gScore[next] = tentative_gScore
                    fScore[next] = gScore[next] + self.cbDist(next.position, goal.position, 1.0) 
                    openSet.put(next, fScore[next])
                    cameFrom[next] = current
                    
        print("Path not Found")
        return None
