public class Acc2 extends Acc {
    public Acc2(String username, int pin) {
        super(username, pin);
    }

    @Override
    public boolean deposit (double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount! Must be greater than 0.");
            return false;
        }
        balance += amount;
        System.out.printf("Deposited Php %.2f%n", amount);
        return true;
    }

    public boolean deposit (int amount) {
        return deposit((double) amount);
    }

    @Override
    public boolean withdraw (double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdraw amount! Must be greater than 0.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return false;
        }
        balance -= amount;
        System.out.printf("Withdrew Php %.2f%n", amount);
        return true;
    }

    public boolean withdraw (int amount) {
        return withdraw((double) amount);
    }

    @Override
    public double getBalance () {
        return balance;
    }

    @Override
    public int getPin () {
        return pin;
    }

    @Override
    public String getUsername () {
        return username;
    }
}
