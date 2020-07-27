import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import com.bmai.astar.*;

/**
 * Driver test class for the AStar class in the com.bmai.astar package.
 * To compile run > javac AStarDriver.java
 * To run > java AStarDriver
 * Ref
 * Generate ascii maps from this Web site: https://www.dcode.fr/maze-generator
 */
public class AStarDriver {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        System.out.println("1. Manually Create Grid\n" +
        "2. Load from file");
        int option = scan.nextInt();

        // Create grid and display in ascii charactors
        Map gridMap = null; //new Map(cols, rows);// grid cell map object

        switch (option) {

        case 1:// manual create grid

            // Get user input for grid size
            System.out.println("Enter number of Grid Map Columns");
            int cols = scan.nextInt();
            // long cols = 0;
            System.out.println("Enter number of Grid Map Rows");
            int rows = scan.nextInt();
            // long rows = 0;

            System.out.println("You entered " + cols + " cols and " + rows + " rows");
            gridMap = manualGrid(rows, cols);
            break;

        case 2:// load from file

            System.out.println("Enter file name or press enter to accept the default (maze.txt)");
            scan.nextLine();//consume any junk chars
            String fileName = scan.nextLine();
            if (fileName.isEmpty()) {
                fileName = "maze.txt";
            }
            gridMap = gridFromFile(fileName, gridMap);
            break;
        default:
            // TODO
            break;
        }

        displayGrid(gridMap);

        boolean repeat = true;
        while (repeat) {
            // get user input to add obstacle to grid
            System.out.println("Cell row");
            int row = scan.nextInt();

            System.out.println("Cell col");
            int col = scan.nextInt();

            System.out.println("Type of cell:\n" + "1. Start Cell\n" + "2. Easy Cell\n" + "3. Normal Cell\n"
                    + "4. Tough Cell\n" + "5. Very Tough Cell\n" + "6. Block Cell\n" + "7. Finish Cell");

            int cellType = scan.nextInt();
            switch (cellType) {

            case 1:
                gridMap.setGridCell(row, col, Map.NORMAL, Map.START_CELL);
                break;

            case 2:
                gridMap.setGridCell(row, col, Map.EASY, Map.NORMAL_CELL);
                break;

            case 3:
                gridMap.setGridCell(row, col, Map.NORMAL, Map.NORMAL_CELL);
                break;

            case 4:
                gridMap.setGridCell(row, col, Map.TOUGH, Map.NORMAL_CELL);
                break;

            case 5:
                gridMap.setGridCell(row, col, Map.VERY_TOUGH, Map.NORMAL_CELL);
                break;

            case 6:
                gridMap.setGridCell(row, col, Map.BLOCK, Map.NORMAL_CELL);
                break;

            case 7:
                gridMap.setGridCell(row, col, Map.NORMAL, Map.FINISH_CELL);
                break;

            default:
                break;
            }
            displayGrid(gridMap);

            System.out.println("Add another cell? y or n");
            String input = scan.next();
            if (input.equalsIgnoreCase("n")) {
                repeat = false;
            }
        }//end while loop

        //Calculate A* path
        gridMap = calcAStar(gridMap);
            
        //plot path on display grid
        displayGrid(gridMap);
    }

    /**
     * Calculate AStar* path planning algorithm.
     * @param gridMap
     * @return Map containing path waypoints.
     */
    private static Map calcAStar(Map gridMap) {

        AStar aStar = new AStar();//shortest path algorithm class
        Object[] pathWaypoints = aStar.findPath(gridMap);

        if(pathWaypoints == null ){//no path found        		
            System.out.println("NO PATH FOUND!");	 	        				
        }
        else{//path found
            System.out.println("PATH FOUND!");

            /*
             * Plot path planner waypoints in grid
             */
            int waypointXCoordinate = 0;
            int waypointYCoordinate = 0;
            for(Object gridCell: pathWaypoints){
                if (((GridCell) gridCell).position.y > 0) {
                    waypointXCoordinate = (int) (((GridCell) gridCell).position.x);
                    waypointYCoordinate = (int) (((GridCell) gridCell).position.y);
                    /* Ignore points at y = 0 coordinate for more accurate centroid */
                    gridMap.setGridCell(waypointXCoordinate, waypointYCoordinate, Map.PATH, Map.NORMAL_CELL);
                } // end ignore y = 0 points
            }
        }
        return gridMap;
    }

    /**
     * Load A* grid from file.
     */
    private static Map gridFromFile(String fileName, Map gridMap) {

        long cols = 0;
        long rows = 0;
        // Read gridmap data from ascii text file
        //Calculate size of grid to make.
        Path path = Paths.get(fileName);
        try {
            rows = Files.lines(path).count();
            long fileSize = Files.size(path);
            cols = (long) Math.ceil((((double) fileSize / rows) / 1.866));
            gridMap = new Map((int) rows, (int) cols);// new Map((int)cols, (int)rows);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        FileReader io;
        try {
            io = new FileReader(fileName);
            int x = 0;
            int y = 0;

            while (io.ready()) {
                int val = io.read();

                if (val == '\n') {// start new row
                    y++;
                    x = 0;
                    if (y > rows - 1) {
                        y--;
                    }
                } else {
                    if (val == 32) {
                        gridMap.setGridCell(y, x, Map.NORMAL, Map.NORMAL_CELL);
                    } else {
                        gridMap.setGridCell(y, x, Map.BLOCK, Map.NORMAL_CELL);
                    }
                    x++;
                    if (x > cols - 1) {
                        x--;
                    }
                }
            } // end file read loop

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gridMap;
    }

    /**
     * Manually create A* grid.
     */
    private static Map manualGrid(int rows, int cols) {
        return new Map(rows, cols);// grid cell map object
    }

    /**
     * Displays gridMap as an ascii command line graphic
     * 
     * symbols unicode values 0x25A0 solid box 0x2593 dark shade 0x2592 medium shade
     * 0x2591 light shade 0x26AA medium white circle 0x26AB medium black circle
     * 0x2588 full black block
     * 
     * @param gridMap
     */
    private static void displayGrid(Map gridMap) {

        GridCell[][] grid = gridMap.gridCellMap;

        //Column number formating code
        int w = String.valueOf(grid.length).length(); //max number of digits for row number. Used for formatting row numbers.
        System.out.printf("%" + w + "s", "");//
        for(int col = 0; col < grid[0].length; col++){
            System.out.printf("%d", col%10);//
        }
        System.out.println();// newline

        for (int row = 0; row < grid.length; row++) {
            System.out.printf("%" + w + "d", row);//grid row number
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].isStart) {
                    System.out.printf("%c", 0x26AA);// medium white circle 
                }else if (grid[row][col].isFinish) {
                    System.out.printf("%c", 0x26AB);// medium black circle 
                }else if (grid[row][col].cost == Map.NORMAL) {
                    System.out.printf("%c", 0x2591);// light shade
                } else if (grid[row][col].cost == Map.BLOCK) {
                    System.out.printf("%c", 0x2588);// solid box
                } else if (grid[row][col].cost == Map.VERY_TOUGH) {
                    System.out.printf("%c", 0x2592);// medium shade
                } else if (grid[row][col].cost == Map.TOUGH) {
                    System.out.printf("%c", 0x2593);// dark shade
                } else if (grid[row][col].cost == Map.EASY) {
                    System.out.printf("%c", ' ');// solid box
                } else if (grid[row][col].cost == Map.PATH) {
                    System.out.printf("%c", '*');// solid box
                }
            }
            System.out.println();// newline
        }
    }
}