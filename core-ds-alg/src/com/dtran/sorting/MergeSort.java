package com.dtran.sorting;

import java.util.Arrays;

public class MergeSort implements Sorter {
    @Override
    public int[] sort(int[] arr) {
        int[] tmpArr = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, tmpArr);
        return arr;
    }

    public void mergeSort(int[] arr, int start, int end, int[] tmpArr) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        mergeSort(arr, start, mid, tmpArr);
        mergeSort(arr, mid + 1, end, tmpArr);
        merge(arr, start, mid, end, tmpArr);
        copyArray(arr, start, end, tmpArr);
    }

    public void copyArray(int[] arr, int start, int end, int[] tmpArr) {
        for (int i = start; i < end; i++) {
            arr[i] = tmpArr[i];
        }
    }

    public void merge(int[] arr, int start, int mid, int end, int[] tmpArr) {
        for (int curr = start, i = start, j = mid; curr < end; curr++) {
            if (i < mid && (j >= end || arr[i] <= arr[j])) {
                tmpArr[curr] = arr[i];
                i++;
            } else {
                tmpArr[curr] = arr[j];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        Sorter.testSort(new MergeSort());
    }
}
