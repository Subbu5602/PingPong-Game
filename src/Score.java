import java.awt.*;

public class Score extends Rectangle {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1; // The score of player 1
    int player2; // The score of player 2

    // Constructor to initialize the game width and height
    Score(int GAME_WIDTH, int GAME_HEIGHT) {
        // Assigning the provided game dimensions to the static variables
        Score.GAME_HEIGHT = GAME_HEIGHT;
        Score.GAME_WIDTH = GAME_WIDTH;
    }

    // Method to draw the score on the screen
    public void draw(Graphics g) {
        // Sets the color to white for drawing the score
        g.setColor(Color.white);

        // Sets the font to "Consolas" with a plain style and size 60 for the score display
        g.setFont(new Font("Consolas", Font.PLAIN, 60));

        // Draws a vertical line at the center of the game window to divide the score area
        g.drawLine((GAME_WIDTH / 2), 0, (GAME_WIDTH / 2), GAME_HEIGHT); //drawLine(starting x-coord,starting y-coord,ending x-coord, ending y-coord)

        // Draws the score of player 1 on the left side of the screen
        // The score is split into tens and units (e.g., "12" becomes "1" and "2")
        //3rd and 4th parameters are coordinates where the string should be placed
        g.drawString(String.valueOf(player1 / 10) + String.valueOf(player1 % 10), (GAME_WIDTH / 2) - 85, 50);

        // Draws the score of player 2 on the right side of the screen
        // The score is split into tens and units (e.g., "15" becomes "1" and "5")
        g.drawString(String.valueOf(player2 / 10) + String.valueOf(player2 % 10), (GAME_WIDTH / 2) + 20, 50);
    }
}
