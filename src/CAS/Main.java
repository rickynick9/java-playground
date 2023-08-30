package CAS;

/*
https://accessun.github.io/2017/03/05/What-the-heck-is-the-volatile-keyword-and-why-use-it/

 */
public class Main {
    //static boolean stop = false;

    static volatile boolean stop = false;

    public static void main(String[] args) {
        //new Thread(() -> stop = true, "WRITE-THREAD").start();

        new Thread(() -> {
            try {
                Thread.sleep(100); // block this thread for 100 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop = true;
        }, "WRITE-THREAD").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stop) {}
            }
        }, "READ-THREAD").start();

    }

}
