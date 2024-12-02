import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
      GamePanel panel;

      GameFrame(){
          panel = new GamePanel();
          this.add(panel); //add panel to frame
          this.setTitle("Ping-Pong-Game");
          this.setResizable(false);
          this.setBackground(Color.black);

          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.pack(); //resizes jFrame according to the respective components as needed
          this.setVisible(true);
          this.setLocationRelativeTo(null);
      }
}
