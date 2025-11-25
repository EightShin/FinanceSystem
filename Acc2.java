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
<<<<<<< HEAD
        System.out.printf("Deposited ₱%.2f%n", amount);
        return true;
=======
        System.out.printf("Deposited Php %.2f%n", amount);
>>>>>>> dab3f1519c4338d7effc7be8cd28aee27d07f6ba
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
<<<<<<< HEAD
            return false;
=======
        } else {
            balance -= amount;
            System.out.printf("Withdrew Php %.2f%n", amount);
>>>>>>> dab3f1519c4338d7effc7be8cd28aee27d07f6ba
        }
        balance -= amount;
        System.out.printf("Withdrew ₱%.2f%n", amount);
        return true;
    }

    @Override
    public boolean sendLoad(double amount, Acc receiver) {
        if (amount <= 0) {
            System.out.println("Load amount must be positive!");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance to send load!");
            return false;
        }

        balance -= amount;
        receiver.loadBalance += amount;

<<<<<<< HEAD
        System.out.printf("Successfully sent ₱%.2f load to %s%n", amount, receiver.getUsername());
        return true;
=======
    System.out.printf("Successfully sent Php %.2f load to %s%n", amount, receiver.getUsername());
>>>>>>> dab3f1519c4338d7effc7be8cd28aee27d07f6ba
    }

    public boolean sendLoad(int amount, Acc receiver) {
        return sendLoad((double) amount, receiver);
    }


    public boolean withdraw (int amount) {
        return withdraw((double) amount);
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
