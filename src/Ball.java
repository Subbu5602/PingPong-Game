import java.awt.*;
import java.util.Random;


public class Ball extends Rectangle {
    Random random; // Random object for generating random directions
    int xVelocity; // Velocity of the ball in the x-direction
    int yVelocity; // Velocity of the ball in the y-direction
    int initialSpeed = 2; // The initial speed of the ball

    // Constructor to initialize the ball with specific coordinates and dimensions
    Ball(int x, int y, int width, int height) {
        // Calls the Rectangle constructor to set the initial position and size of the ball
        super(x, y, width, height);

        // Initializes the random object for generating random numbers
        random = new Random();

        // Generates a random direction for the x-axis (0 or 1)
        int randomXDirection = random.nextInt(2);
        // Generates a random direction for the y-axis (0 or 1)
        int randomYDirection = random.nextInt(2);

        // Adjusts the direction to be either -1 or 1 (randomly)
        if (randomXDirection == 0) {
            randomXDirection--; // Will result in -1
        }
        // Sets the xVelocity to the random direction multiplied by the initial speed
        setXDirection(randomXDirection * initialSpeed);

        // Adjusts the direction to be either -1 or 1 (randomly)
        if (randomYDirection == 0) {
            randomYDirection--; // Will result in -1
        }
        // Sets the yVelocity to the random direction multiplied by the initial speed
        setYDirection(randomYDirection * initialSpeed);
    }

    // Method to set the x direction (velocity) of the ball
    public void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }

    // Method to set the y direction (velocity) of the ball
    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }

    // Method to update the position of the ball based on its current velocity
    public void move() {
        x += xVelocity; // Updates the x-coordinate based on the xVelocity
        y += yVelocity; // Updates the y-coordinate based on the yVelocity
    }

    // Method to draw the ball on the screen
    public void draw(Graphics g) {
        // Sets the color for drawing the ball
        g.setColor(Color.white);
        // Draws an oval representing the ball using its current x, y, width, and height
        g.fillOval(x, y, height, width);
    }
}
