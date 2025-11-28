import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class MainSystem {
    static desrecorder cv = new desrecorder();
    static Scanner one = new Scanner(System.in);
    static HashMap<String, Acc2> Data = new HashMap<>();
    static HashMap<String, EWallet> eWallets = new HashMap<>();

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

            if (pinInput.length() != 4) {
                System.out.println("Invalid PIN! Must be exactly 4 digits. Please try again.");
                continue;
            }

            if (!pinInput.matches("^[0-9]{4}$")) {
                System.out.println("Invalid PIN input. Please enter numeric 4 digits.");
                continue;
            }

            try {
                pin = Integer.parseInt(pinInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid PIN input. Please enter numeric 4 digits.");
                continue;
            }

            if (pin == 0) {
                System.out.println("Invalid PIN! PIN cannot be all zeros. Please try again.");
                continue;
            }

            break; 
        }

        Data.put(username, new Acc2(username, pin));
        String a = Integer.toString(pin);
        cv.AssignList(username, a);
        eWallets.put(username, new EWallet());
        System.out.println("Account created successfully!");
        delay(1000);
    }

    public static void login () {
        while (true) {
            System.out.print("Enter username (or type 'cancel' to go back): ");
            String username = one.nextLine();
            if (username == null) username = "";
            username = username.trim();
            if (username.equalsIgnoreCase("cancel")) {
                System.out.println("Login cancelled.");
                return;
            }

            System.out.print("Enter 4-digit PIN (or type 'cancel' to go back): ");
            String pinInput = one.nextLine();
            if (pinInput == null) pinInput = "";
            pinInput = pinInput.trim();
            if (pinInput.equalsIgnoreCase("cancel")) {
                System.out.println("Login cancelled.");
                return;
            }

            int pin;
            try {
                pin = Integer.parseInt(pinInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid PIN input. Please enter numeric 4 digits.");
                continue;
            }

            Acc2 acc = Data.get(username);
            if (acc != null && acc.getPin() == pin) {
                System.out.println("Login successful! Welcome, " + username + ".");
                delay(1000);
                mainMenu(acc);
                return;
            } else {
                System.out.println("Invalid username or PIN! Please try again or type 'cancel' to go back.");
            }
        }
    }

    public static void mainMenu(Acc2 acc) {

    while (true) {
        
        System.out.println("--- ACCOUNT MENU ---");
        System.out.println("1. Cash-In");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Transfer");
        System.out.println("5. E-Wallet");
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
                
                while (true) {
                    System.out.print("Enter amount to deposit (or type 'cancel' to go back): ");
                    String line = one.nextLine().trim();
                    if (line.equalsIgnoreCase("cancel")) {
                        System.out.println("Deposit cancelled.");
                        break;
                    }
                    double amount = InputUtils.parseAmountOrNaN(line);
                    if (Double.isNaN(amount)) {
                        System.out.println("Invalid input. Please enter a valid positive amount (no leading zeros). Please try again.");
                        continue;
                    }

                    boolean depOk = acc.deposit(amount);
                    if (depOk) {
                        String currentTime = LocalDateTime.now().format(dateTimeFormatter);
                        cv.AddHistory(acc.getUsername(), ":Deposit: +", amount, currentTime);
                        delay(1000);
                        break;
                    }
                }
            }
            case 2 -> {
                while (true) {
                    System.out.print("Enter amount to withdraw (or type 'cancel' to go back): ");
                    String line = one.nextLine().trim();
                    if (line.equalsIgnoreCase("cancel")) {
                        System.out.println("Withdrawal cancelled.");
                        break;
                    }
                    double amount = InputUtils.parseAmountOrNaN(line);
                    if (Double.isNaN(amount)) {
                        System.out.println("Invalid input. Please enter a valid positive amount (no leading zeros). Please try again.");
                        continue;
                    }

                    boolean wOk = acc.withdraw(amount);
                    if (wOk) {
                        String currentTime = LocalDateTime.now().format(dateTimeFormatter);
                        cv.AddHistory(acc.getUsername(), ":Withdraw: -", amount, currentTime);
                        delay(1000);
                        break;
                    }
                }
            }
            case 3 -> {
                System.out.printf("Your balance is: Php %.2f%n", acc.getBalance());
                delay(500);
            }
            case 4 -> transfer(acc);
            case 5 -> processEWallet(acc);
            case 6 -> BillingSystem.processBilling(acc, one, cv);  
            case 7 -> {
                System.out.println("Logging out...");
                return;
            }
            case 8 -> {
                System.out.printf("Your load balance is: Php %.2f%n", acc.getLoadBalance());
                delay(500);
            }
            case 9 -> {
                cv.ViewHistory(acc.getUsername());
                delay(500);
            }
            case 10 -> {
                if (deleteAccount(acc)) {
                    return;
                }
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
        return;
    }

    if (targetName.equals(sender.getUsername())) {
        System.out.println("Invalid! Cannot transfer to yourself! Please try again.");
        return;
    }

    
    while (true) {
        System.out.print("Amount to transfer (or type 'cancel' to go back): ");
        String line = one.nextLine().trim();
        if (line.equalsIgnoreCase("cancel")) {
            System.out.println("Transfer cancelled.");
            return;
        }

        double amt = InputUtils.parseAmountOrNaN(line);
        if (Double.isNaN(amt)) {
            System.out.println("Invalid input. Please enter a valid positive amount (no leading zeros). Please try again.");
            continue;
        }

        if (amt <= 0) {
            System.out.println("Invalid! Amount must be positive! Please try again.");
            continue;
        }

        if (sender.getBalance() < amt) {
            System.out.println("Invalid! Insufficient balance! Please try again.");
            continue;
        }

        boolean withdrawn = sender.withdraw(amt);
        if (!withdrawn) {
            System.out.println("Transfer failed during withdrawal.");
            continue;
        }

        boolean deposited = receiver.deposit(amt);
        if (!deposited) {
            System.out.println("Transfer failed during deposit. Rolling back.");
            sender.deposit(amt);
            continue;
        }

        String currentTime = LocalDateTime.now().format(dateTimeFormatter);
        cv.AddHistory(sender.getUsername(), ":Transfer Out: -", amt, currentTime);
        cv.AddHistory(receiver.getUsername(), ":Transfer In: +", amt, currentTime);

        System.out.printf("Successfully transferred Php %.2f to %s%n", amt, targetName);
        delay(1000);
        return;
    }
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

    private static void processEWallet(Acc2 acc) {
        EWallet wallet = eWallets.get(acc.getUsername());

        while (true) {
            System.out.println("\n=== E-WALLET MENU ===");
            System.out.println("1. Top Up");
            System.out.println("2. Recharge Wallet");
            System.out.println("3. View E-Wallet Balance");
            System.out.println("4. View Top-Up History");
            System.out.println("5. Go Back");
            System.out.print("Choose: ");

            int choice = readIntSafe();
            if (choice == Integer.MIN_VALUE) continue;

            switch (choice) {
                case 1 -> {
                    if (wallet.processTopUp(one)) {
                        delay(1500);
                    }
                }
                case 2 -> {
                    
                    while (true) {
                        System.out.print("Enter amount to recharge (or type 'cancel' to go back): ");
                        String line = one.nextLine().trim();
                        if (line.equalsIgnoreCase("cancel")) {
                            System.out.println("Recharge cancelled.");
                            break;
                        }
                        double amount = InputUtils.parseAmountOrNaN(line);
                        if (Double.isNaN(amount)) {
                            System.out.println("Invalid input. Please enter a valid positive amount (no leading zeros). Please try again.");
                            continue;
                        }

                        if (amount <= 0) {
                            System.out.println("Invalid recharge amount! Must be greater than 0.");
                            continue;
                        }

                        if (acc.getBalance() < amount) {
                            System.out.printf("Insufficient account balance! Current balance: Php %.2f%n", acc.getBalance());
                            continue;
                        }

                        System.out.printf("Recharge Php %.2f to E-Wallet? (yes/no): ", amount);
                        String confirmation = one.nextLine().trim().toLowerCase();
                        if (!confirmation.equals("yes")) {
                            System.out.println("Recharge cancelled.");
                            continue;
                        }

                        boolean withdrawOk = acc.withdraw(amount);
                        if (withdrawOk) {
                            wallet.rechargeWallet(amount);
                            String currentTime = LocalDateTime.now().format(dateTimeFormatter);
                            cv.AddHistory(acc.getUsername(), ":E-Wallet Recharge: -", amount, currentTime);
                            delay(1000);
                            break;
                        } else {
                            System.out.println("Recharge failed. Please try again.");
                        }
                    }
                }
                case 3 -> System.out.printf("E-Wallet Balance: Php %.2f%n", wallet.getWalletBalance());
                case 4 -> wallet.viewTopUpHistory();
                case 5 -> {
                    System.out.println("Returning to Account Menu...");
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static boolean deleteAccount(Acc2 acc) {
        System.out.println("\n=== DELETE ACCOUNT ===");
        
        if (acc.getBalance() > 0) {
            System.out.println("WARNING: Your account still has a balance of Php " + String.format("%.2f", acc.getBalance()));
            System.out.println("Please withdraw or spend your money before deleting your account.");
            System.out.println("Deletion cancelled.");
            return false;
        }

        System.out.println("WARNING: This action cannot be undone!");
        System.out.println("To confirm deletion, type your username: " + acc.getUsername());
        System.out.print("Type confirmation (or press Enter to cancel): ");
        String confirmation = one.nextLine().trim();

        if (!confirmation.equals(acc.getUsername())) {
            System.out.println("Deletion cancelled.");
            return false;
        }

        Data.remove(acc.getUsername());
        eWallets.remove(acc.getUsername());
        System.out.println("Account deleted successfully!");
        System.out.println("Logging out...");
        delay(1000);
        return true;
    }
}