import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SustainableLivingAppGUI {
    private JFrame frame;
    private JTextArea outputTextArea;
    private JComboBox<String> choiceBox;
    private JButton submitButton;
    private JTextField userInputField;
    private Map<Integer, String> environmentalTips;
    private Map<String, Integer> communityEngagementLeaderboard;
    private Map<String, Double> sustainabilityGoals;
    private int tipToRate;

    public SustainableLivingAppGUI() {
        frame = new JFrame("Sustainable Living App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Create components
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        choiceBox = new JComboBox<>(new String[]{"Environmental Tips", "Rate Environmental Tips", "Community Engagement", "Leaderboard", "Calculate Carbon Footprint", "Set Sustainability Goals", "Quit"});
        submitButton = new JButton("Submit");
        userInputField = new JTextField(15);

        // Create data structures
        environmentalTips = new HashMap<>();
        communityEngagementLeaderboard = new HashMap<>();
        sustainabilityGoals = new HashMap<>();

        // Add sample environmental tips
        environmentalTips.put(1, "Reduce, Reuse, Recycle.");
        environmentalTips.put(2, "Conserve water by fixing leaks.");
        environmentalTips.put(3, "Switch to energy-efficient light bulbs.");

        // Add components to the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        inputPanel.add(choiceBox);
        inputPanel.add(userInputField);
        inputPanel.add(submitButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserInput((String) choiceBox.getSelectedItem(), userInputField.getText());
            }
        });
    }

    private void handleUserInput(String selectedOption, String userInput) {
        outputTextArea.setText("");

        if (selectedOption.equals("Quit")) {
            System.exit(0);
        }

        String output = processUserChoice(selectedOption, userInput);
        outputTextArea.setText(output);
    }

    private String processUserChoice(String selectedOption, String userInput) {
        switch (selectedOption) {
            case "Environmental Tips":
                return displayEnvironmentalTips();
            case "Rate Environmental Tips":
                return rateEnvironmentalTips(userInput);
            case "Community Engagement":
                return engageWithYourCommunity(userInput);
            case "Leaderboard":
                return showLeaderboard();
            case "Calculate Carbon Footprint":
                return calculateCarbonFootprint(userInput);
            case "Set Sustainability Goals":
                return setSustainabilityGoals(userInput);
            default:
                return "Invalid choice. Please enter a valid option.";
        }
    }

    private String rateEnvironmentalTips(String userInput2) {
        try {
            String userInput = JOptionPane.showInputDialog("Enter the tip number you want to rate:");
            tipToRate = Integer.parseInt(userInput);
            if (environmentalTips.containsKey(tipToRate)) {
                userInput = JOptionPane.showInputDialog("Rate this tip (1-5, 5 being the best): ");
                int rating = Integer.parseInt(userInput);
                // Store the rating in a database or file for future reference
                // You can also calculate the average rating for each tip
                return "Thank you for rating the tip.";
            } else {
                return "Invalid tip number.";
            }
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid integer for the tip number and rating.";
        }
    }

    private String displayEnvironmentalTips() {
        if (environmentalTips.isEmpty()) {
            return "No environmental tips available.";
        }
        StringBuilder tips = new StringBuilder("Environmental Tips:\n");
        for (Map.Entry<Integer, String> entry : environmentalTips.entrySet()) {
            tips.append(entry.getKey()).append(". ").append(entry.getValue()).append("\n");
        }
        return tips.toString();
    }

    private String engageWithYourCommunity(String userInput) {
        String userName = userInput.trim();
        int currentPoints = communityEngagementLeaderboard.getOrDefault(userName, 0);
        currentPoints += 10; // Example: Gain 10 points for community engagement
        communityEngagementLeaderboard.put(userName, currentPoints);
        return "You earned 10 points for community engagement.";
    }

    private String showLeaderboard() {
        if (communityEngagementLeaderboard.isEmpty()) {
            return "The leaderboard is empty.";
        }
        StringBuilder leaderboard = new StringBuilder("Community Engagement Leaderboard:\n");
        AtomicInteger rank = new AtomicInteger(1);
        communityEngagementLeaderboard.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    leaderboard.append(rank.getAndIncrement()).append(". ").append(entry.getKey()).append(" - ").append(entry.getValue()).append(" points\n");
                });
        return leaderboard.toString();
    }

    private String calculateCarbonFootprint(String userInput) {
        try {
            double milesDrivenPerYear = Double.parseDouble(userInput);
            // Assume average fuel efficiency and emissions factor for demonstration purposes
            double fuelEfficiency = 25.0; // miles per gallon
            double emissionsFactor = 2.3; // CO2 per gallon of fuel
            double carbonFootprint = (milesDrivenPerYear / fuelEfficiency) * emissionsFactor;
            return "Your estimated annual carbon footprint is: " + carbonFootprint + " CO2-equivalent emissions.";
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid number for miles driven per year.";
        }
    }

    private String setSustainabilityGoals(String userInput2) {
        String userInput = JOptionPane.showInputDialog("Enter your goal description and target value (e.g., 'Reduce energy usage 10%'): ");
        String[] inputParts = userInput.split(" ", 2); // Split into two parts
        if (inputParts.length < 2) {
            return "Invalid input. Please enter a goal description and target value.";
        }
        String goalDescription = inputParts[0];
        try {
            double goalValue = Double.parseDouble(inputParts[1]);
            sustainabilityGoals.put(goalDescription, goalValue);
            return "You have set a sustainability goal: " + goalDescription + " - " + goalValue;
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid number for the target value.";
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SustainableLivingAppGUI());
    }
}