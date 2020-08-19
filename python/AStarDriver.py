#os.system().path.append(".")
from GridCell import GridCell, CellConstants
from GridMap import GridMap
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
        for point in pathWaypoints:
            if not point == None:
                gridMap.setGridCell(point.position.x, point.position.y, CellConstants.PATH, CellConstants.NORMAL_CELL)

option = int(input("1. Manually Create Grid\n" +
"2. Load from file\n"))

#gridMap = None

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
    print("*** Edit Cell Map ***")

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