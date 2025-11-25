import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class MainSystem {
    static desrecorder cv = new desrecorder();
    static Scanner one = new Scanner(System.in);
    static HashMap<String, Acc2> Data = new HashMap<>();

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static int readIntSafe() {
        try {
            int v = one.nextInt();
            one.nextLine();
            return v;
        } catch (java.util.InputMismatchException e) {
            one.nextLine();
            System.out.println("Invalid input. Please enter a valid integer.");
            return Integer.MIN_VALUE;
        }
    }

    private static double readDoubleSafe() {
        try {
            double v = one.nextDouble();
            one.nextLine();
            return v;
        } catch (java.util.InputMismatchException e) {
            one.nextLine();
            System.out.println("Invalid input. Please enter a valid number.");
            return Double.NaN;
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\nMain Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = readIntSafe();
            if (choice == Integer.MIN_VALUE) continue;

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
        String username;
        while (true) {
            System.out.print("Enter username (or type 'cancel' to go back): ");
            username = one.nextLine();
            if (username == null) username = "";
            username = username.trim();

            if (username.equalsIgnoreCase("cancel")) {
                System.out.println("Account creation cancelled.");
                return;
            }

            if (username.isEmpty()) {
                System.out.println("Invalid username! Username cannot be empty.");
                continue;
            }

            if (username.length() < 3 || !username.matches("^[A-Za-z0-9_-]+$") || !username.matches(".*[A-Za-z].*")) {
                System.out.println("Invalid username! Must be 3+ chars, contain at least one letter, and only letters/digits/Sysmbols(_ or -)/");
                continue;
            }

            if (Data.containsKey(username)) {
                System.out.println("Username already exists! Please choose another.");
                continue;
            }

            break; 
        }

        int pin;
        while (true) {
            System.out.print("Enter 4-digit PIN (or type 'cancel' to go back): ");
            String pinInput = one.nextLine();
            if (pinInput == null) pinInput = "";
            pinInput = pinInput.trim();

            if (pinInput.equalsIgnoreCase("cancel")) {
                System.out.println("Account creation cancelled.");
                return;
            }

            try {
                pin = Integer.parseInt(pinInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid PIN input. Please enter numeric 4 digits.");
                continue;
            }

            if (pin < 1000 || pin > 9999) {
                System.out.println("Invalid PIN! Must be 4 digits. Please try again.");
                continue;
            }

            break; 
        }

        Data.put(username, new Acc2(username, pin));
        String a = Integer.toString(pin);
        cv.AssignList(username, a);
        System.out.println("Account created successfully!");
    }

    public static void login () {

        System.out.print("Enter username: ");
        String username = one.nextLine();
        System.out.print("Enter 4-digit PIN: ");

        int pin = readIntSafe();
        if (pin == Integer.MIN_VALUE) {
            System.out.println("Login cancelled due to invalid PIN input.");
            return;
        }

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
        
        System.out.println("--- ACCOUNT MENU ---");
        System.out.println("1. Cash-In");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Transfer");
        System.out.println("5. Send Load");
        System.out.println("6. Billing");
        System.out.println("7. Logout");
        System.out.println("8. Check Load Balance");
        System.out.println("9. Check History");
        System.out.println("10. Delete Account");
        System.out.print("Choose: ");

        int choice = readIntSafe();
        if (choice == Integer.MIN_VALUE) continue;

        switch (choice) {
            case 1 -> {
                System.out.print("Enter amount to deposit: ");
                double amount = readDoubleSafe();
                if (Double.isNaN(amount)) continue;
                boolean depOk = acc.deposit(amount);
                if (depOk) {
                    String currentTime = LocalDateTime.now().format(dateTimeFormatter);
                    cv.AddHistory(acc.getUsername(), ":Deposit: +", amount, currentTime);
                    delay(1000);
                }
            }
            case 2 -> {
                System.out.print("Enter amount to withdraw: ");
                double amount = readDoubleSafe();
                if (Double.isNaN(amount)) continue;
                boolean wOk = acc.withdraw(amount);
                if (wOk) {
                    String currentTime = LocalDateTime.now().format(dateTimeFormatter);
                    cv.AddHistory(acc.getUsername(), ":Withdraw: -", amount, currentTime);
                    delay(1000);
                }
            }
            case 3 -> System.out.printf("Your balance is: Php %.2f%n", acc.getBalance());
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
                double loadAmt = readDoubleSafe();
                if (Double.isNaN(loadAmt)) continue;

                loadTransfer(acc, receiver, loadAmt);
            }
            case 6 -> BillingSystem.processBilling(acc, one, cv);  
            case 7 -> {
                System.out.println("Logging out...");
                return;
            }
            case 8 -> System.out.printf("Your load balance is: Php %.2f%n", acc.getLoadBalance());
            case 9 -> {
                cv.ViewHistory(acc.getUsername());
            }
            case 10 -> deleteAccount(acc);
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
    double amt = readDoubleSafe();
    if (Double.isNaN(amt)) {
        System.out.println("Transfer cancelled due to invalid amount input.");
        return;
    }

    if (amt <= 0) {
        System.out.println("Invalid! Amount must be positive! Please try again.");
        return; }

    if (sender.getBalance() < amt) {
        System.out.println("Invalid! Insufficient balance! Please try again.");
        return; }

    boolean withdrawn = sender.withdraw(amt);
    if (!withdrawn) {
        System.out.println("Transfer failed during withdrawal.");
        return;
    }

    boolean deposited = receiver.deposit(amt);
    if (!deposited) {
        
        System.out.println("Transfer failed during deposit. Rolling back.");
        sender.deposit(amt);
        return;
    }

    String currentTime = LocalDateTime.now().format(dateTimeFormatter);
    cv.AddHistory(sender.getUsername(), ":Transfer Out: -", amt, currentTime);
    cv.AddHistory(receiver.getUsername(), ":Transfer In: +", amt, currentTime);

    System.out.printf("Successfully transferred Php %.2f to %s%n", amt, targetName);
    delay(1000);
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

        boolean ok = sender.sendLoad(amount, receiver);
        if (ok) {
            String currentTime = LocalDateTime.now().format(dateTimeFormatter);
            cv.AddHistory(sender.getUsername(), ":Send Load: -", amount, currentTime);
            cv.AddHistory(receiver.getUsername(), ":Receive Load: +", amount, currentTime);
            delay(1000);
        }
    }

    private static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void deleteAccount(Acc2 acc) {
        System.out.println("\n=== DELETE ACCOUNT ===");
        System.out.println("WARNING: This action cannot be undone!");
        System.out.println("To confirm deletion, type your username: " + acc.getUsername());
        System.out.print("Type confirmation (or press Enter to cancel): ");
        String confirmation = one.nextLine().trim();

        if (!confirmation.equals(acc.getUsername())) {
            System.out.println("Deletion cancelled.");
            return;
        }

        Data.remove(acc.getUsername());
        System.out.println("Account deleted successfully!");
        System.out.println("Logging out...");
        delay(1000);
    }
}