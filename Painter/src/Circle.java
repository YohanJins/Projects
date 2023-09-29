import java.awt.*;

// Circle.java
class Circle extends GeometricObject {
    private int radius;

    public Circle(String color, int x, int y, int radius) {
        super(color, x, y);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.decode(color));
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2); // 중심을 기준으로 그리기 위해 radius 만큼 오프셋
    }

    @Override
    public boolean containsPoint(int x, int y) {
        int dx = x - this.x;
        int dy = y - this.y;
        return dx * dx + dy * dy <= radius * radius;
    }
    @Override
    public boolean isInsideRange(int mouseX, int mouseY, int range) {
        int dx = mouseX - x;
        int dy = mouseY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= (radius + range);
    }
}