package ztm.array;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class DynamicArray {

    private int[] arr;
    private int size;
    private static final int DEFAULT_CAPACITY = 5;

    public DynamicArray(int initialCapacity) {
        if(initialCapacity >= 0) {
            arr = new int[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    DynamicArray() {
        arr = new int[DEFAULT_CAPACITY];
    }

    public boolean add(int element) {
        if(size == arr.length)
            grow();
        arr[size++] = element;
        return true;
    }

    private void grow() {
        int oldCapacity = this.size;
        System.out.println("Old capacity is :"+oldCapacity);
        int newCapacity = oldCapacity * 2;
        System.out.println("New Capacity is :"+newCapacity);
        arr = Arrays.copyOf(arr, newCapacity);
    }

    // To remove - Find the index then remove
    public boolean remove(int num) {
        for (int i = 0; i< arr.length; i++) {
            if(arr[i] == num) {
                fastRemove(i);
                return true;
            }
        }
        return false;
    }

    public void fastRemove(int index) {
        int newSize = size - 1;
        System.arraycopy(arr, index + 1, arr, index, newSize - index);
        arr[size = newSize] = 0;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        }
        System.arraycopy(arr, toIndex, arr, fromIndex, size - toIndex);
    }

    public int[] get() {
        return Arrays.copyOf(arr, arr.length);
    }

    public IntItr intItr(int index) {
        rangeCheckForAdd(index);
        return new IntItr(index);
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();
    }

    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray();
        int min = 100;
        int max = 200;
        for(int i=0; i<20; i++) {
            dynamicArray.add(ThreadLocalRandom.current().nextInt(min, max + 1));
        }

        dynamicArray.intItr(3).forEachRemaining((e) -> {
            System.out.println("Remaining elements : "+e);
        });
        //Arrays.stream(dynamicArray.get()).forEach(System.out::println);
    }

    class IntItr implements IntIterator {

        int cursor; // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        IntItr(int index) {
            cursor = index;
        }
        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public int next() {
            int i = cursor;
            if(cursor > size)
                throw new NoSuchElementException();

            cursor++;
            return arr[i];
        }

        @Override
        public void forEachRemaining(IntConsumer consumer) {
            Objects.requireNonNull(consumer);
            int i = cursor;
            if(i < size) {
                for(; i< size; i++)
                    consumer.accept(arr[i]);
            }
        }
    }

}
