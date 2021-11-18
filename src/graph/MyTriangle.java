package graph;

import java.awt.*;

/**
 * 三角形类
 *
 * @date 21:34 2021/11/18
 */
public class MyTriangle extends MyShape {
    public MyTriangle() {
        super();
    }

    public MyTriangle(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D p = (Graphics2D) g;
        this.swap();
        p.setStroke(new BasicStroke(this.width));

        p.setColor(this.mainColor);

        int[] xx = {(x1 + x2) / 2, x1, x2};
        int[] yy = {y1, y2, y2};
        p.drawPolygon(xx, yy, 3);
        if (isFillShare) {
            p.setColor(this.fillColor);
            p.fillPolygon(xx, yy, 3);
        }
    }
}
