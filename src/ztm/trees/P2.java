package ztm.trees;

import ztm.section8.LinkedListC;

import java.util.*;

public class P2 {

    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static class ListNode {
        int value;
        ListNode next;

        public ListNode(int value) {
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

    private static List<ListNode> levelOrder(Node root) {
        //Each level will be stored in a linked list. This list stores the head of linked list at each level.
        List<ListNode> list = new ArrayList<>();

        Deque<Node> q = new ArrayDeque<>();
        q.push(root);

        while (!q.isEmpty()) {
            //before you take out the node, store the size of queue
            ListNode head = null;
            ListNode curr = null;
            int levelNodes = q.size();
            while(levelNodes > 0) {
                Node n = q.poll();
                ListNode ln = new ListNode(n.value);
                if(head == null) {
                    head = ln;
                    curr = ln;
                } else {
                    curr.next = ln;
                    curr = curr.next;
                }

                if(n.left != null)
                    q.offer(n.left);
                if(n.right != null)
                    q.offer(n.right);

                levelNodes--;
            }

            list.add(head);
        }

        return list;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Node root = createMinimalBST(arr, 0, arr.length -1);

        List<ListNode> ln = levelOrder(root);
        ln.forEach(n -> {
            System.out.print("head node :"+n.value);
            ListNode local = n;
            while (local != null) {
                System.out.print(" --> "+local.value+" ");
                local = local.next;
            }
            System.out.println("\n");
        });
    }
}
