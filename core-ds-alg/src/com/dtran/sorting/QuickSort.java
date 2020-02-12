package com.dtran.sorting;

import java.util.Arrays;

public class QuickSort implements Sorter {

    public int[] sort(int[] arr) {
        int[] sortedArray = Arrays.copyOf(arr, arr.length);
        sort(sortedArray, 0, arr.length - 1);
        return sortedArray;
    }

    public void sort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int partition = partition(arr, start, end);
        sort(arr, start, partition - 1);
        sort(arr, partition + 1, end);
    }

    public int partition(int[] arr, int start, int end) {
        int partition = end;
        int i = start;
        for (int j = start; j < partition; j++) {
            if (arr[j] < arr[partition]) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, partition);
        return i;
    }

    public void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static void main(String[] args) {
        Sorter sorter = new QuickSort();
        int[] arr = {4, 3, 1, 5, 7};
        int[] sortedArray = sorter.sort(arr);
        for (int i : sortedArray) {
            System.out.print(i + " ");
        }
    }
}
