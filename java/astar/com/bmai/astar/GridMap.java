package com.bmai.astar;

import java.util.ArrayList;

/**
 * 
 * Program
 * Filename:	GridMap.java
 * 
 * Title:		GridMap Class (version 2.0)
 * Created on: 	August 21, 2020
 * 
 * Last Date
 * Modified:	
 *  
 * @author		Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/
 * 
 * Target
 * Compilers:	Java - j2sdk 1.4.2
 *
 * Description:	Class that represents a map divided into a X x Y grid of gridCell objects.
 */
public class GridMap {
    
	//constants
    /**
     * map x dimesion height <code>mapWidth</code>
     */
    public int mapWidth = 20;   
    /**
     * map y dimesion height <code>mapHeight</code>
     */
    public int mapHeight = 20;
	//cost constants
	public static double PATH = 0;
	
    /**
     * Grid cell strength <code>EASY</code>
     */
    public  static double EASY = 0.3;
    /**
     * Grid cell strength <code>NORMAL</code>
     */
    public  static double NORMAL  = 1.0;
    /**
     * Grid cell strength <code>TOUGH</code>
     */
    public  static double TOUGH  = 5.0;
    /**
     * Grid cell strength <code>VERY_TOUGH</code>
     */
    public  static double VERY_TOUGH  = 10.0;
    /**
     * Grid cell strength <code>BLOCK</code>
     */
    public  static double BLOCK  = Double.MAX_VALUE;
    /**
     *  Constant indicating that a gridcell is a normal
     *  gridcell <code>NORMAL_CELL</code>
     */
    public  static int NORMAL_CELL = 0;
    /**
     * Constant indicating that a gridcell is a starting
     * gridcell type <code>START_CELL</code>
     */
    public  static int START_CELL  = 1;
    /**
     * Constant indicating that a gridcell is a finish
     * or target gridcell type<code>FINISH_CELL</code>
     */
    public  static int FINISH_CELL  = 2;
    
    /**
     * 'this gridcells cost distance from startcell <code>distanceFromStart</code>
     */
    public double distanceFromStart;//
    
    public GridCell[][] gridCellMap;  //create map as an 2D array of GridCell objects
    //public double minCost = Double.MaxValue //min cell cost in map

	/**
	 * Default constructor.
	 */
	public GridMap() {
		super();//create map based on specified size parameters as an 2D array of GridCell objects
        gridCellMap = new GridCell[mapWidth][mapHeight];
	}//
	
	/**
	 * Parameterized constructor that takes size of map grid as parameters.        
	 * @param mapWidth - Total width of gridMap
	 * @param mapHeight- Total height of gridMap
	 */
	public GridMap(int mapWidth, int mapHeight) {
		super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;        
        //create map based on specified size parameters as an 2D array of GridCell objects
		gridCellMap = new GridCell[mapWidth][mapHeight];
		initializeMap(); //set up map and initialize it with gridcells     		
	}//

	 /**
	 * Initilize all grid cells in gridCellMap to default values. 
	 */
	public void initializeMap(){  
       for(int i = 0; i < gridCellMap.length; i++){
          for (int j = 0; j < gridCellMap[i].length;j++){          	
               gridCellMap[i][j] = new GridCell(new Point(i, j));
               gridCellMap[i][j].resetCell();
          }//end inner for loop
       }//end outer for loop
	}//
	
	/**
	 * Reset existing gridcells to initialized values.
	 */
	public void resetMap(){      
	       for(int i = 0; i < gridCellMap.length; i++){
	          for (int j = 0; j < gridCellMap.length;j++){	          	
	               gridCellMap[i][j].resetCell();	              
	          }//end inner for loop
	       }//end outer for loop
		   }//	

	/**
	 * 4-way search to get adjacent gridcells.
	 */		   
	public GridCell[] getNeighbors(GridCell current){

        Integer[][] dirs = {
			{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
			
        ArrayList<GridCell> result = new ArrayList<GridCell>();
        for(Integer[] dir: dirs){
			if (0 <= (current.position.x + dir[0]) && (current.position.x + dir[0]) < this.mapWidth
			&& (0 <= (current.position.y + dir[1]) && (current.position.y + dir[1]) < this.mapHeight)){
				GridCell node = this.gridCellMap[current.position.x + dir[0]][current.position.y + dir[1]];
				result.add(node);
			}
		}
		GridCell arr[] = new GridCell[result.size()]; 
        return result.toArray(arr);
	}
	  
	/**
	* Return the start grid cell in the map grid.
	* @return - The grid cell designated as the start cell in the gridcellmap.
	*/
	public GridCell getStartCell() {
       //
       for(int i = 0; i < gridCellMap.length; i++){
           for(int j = 0; j < gridCellMap[i].length; j++){
               if (gridCellMap[i][j].isStart == true){
                   return gridCellMap[i][j]; //return the start cell object                    
               }//End If
           }//end inner for loop
       }//end outer for loop
       return null;
	   }//
   
	/**
	* Return the target finish cell in the map grid.
	* @return - Gridcell that is the target finish cell.
	*/
	public GridCell getFinishCell(){
       //
       for (int i = 0; i < gridCellMap.length; i++){
           for(int j = 0; j < gridCellMap[i].length;j++){
               if(gridCellMap[i][j].isFinish == true) {
                   return gridCellMap[i][j]; //Return the finish target cell object                    
               }//End If
           }//end inner for loop
       }//end outer for loop
       return null;
	   }//
	
	/**
	 * Set a gridcell to type of obstacle it represents.
	 * @param xPosition - x grid coordinate of the cell to set.
	 * @param yPosition - y grid coordinate of the cell to set.
	 * @param gridCellCost - Cost value of the cell to be set.
	 * @param cellType - Type of cell.
	 */
	public void setGridCell(int xPosition, int yPosition, double gridCellCost, int cellType){
      //set a gridcell to type of obstacle
      gridCellMap[xPosition][yPosition].cost = gridCellCost;
      if (cellType == START_CELL){
          //START CELL case
          gridCellMap[xPosition][yPosition].isStart = true;
          gridCellMap[xPosition][yPosition].cost = NORMAL;}
      else if (cellType == FINISH_CELL){
          //FINISH CELL case
          gridCellMap[xPosition][yPosition].isFinish = true;
          gridCellMap[xPosition][yPosition].cost = NORMAL;
      }//End If
	}//

}//end class
