package ui;

import graph.*;
import saveobject.PPT;
import saveobject.Page;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * 画板类，记录了绘画时的各种工作状态参数，以及绑定画板鼠标事件
 *
 * @date 21:05 2021/11/18
 */
public class DrawBoard extends JPanel implements MouseInputListener {
    private static DrawBoard drawBoard = new DrawBoard();

    //这俩对象大多模块都用到
    public PPT nowPPT;  //当前正在操作的PPT
    public Page nowPage; //当前的正在操作的page

    //当前画板的相关对象、模式
    public JPanel drawPanel = this;  //DragDrawPanel的唯一实例
    public String selectModule = "矩形";//选择的模式
    public int penWidth = 10;//线条尺寸
    public Color mainColor = Color.CYAN;//主笔色
    public Color fillColor = Color.PINK;//填充色
    public int start_x, start_y, stop_x, stop_y;//画笔的起始点到终点
    public boolean isFillShape = true;//是否填充图形

    //特殊用途的一些变量,只有这个模块用到
    private MyShape lastShape = null;  //当前画板上最新的一个图像对象（不包括自由绘、橡皮擦）
    private MyShape selectShape = null;//当前画板上可选的一个图像对象（即不包括自由绘、橡皮擦）
    private int offsetX; //坐标偏移量X
    private int offsetY; //坐标偏移量Y , 这俩用于移动图像
    private boolean fangsuo; //当前是否处于图形放缩模式，即是否点击了右下角小方框
    private int shapeStartX; //图形的起点X坐标
    private int shapeStartY; //图形的起点Y坐标

    //非常重要的三个列表
    public LinkedList<MyShape> history = new LinkedList<>();    // 所有画过的图
    public LinkedList<MyShape> previous = new LinkedList<>();    // 保存按鼠标时的历史状态（用于笔和橡皮的撤销）
    public LinkedList<MyShape> moveShape = new LinkedList<>();    // 保存所有可以移动的对象

    private DrawBoard() {

        //画板界面设计
        setPreferredSize(new Dimension(1100, 648));
        Border border = new LineBorder(Color.black);
        setBorder(border);
        setBackground(Color.white);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //程序启动时new一个PPT，这个PPT有一空白Page
        nowPPT = new PPT();
        nowPPT.allPage.add(new Page());
        nowPage = nowPPT.allPage.get(0);
    }


    public static DrawBoard getInstance() {
        return drawBoard;
    }


    /**
     * 鼠标按下时触发
     *
     * @param
     * @return
     * @date 20:20 2021/11/18
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // System.out.println("鼠标按下" + " " + start_x + " " + start_y + " " + stop_x + " " + stop_y);

        // 保存该个时刻的最新状态
        if (previous.peekLast() != history.peekLast())
            previous.push(history.peekLast());

        //更新鼠标坐标
        start_x = e.getX();
        start_y = e.getY();
        stop_x = e.getX();
        stop_y = e.getY();

        if (selectModule.equals("选择")) {
            clickWhenSelect(); //如果当前是选择模式则进行图形移动或者放缩操作
        } else {
            addShape();//其他模式
        }
        drawPanel.requestFocus();//画板获取焦点
    }

    /**
     * 鼠标释放时触发
     *
     * @param
     * @return
     * @date 20:25 2021/11/18
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //  System.out.println("鼠标释放"+" "+start_x+" "+start_y+" "+stop_x+" "+stop_y);

        stop_x = e.getX();
        stop_y = e.getY();

        //根据所选的模式执行不同操作
        switch (this.selectModule) {
            case "画笔": //画笔和橡皮的思路一样，都是保持历史轨迹
            case "橡皮":
                addShape();
                break;

            case "选择":
                if (selectShape != null) {
                    if (fangsuo) { //如果当前是放缩模式则改变选择的图形的坐标
                        selectShape.setChangeShapeXY(shapeStartX, shapeStartY, stop_x, stop_y);
                        selectShape.draw(drawPanel.getGraphics());
                    } else {//如果不是放缩模式则移动图形，也是改变坐标
                        selectShape.dragShapeXY(stop_x, stop_y, offsetX, offsetY);
                    }

                    // 作画
                    revert(true);
                    // 加入历史
                    lastShape = selectShape;
                    history.add(lastShape);
                    lastShape.draw(drawPanel.getGraphics());
                }

                //鼠标释放后记得把这俩标记变量重置
                selectShape = null;
                fangsuo = false;
                break;

            default:  //相比于画笔和橡皮，需要revert（）删掉最新一次的作图，即：删除历史轨迹
                revert(true);
                addShape();
                moveShape.add(lastShape);
                break;
        }

    }

    /**
     * 鼠标拖拽时触发
     *
     * @param
     * @return
     * @date 20:30 2021/11/18
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        // System.out.println("鼠标拖拽");
        stop_x = mouseEvent.getX();
        stop_y = mouseEvent.getY();

        //根据所选的模式执行不同操作
        switch (this.selectModule) {

            case "画笔":  //画笔和橡皮的思路一样，都是保持历史轨迹
            case "橡皮":
                addShape();
                start_x = stop_x;
                start_y = stop_y;
                break;

            case "选择":
                if (selectShape != null) {
                    if (fangsuo) {//如果当前是放缩模式则改变选择的图形的坐标
                        selectShape.setChangeShapeXY(shapeStartX, shapeStartY, stop_x, stop_y);
                        selectShape.draw(drawPanel.getGraphics());
                    } else {//如果不是放缩模式则移动图形，也是改变坐标
                        selectShape.dragShapeXY(stop_x, stop_y, offsetX, offsetY);
                    }

                    revert(true);// 作画

                    // 加入历史
                    lastShape = selectShape;
                    history.add(lastShape);
                    lastShape.draw(drawPanel.getGraphics());
                }
                break;

            default:
                revert(true);
                addShape();
                break;
        }

    }

    /**
     * 添加新图片
     *
     * @param
     * @return
     * @date 20:36 2021/11/18
     */
    private void addShape() {
        // 根据不同的选择的模式new不同的图案对象
        switch (this.selectModule) {
            case "画笔":  //没错，画笔也是line，留下痕迹的line
                lastShape = new MyLine(start_x, start_y, stop_x, stop_y);
                break;
            case "橡皮":
                lastShape = new MyEraser(start_x, start_y, stop_x, stop_y);
                break;
            case "直线":
                lastShape = new MyLine(start_x, start_y, stop_x, stop_y);
                break;
            case "矩形":
                lastShape = new MyRectangle(start_x, start_y, stop_x, stop_y);
                break;
            case "圆形":
                lastShape = new MyCircle(start_x, start_y, stop_x, stop_y);
                break;
            case "三角形":
                lastShape = new MyTriangle(start_x, start_y, stop_x, stop_y);
                break;
            case "八边形":
                lastShape = new MyOctagon(start_x, start_y, stop_x, stop_y);
                break;
            case "星星":
                lastShape = new MyFiveStar(start_x, start_y, stop_x, stop_y);
                break;
            case "文本":
                lastShape = new MyText(start_x, start_y, stop_x, stop_y);
                break;
            default:
                lastShape = null;
                break;
        }

        // 加入历史
        if (lastShape != null)
            history.add(lastShape);
        lastShape.draw(drawPanel.getGraphics());
    }


    /**
     * 撤销有两种类型，锁定撤销和非锁定撤销
     * 锁定撤销时，起点不会被删除，在动态拖拽操作中使用
     * 非锁定撤销时，起点和边同时被删除，在手动调用的撤销操作中使用
     *
     * @param
     * @return
     * @date 20:35 2021/11/18
     */
    public void revert(boolean fixed) {
        MyShape toCompare = fixed ? previous.peek() : previous.poll();
        MyShape tmp;
        while ((tmp = history.peekLast()) != null) {
            if (!tmp.equals(toCompare)) {
                history.pollLast();
            } else {
                break;
            }
        }
        drawPanel.repaint();
    }

    /**
     * 用新的Page数据刷新当前Page数据，一般用于”更新了nowPage但是尚未更新nowPage里的数据“之后
     *
     * @param pageIndex 当前页面的页码
     * @return
     * @date 22:45 2021/11/18
     */
    public void flushNowPage(int pageIndex) {
        //更新画板当前page
        drawBoard.nowPage = drawBoard.nowPPT.allPage.get(pageIndex);

        //更新画板page的相关数据
        DrawBoard db = drawBoard;
        db.history = drawBoard.nowPage.history;
        db.moveShape = drawBoard.nowPage.moveShape;
        db.previous = drawBoard.nowPage.previous;

        //取得画板对象重绘
        drawBoard.nowPage = drawBoard.nowPPT.allPage.get(0);

        //取得画板对象重绘
        drawBoard.paint(drawBoard.drawPanel.getGraphics());
    }

    /**
     * 该函数是窗口大小变化时自动调用的函数，其中的p默认是this.getGraphics()（也就是绘图区域的画笔）
     *
     * @param
     * @return
     * @date 20:48 2021/11/18
     */
    public void paint(Graphics p) {
        // 为父类重新绘制（即添加背景色）
        super.paint(p);

        // 如果插入了背景图，则先加入背景图片
        if (drawBoard.nowPage.insertImage != null) {
            p.drawImage(drawBoard.nowPage.insertImage, 0, 0, null);
        }

        // 遍历绘图历史，绘制该图形
        for (MyShape item : history) {
            item.draw(p);
        }
    }

    /**
     * 当为“选择"模式并且鼠标点击画板时调用
     * 一：获取点击时鼠标的坐标
     * 二、从最下层图像开始遍历，当鼠标坐标在此图片的“容器”内时，停止遍历，将此图像置于最上层，标记为“选中”图形
     * 三、根据是否为放缩模式（boolean fangsuo），对图形移动或放缩
     *
     * @param
     * @return
     * @date 20:50 2021/11/18
     */
    public void clickWhenSelect() {

        // for (int i = moveShape.size() - 1; i >= 0; i--) {//最上层开始
        for (int i = 0; i < moveShape.size(); i++) {//最下层开始
            if (moveShape.get(i).pointInShape(start_x, start_y)) {  //鼠标是否落在图形内

                //设置变量且将图形置于最上层
                selectShape = moveShape.remove(i);
                moveShape.add(selectShape);
                offsetX = start_x - selectShape.getX1();
                offsetY = start_y - selectShape.getY1();

                //放在history最上层
                for (int j = 0; j < history.size(); j++) {
                    if (selectShape.equals(history.get(j))) {
                        MyShape remove = history.remove(j);
                        history.add(remove);
                        remove.draw(drawPanel.getGraphics());
                        break;
                    }
                }

                //删除previous里的此图
                for (int j = 0; j < previous.size(); j++) {
                    if (selectShape.equals(previous.get(j))) {
                        previous.remove(j);
                        break;
                    }
                }

                //画个选中样式框
                int bordWidth = selectShape.getWidth() / 2;
                ((Graphics2D) drawPanel.getGraphics()).setColor(Color.BLACK);
                ((Graphics2D) drawPanel.getGraphics()).setStroke(new BasicStroke(3));

                //这是那个右下角小框框（是否放缩）
                ((Graphics2D) drawPanel.getGraphics()).drawRect(selectShape.getX2() - bordWidth / 2, selectShape.getY2() - bordWidth / 2, 10, 10);

                //若鼠标落在这个小框框里面，则进入放缩模式
                if (start_x >= selectShape.getX2() - bordWidth / 2 && start_x <= selectShape.getX2() + 10 && start_y >= selectShape.getY2() - bordWidth / 2 && start_y <= selectShape.getY2() + 10) {
                    System.out.println("放缩模式");
                    ((Graphics2D) drawPanel.getGraphics()).drawRect(selectShape.getX1() - bordWidth - 6, selectShape.getY1() - bordWidth - 6, selectShape.getX2() - selectShape.getX1() + 2 * bordWidth + 12, selectShape.getY2() - selectShape.getY1() + 2 * bordWidth + 12);

                    fangsuo = true;
                    shapeStartX = selectShape.getX1();
                    shapeStartY = selectShape.getY1();

                } else {//否则fangsuo=false，即移动图像模式，下面这句是画个圆角矩形选中框，以便区别于上面那个方角矩形选中框
                    ((Graphics2D) drawPanel.getGraphics()).drawRoundRect(selectShape.getX1() - bordWidth - 6, selectShape.getY1() - bordWidth - 6, selectShape.getX2() - selectShape.getX1() + 2 * bordWidth + 12, selectShape.getY2() - selectShape.getY1() + 2 * bordWidth + 12, 40, 40);
                }
                break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
