import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the "Guess The Number" game.
 * This game allows multiple players to guess a random number based on selected difficulty levels.
 */
public class GuessTheNumberGame {
    public static void main(String[] args) {
        ensureScoreFileExists();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the 'Guess The Number' game!");
        System.out.print("How many players will participate? ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        List<GuessTheNumber> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            int difficulty = chooseDifficulty(scanner, playerName);
            players.add(new GuessTheNumber(playerName, difficulty));
        }

        for (GuessTheNumber player : players) {
            player.play();
        }

        displayBestScore();
        scanner.close();
    }

    /**
     * Prompts the player to select a difficulty level and returns the number of attempts allowed.
     *
     * @param scanner    the Scanner object to read user input
     * @param playerName the name of the current player
     * @return the number of attempts allowed based on the selected difficulty
     */
    private static int chooseDifficulty(Scanner scanner, String playerName) {
        System.out.println("\n" + playerName + ", select the difficulty:");
        System.out.println("1. Easy (10 attempts)");
        System.out.println("2. Medium (7 attempts)");
        System.out.println("3. Hard (5 attempts)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 1 -> 10;
            case 2 -> 7;
            case 3 -> 5;
            default -> {
                System.out.println("Invalid option. Defaulting to medium difficulty.");
                yield 7;
            }
        };
    }

    /**
     * Displays the best score (lowest attempts) recorded in the "scores.txt" file.
     * If the file doesn't exist or contains no scores, it will notify the user.
     */
    private static void displayBestScore() {
        File scoreFile = new File("scores.txt");

        if (!scoreFile.exists()) {
            System.out.println("\nNo scores recorded yet.");
            return;
        }

        System.out.println("\nBest Score (Lowest Attempts):");
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String bestPlayer = null;
            int bestScore = Integer.MAX_VALUE;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - Attempts: ");
                String player = parts[0];
                int score = Integer.parseInt(parts[1]);
                if (score < bestScore) {
                    bestScore = score;
                    bestPlayer = player;
                }
            }
            if (bestPlayer != null) {
                System.out.println(bestPlayer + " with " + bestScore + " attempts.");
            } else {
                System.out.println("No scores recorded.");
            }
        } catch (IOException e) {
            System.out.println("Error reading scores: " + e.getMessage());
        }
    }

    /**
     * Ensures the "scores.txt" file exists in the working directory.
     * If the file doesn't exist, it attempts to create it.
     */
    private static void ensureScoreFileExists() {
        File scoreFile = new File("scores.txt");
        if (!scoreFile.exists()) {
            try {
                if (scoreFile.createNewFile()) {
                    System.out.println("File 'scores.txt' successfully created.");
                }
            } catch (IOException e) {
                System.out.println("Error creating the score file: " + e.getMessage());
            }
        }
    }
}
