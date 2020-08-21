package com.bmai.astar;

import java.util.Comparator;

/**
 * Program
 * Filename:	GridCellComparator.java
 * 
 * Title:		GridCell class
 * Created on: 	August 21, 2020
 * 
 * @author      Maurice Tedder
 * 
 * Target
 * Compilers:	Java - j2sdk 1.4.2
 *
 * Description:	Class to indicate how a map gridcell should be compared/sorted.
 * Ref:
 * A custom comparator that compares two Strings by their length.
 * https://www.callicoder.com/java-priority-queue/
 * https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/
 */
public class GridCellComparator implements Comparator<GridCell>{

    // Overriding the compare method to sort in Priority queue.
    // needed to use this object in a Priority queue.
    @Override
    public int compare(GridCell s1, GridCell s2) {
        int result = (int) (s1.cost - s2.cost);
        return result;
    }
}