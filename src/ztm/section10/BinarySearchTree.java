package ztm.section10;

/*
            9
      4           20
   1     6     15       170
 */
public class BinarySearchTree {

    private Node root;
    class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(int element) {
        Node newNode = new Node(element);
        if(root == null) {
            root = newNode;
            return;
        }

        Node currentNode = root;
        while (true) {
            if(element < currentNode.value) {
                //left tree
                if(currentNode.left == null) {
                    currentNode.left = newNode;
                    return;
                }
                currentNode = currentNode.left;
            } else {
                //right tree
                if(currentNode.right == null) {
                    currentNode.right = newNode;
                    return;
                }
                currentNode = currentNode.right;
            }
        }
    }

    /*
    Pre-order traversal sequence:  (root, left, right)
    In-order traversal sequence:  (left, root, right)
    Post-order traversal sequence: (left, right, root)

     */
    public void traverse(Node node) {
        if(node == null)
            return;
        traverse(node.left);
        System.out.print(node.value+ " ");
        traverse(node.right);
    }

   // even though we have while loop we are not visiting each and every node.
    // each node we visit, we make a decision whether to go left or right
    public boolean lookUp(int value) {
        if(root == null)
            return false;
        Node currentNode = root;
        while(currentNode != null) {
            if (value < currentNode.value) {
                currentNode = currentNode.left;
            } else if(value > currentNode.value) {
                currentNode = currentNode.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean remove(int value) {
        if(root == null)
            return false;
        Node currentNode = this.root;
        Node parentNode = null;
        while(currentNode !=null) {
            if(value < currentNode.value) {
                parentNode = currentNode;
                currentNode = currentNode.left;
            } else if(value > currentNode.value) {
                parentNode = currentNode;
                currentNode = currentNode.right;
            } else if(value == currentNode.value) {
                //We have a match, get to work

                //Option 1: it's a leaf node. No left and right child
                if(currentNode.left == null && currentNode.right == null) {
                    if(parentNode.left.value == currentNode.value) {
                        //leaf node is left child of parent. set parent left pointer to null
                        parentNode.left = null;
                    } else if (parentNode.right.value == currentNode.value) {
                        parentNode.right = null;
                    }
                }

                //Option 2 : Node has one child, either left or right
                if(currentNode.left == null || currentNode.right == null) {
                    Node child = currentNode.left == null ? currentNode.right: currentNode.left;
                    if(currentNode.left == null) {
                        parentNode.right = child;
                    } else {
                        parentNode.left = child;
                    }
                }

                //Option 3 : Node has both children. Find inorder successor node
                // i.e. find the smallest value in right subtree, copies the value of inorder successor to current node
                // delete in order successor

                if(currentNode.left != null && currentNode.right != null) {
                    Node temp = currentNode;
                    //Node tempParent = null;
                    while (temp.right != null) {
                        //tempParent = temp;
                        temp = temp.right;
                    }
                    currentNode.value = temp.value;
                    //recursively delete successor node
                    remove(temp.value);
                }

                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(9);
        tree.insert(4);
        tree.insert(6);
        tree.insert(20);
        tree.insert(170);
        tree.insert(15);
        tree.insert(1);

        tree.traverse(tree.root);
        System.out.println(tree.lookUp(170));
        System.out.println(tree.lookUp(30));
    }

}
