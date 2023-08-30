package ztm.sorting;

import ztm.section10.BinarySearchTree;

import java.util.ArrayDeque;
import java.util.Deque;

public class BTree {

    private Node root;
    class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    public BTree() {
        this.root = null;
    }

    private void insert(int number) {
        Node newNode = new Node(number);
        if(root == null)
            root = newNode;
        else {
            Node ptr = root;
            Node parent = root;
            while (ptr !=null) {
                parent = ptr;
                if(number < ptr.value)
                    ptr = ptr.left;
                else if(number > ptr.value)
                    ptr = ptr.right;
            }

            if(number > parent.value)
                parent.right = newNode;
            else
                parent.left = newNode;
        }
    }

    private Node search(int number) {
        Node ptr = root;
        while(ptr.left != null || ptr.right != null) {
            if(ptr.value < number)
                ptr = ptr.left;
            else if(ptr.value > number)
                ptr = ptr.right;
            else return ptr;
        }
        return null;
    }

    public void inorderIterative() {
        // create an empty stack
        Deque<Node> stack = new ArrayDeque<>();

        // start from the root node (set current node to the root node)
        Node curr = root;

        // if the current node is null and the stack is also empty, we are done
        while (!stack.isEmpty() || curr != null) {
            // if the current node exists, push it into the stack (defer it)
            // and move to its left child
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                // otherwise, if the current node is null, pop an element from
                // the stack, print it, and finally set the current node to its
                // right child
                curr = stack.pop();
                System.out.print(curr.value + " ");

                curr = curr.right;
            }
        }
    }

    public void inorderRecursive(Node n) {
        if(n == null)
            return;
        inorderRecursive(n.left);
        System.out.print(n.value+ " ");
        inorderRecursive(n.right);
    }


    public static void main(String[] args) {
        BTree bTree = new BTree();
        bTree.insert(20);
        bTree.insert(15);
        bTree.insert(25);
        bTree.insert(10);
        bTree.insert(23);
        bTree.insert(5);
        bTree.insert(13);
        bTree.insert(24);

        bTree.inorderIterative();
        System.out.println("\n");
        bTree.inorderRecursive(bTree.root);

    }
}
