package com.dtran.other;

/*

1.
Given an m * n matrix of 1s and 0s , the value in it is either 1 or 0. 1 means there is a person; 0 means there is no person.
If there are 2 or more persons on the same row or the same column, then they can see each other.
Return the number of persons who can see others.
Example:
Input:
[
  [ 1 0 0 0 ],
  [ 0 0 1 1 ],
  [ 0 1 0 0 ],
  [ 0 1 0 0 ]
]
Output:
4
Explanation:
person at [1][2], [1][3] can see others
person at [2][1], [3][1] can see others
In total, there are 4 people who can see others

 */
public class NumPeopleSeeEachother {
    public static int numPeopleSeeEachOther(int[][] matrix) {
        int[] row = new int[matrix.length];
        int[] column = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    row[i] += 1;
                    column[j] += 1;
                }
            }
        }

        int peopleCanSeeEachother = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1 && (row[i] > 1 || column[j] > 1)) {
                    peopleCanSeeEachother += 1;
                }
            }
        }
        return peopleCanSeeEachother;
    }

    public static void main(String[] args) {
        int[][] matrix1 = new int[][] {
            {1, 0, 0, 0},
            {0, 0, 1, 1},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
        };
        System.out.println(numPeopleSeeEachOther(matrix1));
        int[][] matrix2 = new int[][] {
            {1, 0, 0, 0},
            {0, 1, 1, 1},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
        };
        System.out.println(numPeopleSeeEachOther(matrix2));
        int[][] matrix3 = new int[][] {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
        };
        System.out.println(numPeopleSeeEachOther(matrix3));

    }
}
