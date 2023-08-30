package ztm.section7;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HashMapWithResize<K, V> {

/*

Load factor is defined as (m/n) where n is the total size of the hash table and m is the preferred number of entries
which can be inserted before a increment in size of the underlying data structure is required.

Rehashing is a technique in which the table is resized, i.e., the size of table is doubled by creating a new table.

The Hash table provides Constant time complexity of insertion and searching, provided the hash function is able to
distribute the input load evenly.

That’s because if each element is at a different index, then we can directly calculate the hash and locate it at that
index, but in case of Collision, the time complexity can go up to O(N) in the worst case.

Even with the best possible Hash function we will run out of vacant indexes and will result in unavoidable collisions.
To handle such a scenario we have the Load Factor in hashing which is basically a measure that decides when exactly
to increase the size of the Hash Table to maintain the same time complexity of O(1).

The Load factor is a measure that decides when to increase the HashTable capacity to maintain the search and insert
operation complexity of O(1).
The default load factor of HashMap used in java, for instance, is 0.75f (75% of the map size).
That means if we have a HashTable with an array size of 100, then whenever we have 75 elements stored, we will
increase the size of the array to double of its previous size i.e. to 200 now, in this case.

 */

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient Node<K,V>[] table;

    transient int size;
    int threshold;
    final float loadFactor;

    static class Node<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;

            return o instanceof Map.Entry<?, ?> e
                    && Objects.equals(key, e.getKey())
                    && Objects.equals(value, e.getValue());
        }
    }

//    static final int hash(Object key) {
//        int h;
//        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
//    }

    public HashMapWithResize(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +loadFactor);
        this.loadFactor = loadFactor;
        //this.threshold = tableSizeFor(initialCapacity);
    }

    private int hash(K key) {
        int noOfBuckets = table.length;
        return (key.hashCode() & 0x7fffffff) % noOfBuckets;
    }

//    public boolean put(K key, V value) {
//        int hash = hash(key);
//        Node<K,V> newNode = new Node<>(key, value, )
//    }

//    final Node<K,V>[] resize() {
//        Node<K,V>[] oldTab = table;
//        int oldCapacity = (oldTab == null) ? 0 : oldTab.length;
//        //load factor = bucket_size / no of nodes
//        int newCapacity = oldCapacity << 1; //double
//        Node<K,V>[] newTable = (Node<K,V>[])new Node[newCapacity];
//
//        for(int i=0; i< oldCapacity; i++) {
//
//        }
//
//    }

}
