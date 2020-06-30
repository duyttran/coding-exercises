package com.dtran.algorithm;

import java.util.ArrayList;
import java.util.List;

public class KMPAlgorithm {

    public static int[] preprocess(String pattern) {
        int[] preprocess = new int[pattern.length()];
        int len = 0; // keeps track of the end of the prefix
        int i = 1; // tracks for the given prefix tracked by len, what can skip matching
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                preprocess[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = preprocess[len - 1];
                } else {
                    preprocess[i] = 0;
                    i++;
                }
            }
        }
        return preprocess;
    }

    public static List<Integer> kmp(String input, String pattern) {
        List<Integer> matchIndices = new ArrayList<>();
        int[] preprocess = preprocess(pattern);
        int i = 0;
        int j = 0;
        while (i < input.length()) {
            if (input.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            if (j == pattern.length()) {
                j = preprocess[j - 1];
                matchIndices.add(i);
            } else if (i < input.length() && input.charAt(i) != pattern.charAt(j)) {
                if (j > 0) {
                    j = preprocess[j - 1];
                } else {
                    i++;
                }
            }
        }
        return matchIndices;
    }
    public static void main(String[] args) {
        // kmp for substring matching
        // optimization comes from not rematching everything based on a pattern if a pattern has partial matches
        // that indicate what we can skip rematching
        // preprocess based on the pattern a string
        // then use that to know when to skip matches

        List<Integer> matchIndices = kmp("AABAACAADAABAABA", "AABA");
    }
}
