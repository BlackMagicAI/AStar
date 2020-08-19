package com.bmai.astar;

import java.awt.Point;

/*
 * 
 * Program
 * Filename:	Map.java
 * 
 * Title:		Map Class (version 1.0)
 * Created on: 	Feb 1, 2005
 * 
 * Last Date
 * Modified:	August 15, 2005, 10:46pm
 *  
 * Author:		Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/
 * 
 * Target
 * Compilers:	Java - j2sdk 1.4.2
 *
 * Description:	Class that represents a map divided into a X x Y grid of gridCell objects.
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Map {
    
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
	public static final double PATH = 0;
	
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
    public  static double BLOCK  = 100;//Double.MAX_VALUE;
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
	public Map() {
		super();//create map based on specified size parameters as an 2D array of GridCell objects
        gridCellMap = new GridCell[mapWidth][mapHeight];
		initializeMap(); //set up map and initialize it with gridcells     
		
	}//
	
	/**
	 * Parameterized constructor that takes size of map grid as parameters.        
	 * @param mapWidth - Total width of gridMap
	 * @param mapHeight- Total height of gridMap
	 */
	public Map(int mapWidth, int mapHeight) {
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
          	 //for(int i = 0; i < 20; i++){
                //for (int j = 0; j < 20;j++){
               gridCellMap[i][j] = new GridCell();
               gridCellMap[i][j].position = new Point(i, j); //set point coordinates               
               gridCellMap[i][j].resetCell();
               //minCost = Math.Min(minCost, gridCellMap(i, j).cellCost)
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
	 * Finds surrounding gridcells that are adjacent to this one.
	 * @param g - GridCell object to find adjacent cells.
	 * @return - GridCell array of the adjacent cells found.
	 */
	public GridCell[] getAdjacent(GridCell g ){
    
    GridCell nextCell[] = new GridCell[]{null, null, null, null};

    Point p = g.position;
   
    if (p.y > 0){
        nextCell[0] = gridCellMap[p.x][p.y - 1];
        //System.out.println("debug:" + nextCell[0].position.x + ", " + nextCell[0].position.y);
    }//
    
    if (p.x < mapWidth-1){
        nextCell[1] = gridCellMap[p.x + 1][p.y]; 
        //System.out.println("debug:" + nextCell[1].position.x + ", " + nextCell[1].position.y);
    }//
    
    if (p.y < mapHeight-1) {       
        nextCell[2] = gridCellMap[p.x][p.y + 1];
        //System.out.println("debug:" + nextCell[2].position.x + ", " + nextCell[2].position.y);      
    }//
    if (p.x > 0) {        
        nextCell[3] = gridCellMap[p.x - 1] [p.y];
        //System.out.println("debug:" + nextCell[3].position.x + ", " + nextCell[3].position.y);
     }//
    return nextCell;
	}//

	/**
	  Finds surrounding gridcells that are adjacent to this one including diagonal cells.
	 * @param g - starting gridcell.
	 * @return - Gridcell array of adjacent cells.
	 */
	public GridCell[] getAdjacentDiagonal(GridCell g ){
	    
	    GridCell nextCell[] = new GridCell[]{null, null, null, null, null, null, null, null};

	    Point p = g.position;
	   
	    if (p.y > 0){
	        nextCell[0] = gridCellMap[p.x][p.y - 1];
	        //System.out.println("debug:" + nextCell[0].position.x + ", " + nextCell[0].position.y);
	    }//
	    if (p.y > 0 && p.x < mapWidth-1){
	        nextCell[1] = gridCellMap[p.x+1][p.y - 1];
	        //System.out.println("debug:" + nextCell[0].position.x + ", " + nextCell[0].position.y);
	    }//
	    
	    if (p.x < mapWidth-1){
	        nextCell[2] = gridCellMap[p.x + 1][p.y]; 
	        //System.out.println("debug:" + nextCell[1].position.x + ", " + nextCell[1].position.y);
	    }//
	    
	    if (p.x < mapWidth-1 && p.y < mapHeight-1){
	        nextCell[3] = gridCellMap[p.x + 1][p.y+1]; 
	        //System.out.println("debug:" + nextCell[1].position.x + ", " + nextCell[1].position.y);
	    }//
	    
	    if (p.y < mapHeight-1) {       
	        nextCell[4] = gridCellMap[p.x][p.y + 1];
	        //System.out.println("debug:" + nextCell[2].position.x + ", " + nextCell[2].position.y);      
	    }//
	    if (p.x > 0 && p.y < mapHeight-1){
	        nextCell[5] = gridCellMap[p.x - 1][p.y + 1]; 
	        //System.out.println("debug:" + nextCell[1].position.x + ", " + nextCell[1].position.y);
	    }//	    	 
	   
	    if (p.x > 0) {        
	        nextCell[6] = gridCellMap[p.x - 1] [p.y];
	        //System.out.println("debug:" + nextCell[3].position.x + ", " + nextCell[3].position.y);
	     }//
	    
	    if (p.x > 0 && p.y > 0) {        
	        nextCell[7] = gridCellMap[p.x - 1] [p.y - 1];
	        //System.out.println("debug:" + nextCell[3].position.x + ", " + nextCell[3].position.y);
	     }//
	    
	    return nextCell;
		}//
	
	  /**
	   * Finds lowest adjacent cell - used for tracing path found.
	 * @param g - Gridcell to analize.
	 * @return - Gridcell with the next lowest distance from start.
	 */
	public GridCell getLowestAdjacent(GridCell g  ){
      
	  	//GridCell nextCells[] = getAdjacent(g); //find surrounding adjacent
	  	GridCell nextCells[] = getAdjacentDiagonal(g); //find surrounding adjacent with diagonal distance allowed
	  	GridCell small   = nextCells[0];
	  	double dist  = Double.MAX_VALUE;
	  	double nextDist;

      for(int i = 0; i < nextCells.length;i++){
          if (!(nextCells[i] == null)) {
              nextDist = nextCells[i].getDistFromStart();
              if (nextDist < dist && nextDist >= 0) {
                  small = nextCells[i];
                  dist = nextCells[i].getDistFromStart();
              }//
          }//end null check if block
      }//end for loop
      return small;
	  }//
	  
	/**
	* Return the start grid cell in the map grid.
	 * @return - The grid cell designated as the start cell in the gridcellmap.
	 */
	public GridCell getStartCell() {
       //
       for(int i = 0; i < gridCellMap.length; i++){
           for(int j = 0; j < gridCellMap[i].length; j++){
               if (gridCellMap[i][j].isStart == true){
                   gridCellMap[i][j].distanceFromStart = 0;
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
	
	/**
	 * Set a gridcell to type of obstacle it represents and set cells that are dilationValue
	 * parameter cells adjacent to this to the same value.
	 ** @param xPosition - x grid coordinate of the cell to set.
	 * @param yPosition - y grid coordinate of the cell to set.
	 * @param gridCellCost - Cost value of the cell to be set.
	 * @param cellType - Type of cell.
	 * @param dilationValue - number gridcells distance to set dilation cells. 
	 */
	public void setGridCellWithDilation(int xPosition, int yPosition, double gridCellCost, int cellType, int dilationValue, double dilationCellCost){
	      //set a gridcell to type of obstacle		
		 if (yPosition - dilationValue > 0 ){		 	
		 	if(!gridCellMap[xPosition][yPosition - dilationValue].isTotalBlock())
	        gridCellMap[xPosition][yPosition - dilationValue].cost = dilationCellCost;
	        //System.out.println("debug:" + nextCell[0].position.x + ", " + nextCell[0].position.y);
	    }//
	    
	    if (xPosition + dilationValue < mapWidth-1 ){
	        if(!gridCellMap[xPosition + dilationValue][yPosition].isTotalBlock())
	    	gridCellMap[xPosition + dilationValue][yPosition].cost = dilationCellCost; 
	        //System.out.println("debug:" + nextCell[1].position.x + ", " + nextCell[1].position.y);
	    }//
	    
	    if (yPosition + dilationValue < mapHeight-1 ) {       
	        if(!gridCellMap[xPosition][yPosition + dilationValue].isTotalBlock())
	    	gridCellMap[xPosition][yPosition + dilationValue].cost = dilationCellCost;
	        //System.out.println("debug:" + nextCell[2].position.x + ", " + nextCell[2].position.y);      
	    }//
	    if (xPosition - dilationValue > 0 ) {        
	        if(!gridCellMap[xPosition - dilationValue][yPosition].isTotalBlock())
	    	gridCellMap[xPosition - dilationValue][yPosition].cost = dilationCellCost;
	        //System.out.println("debug:" + nextCell[3].position.x + ", " + nextCell[3].position.y);
	     }//
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
