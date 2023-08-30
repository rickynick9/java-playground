package ztm.trees;

import ztm.sorting.BTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
Given a sorted array with unique integer elements, write an algorithm to create a binary search tree with
minimal height.

Minimal height tree can only be created if number of left nodes id equal to number of right nodes.
this means we have to take mid as root node and recursively create left and right subtree.

 */
public class P1 {

    private static List<Integer> ans = new ArrayList<>();//to store ans in this list

    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static Node createMinimalBST(int[] arr, int left, int right) {
        if(left > right)
            return null;

        int mid = (left + right) / 2;
        Node n = new Node(arr[mid]);
        n.left = createMinimalBST(arr, left, mid -1);
        n.right = createMinimalBST(arr, mid + 1, right);

        return n;
    }

    private static void inorder(Node root) {
        if(root == null)
            return;

        inorder(root.left);
        System.out.print(root.value+ " ");
        inorder(root.right);
    }

    private static void preorder(Node root) {
        if(root == null)
            return;

        System.out.print(root.value+ " ");
        preorder(root.left);
        preorder(root.right);
    }

    public static List<Integer> preorderTraversal(Node root) {
        if(root == null) return ans;//return the list

        ans.add(root.value);//add val to the ans List

        preorderTraversal(root.left);//left call by recursion
        preorderTraversal(root.right);// right call by recurtion

        return ans;//rsturn the stored list of ans
    }

    public static int getHeight(Node root) {
        if(root == null)
            return 0;

        int leftH = getHeight(root.left);
        int rightH = getHeight(root.right);

        if(leftH > rightH)
            return leftH + 1;
        else
            return rightH + 1;
    }

    public static void levelOrder(Node root, int level) {
        if(root == null)
            return;
        if(level == 1) {
            System.out.print(root.value + " ");
        } else if(level > 1){
          levelOrder(root.left, level -1);
          levelOrder(root.right, level -1);
        }
    }

    public static void levelOrderQ(Node root) {
        Deque<Node> q = new ArrayDeque<>();
        q.push(root);

        while (!q.isEmpty()) {
            Node n = q.poll();
            System.out.print(n.value + " ");
            if(n.left != null)
                q.offer(n.left);
            if(n.right != null)
                q.offer(n.right);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Node root = createMinimalBST(arr, 0, arr.length -1);
        inorder(root);
        //preorder(root);
        System.out.println("\n");

        int height = getHeight(root);
        System.out.println("Height is :"+height);
        for(int i =1; i<= height; i++)
            levelOrder(root, i);

        System.out.println("\n");
        levelOrderQ(root);

        //preorderTraversal(root).forEach(e -> System.out.print(" "+e+ " "));
    }
}
