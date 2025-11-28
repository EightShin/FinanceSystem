public abstract class Acc {
    protected String username;
    protected int pin;
    protected double balance;
    protected double loadBalance;

    public Acc (String username, int pin) {
        this.username = username;
        this.pin = pin;
        this.balance = 0.0;
        this.loadBalance = 0.0;
    }

    public abstract boolean deposit (double amount);
    public abstract boolean withdraw (double amount);

    public double getBalance() {
        return balance;
    }

    public int getPin() {
        return pin;
    }

    public String getUsername() {
        return username;
    }
}