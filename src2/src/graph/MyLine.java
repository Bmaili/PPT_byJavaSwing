package graph;

import java.awt.*;

public class MyLine extends MyShape {
    private int start_x, start_y, stop_x, stop_y;

    public MyLine() {
        super();
    }

    public MyLine(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        start_x = x1;
        start_y = y1;
        stop_x = x2;
        stop_y = y2;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D p = (Graphics2D) g;
        this.swap();
        p.setStroke(new BasicStroke(this.width));

        p.setColor(this.mainColor);
        p.drawLine(start_x, start_y, stop_x, stop_y);
    }

    @Override
    public void dragShapeXY(int stop_x, int stop_y, int offsetX, int offsetY) {



        int offsetStartX = this.start_x - x1;
        int offsetStartY = this.start_y - y1;
        int offsetStopX = this.stop_x - x1;
        int offsetStopY = this.stop_y - y1;

        int boxWidth = x2 - x1;
        int boxHigh = y2 - y1;
        x1 = stop_x - offsetX;
        y1 = stop_y - offsetY;
        x2 = x1 + boxWidth;
        y2 = y1 + boxHigh;

        this.start_x = x1 + offsetStartX;
        this.start_y = y1 + offsetStartY;
        this.stop_x = x1 + offsetStopX;
        this.stop_y = y1 + offsetStopY;
    }
}
