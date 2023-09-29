import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * PaintAppController class represents the controller component of a simple drawing application.
 * This class handles the user interactions and manages the drawing and erasing of geometric shapes.
 */
public class PaintAppController {
    private PaintAppView view;
    private ArrayList<GeometricObject> shapes = new ArrayList<>();
    private String currentShape = "Square"; // Default shape
    private boolean isErasing = false;

    /**
     * Constructor to initialize the PaintAppController and set up event listeners.
     *
     * @param view The view component of the drawing application.
     */
    public PaintAppController(PaintAppView view) {
        this.view = view;

        // Event listeners
        view.drawButton.addActionListener(e -> {
            isErasing = false; // Disable erasing mode when Draw button is pressed
            handleDrawAction();
        });
        view.undrawButton.addActionListener(e -> isErasing = true); // Enable erasing mode when Undraw button is pressed
        view.squareButton.addActionListener(e -> currentShape = "Square");
        view.circleButton.addActionListener(e -> currentShape = "Circle");
        view.canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isErasing) {
                    eraseShapeAt(e.getX(), e.getY());
                } else {
                    drawShapeAt(e.getX(), e.getY());
                }
            }
        });
        view.canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isErasing) {
                    eraseShapeAt(e.getX(), e.getY());
                } else {
                    drawShapeAt(e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Method to draw a geometric shape at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    private void drawShapeAt(int x, int y) {
        if (validateInput()) {
            GeometricObject shape;
            int size = Integer.parseInt(view.sizeField.getText());

            int r = Integer.parseInt(view.rField.getText());
            int g = Integer.parseInt(view.gField.getText());
            int b = Integer.parseInt(view.bField.getText());
            String color = String.format("#%02x%02x%02x", r, g, b); // Convert RGB values to HEX format

            if (currentShape.equals("Square")) {
                shape = new Square(color, x, y, size);
            } else {
                shape = new Circle(color, x, y, size);
            }

            shapes.add(shape);
            view.setShapes(shapes);
        }
    }

    /**
     * Method to erase a geometric shape at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    private void eraseShapeAt(int x, int y) {
        int size = Integer.parseInt(view.sizeField.getText());
        ArrayList<GeometricObject> shapesToRemove = new ArrayList<>();

        for (GeometricObject shape : shapes) {
            if (shape.isInsideRange(x, y, size/2)) { // Set the range based on the mouse position
                shapesToRemove.add(shape);
            }
        }

        shapes.removeAll(shapesToRemove);
        view.setShapes(shapes);
    }

    /**
     * Method to validate the input fields for RGB values and brush size.
     *
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateInput() {
        // Check if all fields are filled
        if (view.rField.getText().isEmpty() || view.gField.getText().isEmpty() || view.bField.getText().isEmpty() || view.sizeField.getText().isEmpty()) {
            view.errorLabel.setText("모든 필드를 채워주세요!");
            view.errorLabel.setForeground(Color.RED);
            return false;
        }

        // Validate RGB values are between 0 and 255
        try {
            int r = Integer.parseInt(view.rField.getText());
            int g = Integer.parseInt(view.gField.getText());
            int b = Integer.parseInt(view.bField.getText());

            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                view.errorLabel.setText("RGB 값은 0과 255 사이여야 합니다!");
                view.errorLabel.setForeground(Color.RED);
                return false;
            }
        } catch (NumberFormatException e) {
            view.errorLabel.setText("잘못된 RGB 값입니다!");
            view.errorLabel.setForeground(Color.RED);
            return false;
        }

        return true;
    }

    /**
     * Method to handle the drawing action when the Draw button is pressed.
     */
    private void handleDrawAction() {
        if (validateInput()) {
            GeometricObject shape;
            int size = Integer.parseInt(view.sizeField.getText());

            int r = Integer.parseInt(view.rField.getText());
            int g = Integer.parseInt(view.gField.getText());
            int b = Integer.parseInt(view.bField.getText());
            String color = String.format("#%02x%02x%02x", r, g, b); // Convert RGB values to HEX format

            if (currentShape.equals("Square")) {
                shape = new Square(color, 0, 0, size);
            } else {
                shape = new Circle(color, 0, 0, size);
            }

            shapes.add(shape);
            view.setShapes(shapes);
        }
    }

    /**
     * Method to handle the undrawing action when the Undraw button is pressed.
     */
    private void handleUndrawAction() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            view.setShapes(shapes);
        }
    }

    /**
     * Main method to launch the drawing application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        PaintAppView view = new PaintAppView();
        new PaintAppController(view);
        view.frame.setVisible(true);
        Timer timer = new Timer(1000/120, e -> view.canvas.repaint());
        timer.start();
    }
}
