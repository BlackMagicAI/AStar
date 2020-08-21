package com.bmai.astar;

//import java.awt.Point;

/*
 * 
 * Program
 * Filename:	GridCell.java
 * 
 * Title:		GridCell class
 * Created on: 	Feb 1, 2005
 * 
 * Author:		Maurice Tedder (based on the Java Applet by James Macgill - http://www.ccg.leeds.ac.uk/james/aStar/
 * 
 * Target
 * Compilers:	Java - j2sdk 1.4.2
 *
 * Description:	Class representing a map gridcell
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
// public class GridCell implements Comparator<GridCell>, Comparable<GridCell> {
public class GridCell {    
	
    /**
     * Grid coordinates of this gridcell as a Point type<code>position</code>
     */
    public Point position;
    /**
     * Cost of this gridcell <code>cost</code>
     */
    public double cost;
    /**
     * This grid cells pixel width <code>width</code>
     */
    public  static int width  = 20;
    /**
     * This grid cells pixel height <code>height</code>
     */
    public  static int height  = 20;
    /**
     * Indicates that this cell is a start cell <code>isStart</code>
     */
    public boolean isStart;
    /**
     * indicates that this cell is a finish or target cell <code>isFinish</code>
     */
    public boolean isFinish;
    /**
     * Indicates that this is part of shortest path <code>partOfPath</code>
     */
    public boolean partOfPath;
    /**
     * This gridcells cost distance from start cell <code>distanceFromStart</code>
     */
    public double distanceFromStart;
	

	/**
	 * 
	 */
	public GridCell(Point position) {
        super();
        this.position = position;
		resetCell();//reset cells parameters to default values
	}

    /**
     * Resets and initializes all of this grid cells parameters to default values.
     */
	public void resetCell(){
		
		isStart = false;
		isFinish = false;
		cost = 1.0;
		distanceFromStart = -1;
		partOfPath = false;
	}//
	
	/**
     * Clears cell by reseting cost, distanceFromStart, partOfPath parameters to default values.
     * Leaves all other cell information intact such as isStart and isFinish parameters. Use this
     * method to replot and existing Map.
     */
	public void clearCell(){
		
		//isStart = false;
		//isFinish = false;
		cost = 1;
		distanceFromStart = -1;
		partOfPath = false;
	}//
	
    /**
     * Calculates updated distance from startcell using distance traveled
     * so far as a parameter.
     * @param distanceSoFar - Distance from the startcell
     */
    public int addToPathFromStart(double distanceSoFar ){
    
    if (distanceFromStart == -1){ //? don't know about this part
        distanceFromStart = distanceSoFar + cost;
        return 0;
    }//endif
    if (distanceSoFar + cost < distanceFromStart){ //? don't know about this part
        distanceFromStart = distanceSoFar + cost;
    }//endif
    	return 0;
    }//
    
    /**
     * Is this cell an obstacle block?    
     * @return - true if this gridcell is a impossible obstacle, false otherwise.
     */
    public boolean isTotalBlock() {
    	
    	if (cost == Map.BLOCK){
    		return true;
    	}
    	else{
    		return false;
    	}//end if block
    }//
    
    /**
     * Returns current distance of this griddcell from start gridcell.
     * @return - the distance from the start cell.
     */
    public double getDistFromStart(){
    if (isStart == true) { //if this is a start cell distance is zero 
        return 0;
    }//End If
    if (isTotalBlock()){
        return -1;
    }//End If
    return distanceFromStart;
    }//

    // Overriding the compare method to sort 
    // needed to use this object in a Priority queue.
    // @Override
    // public int compare(GridCell d, GridCell d1) {
    //     if(d.cost < d1.cost)
    //         return 1;
    //     else if (d.cost > d1.cost)
    //         return -1;
    //             return 0;
    //     //return Double.compare(d.cost, d1.cost);//descending order
    //     //return Double.compare(d1.cost, d.cost);//ascending order
    // }

    // needed to use this object in a Priority queue.
    // @Override
    // public int compareTo(GridCell o) {
    //     //descending order
    //     if (this.cost > o.cost) return 1;
    //     if (this.cost < o.cost) return -1;
    //     else return 0;

    //     //ascending order
    //     // if (o.cost > this.cost) return 1;
    //     // if (o.cost < this.cost) return -1;
    //     // else return 0;        
    // }
}//end class
