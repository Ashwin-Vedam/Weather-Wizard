import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SustainableLivingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        Map<Integer, String> environmentalTips = new HashMap<>();
        Map<String, Integer> communityEngagementLeaderboard = new HashMap<>();
        Map<String, Double> sustainabilityGoals = new HashMap<>();

        // Add some sample environmental tips
        environmentalTips.put(1, "Reduce, Reuse, Recycle.");
        environmentalTips.put(2, "Conserve water by fixing leaks.");
        environmentalTips.put(3, "Switch to energy-efficient light bulbs.");

        System.out.println("Welcome to the Sustainable Living App!");
        do {
            System.out.println("Choose an option:");
            System.out.println("1. Environmental Tips");
            System.out.println("2. Rate Environmental Tips");
            System.out.println("3. Community Engagement");
            System.out.println("4. Leaderboard");
            System.out.println("5. Calculate Carbon Footprint");
            System.out.println("6. Set Sustainability Goals");
            System.out.println("7. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    displayEnvironmentalTips(environmentalTips);
                    break;
                case 2:
                    rateEnvironmentalTips(scanner, environmentalTips);
                    break;
                case 3:
                    engageWithYourCommunity(communityEngagementLeaderboard, scanner);
                    break;
                case 4:
                    showLeaderboard(communityEngagementLeaderboard);
                    break;
                case 5:
                    calculateCarbonFootprint(scanner);
                    break;
                case 6:
                    setSustainabilityGoals(sustainabilityGoals, scanner);
                    break;
                case 7:
                    System.out.println("Thank you for using the Sustainable Living App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 7);
    }

    public static void displayEnvironmentalTips(Map<Integer, String> environmentalTips) {
        if (environmentalTips.isEmpty()) {
            System.out.println("No environmental tips available.");
            return;
        }
        System.out.println("Environmental Tips:");
        for (Map.Entry<Integer, String> entry : environmentalTips.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    public static void rateEnvironmentalTips(Scanner scanner, Map<Integer, String> environmentalTips) {
        if (environmentalTips.isEmpty()) {
            System.out.println("No environmental tips available to rate.");
            return;
        }
        System.out.println("Rate Environmental Tips:");
        displayEnvironmentalTips(environmentalTips);
        System.out.print("Enter the number of the tip you want to rate: ");

        if (scanner.hasNextInt()) {
            int tipNumber = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (environmentalTips.containsKey(tipNumber)) {
                System.out.print("Rate this tip (1-5, 5 being the best): ");

                if (scanner.hasNextInt()) {
                    int rating = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    // Store the rating in a database or file for future reference
                    // You can also calculate the average rating for each tip

                    System.out.println("Thank you for rating the tip.");
                } else {
                    System.out.println("Invalid rating. Please enter a valid integer between 1 and 5.");
                }
            } else {
                System.out.println("Invalid tip number.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid integer for the tip number.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    public static void engageWithYourCommunity(Map<String, Integer> communityEngagementLeaderboard, Scanner scanner) {
        System.out.println("Community Engagement:");
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();

        int currentPoints = communityEngagementLeaderboard.getOrDefault(userName, 0);
        currentPoints += 10; // Example: Gain 10 points for community engagement

        communityEngagementLeaderboard.put(userName, currentPoints);
        System.out.println("You earned 10 points for community engagement.");
    }

    public static void showLeaderboard(Map<String, Integer> communityEngagementLeaderboard) {
        if (communityEngagementLeaderboard.isEmpty()) {
            System.out.println("The leaderboard is empty.");
            return;
        }

        System.out.println("Community Engagement Leaderboard:");
        AtomicInteger rank = new AtomicInteger(1);
        communityEngagementLeaderboard.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    System.out.println(rank.getAndIncrement() + ". " + entry.getKey() + " - " + entry.getValue() + " points");
                });
    }

    public static void calculateCarbonFootprint(Scanner scanner) {
        System.out.println("Calculate Carbon Footprint:");
        System.out.print("Enter the number of miles driven per year: ");
        double milesDrivenPerYear = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the average fuel efficiency (miles per gallon): ");
        double fuelEfficiency = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the carbon emissions factor (CO2 per gallon of fuel): ");
        double emissionsFactor = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        double carbonFootprint = (milesDrivenPerYear / fuelEfficiency) * emissionsFactor;

        System.out.println("Your estimated annual carbon footprint is: " + carbonFootprint + " CO2-equivalent emissions.");
    }

    public static void setSustainabilityGoals(Map<String, Double> sustainabilityGoals, Scanner scanner) {
        System.out.println("Set Sustainability Goals:");
        System.out.print("Enter your goal description: ");
        String goalDescription = scanner.nextLine();

        System.out.print("Enter your target value (e.g., kWh, miles, etc.): ");
        double goalValue = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        sustainabilityGoals.put(goalDescription, goalValue);
        System.out.println("You have set a sustainability goal: " + goalDescription + " - " + goalValue);
    }
}