// ---------- Custom exception ----------
class LibraryException extends Exception {
    LibraryException(String msg) { super(msg); }
}

// ---------- Interface ----------
interface Borrowable {
    void borrowItem() throws LibraryException;
    void returnItem() throws LibraryException;
}

// ---------- Generic node, list and queue (ADTs) ----------
class Node<T> {
    T data;
    Node<T> next;
    Node(T data) { this.data = data; }
}

class SimpleList<T> {                       // List ADT
    private Node<T> head;
    private int size = 0;

    void add(T x) {
        Node<T> n = new Node<>(x);
        if (head == null) head = n;
        else {
            Node<T> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = n;
        }
        size++;
    }

    T get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
        Node<T> cur = head;
        for (int k = 0; k < i; k++) cur = cur.next;
        return cur.data;
    }

    int size() { return size; }
}

class MyQueue<T> {                          // Queue ADT (FIFO)
    private Node<T> head, tail;

    boolean isEmpty() { return head == null; }

    void enqueue(T x) {
        Node<T> n = new Node<>(x);
        if (isEmpty()) head = tail = n;
        else { tail.next = n; tail = n; }
    }

    T dequeue() throws LibraryException {
        if (isEmpty()) throw new LibraryException("Waiting list is empty");
        T x = head.data;
        head = head.next;
        if (head == null) tail = null;
        return x;
    }
}

// ---------- Abstract class (abstraction + encapsulation) ----------
abstract class LibraryItem implements Borrowable {
    private int id;
    private String title;
    private boolean available = true;
    private MyQueue<String> waitList = new MyQueue<>();   // composition

    LibraryItem(int id, String title) throws LibraryException {
        if (id <= 0) throw new LibraryException("ID must be positive");
        if (title == null || title.trim().isEmpty())
            throw new LibraryException("Title cannot be empty");
        this.id = id;
        this.title = title.trim();
    }

    int getId() { return id; }
    String getTitle() { return title; }
    boolean isAvailable() { return available; }

    abstract int getLoanDays();             // each subclass decides

    public void borrowItem() throws LibraryException {
        if (!available) throw new LibraryException("'" + title + "' is already borrowed");
        available = false;
    }

    public void returnItem() throws LibraryException {
        if (available) throw new LibraryException("'" + title + "' was not borrowed");
        available = true;
    }

    void joinWaitList(String member) { waitList.enqueue(member); }
    boolean hasWaiting() { return !waitList.isEmpty(); }
    String nextWaiting() throws LibraryException { return waitList.dequeue(); }

    void display() {
        System.out.println("[" + id + "] " + title + " | "
                + (available ? "Available" : "Borrowed")
                + " | Loan: " + getLoanDays() + " days");
    }
}

// ---------- Inheritance + polymorphism ----------
class Book extends LibraryItem {
    private String author;

    Book(int id, String title, String author) throws LibraryException {
        super(id, title);                   // constructor chaining
        this.author = author;
    }

    int getLoanDays() { return 14; }

    @Override
    void display() {
        super.display();
        System.out.println("     Author: " + author);
    }
}

class Magazine extends LibraryItem {
    private int issueNo;

    Magazine(int id, String title, int issueNo) throws LibraryException {
        super(id, title);
        this.issueNo = issueNo;
    }

    int getLoanDays() { return 7; }

    @Override
    void display() {
        super.display();
        System.out.println("     Issue: " + issueNo);
    }
}

// ---------- Generic repository (bounded type) ----------
class Repository<T extends LibraryItem> {
    private SimpleList<T> items = new SimpleList<>();

    void add(T item) throws LibraryException {
        for (int i = 0; i < items.size(); i++)
            if (items.get(i).getId() == item.getId())
                throw new LibraryException("Duplicate ID: " + item.getId());
        items.add(item);
    }

    T findById(int id) throws LibraryException {
        for (int i = 0; i < items.size(); i++)
            if (items.get(i).getId() == id) return items.get(i);
        throw new LibraryException("Item not found: ID " + id);
    }

    int count() { return items.size(); }
    T get(int i) { return items.get(i); }
}

// ---------- Library (composition: it creates and owns the repository) ----------
class Library {
    private String name;
    private Repository<LibraryItem> repo = new Repository<>();

    Library(String name) { this.name = name; }

    void addItem(LibraryItem item) throws LibraryException {
        repo.add(item);
    }

    void borrow(int id, String member) {
        try {
            LibraryItem item = repo.findById(id);
            if (!item.isAvailable()) {
                item.joinWaitList(member);
                System.out.println(member + ": '" + item.getTitle()
                        + "' is out. Added to waiting list.");
                return;
            }
            item.borrowItem();
            System.out.println(member + " borrowed '" + item.getTitle()
                    + "' for " + item.getLoanDays() + " days");
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void giveBack(int id) {
        try {
            LibraryItem item = repo.findById(id);
            item.returnItem();
            System.out.println("Returned '" + item.getTitle() + "'");
            if (item.hasWaiting())
                System.out.println("Notify " + item.nextWaiting()
                        + ": '" + item.getTitle() + "' is now available");
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void showAll() {
        System.out.println("=== " + name + " (" + repo.count() + " items) ===");
        for (int i = 0; i < repo.count(); i++) {
            LibraryItem item = repo.get(i);   // parent reference
            item.display();                   // runs Book/Magazine version
        }
    }
}

// ---------- Test / demo ----------
public class LibraryApp {
    public static void main(String[] args) {
        Library lib = new Library("City Library");

        try {
            lib.addItem(new Book(1, "Java Basics", "Gosling"));
            lib.addItem(new Book(2, "Data Structures", "Sharma"));
            lib.addItem(new Magazine(3, "Tech Today", 42));
            lib.addItem(new Book(1, "Duplicate", "X"));     // duplicate ID
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }

        lib.showAll();
        System.out.println("---");

        lib.borrow(1, "Aman");        // success
        lib.borrow(1, "Riya");        // waiting list
        lib.borrow(1, "Prince");      // waiting list
        lib.borrow(9, "Aman");        // not found
        lib.giveBack(1);              // notifies first in queue
        lib.giveBack(2);              // was not borrowed
        lib.borrow(3, "Aman");

        try {
            new Book(4, "  ", "X");   // invalid title
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("---");
        lib.showAll();
    }
}
