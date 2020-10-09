package com.company;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws IOException {

        Reader reader = new Reader("URL");
        // ex : C:\Users\Cédric\IdeaProjects\tp1-algo2\src\com\company\formule.txt
        //System.out.println("taille de la formule : " + reader.getSize());
        //System.out.println("nombre de clauses : " + reader.getclose());
        //System.out.println("ensemble de clauses :" + reader.getList());
        List<List<Integer>> pairs =  reader.getList();

        Graph<String> g = makeGraph(pairs,reader.getSize());

        strongComponents(g).forEach(System.out::println);
        System.out.println(isSatisfiableFormula(pairs,reader.getSize()));
    }

    public static Graph<String> makeGraph(List<List<Integer>> pairs, int size) {
        Graph<String> graph = new Graph<>(size);
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


    public static void inverseGraph(Graph<String> graph) { // ToDO
        Graph<String> inversedGraph = new Graph<>(graph.order());
        inversedGraph.setIncidency(graph.getIncidency());
        graph.reverseIncidency();
    }

    public static List<Set<Integer>> strongComponents(Graph<String> graph) {
        List<Set<Integer>> result = new ArrayList<>();
        List<Integer> firstIteration= depthFirstSearch(graph);
        Collections.reverse(firstIteration);
        inverseGraph(graph);
        List<Integer> visitedNodes = new ArrayList<>();
        for (Integer node : firstIteration){ // firstIteration is inversed here
            if (!visitedNodes.contains(node)){
                Set<Integer> exploredSet = new HashSet<>();
                explore(graph,node,visitedNodes,exploredSet);
                result.add(exploredSet.stream()
                        .map(integer -> indexToIntLabel(integer,graph.order()))
                        .collect(Collectors.toSet())); // converting indexes to labels*/
            }
        }
        return result;//work gg wineuh
    }

    public static List<Integer> depthFirstSearch(Graph<String> g){
        List<Integer> exploredNodes = new ArrayList<>();
        for (Integer node : g.getNodes()) {
            if (!exploredNodes.contains(node))
                explore(g,node,exploredNodes, new HashSet<>());
        }
        return exploredNodes;
    }

    public static void explore(Graph<String> g, Integer s, List<Integer> exploredNodes
            , Set<Integer> visitedNodes ){
        visitedNodes.add(s);//we'v visited this node, not explored yet (avoid overflow from cycle ex:{1->2->3->1})
        for (Integer node : g.getNeigbours(s)){
            if(!exploredNodes.contains(node) && !visitedNodes.contains(node))
                explore(g,node, exploredNodes,visitedNodes);
        }
        exploredNodes.add(s);//we'v succesfully explored all neigbours of this node
    }


    public static boolean isSatisfiableFormula (List<List<Integer>> pairs,int order){
        Graph<String> g = makeGraph(pairs,order);
        for (Set<Integer> strongComponent : strongComponents(g) ){
            for (Integer node : strongComponent ){
                if (strongComponent.contains(Math.negateExact(node)))
                    return false;
            }
        }
        return true;
    }
}
