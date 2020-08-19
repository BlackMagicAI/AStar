# Point class indicating grid position on the map.
class Point:
   def __init__(self, x=0, y=0):
      self.x = x
      self.y = y

   # display this class as readable string in print methods.
   def __str__(self):
        return '(' + str(self.x) + ',' + str(self.y) + ')'   