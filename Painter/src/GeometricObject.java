import java.awt.*;

abstract class GeometricObject {
    protected String color;
    protected int x, y;

    public GeometricObject(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public abstract void draw(Graphics g);
    public abstract boolean containsPoint(int x, int y);
    public abstract boolean isInsideRange(int mouseX, int mouseY, int range);
}