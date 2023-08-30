package CAS;

import java.util.PriorityQueue;

/*
public static void main(String[] args) {
    System.out.println(Thread.activeCount());

    //doSomething()
}
The code above output 2

GC occupies a thread, so you get 2, instead of 1

activeCount() static method which counts and returns the number of active threads in the current thread group
and subgroups.

Here, we call the yield() method on the Thread object. Calling the yield() method on the Thread object pauses the
currently executing thread and puts it in Ready state and selects another runnable thread from the ready state to
start its execution.

 */
public class CasTest {

    private static int NUMBER_OF_THREADS = 10;


    public static void main(String[] args) {
        //System.out.println(Thread.activeCount());
        MyAtomicInteger atomicInteger = new MyAtomicInteger();

        for(int i =0; i< NUMBER_OF_THREADS; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.increment();
                }
            },"Thread-"+i).start();
        }

        // 2 because 1 thread is main thread and another thread is GC thread
        while(Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("Final Result :"+atomicInteger.getCounter());
    }
}
