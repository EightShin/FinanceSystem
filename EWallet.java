import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EWallet {
    private double walletBalance;
    private final List<TopUpRecord> topUpHistory;
    
    private static final String[] GAMES = {"Mobile Legends", "League of Legends", "Valorant", "Call of Duty Mobile"};
    private static final int[] PRICES = {50, 100, 200, 500, 750, 1000};
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EWallet() {
        this.walletBalance = 0.0;
        this.topUpHistory = new ArrayList<>();
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void rechargeWallet(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid recharge amount! Must be greater than 0.");
            return;
        }
        walletBalance += amount;
        System.out.printf("Wallet recharged with Php %.2f. New balance: Php %.2f%n", amount, walletBalance);
    }

    public boolean processTopUp(Scanner scanner) {
        
        System.out.println("\n=== SELECT GAME ===");
        for (int i = 0; i < GAMES.length; i++) {
            System.out.println((i + 1) + ". " + GAMES[i]);
        }
        System.out.println((GAMES.length + 1) + ". Cancel");
        System.out.print("Choose game: ");

        int gameChoice;
        while (true) {
            try {
                gameChoice = scanner.nextInt();
                scanner.nextLine();
                if (gameChoice < 1 || gameChoice > GAMES.length + 1) {
                    System.out.println("Invalid choice! Please try again.");
                    continue;
                }
                break;
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        if (gameChoice == GAMES.length + 1) {
            System.out.println("Top-up cancelled.");
            return false;
        }

        String selectedGame = GAMES[gameChoice - 1];

        
        System.out.println("\n=== SELECT AMOUNT ===");
        for (int i = 0; i < PRICES.length; i++) {
            System.out.println((i + 1) + ". Php " + PRICES[i]);
        }
        System.out.println((PRICES.length + 1) + ". Cancel");
        System.out.print("Choose amount: ");

        int priceChoice;
        while (true) {
            try {
                priceChoice = scanner.nextInt();
                scanner.nextLine();
                if (priceChoice < 1 || priceChoice > PRICES.length + 1) {
                    System.out.println("Invalid choice! Please try again.");
                    continue;
                }
                break;
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        if (priceChoice == PRICES.length + 1) {
            System.out.println("Top-up cancelled.");
            return false;
        }

        int selectedPrice = PRICES[priceChoice - 1];

        
        System.out.println("\n=== CONFIRMATION ===");
        System.out.println("Game: " + selectedGame);
        System.out.printf("Amount: Php %d%n", selectedPrice);
        System.out.print("Proceed? (yes/no): ");

        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("yes")) {
            System.out.println("Top-up cancelled.");
            return false;
        }

    
        if (walletBalance < selectedPrice) {
            System.out.printf("Insufficient wallet balance! Current balance: Php %.2f, Amount needed: Php %d%n", walletBalance, selectedPrice);
            return false;
        }

        
        walletBalance -= selectedPrice;
        String redemptionKey = generateRedemptionKey();
        addTopUpRecord(selectedGame, selectedPrice, redemptionKey);

        System.out.println("\n=== TOP-UP SUCCESSFUL ===");
        System.out.println("Game: " + selectedGame);
        System.out.printf("Amount: Php %d%n", selectedPrice);
        System.out.println("Redemption Key: " + redemptionKey);
        System.out.printf("Wallet Balance: Php %.2f%n", walletBalance);

        return true;
    }

    public void viewTopUpHistory() {
        if (topUpHistory.isEmpty()) {
            System.out.println("No top-up history yet.");
            return;
        }

        System.out.println("\n=== E-WALLET TOP-UP HISTORY ===");
        System.out.println("--------------------------------------------------");
        for (TopUpRecord record : topUpHistory) {
            System.out.printf("Date: %s%n", record.timestamp);
            System.out.printf("Game: %s%n", record.game);
            System.out.printf("Amount: Php %d%n", record.amount);
            System.out.printf("Redemption Key: %s%n", record.redemptionKey);
            System.out.println("--------------------------------------------------");
        }
    }

    private String generateRedemptionKey() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            key.append(chars.charAt(random.nextInt(chars.length())));
        }
        return key.toString();
    }

    private void addTopUpRecord(String game, int amount, String redemptionKey) {
        topUpHistory.add(new TopUpRecord(game, amount, redemptionKey, LocalDateTime.now().format(dateTimeFormatter)));
    }

    private static class TopUpRecord {
        String game;
        int amount;
        String redemptionKey;
        String timestamp;

        TopUpRecord(String game, int amount, String redemptionKey, String timestamp) {
            this.game = game;
            this.amount = amount;
            this.redemptionKey = redemptionKey;
            this.timestamp = timestamp;
        }
    }
}
