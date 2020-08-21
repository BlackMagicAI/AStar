package com.bmai.astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 
 * Program
 * Filename:	AStar.java
 * 
 * Title:		AStar Class (version 2.0)
 * Created on: 	August 21, 2020
 * 
 * Last Date
 * Modified:	
 * 
 * @author Author Maurice Tedder
 *              (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/
 * 				Informaion about A* algorithm by: Patel, Amits. Amit's A* Pages. Retrieved on February 2005 from
 * 				http://theory.stanford.edu/~amitp/GameProgramming.
 *              Additional references:
 *              https://www.redblobgames.com/pathfinding/a-star/introduction.html
 *              https://en.wikipedia.org/wiki/A*_search_algorithm
 *              https://www.redblobgames.com/pathfinding/a-star/implementation.html#optimize-queue
 *              https://www.redblobgames.com/pathfinding/grids/graphs.html
 * Target
 * Compilers:	Java - j2sdk 1.4.2
 *
 * Description:	Class that implements the A Start shortest path 
 * 				algorithm for a grid map divided into a X x Y grid of gridcells.
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
	
	/**
	 * Default constructor.
	 */
	public AStar() {
		super();
	}
    
   /**
     * Finds the shortest path from the start location to finish location
     * using the A* algorithm and returns the waypoints of the path found
     * as an array of objects.
     * @param gridMap - Gridmap to perform the A star search on to find the 
     * 					shortest path.
     * @return - Gridcell object list of waypoints of the shortest path found by the A start algorithm.
     */
    public GridCell[] findPath(GridMap gridMap){

        GridCell startCell = gridMap.getStartCell();
        // System.out.println(startCell.position);

        GridCell goal = gridMap.getFinishCell();

        PriorityQueue<GridCell> openSet = new PriorityQueue<GridCell>(new GridCellComparator());
        openSet.add(startCell);

        HashMap<GridCell, Double> gScore = new HashMap<GridCell, Double>(); // cost so far
        gScore.put(startCell, (double) 0.0);

        //calculate huristic (h()) and add to g() to get f()
        HashMap<GridCell, Double> fScore = new HashMap<GridCell, Double>();

        HashMap<GridCell, GridCell> cameFrom = new HashMap<GridCell, GridCell>();
        cameFrom.put(startCell, null);

        GridCell current;
        while (!openSet.isEmpty()) {
            current = openSet.poll();
            
            if (current.equals(goal)){
                System.out.println("Path found");
                GridCell[] waypointsList = reconstructPath(cameFrom, current);//path found gridcells object array
                return waypointsList;
            }
            
            GridCell[] neighbors = gridMap.getNeighbors(current);
            
            for(GridCell next: neighbors){
                double tentativeGScore = gScore.get(current) + next.cost;
                if ((gScore.containsKey(next) == false) || tentativeGScore < gScore.getOrDefault(next, Double.MAX_VALUE)){
                    gScore.put(next, tentativeGScore);
                    fScore.put(next, gScore.get(next) + cbDist(next.position, goal.position, 1.0));//calculate huristic (h()) and add to g() to get f()
                    //
                    next.cost = fScore.get(next);
                    openSet.add(next);
                    cameFrom.put(next, current);
                }
            }
    
        }
        System.out.println("Path not Found");
        return null;
    }

    //Assemble A* breadcrumbs in to waypoints list
    public GridCell[] reconstructPath(HashMap<GridCell, GridCell> cameFrom, GridCell current){
        
        ArrayList<GridCell> totalPath = new ArrayList<GridCell>();
        totalPath.add(current);
        int l = cameFrom.keySet().size();
        for(int i=0; i < l; i++){
            current = cameFrom.get(current);
            if (current != null){
                totalPath.add(0, current);
            }
        }

        GridCell[] arr = new GridCell[totalPath.size()];
        return totalPath.toArray(arr);
    }

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

}//end class
