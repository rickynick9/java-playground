package ztm.sorting;

import java.util.Arrays;

/*
Search element in a 2D matrix
M rows
N columns

Approach 1: We can perform binary search on each rows but the time complexity in this case would be O(Mlog(N))
Approach 2: We can flatten the matrix and time complexity in this case would be O(log(MN))

We are going to code approach 2
 */
public class P7 {

    public static int[] searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0, right = m * n - 1;
        int[] coordinates = new int[2];
        coordinates[0] = -1; coordinates[1] = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int mid_val = matrix[mid / n][mid % n];

            if (mid_val == target) {
                coordinates[0] = mid / n;
                coordinates[1] = mid % n;
                return coordinates;
            }
            else if (mid_val < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return coordinates;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        int target = 60;
        Arrays.stream(searchMatrix(matrix, target)).forEach(e -> {
            System.out.print(" "+e+ " ");
        });
    }
}
