    import java.util.Scanner;

public class BillingSystem {
    private static final double TAX_RATE = 0.01; 

    private static double readDoubleSafe(Scanner scanner) {
        try {
            double v = scanner.nextDouble();
            scanner.nextLine();
            return v;
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid input. Please enter a valid number.");
            return Double.NaN;
        }
    }

    public static void processBilling(Acc acc, Scanner scanner, desrecorder cv) {

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
                case 1 -> payBill(acc, scanner, "Wifi/PLDT", cv);
                case 2 -> payBill(acc, scanner, "Electricity/Meralco", cv);
                case 3 -> payBill(acc, scanner, "School/STI", cv);
                case 4 -> {
                    System.out.println("Returning to Account Menu...");
                    return;
                }
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    protected static void payBill(Acc acc, Scanner scanner, String billType, desrecorder cv) {
        double billAmount;
        while (true) {
            System.out.print("Enter " + billType + " bill amount: ₱");
            billAmount = readDoubleSafe(scanner);
            if (Double.isNaN(billAmount)) {
                System.out.println("Please try again.");
                continue;
            }
            break;
        }

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

            boolean ok = acc.withdraw(totalAmount);
            if (ok) {
                if (cv != null) {
                    String currentTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    cv.AddHistory(acc.getUsername(), ":Billing: -", totalAmount, currentTime);
                }
                System.out.println("Payment successful!!!");
                System.out.println("1% tax has been added to your bill.");
                System.out.printf("Remaining balance: ₱%.2f%n", acc.getBalance());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("Payment failed during withdrawal. No changes made.");
            }
        }
    }
}
