public abstract class Acc {
    protected String username;
    protected int pin;
    protected double balance;

    public Acc (String username, int pin) {
        this.username = username;
        this.pin = pin;
        this.balance = 0.0;
    }

    public abstract void deposit (double amount);
    public abstract void withdraw (double amount);
    public abstract void sendLoad(double amount, Acc2 receiver);
}
