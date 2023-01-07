from GridMap import GridMap
from GridCell import GridCell, CellConstants
from AStar import AStar

# Displays gridMap as an ascii command line graphic
#
# symbols unicode values 0x25A0 solid box 0x2593 dark shade 0x2592 medium shade
# 0x2591 light shade 0x26AA medium white circle 0x26AB medium black circle
# 0x2588 full black block
#
# @param gridMap
def displayGrid(gridMap):

    # Column number formating code
    numCols = len(gridMap.gridCellMap[0])
    # Print column number header
    print(" ", end="")
    for col in range(numCols):
        print(col%10, end="") #grid column number
    print()

    for idx, row in enumerate(gridMap.gridCellMap):
        print(idx%10, end="") #grid row number
        for cell in row:
            if cell.isStart:
                print(chr(0x26AA), end="")
            elif cell.isFinish:
                print(chr(0x26AB), end="")
            elif cell.cost == CellConstants.NORMAL:
                print(' ', end="")
            elif cell.cost == CellConstants.BLOCK:
                print(chr(0x2588), end="")
            elif cell.cost == CellConstants.VERY_TOUGH:
                print(chr(0x2592), end="")
            elif cell.cost == CellConstants.TOUGH:
                print(chr(0x2593), end="")
            elif cell.cost == CellConstants.EASY:
                print(chr(0x2591), end="")
            elif cell.cost == CellConstants.PATH:
                print('*', end="") 
        print("")

# Convert gridMap parameter into a text representation.
# @param gridMap
# @return text representation of gridMap.
def gridToText(gridMap):

    # Column number formating code
    numCols = len(gridMap.gridCellMap[0])
    # Print column number header
    #print(" ", end="")
##    for col in range(numCols):
##        print(col%10, end="") #grid column number
##    print()
    data = ''
    for idx, row in enumerate(gridMap.gridCellMap):
##        print(idx%10, end="") #grid row number
        for cell in row:
            if cell.isStart:
                #print(chr(0x26AA), end="")
                data = data + chr(0x26AA)
            elif cell.isFinish:
                #print(chr(0x26AB), end="")
                data = data + chr(0x26AB)
            elif cell.cost == CellConstants.NORMAL:
                #print(' ', end="")
                data = data + ' '
            elif cell.cost == CellConstants.BLOCK:
                #print(chr(0x2588), end="")
                data = data + chr(0x2588)
            elif cell.cost == CellConstants.VERY_TOUGH:
                #print(chr(0x2592), end="")
                data = data + chr(0x2592)
            elif cell.cost == CellConstants.TOUGH:
                #print(chr(0x2593), end="")
                data = data + chr(0x2593)
            elif cell.cost == CellConstants.EASY:
                #print(chr(0x2591), end="")
                data = data + chr(0x2591)
            #elif cell.cost == CellConstants.PATH:
                #print('*', end="") 
##        print("")
        data = data + "\n"
    return data

# Load A* grid from file.
def gridFromFile(fileName):

    cols = 0
    rows = 0
    #Read gridmap data from ascii text file
    #Calculate size of grid to make.
    lines = open(fileName).readlines(  )
    for line in lines:
        rows += 1
        cols = len(line)
    
    print("Grid size " + str(cols) + " cols and " + str(rows) + " rows")

    #initialize empty gridmap
    gridMap = GridMap(int(cols), int(rows))

    # for line in lines:
    for idCol, line in enumerate(lines):
        for idRow, ch in enumerate(line):
            if ch == "\n":#ignore newline
                pass
            elif ord(ch) == 32:#open cell
                gridMap.setGridCell(idCol, idRow, CellConstants.NORMAL, CellConstants.NORMAL_CELL)
            else:#block cell
                gridMap.setGridCell(idCol, idRow, CellConstants.BLOCK, CellConstants.NORMAL_CELL)

    return gridMap

#  Calculate AStar* path planning algorithm.
#  @param gridMap
#  @return Map containing path waypoints.
def calcAStar(gridMap):
    aStar = AStar() # shortest path algorithm class
    pathWaypoints = aStar.findPath(gridMap)
    if not pathWaypoints == None:
        for gridCell in pathWaypoints:
            if not gridCell == None:
                gridMap.setGridCell(gridCell.position.x, gridCell.position.y, CellConstants.PATH, CellConstants.NORMAL_CELL)
    return pathWaypoints
