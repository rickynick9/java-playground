package CAS;

public class MyAtomicInteger {

    private volatile int value;

    public MyAtomicInteger() {
        this.value = 0;
    }

    public MyAtomicInteger(int counter) {
        this.value = counter;
    }

    public int getCounter() {
        return value;
    }

    public void setCounter(int counter) {
        this.value = counter;
    }

    public void increment() {
        int expectedVal = value;
        int newVal = expectedVal + 1;
        if(!compareAndSet(expectedVal, newVal)) {
            //expected value was changed by some other thread. call increment() again
            increment();
        }
    }

    synchronized private boolean compareAndSet(int expectedVal, int newVal) {
        boolean successful = false;
        int currentValueInMainMemory = value;
        if(expectedVal == value) {
            value = newVal;
            successful =  true;
        }
        // To see it more clearly, let's check out which thread is trying to set value
        System.out.println(Thread.currentThread().getName() + " trying to set value. Successful? " + successful);
        return successful; // notify the invoker if the set operation is successful
    }
}
