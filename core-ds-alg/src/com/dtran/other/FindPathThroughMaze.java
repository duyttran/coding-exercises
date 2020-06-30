package com.dtran.other;

import java.util.LinkedList;
import java.util.Queue;

public class FindPathThroughMaze {
    public static class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static boolean isValid(int x, int y, long[][] visited, long[][] maze) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (x >= visited.length || y >= visited.length) {
            return false;
        }
        if (maze[x][y] == 1) {
            return false;
        }
        if (visited[x][y] == 1) {
            return false;
        }
        return true;
    }

    public static boolean solution(long[][] maze, long n) {
        long[][] visited = new long[maze.length][maze.length];
        if (!isValid(0, 0, visited, maze)) {
            return false;
        }
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(0, 0));
        while (!queue.isEmpty()) {
            Position position = queue.poll();
            if (position.x == maze.length - 1 && position.y == maze.length - 1) {
                return true;
            }
            visited[position.x][position.y] = 1;
            if (isValid(position.x - 1, position.y, visited, maze)) {
                queue.add(new Position(position.x - 1, position.y));
            }
            if (isValid(position.x, position.y - 1, visited, maze)) {
                queue.add(new Position(position.x, position.y - 1));
            }
            if (isValid(position.x + 1, position.y, visited, maze)) {
                queue.add(new Position(position.x + 1, position.y));
            }
            if (isValid(position.x, position.y + 1, visited, maze)) {
                queue.add(new Position(position.x, position.y + 1));
            }
        }
        return false;
    }


}
