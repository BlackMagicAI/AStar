# Program
# Filename:    Point.py

# Title:		   Point Class (version 1.0)
# Created on: 	July 29, 2020

# Last Date
# Modified:	

# Author:	   Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/

# Target
# Compilers:	python 3

# Description:	Point class indicating grid position coordinaes on the gridmap.

# 
class Point:
   def __init__(self, x=0, y=0):
      self.x = x
      self.y = y

   # display this class as readable string in print methods.
   def __str__(self):
        return '(' + str(self.x) + ',' + str(self.y) + ')'   