package com.dtran.other;

public class RotateMatrix {
    public static long[][] solution(long[][] matrix) {
        // rotate square matrix
        // Type your solution here
        long[][] rotated = new long[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                rotated[j][matrix.length - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }
}
