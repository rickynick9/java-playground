import java.util.*;
import java.util.function.Consumer;

/*
If you can’t stop the underlying collection from being modified during iteration,
create a clone of the target data structure and iterate through the clone
 */

public class ShoppingCart<E> implements Iterable<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /*
    Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
    */

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    transient Object[] elementData; // non-private to simplify nested class access
    private int size;

    /*
    The number of times this list has been structurally modified. Structural modifications are those that change the size of the list,
     or otherwise perturb it in such a fashion that iterations in progress may yield incorrect results.
    This field is used by the iterator and list iterator implementation returned by the iterator and listIterator methods.
    If the value of this field changes unexpectedly, the iterator (or list iterator) will throw a ConcurrentModificationException
    in response to the next, remove, previous, set or add operations.
    This provides fail-fast behavior, rather than non-deterministic behavior in the face of concurrent modification during iteration.
    */
    protected transient int modCount = 0;

    public ShoppingCart(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    public ShoppingCart() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public E remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] es = elementData;

        @SuppressWarnings("unchecked")
        E oldData = (E) elementData[index];
        fastRemove(es, index);
        return oldData;
    }

    /*
    The loop will simply repeat as long as i is less than length. It simply assumes i is already
    declared elsewhere.
     */
    public boolean remove(Object o) {
        final Object[] es = elementData;
        final int size = this.size;
        int i = 0;
        found: {
            if (o == null) {
                for (; i < size; i++)
                    if (es[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (o.equals(es[i]))
                        break found;
            }
            return false;
        }
        fastRemove(es, i);
        return true;
    }



    /*
    Parameters :
    source_arr : array to be copied from
    sourcePos : starting position in source array from where to copy
    dest_arr : array to be copied in
    destPos : starting position in destination array, where to copy in
    len : total no. of components to be copied.
     */
    private void fastRemove(Object[] es, int index) {
        modCount++;
        final int newSize;
        if((newSize = size -1) > index)
            System.arraycopy(es, index + 1, es, index, newSize - index);
        es[size = newSize] = null;
    }

    public boolean add(E e) {
        modCount++;
        if(elementData.length == size)
            elementData = grow();
        elementData[size] = e;
        size++;
        return true;
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private Object[] grow(int minCapacity) {
        return elementData = Arrays.copyOf(elementData,
                newCapacity(minCapacity));
    }

    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        return newCapacity;
    }

    @SuppressWarnings("unchecked")
    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear() {
        modCount++;
        final Object[] es = elementData;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        final Object[] es = elementData;
        final int size = this.size;
        for (int i = 0; modCount == expectedModCount && i < size; i++)
            action.accept(elementAt(es, i));
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }



    @Override
    public Iterator<E> iterator() {
        return new ShoppingCartIterator();
    }

    private class ShoppingCartIterator implements Iterator<E> {

        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        ShoppingCartIterator() {}

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            Object[] elementData = ShoppingCart.this.elementData;
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = ShoppingCart.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = elementData;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size && modCount == expectedModCount; i++)
                    action.accept(elementAt(es, i));
                // update once at end to reduce heap write traffic
                cursor = i;
                lastRet = i - 1;
                checkForComodification();
            }
        }


        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}

