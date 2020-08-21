package com.bmai.astar;

/**
 * 
 * Program
 * Filename:	Point.java
 * 
 * Title:		Point Class (version 1.0)
 * Created on: 	August 21, 2020
 * 
 * Last Date
 * Modified:	
 *  
 * @author		Maurice Tedder
 * 
 * Target
 * Compilers:	Java - j2sdk 1.4.2
 *
 * Description:	Class that represents a X & Y coordinate point position of a GridCell on a GridMap.
 */
public class Point {
    public int x;
    public int y;

    /**
     * Parameterized constructor.
     * @param x
     * @param y
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
    * Override toString method for this class/object.
    */
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}