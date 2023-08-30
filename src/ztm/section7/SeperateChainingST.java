package ztm.section7;

import java.util.HashMap;

public class SeperateChainingST<Key, Value> {

    private static final int noOfBuckets = 16;
    // Symbol Table
    //private final Node[] st = new Node[noOfBuckets];
    transient Node<Key, Value>[] st = new Node[noOfBuckets];


    private static class Node<Key, Value> {
        // Keys and values can be generic
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % noOfBuckets;
    }

    public Value get (Key key) {
        int hash = hash(key);
        for(Node x = st[hash]; x !=null; x = x.next) {
            if(key.equals(x.key))
                return (Value)  x.value;
        }
        return null;
    }

    public void put(Key key, Value value) {
        int hash = hash(key);
        Node n = st[hash];
        while(n.next != null) {
            if(key.equals(n.key))
                return;
            n = n.next;
        }
        Node newNode = new Node(key, value, n);
        st[hash] = newNode;
    }

    // Under uniform hashing assumption, If we divide the search cost, which would be N if we have sequential search
    // by a factor of M - no of buckets, N/M would be extremely close to 1.
    /*
    Consequences :
    Number of probes for search or insert is proportional to N/M i.e M times faster than linear search given the
    distribution is uniform.

    If M is too large then we will have too many empty chains.
    M too small -> Chains too long



     */

}
