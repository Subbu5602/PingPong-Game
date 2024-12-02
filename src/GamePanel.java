import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

// GamePanel class inherits from JPanel and implements Runnable for multithreading
public class GamePanel extends JPanel implements Runnable {
    // Static final constants for game dimensions and paddle/ball size
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555)); // Height of the game panel (proportional to the width)
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT); // Dimension of the screen
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread; // Thread for running the game loop
    Image image; // Image for double-buffering to reduce flicker, so image gets drawn off-screen then displayed to user later to make the gameplay smooth
    Graphics graphics; // Graphics context for drawing onto the image
    Random random; // Random object for generating random values
    Paddle paddle1; // First paddle
    Paddle paddle2; // Second paddle
    Ball ball; // Ball object
    Score score; // Score object for tracking scores

    // Constructor for initializing the game panel
    GamePanel() {
        // Create new paddles and ball
        newPaddles();
        newBall();
        // Create a new score object with game dimensions
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        // Set this panel to be focusable and add a key listener for handling input
        this.setFocusable(true);
        this.addKeyListener(new AL());
        // Set the preferred size of the panel
        this.setPreferredSize(SCREEN_SIZE);

        // Start the game thread to handle the game loop
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Method to create a new ball instance at the center of the screen with a random vertical position
    public void newBall() {
        random = new Random(); // Instantiate the random generator
        // Position the ball at the center horizontally and a random position vertically
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER); //(x-coord,y-coord,heightBall,widthBall)
    }

    // Method to create two paddles, one for each player
    public void newPaddles() {
        // Create the first paddle on the left side of the screen
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        // Create the second paddle on the right side of the screen
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    // Override the paint method to handle custom rendering of the game elements
    public void paint(Graphics g) {
        // Create an image buffer to handle double-buffering and reduce flicker
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics(); // Get the graphics context for the image
        draw(graphics); // Call the draw method to render elements onto the image
        // Draw the image buffer onto the screen
        g.drawImage(image, 0, 0, this);
    }

    // Method to draw the game elements onto the screen
    public void draw(Graphics g) {
        // Draw the paddles, ball, and score
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        // Sync the graphics for smooth drawing
        Toolkit.getDefaultToolkit().sync();
    }

    // Method to update the positions of the paddles and ball
    public void move() {
        paddle1.move(); // Move the first paddle
        paddle2.move(); // Move the second paddle
        ball.move(); // Move the ball
    }

    // Method to check for collisions between the ball and other game elements
    public void checkCollision() {
        // Check if the ball hits the top or bottom edges of the screen, change trajectory to be opp of initial vertical direction depending on where it hit
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity); // Reverse the ball's vertical direction
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity); // Reverse the ball's vertical direction
        }

        // Check for collisions with the paddles
        if (ball.intersects(paddle1)) {
            // Reverse the ball's horizontal direction and increase its speed
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            // Modify vertical speed based on direction, if ball moves down after paddle hit, speed more, else speed less
            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddle2)) {
            // Reverse the ball's horizontal direction and increase its speed
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            // Modify vertical speed based on direction
            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        // Prevent paddle1 from moving past the top edge
        if (paddle1.y <= 0) {
            paddle1.y = 0;
        }

        // Prevent paddle1 from moving past the bottom edge
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        // Prevent paddle2 from moving past the top edge
        if (paddle2.y <= 0) {
            paddle2.y = 0;
        }

        // Prevent paddle2 from moving past the bottom edge
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        // Handle scoring when the ball passes a paddle
        if (ball.x <= 0) {
            score.player2++; // Increment the score for player 2
            newPaddles(); // Reset paddles' positions
            newBall(); // Create a new ball
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++; // Increment the score for player 1
            newPaddles(); // Reset paddles' positions
            newBall(); // Create a new ball
        }
    }

    // The run method for the game loop
    public void run() {
        // Game loop variables
        long lastTime = System.nanoTime();
        double amountOfTickets = 60.0; // Target frame rate fixed at 60 fps, so game displays 60 frames per second
        double ns = 1000000000 / amountOfTickets; // Nanoseconds per frame
        double delta = 0; // Time accumulator

        // Infinite loop for running the game
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns; // Calculate the time passed
            lastTime = now; // Update the last time

            // If it's time for the next frame
            if (delta >= 1) {
                move(); // Update the positions of the game elements
                checkCollision(); // Check for collisions
                repaint(); // Repaint the game panel
                delta--; // Reset the time accumulator
            }
        }
    }

    // Inner class for handling key events (input handling)
    public class AL extends KeyAdapter {
        // Called when a key is pressed
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e); // Handle key press for paddle 1
            paddle2.keyPressed(e); // Handle key press for paddle 2
        }

        // Called when a key is released
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e); // Handle key release for paddle 1
            paddle2.keyReleased(e); // Handle key release for paddle 2
        }
    }
}
