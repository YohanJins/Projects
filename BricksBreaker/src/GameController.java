import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GameController extends KeyAdapter implements ActionListener {
    private GameModel model;
    private GameView view;
    private Timer gameTimer;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        gameTimer = new Timer(13, this);  // Adjusted for 60FPS (1000ms/60 = 16.67ms, rounded to 17ms)
        gameTimer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (leftPressed && model.getPaddle().x > 0) {
            model.getPaddle().x -= 10;
        }
        if (rightPressed && model.getPaddle().x < 550) {
            model.getPaddle().x += 10;
        }
        model.moveBall();
        view.repaint();

        // Decrease timer by 1 every second
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
