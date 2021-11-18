package graph;

import java.awt.*;

/**
 * 圆形类
 *
 * @date 21:42 2021/11/18
 */
public class MyCircle extends MyShape {
    public MyCircle(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D p = (Graphics2D) g;
        this.swap();
        p.setStroke(new BasicStroke(this.width));

        p.setColor(this.mainColor);
        p.drawOval(x1, y1, x2 - x1, y2 - y1);
        if (isFillShare) {
            p.setColor(this.fillColor);
            p.fillOval(x1, y1, x2 - x1, y2 - y1);
        }
    }
}
