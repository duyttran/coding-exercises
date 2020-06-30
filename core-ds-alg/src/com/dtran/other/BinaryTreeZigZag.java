package com.dtran.other;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeZigZag {
    public static class Node {
        Node left;
        Node right;
        int val;
        public Node(int val) {
            this.val = val;
        }
    }

    public void printZigZag(Node root) {
        Queue<Node> queue = new LinkedList<>();
        boolean printForward = true;
        List<Node> printList = new LinkedList<>();
        queue.add(root);
        queue.add(new Node(-1));
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr.val == -1) {
                printLevel(printList, printForward);
                printList.clear();
                if (!queue.isEmpty()) {
                    queue.add(curr);
                    printForward = !printForward;
                }
            } else {
                printList.add(curr);
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
        }
        // DFS, after each level add a pointer node
        // print after each level, and specify the direction of the print, either forward
        // after each level switch the direction of the print
    }

    public void printLevel(List<Node> list, boolean printForward) {
        if (printForward) {
            for (Node node : list) {
                System.out.print(" " + node.val + " ");
            }
        } else {
            for (int i = list.size() - 1; i >= 0; i--) {
                System.out.print(" " + list.get(i).val + " ");
            }
        }
        System.out.println();
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
        BinaryTreeZigZag zigZag = new BinaryTreeZigZag();
        zigZag.printZigZag(node1);
    }
}
