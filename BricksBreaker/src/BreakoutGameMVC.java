import javax.swing.JFrame;

public class BreakoutGameMVC {
    public static void main(String[] args) {
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);

        JFrame frame = new JFrame("Breakout Game MVC");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.addKeyListener(controller);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }
}
