package com.company;

import java.util.*;

public class Graph<Label> {

    public Set<Integer> nodes = new HashSet<>();



    class Edge {
        public int source;
        public int destination;
        public Label label;

        public Edge(int from, int to, Label label) {
            this.source = from;
            this.destination = to;
            this.label = label;
        }
    }

    private int cardinal;
    private ArrayList<LinkedList<Edge>> incidency;


    public Graph(int size) {
        cardinal = size;
        incidency = new ArrayList<LinkedList<Edge>>(size + 1);
        for (int i = 0; i < cardinal; i++) {
            incidency.add(i, new LinkedList<Edge>());
        }
    }

    public int order() {
        return cardinal;
    }

    public void addArc(int source, int dest, Label label) {
        nodes.add(source);
        nodes.add(dest);
        incidency.get(source).addLast(new Edge(source, dest, label));
    }

    public String toString() {
        String result = new String("");
        result = result.concat(cardinal + "\n");
        for (int i = 0; i < cardinal; i++) {
            for (Edge e : incidency.get(i)) {
                result = result.concat(e.source + " " + e.destination + " "
                        + e.label.toString() + "\n");
            }
        }
        return result;

    }

    public ArrayList<LinkedList<Edge>> getIncidency() {
        return new ArrayList<>(incidency);
    }

    public void setIncidency(ArrayList<LinkedList<Edge>> incidency) {
        this.incidency = incidency;
    }

    public Set<Integer> getNeigbours(int sourceIndex) {
        Set<Integer> neigbors = new HashSet<>();

            for (Graph.Edge edge : incidency.get(sourceIndex)) {
                neigbors.add(edge.destination);
        }
        return neigbors;
    }

    public Set<Integer> getNodes() {
        return new HashSet<>(nodes);
    }
}



