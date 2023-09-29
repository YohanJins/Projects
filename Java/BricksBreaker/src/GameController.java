// Import necessary libraries for event handling and GUI components
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GameController extends KeyAdapter implements ActionListener {
    // Declare class variables
    private GameModel model;
    private GameView view;
    private Timer gameTimer;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // Constructor for the GameController class
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        // Initialize game timer to call actionPerformed method at regular intervals
        gameTimer = new Timer(13, this);  // Adjusted for 60FPS (1000ms/60 = 16.67ms, rounded to 17ms)
        gameTimer.start();
    }

    // Override keyPressed method to detect when arrow keys are pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    // Override keyReleased method to detect when arrow keys are released
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    // Override actionPerformed method to handle game logic at regular intervals
    @Override
    public void actionPerformed(ActionEvent e) {
        // Move paddle left if left arrow key is pressed and paddle is within bounds
        if (leftPressed && model.getPaddle().x > 0) {
            model.getPaddle().x -= 10;
        }
        // Move paddle right if right arrow key is pressed and paddle is within bounds
        if (rightPressed && model.getPaddle().x < 550) {
            model.getPaddle().x += 10;
        }
        // Move the ball
        model.moveBall();
        // Redraw the game view
        view.repaint();

        // Decrease game timer by 1 every second
        if (model.getTimer() > 0) {
            model.setTimer(model.getTimer() - 1);
        } else {
            gameTimer.stop();
        }

        // Check for game over conditions
        if (model.getBall().y >= 390 - model.getBall().height || model.getTimer() <= 0) {
            model.setGameState(GameModel.GameState.GAME_OVER);
            gameTimer.stop();
            JOptionPane.showMessageDialog(null, "GAME OVER", "Game Over", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // Check for game clear condition
        if (model.getBricks().isEmpty()) {
            model.setGameState(GameModel.GameState.CLEAR);
            gameTimer.stop();
            int option = JOptionPane.showOptionDialog(null, "CLEAR!! Want to try next stage?", "Game Clear",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                if (model.getStage() < 3) {
                    model.nextStage();
                    model.setTimer(model.getTimer() + 1000);  // Add 60 seconds (1000) to the timer
                    gameTimer.start();
                } else {
                    JOptionPane.showMessageDialog(null, "You've cleared all stages! Congratulations!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "GOOD BYE", "Good Bye", JOptionPane.INFORMATION_MESSAGE);
                try {
                    Thread.sleep(2000); // Wait for 2 seconds
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        }
    }
}
