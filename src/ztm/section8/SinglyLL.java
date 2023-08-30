package ztm.section8;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class SinglyLL<E> {

    transient Node<E> head;
    transient int size;

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    public void addFirst(E element) {
        if(head == null) {
            Node<E> newNode = new Node<>(element, null);
            head = newNode;
        } else {
            Node<E> newNode = new Node<>(element, head);
            head = newNode;
        }
        size++;
    }

    public void addLast(E element) {
        //get last node
        Node<E> last = lastNode();
        if(last == null) {
            Node<E> newNode = new Node<>(element, null);
            head = newNode;
        } else {
            Node<E> newNode = new Node<>(element, null);
            last.next = newNode;
        }
    }

    public void forEachRemaining(Consumer<E> consumer) {
        Node<E> x = head;
        while (x != null) {
            consumer.accept(x.element);
            x = x.next;
        }
    }

    private Node<E> lastNode() {
        Node<E> x = head;
        if(x == null)
            return null;
        while(x !=null) {
            x = x.next;
        }
        return x;
    }

    public static void main(String[] args) {

        SinglyLL<Integer> singlyLL = new SinglyLL<>();
        singlyLL.addFirst(1);
        singlyLL.addFirst(2);
        singlyLL.addFirst(3);
        singlyLL.addFirst(4);
        singlyLL.addFirst(5);
        singlyLL.addFirst(6);

        singlyLL.forEachRemaining(System.out::print);
        Node headNode = singlyLL.reverse();
        System.out.println("After reversal");

        singlyLL.forEachRemaining(System.out::print);
    }

    private Node<E> reverse() {
        Node<E> curr = head;
        Node<E> prev = null;
        while(curr != null) {
            Node<E> next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
        System.out.println("After reversal head node :"+head.element);
        return head;
    }
}
