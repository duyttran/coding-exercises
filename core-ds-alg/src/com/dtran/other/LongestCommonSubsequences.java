package com.dtran.other;

import java.util.HashSet;
import java.util.Set;

public class LongestCommonSubsequences {

    public static String longestCommonSubsequence(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        printArray(dp);

        Set<String> subSequences = backTrackAll(dp, s1, s1.length(), s2, s2.length());
        for (String s : subSequences) {
            System.out.println(s);
        }
        System.out.println();
        return backTrack(dp, s1, s1.length(), s2, s2.length());
    }

    public static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println();
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(" " + arr[i][j] + " ");
            }
        }
        System.out.println();
    }
    public static String backTrack(int[][] dp, String s1, int i, String s2, int j) {
        if (i == 0 || j == 0) {
            return "";
        } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return backTrack(dp, s1, i - 1, s2, j - 1) + s1.charAt(i - 1);
        } else if (dp[i][j - 1] > dp[i - 1][j]){
            return backTrack(dp, s1, i, s2, j - 1);
        } else {
            return backTrack(dp, s1, i - 1, s2, j);
        }
    }

    public static Set<String> backTrackAll(int[][] dp, String s1, int i, String s2, int j) {
        if (i == 0 || j == 0) {
            Set<String> subSequences = new HashSet<>();
            subSequences.add("");
            return subSequences;
        }

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            Set<String> subSequences = backTrackAll(dp, s1, i - 1, s2, j - 1);
            Set<String> newSubSequences = new HashSet<>();
            for (String s : subSequences) {
                newSubSequences.add(s + s1.charAt(i - 1));
            }
            return newSubSequences;
        }

        Set<String> subSequences = new HashSet<>();
        if (dp[i][j - 1] >= dp[i - 1][j]) {
            subSequences = backTrackAll(dp, s1, i, s2, j - 1);
        }

        if (dp[i - 1][j] >= dp[i][j - 1]) {
            subSequences.addAll(backTrackAll(dp, s1, i - 1, s2, j));
        }
        return subSequences;
    }

    public static void main(String[] args) {
        // brute force
        // find all subsequences in string 1
        // find all subsequences in string 2
        // for all matches, keep track of the longest one
        // n^2 * k^2
        //
        // oh, subsequence is different from substring
        // two properties
        // given two substrings, if the last letter is common, the LCS is 1 + the larger of LCS of s^n-1 or s2^m-1
        // given two substrings, if the last ltter is not common, the LCS is the larger of LCS of s^n -1 or s2^m-1
        // make a table, fill it out based on the rules above
        // the very last element in the table is the length of the LCS
        // if you trace back on any descending node, you'll find the actual subsequence


        System.out.println(longestCommonSubsequence("agcat", "gac"));
    }
}
