import java.awt.*;
import java.awt.event.KeyEvent;

// The Paddle class extends Rectangle to inherit properties like x, y, width, and height
public class Paddle extends Rectangle {
    int id; // Identifier for the paddle (1 or 2)
    int yVelocity; // The current velocity of the paddle in the y-direction
    int speed = 10; // The speed at which the paddle moves

    // Constructor to initialize the paddle with specific coordinates and dimensions
    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        // Calls the Rectangle constructor to set the initial position and size of the paddle
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id; // Sets the identifier for the paddle (1 for player 1, 2 for player 2)
    }

    // Method to handle key presses
    public void keyPressed(KeyEvent e) {
        // Handles both arrow keys and WASD instructions based on the paddle id
        switch (id) {
            case 1: // Player 1 controls
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(-speed); // Moves the paddle up when 'W' is pressed
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(speed); // Moves the paddle down when 'S' is pressed
                }
                break;

            case 2: // Player 2 controls
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(-speed); // Moves the paddle up when the up arrow key is pressed
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(speed); // Moves the paddle down when the down arrow key is pressed
                }
                break;
        }
    }

    // Method to handle key releases (stopping the paddle when a key is released)
    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1: // Player 1 controls
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(0); // Stops the paddle when 'W' is released
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0); // Stops the paddle when 'S' is released
                }
                break;

            case 2: // Player 2 controls
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(0); // Stops the paddle when the up arrow key is released
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0); // Stops the paddle when the down arrow key is released
                }
                break;
        }
    }

    // Method to set the direction of the paddle's movement (yVelocity)
    public void setYDirection(int yDirection) {
        yVelocity = yDirection; // Sets the yVelocity to the specified direction
        move(); // Moves the paddle based on the new velocity
    }

    // Method to update the position of the paddle
    public void move() {
        y = y + yVelocity; // Updates the y-coordinate by adding the current yVelocity
    }

    // Method to draw the paddle on the screen
    public void draw(Graphics g) {
        // Sets the color based on the paddle's id
        if (id == 1) {
            g.setColor(Color.blue); // Player 1's paddle is blue
        } else {
            g.setColor(Color.red); // Player 2's paddle is red
        }
        // Draws the paddle as a filled rectangle at its current position and size
        g.fillRect(x, y, width, height);
    }
}
