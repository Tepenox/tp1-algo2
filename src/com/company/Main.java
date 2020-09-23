package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<List<Integer>> pairs =  new ArrayList<>();
        pairs.add(Arrays.asList(1 , -2));
        pairs.add(Arrays.asList(-1 , 3));
        pairs.add(Arrays.asList(1 , 3));
        pairs.add(Arrays.asList(-2 , -3));
        System.out.println(makeGraph(pairs));
    }

    public static Graph<String> makeGraph(List<List<Integer>> pairs) {
        Graph<String> graph = new Graph<>(6);
        int graphOrder = graph.order();
        for (List<Integer> pair : pairs) {
            graph.addArc(intLabelToIndex(Math.negateExact(pair.get(0)), graphOrder) , intLabelToIndex(pair.get(1), graphOrder)
                    , Math.negateExact(pair.get(0)) + "->" + pair.get(1));
            graph.addArc(intLabelToIndex(Math.negateExact(pair.get(1)),graphOrder) , intLabelToIndex(pair.get(0),graphOrder)
                    , Math.negateExact(pair.get(1)) + "->" + pair.get(0));
        }
        return graph;

    }

    public static int intLabelToIndex(int intLabel , int order) {
        return intLabel > 0 ? intLabel + (order /2 ) - 1 : intLabel + (order / 2);
    }
}
