package graph;

import java.awt.*;

/**
 * 直线类
 *
 * @date 21:37 2021/11/18
 */
public class MyLine extends MyShape {
    private int start_x, start_y, stop_x, stop_y;//线条的起点与终点，线条的绘图逻辑不同于其他

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
        //因为绘图逻辑不一样，所以dragshapexy() 与setChangeShapeXY()需要重写
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

    @Override
    public void setChangeShapeXY(int x1, int y1, int x2, int y2) {
        // super.setChangeShapeXY(x1, y1, x2, y2);
        stop_x = x2;
        stop_y = y2;

        this.x1 = start_x;
        this.y1 = start_y;
        this.x2 = stop_x;
        this.y2 = stop_y;
        swap();
    }

}
