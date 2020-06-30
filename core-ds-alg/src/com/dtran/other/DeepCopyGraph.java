package com.dtran.other;

import java.util.*;

public class DeepCopyGraph {
    public static class Node {
        List<Node> neighbors = new ArrayList<>();
        int val;
        public Node(int val) {
            this.val = val;
        }
    }

    public static Node deepCopy(Map<Node, Node> oldToNew, Node curr) {
        if (oldToNew.containsKey(curr)) {
            return oldToNew.get(curr);
        }
        Node newNode = new Node(curr.val);
        oldToNew.put(curr, newNode);
        for (Node neighbors : curr.neighbors) {
            newNode.neighbors.add(deepCopy(oldToNew, neighbors));
        }
        return newNode;
    }

    public static void printGraphBfs(Node root) {
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            visited.add(node);
            System.out.print(" " + node.val);
            for (Node neighbor : node.neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
        // given a tree, deep copy
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.neighbors.add(n2);
        n1.neighbors.add(n3);
        n3.neighbors.add(n4);
        n4.neighbors.add(n5);
        n5.neighbors.add(n1);

        Node newGraph = deepCopy(new HashMap<>(), n1);
        printGraphBfs(n1);
        System.out.println();
        printGraphBfs(newGraph);
    }
}
