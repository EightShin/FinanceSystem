import java.util.Scanner;

public class BillingSystem {
    private static final double TAX_RATE = 0.01; 

    public static void processBilling(Acc acc, Scanner scanner) {

        while (true) {
            System.out.println("\n=== BILLING MENU ===");
            System.out.println("Note: All bills include 1% taxation");
            System.out.println("1. Wifi/PLDT");
            System.out.println("2. Electricity/Meralco");
            System.out.println("3. School/STI");
            System.out.println("4. Return to Account Menu");
            System.out.print("Choose billing option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> payBill(acc, scanner, "Wifi/PLDT");
                case 2 -> payBill(acc, scanner, "Electricity/Meralco");
                case 3 -> payBill(acc, scanner, "School/STI");
                case 4 -> {
                    System.out.println("Returning to Account Menu...");
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    protected static void payBill(Acc acc, Scanner scanner, String billType) {
        System.out.print("Enter " + billType + " bill amount: ₱");
        double billAmount = scanner.nextDouble();
        scanner.nextLine();

        if (billAmount <= 0) {
            System.out.println("Invalid! Bill amount must be positive! Please try again.");
            return;
        }

        double taxAmount = billAmount * TAX_RATE;
        double totalAmount = billAmount + taxAmount;

        System.out.println("\n--- BILLING DETAILS ---");
        System.out.printf("Bill Type: %s%n", billType);
        System.out.printf("Bill Amount: ₱%.2f%n", billAmount);
        System.out.printf("Tax (1%%): ₱%.2f%n", taxAmount);
        System.out.printf("Total Amount (with 1%% tax): ₱%.2f%n", totalAmount);
        System.out.println("------------------------");

        if (acc.getBalance() < totalAmount) {
            System.out.println("Insufficient balance! Payment failed.");
            System.out.printf("Your current balance: ₱%.2f%n", acc.getBalance());
            System.out.printf("Amount needed: ₱%.2f%n", totalAmount);
            
        } else {

            acc.withdraw(totalAmount);
            System.out.println("Payment successful!!!");
            System.out.println("1% tax has been added to your bill.");
            System.out.printf("Remaining balance: ₱%.2f%n", acc.getBalance());
        }
    }
}
