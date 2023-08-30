package ztm.section12;

public class RecursionDemo {
    private int counter;
    private String inception() {
        System.out.println("counter is :"+counter);
        if(counter > 3) {
            return "Done!";
        }
        counter++;
        return inception();
    }
}
