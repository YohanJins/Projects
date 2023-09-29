import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Timer;

public class PaintAppController {
    private PaintAppView view;
    private ArrayList<GeometricObject> shapes = new ArrayList<>();
    private String currentShape = "Square"; // Default shape
    private boolean isErasing = false;
    public PaintAppController(PaintAppView view) {
        this.view = view;

        view.drawButton.addActionListener(e -> {
            isErasing = false; // Draw 버튼을 누르면 지우기 모드 해제
            handleDrawAction();
        });
        view.undrawButton.addActionListener(e -> isErasing = true); // Undraw 버튼을 누르면 지우기 모드 활성화
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

    private void drawShapeAt(int x, int y) {
        if (validateInput()) {
            GeometricObject shape;
            int size = Integer.parseInt(view.sizeField.getText());

            int r = Integer.parseInt(view.rField.getText());
            int g = Integer.parseInt(view.gField.getText());
            int b = Integer.parseInt(view.bField.getText());
            String color = String.format("#%02x%02x%02x", r, g, b); // RGB 값을 HEX 형식으로 변환

            if (currentShape.equals("Square")) {
                shape = new Square(color, x, y, size);
            } else {
                shape = new Circle(color, x, y, size);
            }

            shapes.add(shape);
            view.setShapes(shapes);
        }
    }

    private void eraseShapeAt(int x, int y) {
        int size = Integer.parseInt(view.sizeField.getText());
        ArrayList<GeometricObject> shapesToRemove = new ArrayList<>();

        for (GeometricObject shape : shapes) {
            if (shape.isInsideRange(x, y, size/2)) { // size/2를 사용하여 마우스 위치를 중심으로 범위를 설정
                shapesToRemove.add(shape);
            }
        }

        shapes.removeAll(shapesToRemove);
        view.setShapes(shapes);
    }


    private boolean validateInput() {
        // 모든 필드가 채워져 있는지 확인
        if (view.rField.getText().isEmpty() || view.gField.getText().isEmpty() || view.bField.getText().isEmpty() || view.sizeField.getText().isEmpty()) {
            view.errorLabel.setText("모든 필드를 채워주세요!");
            view.errorLabel.setForeground(Color.RED);
            return false;
        }

        // RGB 값이 0과 255 사이인지 확인
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

    private void handleDrawAction() {
        if (validateInput()) {
            GeometricObject shape;
            int size = Integer.parseInt(view.sizeField.getText());

            int r = Integer.parseInt(view.rField.getText());
            int g = Integer.parseInt(view.gField.getText());
            int b = Integer.parseInt(view.bField.getText());
            String color = String.format("#%02x%02x%02x", r, g, b); // RGB 값을 HEX 형식으로 변환

            if (currentShape.equals("Square")) {
                shape = new Square(color, 0, 0, size);
            } else {
                shape = new Circle(color, 0, 0, size);
            }

            shapes.add(shape);
            view.setShapes(shapes);
        }
    }


    private void handleUndrawAction() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            view.setShapes(shapes);
        }
    }

    public static void main(String[] args) {
        PaintAppView view = new PaintAppView();
        new PaintAppController(view);
        view.frame.setVisible(true);
        Timer timer = new Timer(1000/120, e -> view.canvas.repaint());
        timer.start();
    }
}
