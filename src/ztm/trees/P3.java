package ztm.trees;

import java.util.ArrayList;
import java.util.List;

/*
Balanced binary tree
getHeight can be modified to check if binary tree is balanced or not.
Difference b/w Height of left and right subtree is not more than 1
https://www.geeksforgeeks.org/balanced-binary-tree/

Check If Binary tree is binary search tree
To check this we can traverse inorder and store elements in an array.
Compare arr[i] and arr[i-1], if arr[i-1] > arr[i] then its not BST
 */
public class P3 {

    private static int index = 0;
    static class TreeNode {
        int number;
        TreeNode left;
        TreeNode right;

        int size;

        public TreeNode(int number) {
            this.number = number;
        }

        public TreeNode(int number, int size) {
            this.number = number;
            this.size = size + 1;
        }
    }

    private static TreeNode balancedBtree(int[] arr, int left, int right) {
        if(left > right)
            return null;

        int mid = (left + right) / 2;
        TreeNode n = new TreeNode(arr[mid], right);
        n.left = balancedBtree(arr, left, mid -1);
        n.right = balancedBtree(arr, mid + 1, right);

        return n;
    }

    private static int height(TreeNode root) {
        if(root == null)
            return 0;

        int leftH = height(root.left);
        int rightH = height(root.right);

        return Math.max(leftH, rightH) + 1;
    }

    private static int checkBalance(TreeNode root) {
        if(root == null)
            return 0;

        int leftH = checkBalance(root.left);
        int rightH = checkBalance(root.right);

        if(Math.abs(leftH - rightH)  > 1) {
            return Integer.MIN_VALUE;
        } else {
            return Math.max(leftH, rightH) + 1;
        }
    }

    private static void levelOrderRecursion(TreeNode root, int level) {
        if(root == null)
            return;
        if(level == 0) {
            System.out.print(root.number + " ");
        } else if(level > 0) {
            levelOrderRecursion(root.left, level - 1);
            levelOrderRecursion(root.right, level - 1);
        }
    }

    private static void inorder(TreeNode root, int[] arr) {
        if(root == null)
            return;

        inorder(root.left, arr);
        arr[index] = root.number;;
        index++;
        inorder(root.right, arr);
    }

    private static boolean checkBST(TreeNode root) {
        if(root == null) return true;

        System.out.println("Size of BST :"+root.size);
        int[] arr = new int[root.size];
        inorder(root, arr);

        for(int i=0; i<arr.length -1; i++) {
            if(arr[i] > arr[i+1])
                return false;
        }

        return true;
    }


    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        TreeNode root = balancedBtree(arr, 0, arr.length -1);

        int height = height(root);
        for(int i=0; i< height; i++)
            levelOrderRecursion(root, i);

        System.out.println("\nIs balanced :"+checkBalance(root));

        System.out.println("\n Check BST :"+checkBST(root));

    }
}
