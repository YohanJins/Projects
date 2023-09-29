import java.awt.*;

// Square.java
class Square extends GeometricObject {
    private int size;

    public Square(String color, int x, int y, int size) {
        super(color, x, y);
        this.size = size;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.decode(color));
        g.fillRect(x - size/2, y - size/2, size, size); // 중심을 기준으로 그리기 위해 size/2 만큼 오프셋
    }

    @Override
    public boolean containsPoint(int x, int y) {
        return x >= this.x - size/2 && x <= this.x + size/2 && y >= this.y - size/2 && y <= this.y + size/2;
    }

    @Override
    public boolean isInsideRange(int mouseX, int mouseY, int range) {
        return mouseX >= (x - size/2 - range) && mouseX <= (x + size/2 + range) &&
                mouseY >= (y - size/2 - range) && mouseY <= (y + size/2 + range);
    }
}