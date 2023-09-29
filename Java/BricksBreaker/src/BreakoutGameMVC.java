// Import the necessary library for creating a GUI window
import javax.swing.JFrame;

public class BreakoutGameMVC {

    // Main method to run the application
    public static void main(String[] args) {
        // Create a new game model
        GameModel model = new GameModel();

        // Create a new game view and pass the model to it
        GameView view = new GameView(model);

        // Create a new game controller and pass the model and view to it
        GameController controller = new GameController(model, view);

        // Create a new JFrame (window) for the game
        JFrame frame = new JFrame("Breakout Game MVC");

        // Set the size of the window
        frame.setSize(600, 400);

        // Set the default close operation for the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the game view to the window
        frame.add(view);

        // Add a key listener to the window to detect key presses, using the game controller
        frame.addKeyListener(controller);

        // Make the window focusable to receive key events
        frame.setFocusable(true);

        // Request focus for the window to ensure it receives key events
        frame.requestFocusInWindow();

        // Make the window visible
        frame.setVisible(true);
    }
}
