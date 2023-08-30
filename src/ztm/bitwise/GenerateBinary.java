package ztm.bitwise;

import java.util.ArrayDeque;
import java.util.Queue;

/*
Given a positive number n, efficiently generate binary numbers between 1 and n using the queue data structure in
linear time.

For example, for n = 16, the binary numbers are:
1 10 11 100 101 110 111 1000 1001 1010 1011 1100 1101 1110 1111 10000




 */
public class GenerateBinary {

    public static void main(String[] args) {
        int n = 16;
        generate(n);
    }

    private static void generate(int n) {
        int i = 1;
        Queue<String> queue = new ArrayDeque<>();
        queue.add("1");
        while(i <= n) {
            queue.add(queue.peek() + "0");
            queue.add(queue.peek() + "1");
            System.out.print(queue.poll() + ' ');
            i++;
        }
    }

}
