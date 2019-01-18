/**
 * @author Aniket Mathur
 * @version 1.2
 *
 * Enumeration that represents directions
 * and the corresponding displacement in x and y
 */
public enum Direction {
    UP (0, -1),
    DOWN (0, 1),
    RIGHT (1, 0),
    LEFT (-1, 0);

    private final int x; //displacement value along x
    private final int y; //displacement value along y

    /**
     * Construtor that assigns x and y values to the Direction objects
     * @param x the direction of displacement in x
     * @param y the direction of displacement in y
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the reverse direction.
     * @return the reverse of the current direction
     */
    public Direction reverse() {
        switch( this ) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: return this;
        }
    }

    /**
     * Method that rotates the direction to the left
     * @return the new rotated direction
     */
    public Direction rotateLeft() {
        switch( this ) {
            case UP: return LEFT;
            case DOWN: return RIGHT;
            case LEFT: return DOWN;
            case RIGHT: return UP;
            default: return this;
        }
    }

    /**
     * Method that rotates the direction to the right
     * @return the new rotated direction
     */
    public Direction rotateRight() {
        switch( this ) {
            case UP: return RIGHT;
            case DOWN: return LEFT;
            case LEFT: return UP;
            case RIGHT: return DOWN;
            default: return this;
        }
    }

    /**
     * method that returns the x val of object
     * @return displacement in x
     */
    public int x() { return x; }

    /**
     * method that returns the y val of the object
     * @return displacement in y
     */
    public int y() { return y; }
}

