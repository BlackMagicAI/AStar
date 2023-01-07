from GridCell import GridCell, CellConstants
from GridMap import GridMap
from utilities import *
import json

def lambda_handler(event, context):
    txt_string = event.get("body","")
    startCell = event.get("start","")
    finishCell = event.get("finish","")
    cols = 0
    rows = 0
    #Read gridmap data from ascii text input
    lines = txt_string.splitlines()
        
    #Calculate size of grid to make.
    rows = len(lines)
    cols = len(lines[0])
    print(str(rows))
    print(str(cols))
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

    gridMap.setGridCell(startCell[0], startCell[1], CellConstants.NORMAL, CellConstants.START_CELL)
    gridMap.setGridCell(finishCell[0], finishCell[1], CellConstants.NORMAL, CellConstants.FINISH_CELL)
    # Calculate A* path
    waypointsMap = calcAStar(gridMap)

    result = {
        'statusCode': 200,
        'waypointsMap': [ob.position.__dict__ for ob in waypointsMap]
    }
    
    return json.dumps(result)
