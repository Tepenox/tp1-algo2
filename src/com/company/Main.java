package com.company;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {


    public static void main(String[] args) {
        List<List<Integer>> pairs =  new ArrayList<>();
        pairs.add(Arrays.asList(1 , -2));
        pairs.add(Arrays.asList(-1 , 3));
        pairs.add(Arrays.asList(1 , 3));
        pairs.add(Arrays.asList(-2 , -3));
        System.out.println(makeGraph(pairs));

        Graph<String> g = new Graph<>(6);

//        g.addArc(1 ,2, " ");
//        g.addArc(2 ,3, " ");
//        g.addArc(3 ,1, " ");
//        g.addArc(2 ,4, " ");
//        g.addArc(4 ,5, " ");

//        System.out.println(g);
//
//        System.out.println("====================");
//        System.out.println(inverseGraph(g));
//        System.out.println("====================");
//        System.out.println(g);


        strongComponents(g).forEach(System.out::println);


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

    public static int indexToIntLabel (int index , int order) {
        return index >= order / 2 ? index - (order /2 ) + 1 : index - (order / 2);
    }


    public static Graph<String> inverseGraph(Graph<String> graph) {
        Graph<String> inversedGraph = new Graph<>(graph.order());
        inversedGraph.setIncidency(graph.getIncidency());
        for (LinkedList<Graph<String>.Edge> linkedList :inversedGraph.getIncidency()) {
            for (Graph.Edge edge : linkedList) {
                int tmp = edge.source;
                edge.source = edge.destination;
                edge.destination = tmp;
                edge.label = indexToIntLabel(edge.source , inversedGraph.order()) + "->"
                        + indexToIntLabel(edge.destination , inversedGraph.order());
            }
            ;
        }
        return inversedGraph;
    }

    public static List<Set<Integer>> strongComponents(Graph<String> graph) {
        List<Set<Integer>> result = new ArrayList<>();
        List<Integer> firstIteration= depthFirstSearch(graph);
        System.out.println(firstIteration);
        Collections.reverse(firstIteration);
        Graph<String> inversedGraph = inverseGraph(graph);
        List<Integer> visitedNodes = new ArrayList<>();
        for (Integer node : firstIteration){ // firstIteration is inversed here
            if (!visitedNodes.contains(node)){
                Set<Integer> exploredSet = new HashSet<>();
                explore(inversedGraph,node,visitedNodes,exploredSet);
                result.add(exploredSet);
            }

        }


        return result;
    }

    public static List<Integer> depthFirstSearch(Graph<String> g){
        List<Integer> visitedNodes = new ArrayList<>(); // stores all previously visited nodes in graph
        for (Integer node : g.getNodes()) {
            if (!visitedNodes.contains(node))
                explore(g,node,visitedNodes, new HashSet<>()); // we dont  need explored Set here
        }
        return visitedNodes;
    }

    public static void explore(Graph<String> g, Integer s, List<Integer> visitedNodes
            , Set<Integer> exploredNodes ){//stores explored node from s
        exploredNodes.add(s);
        visitedNodes.add(s);
        for (Integer node : g.getNeigbours(s)){
            if(!visitedNodes.contains(node))
                explore(g,node, visitedNodes,exploredNodes);
        }
    }
}
