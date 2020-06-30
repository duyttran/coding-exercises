package com.dtran.other;

import java.util.PriorityQueue;
import java.util.Queue;

public class MergeSortedArrays {
    public static class ArrayPointer implements Comparable<ArrayPointer> {

        int subArrIndex;
        int arrIndex;
        int val;

        public ArrayPointer(int arrIndex, int subArrIndex, int val) {
            this.arrIndex = arrIndex;
            this.subArrIndex = subArrIndex;
            this.val = val;
        }

        @Override
        public int compareTo(ArrayPointer o) {
            return Integer.compare(val, o.val);
        }
    }

    public static int[] mergeSortedArrays(int[][] arr) {
        int totalLength = 0;
        Queue<ArrayPointer> heap = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            totalLength += arr[i].length;
            heap.add(new ArrayPointer(i, 0, arr[i][0]));
        }
        int[] resultArr = new int[totalLength];
        for (int i = 0; i < resultArr.length; i++) {
            ArrayPointer arrayPointer = heap.poll();
            resultArr[i] = arrayPointer.val;
            if (arrayPointer.subArrIndex + 1 < arr[arrayPointer.arrIndex].length) {
                heap.add(new ArrayPointer(arrayPointer.arrIndex, arrayPointer.subArrIndex + 1,
                    arr[arrayPointer.arrIndex][arrayPointer.subArrIndex + 1]));
            }
        }
        return resultArr;
    }

    public static void main(String[] args) {
        // distributed merge sort
        // pointeres to all arrays
        // keep the pointers in a heap, so you have the smallest one always
        // the pointer has two peices of information, the array and the index in the array
        // when you pop, add the next element in that array
        int[][] arr = new int[][]{
            {1, 4, 6, 8},
            {2, 4, 4, 5, 6},
            {3},
            {1, 2, 4, 10},
            {13, 15}
        };
        int[] mergedSortedArr = mergeSortedArrays(arr);
        for (int i = 0; i < mergedSortedArr.length; i++) {
            System.out.print(mergedSortedArr[i] + " ");
        }
    }
}
