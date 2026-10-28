import java.util.*;

// Generic class with one type parameter
class Box<T> {
    private T value;

    Box(T value) { this.value = value; }
    T get() { return value; }
    void set(T value) { this.value = value; }
}

// Generic class with two type parameters
class Pair<K, V> {
    private K key;
    private V value;

    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public String toString() { return key + " = " + value; }
}

// Bounded type parameter: T must be Number or its subclass
class NumberBox<T extends Number> {
    private T value;

    NumberBox(T value) { this.value = value; }
    double asDouble() { return value.doubleValue(); }   // Number method is available
}

public class GenericsApp {

    // Generic method: works with any type
    static <T> void printArray(T[] arr) {
        for (T item : arr)
            System.out.print(item + " ");
        System.out.println();
    }

    // Bounded generic method: T must be Comparable
    static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    // Upper bounded wildcard: can READ numbers
    static double sum(List<? extends Number> list) {
        double total = 0;
        for (Number n : list)
            total += n.doubleValue();
        return total;
    }

    // Lower bounded wildcard: can WRITE Integers
    static void addNumbers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
    }

    public static void main(String[] args) {

        // Type safety
        Box<Integer> b1 = new Box<>(10);
        Box<String> b2 = new Box<>("Hello");
        System.out.println(b1.get() + " " + b2.get());
        // b1.set("abc");               // ERROR: String cannot go in Box<Integer>

        // Pair
        Pair<String, Integer> p = new Pair<>("Age", 20);
        System.out.println(p);

        // Bounded type parameter
        NumberBox<Integer> nb = new NumberBox<>(7);
        System.out.println(nb.asDouble());
        // NumberBox<String> x;         // ERROR: String is not a Number

        // Generic methods
        printArray(new Integer[]{1, 2, 3});
        printArray(new String[]{"A", "B"});
        System.out.println(max(10, 20));
        System.out.println(max("apple", "mango"));

        // ? extends Number
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List<Double> dbls = Arrays.asList(1.5, 2.5);
        System.out.println(sum(ints));
        System.out.println(sum(dbls));

        // ? super Integer
        List<Number> nums = new ArrayList<>();
        List<Object> objs = new ArrayList<>();
        addNumbers(nums);
        addNumbers(objs);
        System.out.println(nums);
        System.out.println(objs);
    }
}
