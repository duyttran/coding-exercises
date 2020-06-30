package com.dtran.other;

import java.util.LinkedList;
import java.util.Queue;

public class CloneGraph {
    // if its not a bi directional graph, can just do DFS to traverse the whole graph
    // on each node, create the node and the children node, and put it on the queue so that can traverse
    // side by side
    // i think for a bi directional grpah its going to be the same as well
    // have to make sure we don't create hte same node twice if its a connection between two different nodes
    // if we traverse the exact way between both graphs, and we check equality, we can probably handle this.

    // traversing graph with BFS requires an isVisited set to keep track of all the nodes we've visited.
    public static class Node {
        Node left;
        Node right;
        int val;
        public Node(int val) {
            this.val = val;
        }
    }

    private static void printBfs(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.print(" " + curr.val + " ");
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }

        }
    }

    private static Node copyGraph(Node root) {
        Queue<Node> oldGraphQueue = new LinkedList<>();
        Queue<Node> newGraphQueue = new LinkedList<>();

        Node newRoot = new Node(root.val);
        oldGraphQueue.add(root);
        newGraphQueue.add(newRoot);
        while (!oldGraphQueue.isEmpty()) {
            Node oldCurr = oldGraphQueue.poll();
            Node newCurr = newGraphQueue.poll();
            if (oldCurr.left != null) {
                oldGraphQueue.add(oldCurr.left);
                Node newLeft = new Node(oldCurr.left.val);
                newCurr.left = newLeft;
                newGraphQueue.add(newLeft);
            }
            if (oldCurr.right != null) {
                oldGraphQueue.add(oldCurr.right);
                Node newRight = new Node(oldCurr.right.val);
                newCurr.right = newRight;
                newGraphQueue.add(newRight);
            }
        }

        return newRoot;
    }
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node3.left = node5;
        node3.right = node6;
        printBfs(node1);
        Node newNode1 = copyGraph(node1);
        System.out.println();
        printBfs(newNode1);
    }
}
