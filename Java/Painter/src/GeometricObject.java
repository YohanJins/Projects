import java.awt.*;

/**
 * Abstract class representing a generic geometric object.
 * This class provides a foundation for specific geometric shapes.
 */
abstract class GeometricObject {

    // Variables to store the color and position of the geometric object
    protected String color;
    protected int x, y;

    /**
     * Constructor to initialize a geometric object with the given color and position.
     *
     * @param color The color of the geometric object.
     * @param x The x-coordinate of the geometric object's position.
     * @param y The y-coordinate of the geometric object's position.
     */
    public GeometricObject(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    /**
     * Abstract method to draw the geometric object.
     *
     * @param g The graphics context used for drawing.
     */
    public abstract void draw(Graphics g);

    /**
     * Abstract method to determine if the geometric object contains a given point.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the geometric object contains the point, false otherwise.
     */
    public abstract boolean containsPoint(int x, int y);

    /**
     * Abstract method to determine if a point is inside a given range from the geometric object.
     *
     * @param mouseX The x-coordinate of the point.
     * @param mouseY The y-coordinate of the point.
     * @param range The range to check within.
     * @return true if the point is inside the range, false otherwise.
     */
    public abstract boolean isInsideRange(int mouseX, int mouseY, int range);
}
