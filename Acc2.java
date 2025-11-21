public class Acc2 extends Acc {
    public Acc2(String username, int pin) {
        super(username, pin);
    }

    @Override
    public void deposit (double amount) {
        balance += amount;
        System.out.printf("Deposited ₱%.2f%n", amount);
    }

    public void deposit (int amount) {
        deposit((double) amount);
    }

    @Override
    public void withdraw (double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            System.out.printf("Withdrew ₱%.2f%n", amount);
        }
    }

    @Override
    public void sendLoad(double amount, Acc receiver) {

         if (amount <= 0) {
        System.out.println("Load amount must be positive!");
        return;
        }
        if (amount > balance) {
        System.out.println("Insufficient balance to send load!");
        return;
        }

    balance -= amount;
    receiver.loadBalance += amount;

    System.out.printf("Successfully sent ₱%.2f load to %s%n", amount, receiver.getUsername());
    }
   
    public void sendLoad(int amount, Acc receiver) {
        sendLoad((double) amount, receiver);
    }


    public void withdraw (int amount) {
        withdraw((double) amount);
    }

    public double getBalance () {
        return balance;
    }

    public int getPin () {
        return pin;
    }

    public String getUsername () {
        return username;
    }
}
