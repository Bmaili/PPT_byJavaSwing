package graph;

import java.awt.*;

/**
 * 橡皮擦
 *
 * @date 21:41 2021/11/18
 */
public class MyEraser extends MyShape {
    public MyEraser() {
        super();
    }

    public MyEraser(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void draw(Graphics p) {
        p.setColor(Color.WHITE);
        // 以(x1,y1)为圆心，this.width*3为半径的白色实心圆
        //其实就是一个保留运动轨迹的白色圆，覆盖之前的图形
        p.fillOval(x1 - this.width * 3, y1 - this.width * 3, this.width * 6, this.width * 6);
    }

}
