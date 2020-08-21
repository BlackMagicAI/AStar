package com.bmai.astar;

import java.util.Comparator;

/**
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
        // int val = (int) (s2.cost - s1.cost);
        //System.out.println(val);
        return result;
    }
}