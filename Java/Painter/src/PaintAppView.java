import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * PaintAppView class represents the view component of a simple drawing application.
 * This class provides the GUI components for drawing and controlling geometric shapes.
 */
public class PaintAppView {

    // GUI components
    public JFrame frame;
    public JPanel controlPanel, canvas;
    public JButton drawButton, undrawButton, squareButton, circleButton;
    public JTextField rField, gField, bField, sizeField;
    public JLabel errorLabel;

    // List to store the geometric shapes to be drawn
    private ArrayList<GeometricObject> shapes;

    /**
     * Constructor to initialize the PaintAppView and its GUI components.
     */
    public PaintAppView() {
        frame = new JFrame("Simple Drawing App");
        frame.setSize(1200, 900); // Set the frame size to 1600x1200
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false); // Prevent resizing of the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        controlPanel = new JPanel();

        // Canvas to draw the geometric shapes
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (shapes != null) {
                    for (GeometricObject shape : shapes) {
                        shape.draw(g);
                    }
                }
            }
        };

        // Initialize buttons and text fields
        drawButton = new JButton("Draw");
        undrawButton = new JButton("Erase");
        squareButton = new JButton("Square");
        circleButton = new JButton("Circle");
        rField = new JTextField(3); // Set the size of the RGB fields to 3
        gField = new JTextField(3);
        bField = new JTextField(3);
        sizeField = new JTextField(10);
        errorLabel = new JLabel("No Errors");
        errorLabel.setForeground(Color.GREEN);

        // Add components to the control panel
        controlPanel.add(new JLabel("R:"));
        controlPanel.add(rField);
        controlPanel.add(new JLabel("G:"));
        controlPanel.add(gField);
        controlPanel.add(new JLabel("B:"));
        controlPanel.add(bField);
        controlPanel.add(new JLabel("Brush Size:"));
        controlPanel.add(sizeField);
        controlPanel.add(circleButton);
        controlPanel.add(squareButton);
        controlPanel.add(drawButton);
        controlPanel.add(undrawButton);
        controlPanel.add(errorLabel);

        // Add the control panel and canvas to the frame
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);
    }

    /**
     * Method to set the geometric shapes to be drawn and repaint the canvas.
     *
     * @param shapes List of geometric shapes to be drawn.
     */
    public void setShapes(ArrayList<GeometricObject> shapes) {
        this.shapes = shapes;
        canvas.repaint();
    }
}
