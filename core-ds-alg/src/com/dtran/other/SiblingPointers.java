package com.dtran.other;

import java.util.LinkedList;
import java.util.Queue;

public class SiblingPointers {

    public static class Node {
        int val;
        Node left = null;
        Node right = null;
        Node sibling = null;
        public Node(int val) {
            this.val = val;
        }
    }

    public static void addSiblingPointers(Node root) {
        Node prev = null;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);
        while (queue.size() > 1) {
            Node curr = queue.poll();
            if (curr == null) {
                queue.add(null);
                prev = null;
            } else {
                if (prev != null) {
                    prev.sibling = curr;
                    prev = curr;
                } else {
                    prev = curr;
                }
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }

        }
    }

    public static void bfsPrint(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.print(" [val: " + curr.val + ", sibling: " + (curr.sibling == null ? "" : curr.sibling.val) + "] ");
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }
    }

    public static void main(String[] args) {
        // for a binary tree, connect all the sibling pointers
        // do bfs, and keep track of previous and when you move to a new level
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n3.right = n5;

        bfsPrint(n1);
        addSiblingPointers(n1);
        System.out.println();
        bfsPrint(n1);


    }
}
