package com.bmai.astar;

import java.awt.Point;
import java.util.ArrayList;

/*
 * 
 * Program
 * Filename:	AStar.java
 * 
 * Title:		AStar Class (version 1.0)
 * Created on: 	Feb 2, 2005
 * 
 * Last Date
 * Modified:	May 9, 2005
 * 
 * Author:		Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/
 * 				Informaion about A* algorithm by: Patel, Amits. Amit's A* Pages. Retrieved on February 2005 from
 * 				http://theory.stanford.edu/~amitp/GameProgramming.
 * Target
 * Compilers:	Java - j2sdk 1.4.2
 *
 * Description:	Class that represents implements the A Start shortest path 
 * 				algorithm for a grid map divided into a X x Y grid of gridcells.
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AStar {

    //A* algorithm variables
    /**
     * Constant representing no path result of A star search <code>NO_PATH</code>
     */
    public static int NO_PATH = -1;
    /**
     * Constant representing not found result of A star search <code>NOT_FOUND</code>
     */
    public static int NOT_FOUND = 0;
    /**
     * Constant representing found finish result of A star search <code>FOUND_FINISH</code>
     */
    public static int FOUND_FINISH = 1;
    
    //private GridCell startCell;//starting gridcell object    
    private ArrayList<GridCell> openList = null;   //open nodes list
    private ArrayList<GridCell> closedList = null; //closed nodes list
	
	/**
	 * Default constructor.
	 */
	public AStar() {
		super();
		openList = new ArrayList<GridCell>();   //open nodes list
	    closedList = new ArrayList<GridCell>(); //closed nodes list
	}//
	
    /**
     * Finds the shortest path from the start location to finish location
     * using the A* algorithm and returns the waypoints of the path found
     * as an array of objects.
     * @param gridMap - Gridmap to perform the A star search on to find the 
     * 					shortest path.
     * @return - Gridcell object list of waypoints of the shortest path found by the A start algorithm.
     */
    //public  GridCell[] findPath(Map gridMap){
    public  Object[] findPath(Map gridMap){
    
    int state  = NOT_FOUND; //path found state variable
    //GridCell[] waypointsList = null;
    Object[] waypointsList = null;//path found gridcells object array
    openList.clear();//reset open list
    closedList.clear();//reset open list - start with empty arraylist
    openList.add(gridMap.getStartCell()); //add start cell to openlist

    while (state == NOT_FOUND){
        state = gridStep(gridMap);        
    }//End While
    if (state == FOUND_FINISH) {
        waypointsList = setPath(gridMap); //set find grid cells of path found
    }//End If
    
    return waypointsList;
}//
    
    /**
     * Evaluates grid cell for the shortest path. Returns the state of
     * a path found. 
     * @param gridMap - gridMap to perform A star search on.
     * @return - returns success of failure result of A Star algorithm.
     */
    public int gridStep(Map gridMap) {

		double min = Double.MAX_VALUE; //min score temp variable
		double score; //f() value
		boolean found = false;//finish cell found flag    
		GridCell finishCell = gridMap.getFinishCell(); //finish gridcell object
		//Map.GridCell finishCell  = gridMap2.getFinishCell; //finish gridcell object

		Point endPoint = finishCell.position;//get end cell coordinates
		
		ArrayList<GridCell> temp = (ArrayList<GridCell>) openList.clone(); // open nodes list
		GridCell best = ((GridCell)temp.get(temp.size()-1));
		GridCell now = null;
		
		for (int i = 0; i < temp.size(); i++) {//loop through OPEN list        
			
			now = ((GridCell) temp.get(i));//set first element as node_current   
			//System.out.println("debug1: " + now.position.x + ", " + now.position.y);
			if (closedList.contains(now) == false) {//process if not in CLOSED list
				
				score = now.distanceFromStart; //get g() = cost so far           
				score += cbDist(now.position, endPoint, 1.0);//calculate huristic (h()) and add to g() to get f()
				//use if diagonal movement is allowed calculate huristic (h()) and add to g() to get f()
				//score += cbDistDiagonal(now.position, endPoint, 1.0);
				//use if diagonal movement cost is different from straight movement cost
				//is allowed calculate huristic (h()) and add to g() to get f()
				//score += cbDistDiagonal2(now.position, endPoint, 1.0, .6);

				if (score < min) {//find min f() cell from OPEN list
					min = score;
					best = now;
				}//end min score if block            
			}//End If 'end CLOSED list check if block
		}//end for loop
		now = best; //select best f score as the node_current 
		
		openList.remove(now); //remove from OPEN list        
		closedList.add(now); //Add cell to CLOSED list
		
		GridCell nextCells[] = gridMap.getAdjacent(now);//get adjacent gridcells as successors
		//GridCell nextCells[] = gridMap.getAdjacentDiagonal(now);//get adjacent including diagonal gridcells as successors

		//check adjacent cell to determine which one to add to OPEN list (= edge Vector)
		for (int i = 0; i < nextCells.length; i++) {
			
			//if (!nextCells[i].equals(null)) {//do not process null cells
			if (nextCells[i] != null) {//do not process null cells
				if (nextCells[i].equals(finishCell)) {
					found = true;					
				}//End finish cell found If

				if (nextCells[i].isTotalBlock() == false) {//if not an impossible obstacle

					nextCells[i].addToPathFromStart(now.getDistFromStart());

					//do not process cell already visited (on CLOSED list = done)
					//or (the parent of this cell?) a cell that is already in the OPEN list = edge
					// Otherwise add this adjacent cell to the OPEN list (= edge Vector)               
					if ((openList.contains(nextCells[i]) == false) && (closedList.contains(nextCells[i]) == false)){
						openList.add(nextCells[i]);	
						//System.out.println("fd: " + nextCells[i].position.x + ", " + nextCells[i].position.y);
					}//End If
				}//end grid obstacle if block
			}//'end nextCells if block
			if (found) {
				return FOUND_FINISH;
			}//if End point then path complete path found
		}//end for loop    
		if (openList.size() == 0) {
			return NO_PATH;
		}//End If
		
		openList.trimToSize();
		closedList.trimToSize();
		return NOT_FOUND;
	}//

    /**
     * Calculates the weighted Manhattan distance from a to b.
     * @param a - Start point.
     * @param b - Finish point.
     * @param low - Scaling factor.
     * @return - return Manhatten distance.
     */
    public double cbDist(Point a, Point b, double low) {

		return low * (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) - 1);
		//return low * (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) );
	}//
    
    /**
     * Calculates the waighted diagonal distance heuristic from a to b.
     * @param a - Start point.
     * @param b - Finish point.
     * @param low - Scaling factor.
     * @return - Diagonal distance heuristic.
     */
    public double cbDistDiagonal(Point a, Point b, double low) {

		return low * Math.max(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
		//return low * (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) );
	}//
    
    /**
     * Calculates the waighted diagonal distance heuristic from a to b if diagonal cost
     * is different from straight cost.
     * @param a -Start point.
     * @param b - Finish point.
     * @param straightLow - Straight movement cost.
     * @param diagonalLow - diagonal movement cost.
     * @return
     */
    public double cbDistDiagonal2(Point a, Point b, double straightLow, double diagonalLow) {
    	
    	//double hStr = straightLow * (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) - 1);
    	double hStr = straightLow * (Math.abs(a.x - b.x) + Math.abs(a.y - b.y));
    	double hDiag = diagonalLow * Math.min(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
    	
		return  hDiag +  hStr - 2*hDiag;
		//return low * (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) );
	}//
    
    /**
     * Get point coordinates of shortest path solution found.
     * @param gridMap - Completed Gridmap with distance path information.
     * @return - return array of gridcell that are part of the shortest path found.
     */
    //public GridCell[] setPath(Map gridMap){ 
    public Object[] setPath(Map gridMap){  

    boolean finished = false;
    GridCell nextCell = null;
    GridCell now  = gridMap.getFinishCell();//backtrack from this finish cell
    GridCell stopCell = gridMap.getStartCell();//set start cell as target cell
    ArrayList<GridCell> waypoints = new ArrayList<GridCell>();//stored path waypoints in this arraylist

    waypoints.add(now);// //add finish cell as first point in waypoints array list
    //trace path until startcell is reached
    while (!finished){
        nextCell = gridMap.getLowestAdjacent(now);//find adjacent cell with the smallest distance from start        
        now = nextCell;
        now.partOfPath = true;
        waypoints.add(now);//add remaining cells as waypoints in arraylist
        if (now.equals(stopCell)){
            finished = true;
        }//End If
    }//End While

    waypoints.trimToSize();
    //sort arraylist so that start cell is the first element and last cell is last element
    //waypoints.Sort()
    return waypoints.toArray();//convert array list to array of point objects and return
    }//

}//end class
