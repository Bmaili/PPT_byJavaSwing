package graph;


import ui.DrawBoard;

import java.awt.*;
import java.io.Serializable;

/**
 * 基本图形的抽象类（橡皮擦类和多图形类都从该类继承）
 *
 * @date 21:14 2021/11/18
 */
public abstract class MyShape implements Serializable, Cloneable {
    private static final long serialVersionUID = 1111014L;

    //x1,y1是图形的容器的左上角坐标，x2,y2是图形的容器的右下角坐标，需要保证保证x1<=x2,y1<=y2
    protected int x1, x2, y1, y2; // 对角坐标
    protected Color mainColor;  //主色
    protected Color fillColor; //填充色
    protected int width; //边宽度
    protected boolean isFillShare; //是否填充


    public MyShape() {
        mainColor = DrawBoard.getInstance().mainColor;
        fillColor = DrawBoard.getInstance().fillColor;
        width = DrawBoard.getInstance().penWidth;
        isFillShare = DrawBoard.getInstance().isFillShape;
    }

    public MyShape(int x1, int y1, int x2, int y2) {
        this();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * 保证x1<=x2,y1<=y2
     *
     * @param
     * @return
     * @date 21:17 2021/11/18
     */
    protected void swap() {
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


    /**
     * 图形的绘画方法，不同的图形不一样
     *
     * @param
     * @return
     * @date 21:33 2021/11/18
     */
    public abstract void draw(Graphics p);

    /**
     * 判断鼠标落点是否在图形容器里面
     *
     * @param x 鼠标x坐标
     * @param y 鼠标y坐标
     * @return 落在图形容器里返回true, 否则false
     * @date 21:19 2021/11/18
     */
    public boolean pointInShape(int x, int y) {
        //  if ((x >= x1) && (x <= x2) && (y >= y1) && (y <= y2)) {
        //优化的判断逻辑，保证点落在图形容器边缘时也能选中
        if ((x >= x1 - width / 2 - 6) && (x <= x2 + width / 2 + 6) && (y >= y1 - width / 2 - 6) && (y <= y2 + width / 2 + 6)) {
            return true;
        }
        return false;
    }


    /**
     * 移动图形
     *
     * @param stop_x  当前鼠标坐标x值
     * @param stop_y  当前鼠标坐标y值
     * @param offsetX 最初鼠标落在图形里时相对图形容器左上角的x偏移量
     * @param offsetY 最初鼠标落在图形里时相对图形容器左上角的y偏移量
     * @return
     * @date 21:23 2021/11/18
     */
    public void dragShapeXY(int stop_x, int stop_y, int offsetX, int offsetY) {
        int boxWidth = x2 - x1;
        int boxHigh = y2 - y1;
        x1 = stop_x - offsetX;
        y1 = stop_y - offsetY;
        x2 = x1 + boxWidth;
        y2 = y1 + boxHigh;
    }

    /**
     * 设置放缩图形时，图形容器的坐标
     *
     * @param
     * @return
     * @date 21:32 2021/11/18
     */
    public void setChangeShapeXY(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}

