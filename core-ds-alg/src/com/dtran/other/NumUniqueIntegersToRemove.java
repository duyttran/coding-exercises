package com.dtran.other;

import java.util.*;
import java.util.function.ToIntFunction;

/*

2.
Given an array.  You can choose a set of integers and remove all the occurrences of these integers in the array.
Return the minimum size of the set so that at least half of the integers of the array are removed.
Example:
Input: [1, 2, 2, 3]
Output: 1
Explanation: remove number 2

 */
public class NumUniqueIntegersToRemove {
    public static int minIntegersToRemove(int[] input) {
        Map<Integer, Integer> integerCount = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            if (integerCount.containsKey(input[i])) {
                integerCount.put(input[i], integerCount.get(input[i]) + 1);
            } else {
                integerCount.put(input[i], 1);
            }
        }
        List<Map.Entry<Integer, Integer>> sortedListByValue = new ArrayList<>(integerCount.entrySet());
        sortedListByValue.sort(new Comparator<>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> first, Map.Entry<Integer, Integer> second) {
                return -1*first.getValue().compareTo(second.getValue());
            }
        });
        System.out.println(sortedListByValue);
        int size = input.length;
        int count = 0;
        while (size > input.length / 2) {
            size = size - sortedListByValue.get(count).getValue();
            count++;
        }
        return count;
    }
    public static void main(String[] args) {
        System.out.println(minIntegersToRemove(new int[]{1, 2, 2, 3}));
    }
}
