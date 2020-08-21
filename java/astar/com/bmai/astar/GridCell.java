package com.bmai.astar;

/*
 * 
 * Program
 * Filename:	GridCell.java
 * 
 * Title:		GridCell class
 * Created on: 	August 21, 2020
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
	}//
	
	/**
     * Clears cell by reseting cost, distanceFromStart, partOfPath parameters to default values.
     * Leaves all other cell information intact such as isStart and isFinish parameters. Use this
     * method to replot and existing Map.
     */
	public void clearCell(){
		cost = 1;
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
}//end class
