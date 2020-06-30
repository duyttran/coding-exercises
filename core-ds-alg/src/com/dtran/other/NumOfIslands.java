package com.dtran.other;

import java.util.LinkedList;
import java.util.Queue;

public class NumOfIslands {
    private static class Point {
        int i;
        int j;
        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
    public static int numIslands(int[][] map) {
        int[][] visited = new int[map.length][map[0].length];
        int islandCount = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                islandCount += traverseLand(map, visited, new Point(i, j));
            }
        }
        return islandCount;
    }

    /**
     * Traverses the island from a start point and marks all the traversed points as visited.
     * @param map
     * @param visited
     * @param startPoint
     * @return 1 if traversed land and 0 if traversed an island
     */
    public static int traverseLand(int[][] map, int[][] visited, Point startPoint) {
        if (isVisited(visited, startPoint) || !isLand(map, startPoint)) {
            return 0; // already visited or not land
        }
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(1, 1));
        while (!queue.isEmpty()) {
            Point curr = queue.poll();
            markVisited(visited, curr);
            // try left up
            if (shouldTraverseTo(map, visited, new Point(curr.i - 1, curr.j - 1))) {
                queue.add(new Point(curr.i - 1, curr.j - 1));
            }
            // try up
            if (shouldTraverseTo(map, visited, new Point(curr.i, curr.j - 1))) {
                queue.add(new Point(curr.i, curr.j - 1));
            }
            // try up right
            if (shouldTraverseTo(map, visited, new Point(curr.i + 1, curr.j - 1))) {
                queue.add(new Point(curr.i + 1, curr.j - 1));
            }
            // try right
            if (shouldTraverseTo(map, visited, new Point(curr.i + 1, curr.j))) {
                queue.add(new Point(curr.i + 1, curr.j));
            }
            // try right down
            if (shouldTraverseTo(map, visited, new Point(curr.i + 1, curr.j + 1))) {
                queue.add(new Point(curr.i + 1, curr.j + 1));
            }
            // try down
            if (shouldTraverseTo(map, visited, new Point(curr.i, curr.j + 1))) {
                queue.add(new Point(curr.i, curr.j + 1));
            }
            // try down left
            if (shouldTraverseTo(map, visited, new Point(curr.i - 1, curr.j + 1))) {
                queue.add(new Point(curr.i - 1, curr.j + 1));
            }
        }
        return 1;
    }

    public static boolean isLand(int[][] map, Point startPoint) {
        return map[startPoint.i][startPoint.j] == 1;
    }

    public static boolean isVisited(int[][] visited, Point point) {
        return visited[point.i][point.j] == 1;
    }

    public static void markVisited(int[][] visited, Point point) {
        visited[point.i][point.j] = 1;
    }

    public static boolean shouldTraverseTo(int[][] map, int[][] visited, Point point) {
        if (point.i < 0 || point.i >= map.length || point.j < 0 || point.j >= map[0].length) {
            return false;
        }
        return !isVisited(visited, point) && isLand(map, point);
    }

    public static void main(String[] args) {
        // given two dimensional array of 0 and 1s, determine how many islands there are where an
        // island is completely surrounded by 0s on each side

        // this is just finding connected graphs, bfs and we count all 0s as leaf nodes
        // for each connected graph add it to a list
        // keep a visited list
        int [][] islands1 = {
            {1, 0, 0, 1},
            {1, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 1}
        };
        System.out.println(numIslands(islands1));
    }
}
