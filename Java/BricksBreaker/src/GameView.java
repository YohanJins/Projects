// Import necessary libraries for GUI components and graphics
import javax.swing.JPanel;
import java.awt.*;

public class GameView extends JPanel {
    // Declare class variable for the game model
    private GameModel model;

    // Constructor for the GameView class
    public GameView(GameModel model) {
        this.model = model;
    }

    // Override the paintComponent method to customize the drawing of game components
    @Override
    protected void paintComponent(Graphics g) {
        // Call the superclass's paintComponent method to ensure the panel is properly rendered
        super.paintComponent(g);

        // Draw bricks on the game view
        for (Rectangle brick : model.getBricks()) {
            g.setColor(Color.BLUE); // Set the color to blue for bricks
            g.fillRect(brick.x, brick.y, brick.width, brick.height); // Draw each brick as a filled rectangle
        }

        // Draw paddle on the game view
        g.setColor(Color.RED); // Set the color to red for the paddle
        g.fillRect(model.getPaddle().x, model.getPaddle().y, model.getPaddle().width, model.getPaddle().height); // Draw the paddle as a filled rectangle

        // Draw ball on the game view
        g.setColor(Color.GREEN); // Set the color to green for the ball
        g.fillOval(model.getBall().x, model.getBall().y, model.getBall().width, model.getBall().height); // Draw the ball as a filled oval

        // Draw the score and timer on the game view
        g.setColor(Color.BLACK); // Set the color to black for the text
        g.drawString("Score: " + model.getScore(), 10, 10); // Display the score at the top-left corner
        g.drawString("Timer: " + model.getTimer(), 540, 10); // Display the timer at the top-right corner

        // Check the game state and display appropriate messages
        if (model.getGameState() == GameModel.GameState.GAME_OVER) {
            g.setColor(Color.RED); // Set the color to red for the "GAME OVER" message
            g.setFont(new Font("Arial", Font.BOLD, 30)); // Set the font for the message
            g.drawString("GAME OVER", 200, 200); // Display the "GAME OVER" message at the center
        } else if (model.getGameState() == GameModel.GameState.CLEAR) {
            g.setColor(Color.GREEN); // Set the color to green for the "CLEAR!!" message
            g.setFont(new Font("Arial", Font.BOLD, 30)); // Set the font for the message
            g.drawString("CLEAR!!", 250, 200); // Display the "CLEAR!!" message at the center
        }
    }
}
