import java.util.HashMap;
import java.util.Scanner;

public class MainSystem {
    static desrecorder cv = new desrecorder();
    static Scanner one = new Scanner(System.in);
    static HashMap<String, Acc2> Data = new HashMap<>();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\nMain Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = one.nextInt();
            one.nextLine();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void createAccount () {
        if (Data.size() >= 5) {
            System.out.println("Account limit reached! (Max: 5 users)");
            return;
        }

        System.out.print("Enter username: ");
        String username = one.nextLine();
        if (Data.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Enter 4-digit PIN: ");
        int pin = one.nextInt();
        one.nextLine();

        if (pin < 1000 || pin > 9999) {
            System.out.println("Invalid PIN! Must be 4 digits. Please try again.");
            return;
        }

        Data.put(username, new Acc2(username, pin));
        String a = Integer.toString(pin);
        cv.AssignList(username, a); // List Creation  >w<
        System.out.println("Account created successfully!");
    }

    public static void login () {

        System.out.print("Enter username: ");
        String username = one.nextLine();
        System.out.print("Enter 4-digit PIN: ");

        int pin = one.nextInt();
        one.nextLine();

        Acc2 acc = Data.get(username);

        if (acc != null && acc.getPin() == pin) {
            System.out.println("Login successful! Welcome, " + username + ".");
            mainMenu(acc);
        } else {
            System.out.println("Invalid username or PIN! Please try again.");
        }
    }

    public static void mainMenu(Acc2 acc) {

    while (true) {
        
        System.out.println("\n--- ACCOUNT MENU ---");
        System.out.println("1. Cash-In");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Transfer");
        System.out.println("5. Send Load");
        System.out.println("6. Billing");
        System.out.println("7. Logout");
        System.out.println("8. Check Load Balance");
        System.out.println("9. Check History");
        System.out.print("Choose: ");

        int choice = one.nextInt();
        one.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter amount to deposit: ");
                double amount = one.nextDouble();
                one.nextLine();
                acc.deposit(amount);
                cv.AddHistory(acc.getUsername(), " :Deposit: + ", amount);
            }
            case 2 -> {
                System.out.print("Enter amount to withdraw: ");
                double amount = one.nextDouble();
                one.nextLine();
                acc.withdraw(amount);
                cv.AddHistory(acc.getUsername(), " :Withdraw: - ", amount);
            }
            case 3 -> System.out.printf("Your balance is: ₱%.2f%n", acc.getBalance());
            case 4 -> transfer(acc);
            case 5 -> {
                System.out.print("Enter recipient username: ");
                String targetName = one.nextLine().trim();
                Acc2 receiver = Data.get(targetName);

                if (receiver == null) {
                    System.out.println("Invalid! Username not found! Please try again.");
                    continue;
                }

                if (receiver.getUsername().equals(acc.getUsername())) {
                    System.out.println("Invalid! Cannot send load to yourself! Please try again.");
                    continue;
                }

                System.out.print("Enter load amount: ");
                double loadAmt = one.nextDouble();
                one.nextLine();

                loadTransfer(acc, receiver, loadAmt);
            }
            case 6 -> BillingSystem.processBilling(acc, one, cv);  
            case 7 -> {
                System.out.println("Logging out...");
                return;
            }
            case 8 -> System.out.printf("Your load balance is: ₱%.2f%n", acc.getLoadBalance());
            case 9 -> {
                cv.ViewHistory(acc.getUsername());
            }
            default -> System.out.println("Invalid option! Please try again.");
        }
    }
}

    private static void transfer (Acc2 sender) {

    System.out.print("Enter recipient username: ");
    String targetName = one.nextLine().trim();
    Acc2 receiver = Data.get(targetName);

    if (receiver == null) {
        System.out.println("Invalid! Username not found! Please try again.");
        return; }

    if (targetName.equals(sender.getUsername())) {
        System.out.println("Invalid! Cannot transfer to yourself! Please try again.");
        return; }

    System.out.print("Amount to transfer: ");
    double amt = one.nextDouble();
    one.nextLine();

    if (amt <= 0) {
        System.out.println("Invalid! Amount must be positive! Please try again.");
        return; }

    if (sender.getBalance() < amt) {
        System.out.println("Invalid! Insufficient balance! Please try again.");
        return; }

    sender.withdraw(amt);
    receiver.deposit(amt);

    // Record history for both parties
    cv.AddHistory(sender.getUsername(), " :Transfer Out: - ", amt);
    cv.AddHistory(receiver.getUsername(), " :Transfer In: + ", amt);

    System.out.printf("Successfully transferred ₱%.2f to %s%n", amt, targetName);
    }

    public static void loadTransfer(Acc2 sender, Acc2 receiver, double amount) {
        if (amount <= 0) {
            System.out.println("Load amount must be positive! Please try again.");
            return;
        }
        if (sender.getBalance() < amount) {
            System.out.println("Insufficient balance to send load! Please try again.");
            return;
        }

        sender.sendLoad(amount, receiver);
        // Record history for sender and receiver
        cv.AddHistory(sender.getUsername(), " :Send Load: - ", amount);
        cv.AddHistory(receiver.getUsername(), " :Receive Load: + ", amount);
    }
}