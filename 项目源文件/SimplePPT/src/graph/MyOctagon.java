package graph;

import java.awt.*;

/**
 * 八边形类
 *
 * @date 21:34 2021/11/18
 */
public class MyOctagon extends MyShape {
    public MyOctagon() {
        super();
    }

    public MyOctagon(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D p = (Graphics2D) g;
        this.swap();
        p.setStroke(new BasicStroke(this.width));

        p.setColor(this.mainColor);

        int[] xx = {x1 + (x2 - x1) / 3, x1 + (x2 - x1) / 3 * 2, x2, x2, x1 + (x2 - x1) / 3 * 2, x1 + (x2 - x1) / 3, x1, x1};
        int[] yy = {y1, y1, y1 + (y2 - y1) / 3, y1 + (y2 - y1) / 3 * 2, y2, y2, y1 + (y2 - y1) / 3 * 2, y1 + (y2 - y1) / 3};
        p.drawPolygon(xx, yy, 8);
        if (isFillShare) {
            p.setColor(this.fillColor);
            p.fillPolygon(xx, yy, 8);
        }
    }
}
