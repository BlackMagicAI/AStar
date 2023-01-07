# importing  all the
# functions defined in astar_cloud_vision_api_client.py
from astar_cloud_vision_api_client import *
from utilities import *
from GridMap import GridMap
from GridCell import GridCell, CellConstants
import requests

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
        startCell = [row, col]
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
        finishCell = [row, col]
        gridMap.setGridCell(row, col, CellConstants.NORMAL, CellConstants.FINISH_CELL)

    displayGrid(gridMap)
    #Continue entering data/loop?
    choice = input("Add another cell? y or n: ")
    if choice == "n":
        repeat = False

print("Start Cell: (" + ",".join(map(str, startCell)) + ")")

print("Finish Cell: (" + ",".join(map(str, finishCell)) + ")")

payload= json.dumps({"body": gridToText(gridMap), "start": startCell, "finish":finishCell})

headers = {
  'x-api-key': '<INSERT_DEVELOPER_PORTAL_API_KEY_HERE>',
  'Content-Type': 'application/json'
}

## Call AStar cloud API
response = cloud_api_calcAStar(headers, payload)

## Process and display the response from the AStar cloud API call
res = json.loads(response.text) # convert json string to Python dict for parsing
for point in res["waypointsMap"]:
    gridMap.setGridCell(point["x"], point["y"], CellConstants.PATH, CellConstants.NORMAL_CELL)
    
displayGrid(gridMap)
