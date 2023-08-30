package ztm.sorting;

public class P8 {

    private Node root;
     class Node {
        private int value;
        private Node left;
        private Node right;
        private int rank;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.rank = 0;
        }

        public void incrementRank() {
            this.rank++;
        }

        public int getRank() {
            return this.rank;
        }
    }

    public void insert(int value) {
        Node ptr = root;
        Node parent = root;
        Node newNode = new Node(value);

        if(root == null)
            root = newNode;
        else {
            while(ptr !=null) {
                parent = ptr;
                if(value < ptr.value) {
                    ptr.incrementRank();
                    ptr = ptr.left;
                } else if(value > ptr.value) {
                    ptr = ptr.right;
                }
            }

            if(value > parent.value)
                parent.right = newNode;
            else if(value < parent.value)
                parent.left = newNode;
        }
    }

    public void inorderRecursive(Node n) {
        if(n == null)
            return;
        inorderRecursive(n.left);
        System.out.println(n.value+ " ---> "+n.rank);
        inorderRecursive(n.right);
    }

    public int getRank(Node n, int x) {
         if(x == n.value)
             return n.rank;
         else if(x < n.rank)
             return getRank(n.left, x);
         else
             return n.rank + getRank(n.right, x) + 1;
    }

    public static void main(String[] args) {
        P8 bTree = new P8();
        bTree.insert(20);
        bTree.insert(15);
        bTree.insert(25);
        bTree.insert(10);
        bTree.insert(23);
        bTree.insert(5);
        bTree.insert(13);
        bTree.insert(24);

        bTree.inorderRecursive(bTree.root);

        System.out.println("rank of node 25 :"+bTree.getRank(bTree.root, 25));
    }




}
