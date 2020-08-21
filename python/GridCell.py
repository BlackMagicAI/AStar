# Program
# Filename:     GridCell.py
 
# Title:		GridCell class
# Created on: 	July 29, 2020
 
# Author:       Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/
 
#  Target
#  Compilers:	python 3
 
#  Description:	Class representing a GridMap gridcell

class CellConstants():
    
    #Grid cell path type <code>PATH</code>
    PATH = 0.0

    #Grid cell strength <code>EASY</code>
    EASY = 0.3
    
    #Grid cell strength <code>NORMAL</code>
    NORMAL  = 1.0

    #Grid cell strength <code>TOUGH</code>
    TOUGH  = 5.0
    
    #Grid cell strength <code>VERY_TOUGH</code>
    VERY_TOUGH  = 10.0

    #Grid cell strength <code>BLOCK</code>
    BLOCK  = float('inf')

    # Constant indicating that a gridcell is a normal
    # gridcell <code>NORMAL_CELL</code>
    NORMAL_CELL = 0

    # Constant indicating that a gridcell is a starting
    # gridcell type <code>START_CELL</code>
    START_CELL = 1

    # Constant indicating that a gridcell is a finish
    # or target gridcell type<code>FINISH_CELL</code>
    FINISH_CELL = 2

class GridCell:
    'GridCell class'
    def __init__(self, position):
        self.position = position
        self.cost = 1.0
        self.isStart = False
        self.isFinish = False

    # needed to use this object in a heapq (Priority queue)
    def __lt__(self, other):
        return self.cost < other.cost

    # Is this cell an obstacle block?    
    # @return - true if this gridcell is a impossible obstacle, false otherwise.
    def isTotalBlock(self):
    	if self.cost == CellConstants.BLOCK:
    		return True
    	else:
    		return False

    # display this class as readable string in print methods.
    def __str__(self):
        return "(" + str(self.position.x) + "," + str(self.position.y) + ")"