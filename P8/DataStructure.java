// ---------- Stack ADT (array implementation, LIFO) ----------
// Operations: push(x), pop(), peek(), isEmpty(), isFull()
class Stack {
    private int[] arr;
    private int top = -1;           // -1 means the stack is empty

    Stack(int size) {
        arr = new int[size];
    }

    boolean isEmpty() { return top == -1; }
    boolean isFull()  { return top == arr.length - 1; }

    void push(int x) {
        if (isFull()) {
            System.out.println("Stack Overflow: cannot push " + x);
            return;
        }
        arr[++top] = x;
        System.out.println("Pushed " + x);
    }

    int pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow: stack is empty");
            return -1;
        }
        int x = arr[top--];
        System.out.println("Popped " + x);
        return x;
    }

    int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return -1;
        }
        System.out.println("Peek: " + arr[top]);
        return arr[top];
    }
}

// ---------- Queue ADT (linked node implementation, FIFO) ----------
// Operations: enqueue(x), dequeue(), front(), isEmpty()
class Node {
    int data;
    Node next;

    Node(int data) { this.data = data; }
}

class Queue {
    private Node head;   // front of the queue (remove from here)
    private Node tail;   // rear of the queue (add here)

    boolean isEmpty() { return head == null; }

    void enqueue(int x) {
        Node n = new Node(x);
        if (isEmpty()) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        System.out.println("Enqueued " + x);
    }

    int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow: queue is empty");
            return -1;
        }
        int x = head.data;
        head = head.next;
        if (head == null) tail = null;   // queue became empty
        System.out.println("Dequeued " + x);
        return x;
    }

    int front() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        System.out.println("Front: " + head.data);
        return head.data;
    }
}

public class DataStructures {
    public static void main(String[] args) {

        System.out.println("=== Stack ===");
        Stack s = new Stack(3);
        s.push(10);
        s.push(20);
        s.push(30);
        s.push(40);      // overflow
        s.peek();
        s.pop();
        s.pop();
        s.pop();
        s.pop();         // underflow

        System.out.println("=== Queue ===");
        Queue q = new Queue();
        q.front();       // empty queue
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.front();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();     // underflow
    }
}
