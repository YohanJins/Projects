import java.awt.*;

/**
 * Square class that extends the abstract GeometricObject class.
 * Represents a square with a specified color, position, and size.
 */
class Square extends GeometricObject {

    // Variable to store the size (side length) of the square
    private int size;

    /**
     * Constructor to initialize a square with the given color, position, and size.
     *
     * @param color The color of the square.
     * @param x The x-coordinate of the square's center.
     * @param y The y-coordinate of the square's center.
     * @param size The side length of the square.
     */
    public Square(String color, int x, int y, int size) {
        super(color, x, y); // Call the constructor of the superclass
        this.size = size;
    }

    /**
     * Overrides the abstract draw method to draw the square.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.decode(color)); // Set the color for drawing
        // Draw the square with the specified position and size, offsetting by half the size to center it
        g.fillRect(x - size/2, y - size/2, size, size);
    }

    /**
     * Overrides the abstract method to determine if the square contains a given point.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the square contains the point, false otherwise.
     */
    @Override
    public boolean containsPoint(int x, int y) {
        // Check if the point lies within the boundaries of the square
        return x >= this.x - size/2 && x <= this.x + size/2 && y >= this.y - size/2 && y <= this.y + size/2;
    }

    /**
     * Overrides the abstract method to determine if a point is inside a given range from the square.
     *
     * @param mouseX The x-coordinate of the point.
     * @param mouseY The y-coordinate of the point.
     * @param range The range to check within.
     * @return true if the point is inside the range, false otherwise.
     */
    @Override
    public boolean isInsideRange(int mouseX, int mouseY, int range) {
        // Check if the point lies within the specified range from the square's boundaries
        return mouseX >= (x - size/2 - range) && mouseX <= (x + size/2 + range) &&
                mouseY >= (y - size/2 - range) && mouseY <= (y + size/2 + range);
    }
}
