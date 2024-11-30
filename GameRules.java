/**
 * Abstract class representing the general rules for a game.
 * Provides a structure for managing attempts and the target number.
 */
abstract class GameRules {
    protected int maxAttempts;
    protected int numberToGuess;

    /**
     * Constructor to initialize the game rules with a maximum number of attempts.
     *
     * @param maxAttempts the maximum number of attempts allowed in the game
     */
    public GameRules(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    /**
     * Abstract method to define the gameplay logic.
     * This method must be implemented by subclasses.
     */
    public abstract void play();
}