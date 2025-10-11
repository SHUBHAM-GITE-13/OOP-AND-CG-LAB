import java.awt.*;
import javax.swing.*;

public class LinePattern extends JPanel {

    // ================= DDA Line Algorithm =================
    void drawDDALine(Graphics g, int x1, int y1, int x2, int y2, String style) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        float steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xInc = dx / steps;
        float yInc = dy / steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            switch (style) {
                case "DOTTED":
                    if (i % 4 == 0) g.fillRect(Math.round(x), Math.round(y), 1, 1);
                    break;
                case "THICK":
                    g.fillRect(Math.round(x), Math.round(y), 3, 3);
                    break;
                default:
                    g.fillRect(Math.round(x), Math.round(y), 1, 1);
            }
            x += xInc;
            y += yInc;
        }
    }

    // ================= Bresenham Line Algorithm =================
    void drawBresenhamLine(Graphics g, int x1, int y1, int x2, int y2, String style) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            switch (style) {
                case "DASHED":
                    if ((x1 + y1) % 10 < 6) g.fillRect(x1, y1, 1, 1);
                    break;
                case "SOLID":
                    g.fillRect(x1, y1, 1, 1);
                    break;
                default:
                    g.fillRect(x1, y1, 1, 1);
            }

            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    // ================= Drawing the Pattern =================
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        g.setColor(Color.BLACK);

        // Outer Rectangle (DDA - Dotted)
        drawDDALine(g, 100, 100, 300, 100, "DOTTED");
        drawDDALine(g, 300, 100, 300, 200, "DOTTED");
        drawDDALine(g, 300, 200, 100, 200, "DOTTED");
        drawDDALine(g, 100, 200, 100, 100, "DOTTED");

        // Inner Rectangle (DDA - Thick)
        drawDDALine(g, 150, 130, 250, 130, "THICK");
        drawDDALine(g, 250, 130, 250, 170, "THICK");
        drawDDALine(g, 250, 170, 150, 170, "THICK");
        drawDDALine(g, 150, 170, 150, 130, "THICK");

        // Diamond (Bresenham - Dashed)
        drawBresenhamLine(g, 200, 60, 320, 150, "DASHED");
        drawBresenhamLine(g, 320, 150, 200, 240, "DASHED");
        drawBresenhamLine(g, 200, 240, 80, 150, "DASHED");
        drawBresenhamLine(g, 80, 150, 200, 60, "DASHED");

        // Inner Solid Diamond (Bresenham - Solid)
        drawBresenhamLine(g, 150, 100, 250, 100, "SOLID");
        drawBresenhamLine(g, 250, 100, 250, 200, "SOLID");
        drawBresenhamLine(g, 250, 200, 150, 200, "SOLID");
        drawBresenhamLine(g, 150, 200, 150, 100, "SOLID");
    }

    // ================= Main Method =================
    public static void main(String[] args) {
        JFrame frame = new JFrame("DDA & Bresenham Pattern");
        LinePattern panel = new LinePattern();
        frame.add(panel);
        frame.setSize(420, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
