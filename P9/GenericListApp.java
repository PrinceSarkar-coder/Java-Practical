class Node<T> {
    T data;
    Node<T> next;

    Node(T data) { this.data = data; }
}

class MyList<T> {
    private Node<T> head;
    private int size = 0;

    boolean isEmpty() { return head == null; }
    int size() { return size; }

    // Insert at the beginning
    void addFirst(T x) {
        Node<T> n = new Node<>(x);
        n.next = head;
        head = n;
        size++;
    }

    // Insert at the end
    void addLast(T x) {
        Node<T> n = new Node<>(x);
        if (isEmpty()) {
            head = n;
        } else {
            Node<T> cur = head;
            while (cur.next != null)
                cur = cur.next;
            cur.next = n;
        }
        size++;
    }

    // Insert at a given position
    void insertAt(int index, T x) {
        if (index < 0 || index > size) {
            System.out.println("Invalid index: " + index);
            return;
        }
        if (index == 0) {
            addFirst(x);
            return;
        }
        Node<T> cur = head;
        for (int i = 0; i < index - 1; i++)
            cur = cur.next;
        Node<T> n = new Node<>(x);
        n.next = cur.next;
        cur.next = n;
        size++;
    }

    // Delete the first occurrence of a value
    boolean delete(T x) {
        if (isEmpty()) {
            System.out.println("List is empty, nothing to delete");
            return false;
        }
        if (head.data.equals(x)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> cur = head;
        while (cur.next != null && !cur.next.data.equals(x))
            cur = cur.next;
        if (cur.next == null)
            return false;                 // value not found
        cur.next = cur.next.next;
        size--;
        return true;
    }

    // Search: returns the index, or -1 if not found
    int search(T x) {
        Node<T> cur = head;
        int i = 0;
        while (cur != null) {
            if (cur.data.equals(x))
                return i;
            cur = cur.next;
            i++;
        }
        return -1;
    }

    // Traversal
    void display() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return;
        }
        Node<T> cur = head;
        while (cur != null) {
            System.out.print(cur.data + " -> ");
            cur = cur.next;
        }
        System.out.println("null");
    }
}

public class GenericListApp {
    public static void main(String[] args) {

        System.out.println("=== Integer List ===");
        MyList<Integer> nums = new MyList<>();
        nums.addLast(10);
        nums.addLast(20);
        nums.addLast(30);
        nums.addFirst(5);
        nums.insertAt(2, 15);
        nums.display();
        System.out.println("Size: " + nums.size());
        System.out.println("Index of 20: " + nums.search(20));
        System.out.println("Index of 99: " + nums.search(99));
        nums.delete(10);
        nums.display();
        System.out.println("Delete 99: " + nums.delete(99));
        System.out.println("Size: " + nums.size());

        System.out.println("=== String List ===");
        MyList<String> langs = new MyList<>();
        System.out.println("Empty? " + langs.isEmpty());
        langs.delete("Java");             // delete from an empty list
        langs.addLast("Java");
        langs.addLast("C++");
        langs.addFirst("Python");
        langs.display();
        System.out.println("Index of Java: " + langs.search("Java"));
        langs.delete("Java");
        langs.display();
        System.out.println("Empty? " + langs.isEmpty());
        System.out.println("Size: " + langs.size());
    }
}
