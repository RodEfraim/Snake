/**
 * Strings for PA8.
 * DO NOT EDIT
 */
public final class PA8Strings {

    public static final String USAGE = "Usage: java -cp ./Acme.jar:" + 
        "./objectdraw.jar:. SnakeController WIDTH HEIGHT DELAY\n";

    public static final String EC_USAGE = "Usage: java -cp ./Acme.jar:" + 
        "./objectdraw.jar:. SnakeControllerEC WIDTH HEIGHT DELAY\n";

    public static final String OUT_OF_RANGE = "Error: %s value %d is out of " + 
        "range. It should be between %d and %d\n";

    public static final String NOT_EVENLY_DIVISIBLE = "Error: %s value %d is " +
        "not divisible by the segment size\n";

    public static final String SCORE = "Score: %d";

    public static final String HIGH_SCORE = "High Score: %d";

    public static final String NEW_GAME = "New Game";

    public static final String GAME_OVER = "GAME OVER";

    public static final String WIN = "YOU WIN!!!";

    public static final String PAUSED = "PAUSED";

    // private constructor to prevent instantiation
    private PA8Strings() {}
}
