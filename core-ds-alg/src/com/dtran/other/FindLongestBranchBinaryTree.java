package com.dtran.other;

public class FindLongestBranchBinaryTree {
    public static int left(int i) {
        return i * 2;
    }
    public static int right(int i) {
        return i * 2 + 1;
    }

    public static long dfsLongestBranch(long[] tree, int i) {
        if (tree.length > i || tree[i - 1] == -1) {
            return 0;
        }
        long longestBranchLeft = dfsLongestBranch(tree, left(i));
        long longestBranchRight = dfsLongestBranch(tree, right(i));
        long longestBranch = longestBranchLeft > longestBranchRight ? longestBranchLeft : longestBranchRight;
        return 1 + longestBranch;
    }
    public static long solution(long[] tree) {
        // Type your solution here
        return dfsLongestBranch(tree, 1);
    }


}
