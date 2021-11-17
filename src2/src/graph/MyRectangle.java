package graph;


import java.awt.*;

public class MyRectangle extends MyShape {

    public MyRectangle() {
        super();
    }

    public MyRectangle(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D p = (Graphics2D) g;
        this.swap();
        p.setStroke(new BasicStroke(this.width));

        p.setColor(this.mainColor);
        p.drawRect(x1, y1, x2 - x1, y2 - y1);
        if (isFillShare) {
            p.setColor(this.fillColor);
            p.fillRect(x1, y1, x2 - x1, y2 - y1);
        }
    }
}
