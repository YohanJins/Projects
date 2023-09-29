import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PaintAppView {
    public JFrame frame;
    public JPanel controlPanel, canvas;
    public JButton drawButton, undrawButton, squareButton, circleButton;
    public JTextField rField, gField, bField, sizeField;
    public JLabel errorLabel;
    private ArrayList<GeometricObject> shapes;

    public PaintAppView() {
        frame = new JFrame("Simple Drawing App");
        frame.setSize(1200, 900); // 크기를 1600x1200으로 변경
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false); // 크기 변경 불가능하게 설정
        frame.setLocationRelativeTo(null); // 중앙에 위치하게 설정

        controlPanel = new JPanel();
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

        drawButton = new JButton("Draw");
        undrawButton = new JButton("Erase");
        squareButton = new JButton("Square");
        circleButton = new JButton("Circle");

        rField = new JTextField(3); // RGB 필드의 크기를 3으로 설정
        gField = new JTextField(3);
        bField = new JTextField(3);
        sizeField = new JTextField(10);

        errorLabel = new JLabel("No Errors");
        errorLabel.setForeground(Color.GREEN);

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

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);
    }

    public void setShapes(ArrayList<GeometricObject> shapes) {
        this.shapes = shapes;
        canvas.repaint();
    }
}
