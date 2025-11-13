public class Acc2 extends Acc {
    public Acc2(String username, int pin) {
        super(username, pin);
    }

    @Override
    public void deposit (double amount) {
        balance += amount;
        System.out.println("Deposited ₱" + amount);
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
            System.out.println("Withdrew ₱" + amount);
        }
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
