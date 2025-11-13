import java.util.HashMap;
import java.util.Scanner;

public class MainSystem {
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Acc2> Data = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public static void createAccount () {
        if (Data.size() >= 5) {
            System.out.println("Account limit reached! (Max: 5 users)");
            return;
        }

        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (Data.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Enter 4-digit PIN: ");
        int pin = sc.nextInt();
        sc.nextLine();

        if (pin < 1000 || pin > 9999) {
            System.out.println("Invalid PIN! Must be 4 digits.");
            return;
        }

        Data.put(username, new Acc2(username, pin));
        System.out.println("Account created successfully!");
    }

    public static void login () {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter 4-digit PIN: ");
        int pin = sc.nextInt();
        sc.nextLine();

        Acc2 acc = Data.get(username);

        if (acc != null && acc.getPin() == pin) {
            System.out.println("Login successful! Welcome, " + username + ".");
            accountMenu(acc);
        } else {
            System.out.println("Invalid username or PIN!");
        }
    }

    public static void accountMenu (Acc2 acc) {
        while (true) {
            System.out.println("\n--- ACCOUNT MENU ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter amount to deposit: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    acc.deposit(amount);
                }
                case 2 -> {
                    System.out.print("Enter amount to withdraw: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    acc.withdraw(amount);
                }
                case 3 -> System.out.println("Your balance is: ₱" + acc.getBalance());
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}
