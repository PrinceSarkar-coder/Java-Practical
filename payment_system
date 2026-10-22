class Payment {
    // Overloaded methods (compile-time polymorphism)
    void pay(double amount) {
        System.out.println("Paid " + amount + " in cash");
    }
    void pay(double amount, String cardNo) {
        System.out.println("Paid " + amount + " using card " + cardNo);
    }
    void pay(double amount, String upiId, int pin) {
        System.out.println("Paid " + amount + " using UPI " + upiId);
    }

    // Method to be overridden (run-time polymorphism)
    void process() {
        System.out.println("Processing a payment");
    }
}

class CardPayment extends Payment {
    @Override
    void process() {
        System.out.println("Processing card payment");
    }
}

class UpiPayment extends Payment {
    @Override
    void process() {
        System.out.println("Processing UPI payment");
    }
}

public class payment_system {
    public static void main(String[] args) {

        // Compile-time polymorphism: compiler picks the method by arguments
        Payment p = new Payment();
        p.pay(500);
        p.pay(1200, "1234-5678");
        p.pay(800, "prince@upi", 1111);

        System.out.println("---");

        // Run-time polymorphism: parent reference, child object
        Payment p1 = new CardPayment();
        Payment p2 = new UpiPayment();

        p1.process();   // calls CardPayment's process()
        p2.process();   // calls UpiPayment's process()
    }
}
