import javax.swing.JPanel;
import java.awt.*;

public class GameView extends JPanel {
    private GameModel model;

    public GameView(GameModel model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw bricks
        for (Rectangle brick : model.getBricks()) {
            g.setColor(Color.BLUE);
            g.fillRect(brick.x, brick.y, brick.width, brick.height);
        }
        // Draw paddle
        g.setColor(Color.RED);
        g.fillRect(model.getPaddle().x, model.getPaddle().y, model.getPaddle().width, model.getPaddle().height);
        // Draw ball
        g.setColor(Color.GREEN);
        g.fillOval(model.getBall().x, model.getBall().y, model.getBall().width, model.getBall().height);
        // Draw score and timer
        g.setColor(Color.BLACK);
        g.drawString("Score: " + model.getScore(), 10, 10);
        g.drawString("Timer: " + model.getTimer(), 540, 10);

        if (model.getGameState() == GameModel.GameState.GAME_OVER) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("GAME OVER", 200, 200);
        } else if (model.getGameState() == GameModel.GameState.CLEAR) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("CLEAR!!", 250, 200);
        }
    }
}
