import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a single player's instance in the "Guess The Number" game.
 * Inherits common rules from the GameRules class and adds specific player functionality.
 */
class GuessTheNumber extends GameRules {
    private ArrayList<Integer> guessHistory;
    private Scanner scanner;
    private String playerName;

    /**
     * Constructor to initialize a new GuessTheNumber instance.
     *
     * @param playerName  the name of the player
     * @param maxAttempts the maximum number of attempts allowed
     */
    public GuessTheNumber(String playerName, int maxAttempts) {
        super(maxAttempts);
        this.guessHistory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.numberToGuess = new Random().nextInt(100) + 1;
        this.playerName = playerName;
    }

    /**
     * Starts the gameplay for this player, managing attempts, feedback, and saving results.
     */
    @Override
    public void play() {
        System.out.println("\n" + playerName + "'s turn!");
        System.out.println("You need to guess a number between 1 and 100.");
        System.out.println("You have " + maxAttempts + " attempts.");
        int attempts = 0;

        while (attempts < maxAttempts) {
            int guess = getPlayerGuess(attempts + 1);
            guessHistory.add(guess);
            attempts++;

            if (guess == numberToGuess) {
                System.out.println("Congratulations, " + playerName + "! You guessed the number in " + attempts + " attempts.");
                saveScore(playerName, attempts);
                break;
            } else if (guess < numberToGuess) {
                System.out.println("The number is higher.");
            } else {
                System.out.println("The number is lower.");
            }

            if (attempts == maxAttempts) {
                System.out.println("Sorry, " + playerName + "! You've used all your attempts. The number was: " + numberToGuess);
            }
        }

        displayGuessHistory();
        saveGuessHistoryToFile();
    }

    /**
     * Prompts the player to input a guess and validates the input.
     *
     * @param attemptNumber the current attempt number
     * @return the validated guess as an integer
     */
    private int getPlayerGuess(int attemptNumber) {
        int guess = -1;
        while (true) {
            System.out.print("Attempt " + attemptNumber + ": Enter your number: ");
            try {
                guess = Integer.parseInt(scanner.nextLine());
                if (guess >= 1 && guess <= 100) break;
                else System.out.println("Please enter a number between 1 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return guess;
    }

    /**
     * Displays the history of guesses made by the player.
     */
    private void displayGuessHistory() {
        System.out.println("\nGuess history:");
        for (int i = 0; i < guessHistory.size(); i++) {
            System.out.println("Attempt " + (i + 1) + ": " + guessHistory.get(i));
        }
    }

    /**
     * Saves the player's guess history to a file named after the player.
     */
    private void saveGuessHistoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playerName + "_guess_history.txt"))) {
            writer.write("Guess history for " + playerName + ":\n");
            for (int i = 0; i < guessHistory.size(); i++) {
                writer.write("Attempt " + (i + 1) + ": " + guessHistory.get(i) + "\n");
            }
            System.out.println("History saved to " + playerName + "_guess_history.txt");
        } catch (IOException e) {
            System.out.println("Error saving the history: " + e.getMessage());
        }
    }

    /**
     * Saves the player's score to a shared file "scores.txt".
     *
     * @param playerName the name of the player
     * @param attempts   the number of attempts taken by the player
     */
    private static void saveScore(String playerName, int attempts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) {
            writer.write(playerName + " - Attempts: " + attempts + "\n");
            System.out.println("Score saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the score: " + e.getMessage());
        }
    }
}