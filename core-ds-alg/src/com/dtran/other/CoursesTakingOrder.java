package com.dtran.other;

import java.util.*;

public class CoursesTakingOrder {

    public static class Graph {
        private Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        public Graph(int[][] courses) {
            for (int i = 0; i < courses.length; i++) {
                Set<Integer> preRequisites = adjacencyList.computeIfAbsent(courses[i][0],
                    s -> new HashSet<>());
                if (courses[i].length > 1) {
                    preRequisites.add(courses[i][1]);
                    adjacencyList.computeIfAbsent(courses[i][1],
                        s -> new HashSet<>());
                }
            }
        }

        public Map<Integer, Set<Integer>> getFullAdjacencyList() {
            return adjacencyList;
        }

        public Set<Integer> getAdjacencyList(int node) {
            return adjacencyList.get(node);
        }

        public Set<Integer> getNodeList() {
            return adjacencyList.keySet();
        }
    }

    public static boolean hasLoops(Graph graph) {
        Set<Integer> visited = new HashSet<>();
        for (Integer node : graph.getNodeList()) {
            if (dfsCheckCycle(node, graph, visited)) {
                return true;
            }
        }
        return false;
    }

    public static boolean dfsCheckCycle(int curr, Graph graph, Set<Integer> visited) {
           if (visited.contains(curr)) {
               return true;
           }
           visited.add(curr);
           for (Integer child : graph.getAdjacencyList(curr)) {
               if (dfsCheckCycle(child, graph, visited)) {
                   return true;
               }
           }
           visited.remove(curr);
           return false;
    }

    public static void main(String[] args) {
        // given a set of courses and their pre requisites
        // where a course is a numbered id
        // and their pre, requisite is a pair where [a, b] a is the prerequisite of b
        // find out if there are no loops
        // and find out the order of courses one will need to take to complete
        // ex [1, 2], [4], [3, 2], [1, 5], [3, 6]

        // basically have to find loops
        // you can detect loops by building a graph going one direction
        // if you've visited a node twice in the same traversal, then you have a loop

        // the input comes in as a inefficent format, which requires us to go over input every time we need to look
        // up something, which would be n^2
        // we can put this in a graph structure, nodes and adjacency list

        Graph graph = new Graph(new int[][]
            {
                {1, 2},
                {4},
                {3, 2},
                {2, 6},
                {1, 5},
                {3, 6},
                {6, 1}
            });
        System.out.println(graph.getFullAdjacencyList());
        System.out.println(hasLoops(graph));

        // then each traversal will be linear instead of exponential
        // after we have that structure, we can do DFS, so we can find a loop. in each DFS we keep a list of visited nodes
        // and if we've hit a visited node twice we exit
        // we pop and add to this stack as we traverse

    }
}
