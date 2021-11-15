package graph;


import listener.DrawBoardListener;

import java.awt.*;
import java.io.Serializable;

// 基本图形的抽象类（橡皮擦类和多图形类都从该类继承）
public abstract class MyShape implements Serializable {
    private static final long serialVersionUID = 1111014L;

    public static long shapeNum = 0L;
    //    对角坐标
    protected int x1, x2, y1, y2;
    protected Color mainColor;
    protected Color fillColor;
    protected int width;
    protected boolean isFillShare;
    protected long id;


    public MyShape() {
        id = shapeNum++;
        mainColor = DrawBoardListener.getInstance().mainColor;
        fillColor = DrawBoardListener.getInstance().fillColor;
        width = DrawBoardListener.getInstance().penWidth;
        isFillShare = DrawBoardListener.getInstance().isFillShape;
    }

    public MyShape(int x1, int y1, int x2, int y2) {
        this();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    protected void swap() {
        // 保证x1<=x2,y1<=y2，在绘制矩形、圆时需要
        if (x1 > x2) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 > y2) {
            int tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public long getId() {
        return id;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract void draw(Graphics p);

    public boolean pointInShape(int x, int y) {
        if ((x >= x1) && (x <= x2) && (y >= y1) && (y <= y2)) {
            return true;
        }
        return false;
    }

    public void dragShapeXY(int stop_x, int stop_y, int offsetX, int offsetY) {
        int boxWidth = x2 - x1;
        int boxHigh = y2 - y1;
        x1 = stop_x - offsetX;
        y1 = stop_y - offsetY;
        x2 = x1 + boxWidth;
        y2 = y1 + boxHigh;
    }

}

