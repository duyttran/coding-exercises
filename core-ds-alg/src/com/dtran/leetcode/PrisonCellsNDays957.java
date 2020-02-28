package com.dtran.leetcode;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * https://leetcode.com/problems/prison-cells-after-n-days/submissions/
 */
public class PrisonCellsNDays957 {
    public int[] prisonAfterNDays(int[] cells, int N) {
        List<Boolean> currDay = booleanCells(cells);
        List<Boolean> nextDay = currDay;
        Set<List<Boolean>> seenDay = new HashSet<>();
        List<List<Boolean>> days = new ArrayList<>();
        int iterateTimes = N;
        if (cells[0] == 1 || cells[7] == 1) {
            currDay = iterateDay(currDay);
            nextDay = currDay;
            iterateTimes--;
        }

        // loop n times
        for(int i = 0; i < iterateTimes; i++) {
            // each time iterate to next day
            // next day iterates through each cell and compares neighboars and sets the value in the nextDay cell
            if (seenDay.contains(currDay)) {
                System.out.print("day " + i + ": ");
                currDay.forEach(cell -> System.out.print(cell ? 1 + " " : 0 + " "));
                System.out.println();
                int indexDay = iterateTimes%i;
                System.out.println("index " + i);
                System.out.println("index day " + indexDay);
                return intCells(days.get(indexDay));
            } else {
                nextDay = iterateDay(currDay);
                seenDay.add(currDay);
                days.add(currDay);
            }
            System.out.print("day " + i + ": ");
            currDay.forEach(cell -> System.out.print(cell ? 1 + " " : 0 + " "));
            System.out.println();
            currDay = nextDay;
        }
        return intCells(nextDay);
    }

    public List<Boolean> iterateDay(List<Boolean> currDay) {
        List<Boolean> nextDay = new ArrayList<>(currDay.size());
        for (int i = 0; i < currDay.size(); i++) {
            if (i == 0 || i == currDay.size() - 1) {
                nextDay.add(false);
            } else if (currDay.get(i-1) == currDay.get(i+1)) {
                nextDay.add(true);
            } else {
                nextDay.add(false);
            }
        }
        return nextDay;
    }

    public List<Boolean> booleanCells(int[] cells) {
        List<Boolean> booleanCells = new ArrayList(cells.length);
        for (int i = 0; i < cells.length; i++) {
            booleanCells.add(cells[i] == 1 ? true : false);
        }
        return booleanCells;
    }

    public int[] intCells(List<Boolean> cells) {
        int[] intCells = new int[cells.size()];
        for (int i = 0; i < cells.size(); i++) {
            intCells[i] = cells.get(i) == true ? 1 : 0;
        }
        return intCells;
    }

    public static void main(String[] args) {

        PrisonCellsNDays957 solution = new PrisonCellsNDays957();

        int[] cells = {0,0,1,1,1,1,0,0};
        int[] end = solution.prisonAfterNDays(cells, 16);
        System.out.print("Actual   ");
        Arrays.stream(end).forEach(cell -> System.out.print(cell + " "));
        System.out.println();
        System.out.println("Expected 0 1 0 0 0 0 1 0");
        System.out.println();


        int[] cells2 = {1, 1, 0, 1, 1, 0, 1, 1};
        int[] end2 = solution.prisonAfterNDays(cells2, 6);
        System.out.print("Actual   ");
        Arrays.stream(end2).forEach(cell -> System.out.print(cell + " "));
        System.out.println();
        System.out.println("Expected 0 0 1 0 0 1 0 0");
        System.out.println();


        int[] cells3 = {1,0,0,1,0,0,1,0};
        int[] end3 = solution.prisonAfterNDays(cells3, 1000000000);
        System.out.print("Actual   ");
        Arrays.stream(end3).forEach(cell -> System.out.print(cell + " "));
        System.out.println();
        System.out.println("Expected 0 0 1 1 1 1 1 0");


        int[] cells4 = {0,1,1,1,0,0,0,0};
        int[] end4 = solution.prisonAfterNDays(cells4, 99);
        System.out.print("Actual   ");
        Arrays.stream(end4).forEach(cell -> System.out.print(cell + " "));
        System.out.println();
        System.out.println("Expected 0 0 1 0 0 1 1 0");


        int[] cells5 = {1,0,0,1,0,0,0,1};
        int[] end5 = solution.prisonAfterNDays(cells5, 826);
        System.out.print("Actual   ");
        Arrays.stream(end5).forEach(cell -> System.out.print(cell + " "));
        System.out.println();
        System.out.println("Expected 0 1 1 0 1 1 1 0");
    }
}
