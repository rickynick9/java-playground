package ztm.section8.queue;

import java.util.ArrayDeque;
import java.util.Deque;

//offer
// poll
//remove from front

public class QueueUsingStacks {

    Deque<Integer> s1 = new ArrayDeque<>();
    Deque<Integer> s2 = new ArrayDeque<>();

    public void offer(int element) {
        s1.push(element);
    }

    public int poll() {
        while(!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        int element = s2.pop();

        while(!s2.isEmpty()) {
            s1.push(s2.pop());
        }
        return element;
    }

    public int peek() {
        while(!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        int element = s2.peek();
        while (!s2.isEmpty()) {
            s1.push(s2.pop());
        }
        return element;
    }

    public boolean empty() {
        return s1.isEmpty();
    }

    public static void main(String[] args) {

        QueueUsingStacks queue = new QueueUsingStacks();
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);
        queue.offer(40);
        queue.offer(50);

        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }

}
