import java.awt.*;

/**
 * Circle class that extends the abstract GeometricObject class.
 * Represents a circle with a specified color, position, and radius.
 */
class Circle extends GeometricObject {

    // Variable to store the radius of the circle
    private int radius;

    /**
     * Constructor to initialize a circle with the given color, position, and radius.
     *
     * @param color The color of the circle.
     * @param x The x-coordinate of the circle's center.
     * @param y The y-coordinate of the circle's center.
     * @param radius The radius of the circle.
     */
    public Circle(String color, int x, int y, int radius) {
        super(color, x, y); // Call the constructor of the superclass
        this.radius = radius;
    }

    /**
     * Overrides the abstract draw method to draw the circle.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.decode(color)); // Set the color for drawing
        // Draw the circle with the specified position and radius
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2); // Offset by radius to center the circle
    }

    /**
     * Overrides the abstract method to determine if the circle contains a given point.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the circle contains the point, false otherwise.
     */
    @Override
    public boolean containsPoint(int x, int y) {
        int dx = x - this.x;
        int dy = y - this.y;
        // Check if the point lies within the circle using the distance formula
        return dx * dx + dy * dy <= radius * radius;
    }

    /**
     * Overrides the abstract method to determine if a point is inside a given range from the circle.
     *
     * @param mouseX The x-coordinate of the point.
     * @param mouseY The y-coordinate of the point.
     * @param range The range to check within.
     * @return true if the point is inside the range, false otherwise.
     */
    @Override
    public boolean isInsideRange(int mouseX, int mouseY, int range) {
        int dx = mouseX - x;
        int dy = mouseY - y;
        double distance = Math.sqrt(dx * dx + dy * dy); // Calculate the distance between the point and the circle's center
        return distance <= (radius + range); // Check if the distance is within the specified range
    }
}
