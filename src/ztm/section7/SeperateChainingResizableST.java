package ztm.section7;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class SeperateChainingResizableST<K, V> {

/*
    Writing 1 << 4 instead of 16 doesn't change the behavior here. It's done to emphasize that the number is a power
    of two, and not a completely arbitrary choice. It thus reminds developers experimenting with different numbers that
    they should stick to the pattern (e.g., use 1 << 3 or 1 << 5) 1<< 3 = 2^3 = 8
    1<< 4 = 2^ 4 = 16
    1<< 5 = 2^5 = 32

    The powers of 2 have only one set bit in their Binary representation.
    1 -> 1
    2 -> 10
    4 -> 100
    8 -> 1000
    16 -> 10000
    32 -> 100000
    64 -> 1000000

    To check if a number is power of 2, we need to continiously divide that number by 2. The time complexity of this
    would be O(logn). We can do this in constant time using bitwise operator.

    If we subtract 1 from a power of 2 what we get is 1s till the last unset bit and if we apply Bitwise AND operator
    we should get only zeros.
    n & (n - 1) will not return 0 if number n is power of 2
    Example :
    16	        0	0	0	1	0	0	0	0
    16-1=15	    0	0	0	0	1	1	1	1
    16 AND 15	0	0	0	0	0	0	0	0

    Hence, 16 is a power of 2.

    15	        0	0	0	0	1	1	1	1
    15-1=14	    0	0	0	0	1	1	1	0
    15 AND 14	0	0	0	0	1	1	1	0
    Hence, 15 is not a power of 2.

    bool is_power_of_2(int x) {
	    // Check if the number has only one set bit
	    if ((x & (x - 1)) != 0)
		    return false;
	    return true;
    }
*/

/*
    HashMap adopts this unconventional design, mainly to optimize the modulus and expansion.

    Modulo : modulo reduction involves a division, and divisions are expensive.
    More sophisticated techniques are available in newer processors. Generally, processors strive to parallelize
    bit-pairs operations in order to minimize the clock cycles required. Multiplication algorithms can be parallelized
    quite effectively (though more transistors are required).

    Division algorithms can't be parallelized as efficiently. The most efficient division algorithms are quite complex
    (The Pentium FDIV bug demonstrates the level of complexity). Generally, they requires more clock cycles per bit.

    Bit masking is less expensive in most modern processors.

    In your specific case, where you say my_index = (my_index + 1) % 8; then the compiler -- even with optimizations
    set at their lowest level -- will probably turn that into the machine language equivalent of
    my_index = (my_index + 1) & 0x0007;. In other words, it won't divide (way expensive), it won't even
    branch (less expensive), but it'll mask (least expensive on most processors today). But this will only happen if
    the modulo is by a power of two.

    You could insure that by just using my_index = (my_index + 1) & 0x0007;, at the cost of code comprehension and thus
    code maintainability.

    The Trivial Case: Mod 2, Mod 4, Mod 2^i
    Computing modulus for poweres of two is trivial on a binary computer, the term (b mod m) is zero, so we just take
    the modulus by examining the least significant bits of the binary representation:
    a mod 2^i = a & (2^i–1)

    Thus, for a mod 2, we use a & 1, for a mod 4, we use a & 3, and for a mod 8, we use a & 7.
 */

/*
    Load Factor :
    Threshold : The maximum number of nodes HashMap can hold
    threshold = length * Load factor
    the larger the load factor, the more key-value pairs it can accommodate

    If the number exceeds threshold, it will be resized (expanded). The capacity of the expanded HashMap is twice that
    of the previous capacity. The default load factor of 0.75 is a balanced choice for space and time efficiency.

    The size field is actually easy to understand, it is the number of key-value pairs that actually exist in the HashMap.
*/

    private static final int DEFAULT_CAPACITY = 1 << 4; // aka 16 - no of buckets
    private static float DEFAULT_LOAD_FACOTOR = 0.75f;
    private static final int MAX_CAPACITY = 1<< 31;

    transient Node<K,V>[] table;
    transient int size; // no of nodes present currently
    final float loadFactor;
    int threshold; // max no of nodes table can hold i.e. threshold = loadFactor * DEFAULT_CAPACITY

    static class Node<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public SeperateChainingResizableST() {
        this.table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        this.loadFactor = DEFAULT_LOAD_FACOTOR;
        this.threshold = maxNodes();
    }

    private int maxNodes () {
        return (int) (this.table.length * this.loadFactor);
    }

    private int hash(K key) {
        return key.hashCode() & 0x7fffffff;
    }

    public V put(K key, V value) {
        // no of nodes is greater than threshold
        if(size > threshold)
            resize();
        int hashCode = hash(key);
        //modulo operation to find bucket
        //int bucket = hashCode % table.length;
        int bucket = hashCode & (table.length -1);
        Node<K,V> firstNode = table[bucket];
        Node<K,V> n = firstNode;
        while(n != null) {
            if(n.key.equals(key))
                return n.value;
            n = n.next;
        }
        Node<K,V> newNode = new Node<>(hashCode, key, value, firstNode);
        table[bucket] = newNode;
        size++;
        return newNode.value;
    }

    final Node<K,V>[] resize() {
        Node<K,V>[] oldTable = table;
        int oldCapacity = table.length;
        int newCapacity = oldCapacity << 1;
        table = new Node[newCapacity];
        threshold = (int) (table.length * loadFactor);

        //bofore resizing we must set its size to 0;
        size = 0;
        for(int i=0; i< oldCapacity; i++) {
            Node<K,V> oldNode = oldTable[i];
            while(oldNode != null) {
                put(oldNode.key, oldNode.value);
                oldNode = oldNode.next;
            }
        }
        return table;
    }

    public V get (K key) {
        int hash = hash(key);
        int bucket = hash & (table.length -1);
        for(Node x = table[bucket]; x !=null; x = x.next) {
            if(key.equals(x.key))
                return (V)  x.value;
        }
        return null;
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        Node<K,V>[] tab;
        if (action == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            for (Node<K,V> e : tab) {
                for (; e != null; e = e.next)
                    action.accept(e.key, e.value);
            }
        }
    }


    public static void main(String[] args) {
        SeperateChainingResizableST<String, Integer> table = new SeperateChainingResizableST<>();
        table.put("AA", 10);
        table.put("BB", 20);
        table.put("CC", 30);
        table.put("DD", 40);

        table.put("EE", 50);
        table.put("FF", 60);
        table.put("GG", 70);
        table.put("HH", 80);

        table.put("II", 90);
        table.put("JJ", 100);
        table.put("KK", 110);
        table.put("LL", 120);

        table.put("MM", 130);
        table.put("NN", 140);
//        table.put("OO", 150);
//        table.put("PP", 160);

        System.out.println("Table size :"+table.size);
        System.out.println("LL :"+table.get("LL"));

        table.forEach((k,v) ->{
            System.out.println("Key : "+k+ " Value: "+v);
        });
    }


}
