package com.dtran.other;

import java.util.Stack;

public class MaxSumTree {
    public static class Node {
        int val;
        Node left = null;
        Node right = null;
        public Node(int val) {
            this.val = val;
        }
    }

    public static class SumStack {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
    }

    public static SumStack maxSum(Node root) {
        SumStack maxSumStack = new SumStack();
        maxSumStack.sum = Integer.MIN_VALUE;
        SumStack currSumStack = new SumStack();
        dfsMaxSum(root, maxSumStack, currSumStack);
        return maxSumStack;
    }

    public static void dfsMaxSum(Node curr, SumStack maxSumStack, SumStack currSumStack) {
        if (curr == null) {
            return;
        }
        currSumStack.stack.push(curr.val);
        currSumStack.sum += curr.val;
        if (maxSumStack.sum < currSumStack.sum) {
            maxSumStack.sum = currSumStack.sum;
            maxSumStack.stack.clear();
            maxSumStack.stack.addAll(currSumStack.stack);
        }

        if (curr.left != null) {
            dfsMaxSum(curr.left, maxSumStack, currSumStack);
        }

        if (curr.right != null) {
            dfsMaxSum(curr.right, maxSumStack, currSumStack);
        }
        currSumStack.stack.pop();
        currSumStack.sum -= curr.val;
    }

    public static void main(String[] args) {
        // build a binary tree
        // traverse dfs, so you can keep track of stack
        // hold two stacks, the stack of the current longest, and the stack of the current dfs traversal
        // for each dfs call, pass in the current longest stack, the max sum, and the stack of the current dfs traversal
        // continually push and pop from the current dfs traversal, when it becomes greater than longest then replace
        // continue through the whole tree
        // O(n) with O(n) space

        Node n1 = new Node(1);
        Node n2 = new Node(4);
        Node n3 = new Node(3);
        Node n4 = new Node(1);
        Node n5 = new Node(6);
        Node n6 = new Node(3);
        Node n7 = new Node(10);

        /*
            1
          4  3
         6   1  3
             10
         */
        n1.left = n2;
        n1.right = n3;
        n3.left = n4;
        n2.left = n5;
        n3.right = n6;
        n4.right = n7;
        SumStack maxSumStack = maxSum(n1);
        System.out.println(maxSumStack.sum);
        System.out.println(maxSumStack.stack);
    }
}
