import heapq

#Code copied from https://www.redblobgames.com/pathfinding/a-star/implementation.html#optimize-queue
class PriorityQueue:
    def __init__(self):
        self.elements = []
    
    def empty(self):
        return len(self.elements) == 0
    
    def put(self, item, priority):
        heapq.heappush(self.elements, (priority, item))
    
    def get(self):
        return heapq.heappop(self.elements)[1]

    def contains(self, item):
        return self.elements.__contains__(item)
    
    def length(self):
        return len(self.elements)