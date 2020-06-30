package com.dtran.other;

public class BinarySearchTreeValidation {
    public static class Node {
        int val;
        Node left = null;
        Node right = null;
        public Node(int val) {
            this.val = val;
        }
    }

    public static class Range {
        int min;
        int max;
        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
        public boolean test(int val) {
            return val >= min && val <= max;
        }
    }

    public static boolean bstValidation(Node curr, Range range) {
        if (!range.test(curr.val)) {
            return false;
        }

        boolean leftValid = true;
        boolean rightValid = true;
        if (curr.left != null) {
            leftValid = bstValidation(curr.left, new Range(range.min, curr.val));
        }
        if (curr.right != null) {
            rightValid = bstValidation(curr.right, new Range(curr.val, range.max));
        }
        return leftValid && rightValid;
    }

    public static void main(String[] args) {
        Node n1 = new Node(5);
        Node n2 = new Node(1);
//        Node n3 = new Node(4); // true
        Node n3 = new Node(7); // false
        Node n4 = new Node(8);
        Node n5 = new Node(9);
        n1.left = n2;
        n2.right = n3;
        n1.right = n4;
        n4.right = n5;
        System.out.println(bstValidation(n1, new Range(Integer.MIN_VALUE, Integer.MAX_VALUE)));
    }
}
