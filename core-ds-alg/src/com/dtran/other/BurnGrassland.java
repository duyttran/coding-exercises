package com.dtran.other;

import java.util.*;

/*
Your previous Plain Text content is preserved below:

Two people want to burn a grassland. Grassland is a rectangle board, a fire point will cause its 4 adjacent grass, up, down, right, left, to start burning. If a point is an empty point, no fire will start. If two people can pick any two grass to set initial fire simultaneously, what's the least time to burn out all grass. Consider initial fire happen at time 0.

m rows, n columns
Board size. 1 <= n <=10, 1 <= m <=10
1: grass, 0: empty

Input:
111
001
101
Output:
2

Input:
000
101
000
Output:
0

Input:
010
101
010
Output:
-1
 */
public class BurnGrassland {
    public static class Point {
        int x;
        int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int burnGrassPeriods(int[][] grassland) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < grassland.length; i++) {
            for (int j = 0; j < grassland[i].length; j++) {
                if (grassland[i][j] == 1) {
                    points.add(new Point(i, j));
                }
            }
        }
        int minPeriods = Integer.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                int periods = burnGrassPeriods(grassland, points.size(), points.get(i), points.get(j));
                if (periods == -1) {
                    continue;
                }
                if (periods < minPeriods) {
                    minPeriods = periods;
                }
            }
        }
        return minPeriods == Integer.MAX_VALUE ? -1 : minPeriods;
    }

    public static int burnGrassPeriods(int[][] grassland, int burnableSpaces, Point one, Point two) {
        Set<String> burnt = new HashSet<>();
        Queue<Point> queue = new LinkedList<>();
        queue.add(one);
        queue.add(two);
        queue.add(null);
        int periods = 0;
        while (queue.size() > 1) {
            Point curr = queue.poll();
            if (curr == null) {
                periods += 1;
                queue.add(null);
            } else {
                burnt.add(curr.x + " " + curr.y);
                if (canBurn(grassland, burnt, curr.x + 1, curr.y)) {
                    queue.add(new Point(curr.x + 1, curr.y));
                }
                if (canBurn(grassland, burnt, curr.x - 1, curr.y)) {
                    queue.add(new Point(curr.x - 1, curr.y));
                }
                if (canBurn(grassland, burnt, curr.x, curr.y + 1)) {
                    queue.add(new Point(curr.x, curr.y + 1));
                }
                if (canBurn(grassland, burnt, curr.x, curr.y - 1)) {
                    queue.add(new Point(curr.x, curr.y - 1));
                }
            }
        }
        if (burnt.size() != burnableSpaces) {
            return -1;
        } else {
            return periods;
        }
    }

    public static boolean canBurn(int[][] grassland, Set<String> burnt, int x, int y) {
        if (x < 0 || x >= grassland.length) {
            return false;
        }
        if (y < 0 || y >= grassland[x].length) {
            return false;
        }
        if (burnt.contains(x + " " + y)) {
            return false;
        }
        if (grassland[x][y] == 0) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] grassland1 = new int[][] {
            {1, 1, 1},
            {0, 0, 1},
            {1, 0, 1}
        };
        System.out.println(burnGrassPeriods(grassland1)); // expected 2
        int[][] grassland2 = new int[][] {
            {0, 0, 0},
            {1, 0, 1},
            {0, 0, 0}
        };
        System.out.println(burnGrassPeriods(grassland2)); // expected 0
        int [][] grassland3 = new int[][] {
            {0, 1, 0},
            {1, 0, 1},
            {0, 1, 0}
        };
        System.out.println(burnGrassPeriods(grassland3)); // expected -1
    }
}
