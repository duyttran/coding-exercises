package com.dtran.sorting;

interface Sorter {
    int[] sort(int[] arr);

    static void print(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    static void testSort(Sorter sorter) {
        int[] arr = {4, 3, 1, 5, 7};
        System.out.println("unsorted");
        print(arr);
        int[] sortedArray = sorter.sort(arr);
        System.out.println("sorted");
        print(sortedArray);
    }
}
