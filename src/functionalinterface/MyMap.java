package functionalinterface;

public interface MyMap<K, V> {

    void put(K key, V value);
    V get(K key);
    //<K> V getI(K key);
    //<K> getDetails(K key);

}
