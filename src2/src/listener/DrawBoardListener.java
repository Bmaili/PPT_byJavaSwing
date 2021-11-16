package listener;

import graph.*;
import saveobject.PPT;
import saveobject.Page;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.LinkedList;

public class DrawBoardListener extends JPanel implements MouseInputListener {
    public PPT nowPPT;
    public Page nowPage;
    static long II = 0;

    private static DrawBoardListener drawBoardListener = new DrawBoardListener();

    public JPanel drawPanel = this;  //DragDrawPanel的唯一实例
    public String selectModule = "矩形";//选择的模式
    public int penWidth = 10;//线条尺寸
    public Color mainColor = Color.CYAN;//主笔色
    public Color fillColor = Color.PINK;//填充色
    public int start_x, start_y, stop_x, stop_y;//画笔的起始点到终点
    public boolean isFillShape = true;//是否填充图形

    public MyShape lastShape = null;
    MyShape selectShape = null;
    public int offsetX;
    public int offsetY;

    // 所有画过的图
    public LinkedList<MyShape> history = new LinkedList<>();
    // 保存实时按键的栈
    public Deque<Integer> stack = new LinkedList<>();
    // 保存按鼠标时的历史状态（用于笔和橡皮的撤销）
    public Deque<MyShape> previous = new LinkedList<>();
    // 保存所有可以移动的对象
    public LinkedList<MyShape> moveShape = new LinkedList<>();


    private DrawBoardListener() {
        nowPPT = new PPT();
        nowPPT.allPage.add(new Page());
        nowPPT.allPage.add(new Page());
        nowPPT.allPage.add(new Page());

        nowPage = nowPPT.allPage.get(0);

//		我自己设置的大小 ——11月13日
        setPreferredSize(new Dimension(864, 648));
        Border border = new LineBorder(Color.black);
        setBorder(border);
        setBackground(Color.white);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    public static DrawBoardListener getInstance() {
        return drawBoardListener;
    }
//    public DrawBoardListener(JPanel drawPanel) {
//        this.drawPanel=drawPanel;
//    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //    鼠标按下
    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("鼠标按下" + " " + start_x + " " + start_y + " " + stop_x + " " + stop_y);
        // 保存该个时刻的最新状态
        previous.push(history.peekLast());
        // 按下鼠标时调用的函数
        start_x = e.getX();
        start_y = e.getY();
        stop_x = e.getX();
        stop_y = e.getY();

        if (selectModule.equals("选择")) {
//            for (int i = moveShape.size() - 1; i >= 0; i--) {//最上层开始
            for (int i = 0; i < moveShape.size(); i++) {//最下层开始
                if (moveShape.get(i).pointInShape(start_x, start_y)) {
                    selectShape = moveShape.remove(i);
                    moveShape.add(selectShape);
                    offsetX = start_x - selectShape.getX1();
                    offsetY = start_y - selectShape.getY1();

                    int bordWidth = selectShape.getWidth() / 2;
                    ((Graphics2D) drawPanel.getGraphics()).setColor(Color.BLACK);
                    ((Graphics2D) drawPanel.getGraphics()).setStroke(new BasicStroke(3));
                    ((Graphics2D) drawPanel.getGraphics()).drawRoundRect(selectShape.getX1() - bordWidth - 6, selectShape.getY1() - bordWidth - 6, selectShape.getX2() - selectShape.getX1() + 2 * bordWidth + 12, selectShape.getY2() - selectShape.getY1() + 2 * bordWidth + 12, 40, 40);
                    for (int j = 0; j < history.size(); j++) {
                        if (selectShape.getId() == history.get(j).getId()) {
                            MyShape remove = history.remove(j);
                            history.add(remove);
                            remove.draw(drawPanel.getGraphics());
                            break;
                        }

                    }
                    break;
                }
            }
        } else {
            addShape();
        }
        drawPanel.requestFocus();
    }

    //    鼠标释放
    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("鼠标释放"+" "+start_x+" "+start_y+" "+stop_x+" "+stop_y);
        stop_x = e.getX();
        stop_y = e.getY();
        switch (this.selectModule) {
            case "画笔":
                addShape();
                break;
            case "橡皮":
                addShape();
                break;
            case "选择":
                if (selectShape != null) {
                    selectShape.dragShapeXY(stop_x, stop_y, offsetX, offsetY);
//                    作画
                    revert(true);
                    // 加入历史
                    lastShape = selectShape;
                    history.add(lastShape);
                    // 用pen将tmp画在图上
                    lastShape.draw(drawPanel.getGraphics());
                }
                selectShape = null;
                break;

            default:
                revert(true);
                addShape();
                moveShape.add(lastShape);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
//        System.out.println("鼠标拖拽");
        stop_x = mouseEvent.getX();
        stop_y = mouseEvent.getY();
        switch (this.selectModule) {
            case "画笔":
                addShape();
                start_x = stop_x;
                start_y = stop_y;
                break;
            case "橡皮":
                addShape();
                start_x = stop_x;
                start_y = stop_y;
                break;
            case "选择":
                if (selectShape != null) {
                    selectShape.dragShapeXY(stop_x, stop_y, offsetX, offsetY);
//                    作画
                    revert(true);
                    // 加入历史
                    lastShape = selectShape;
                    history.add(lastShape);
                    // 用pen将tmp画在图上
                    lastShape.draw(drawPanel.getGraphics());
                }
                break;

            default:
                revert(true);
                addShape();
                break;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    // 撤销有两种类型，锁定撤销和非锁定撤销
    // 锁定撤销时，起点不会被删除，在动态拖拽操作中使用
    // 非锁定撤销时，起点和边同时被删除，在手动调用的撤销操作中使用
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

    private void addShape() {
        // 添加新图
        switch (this.selectModule) {
            case "画笔":
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
//                lastShape = new MyRectangle(start_x, start_y, stop_x, stop_y);
                lastShape = null;
                break;
        }

        // 加入历史
        if (lastShape != null)
            history.add(lastShape);
        // 用pen将tmp画在图上
        lastShape.draw(drawPanel.getGraphics());
    }

    public void setDrawBoardListener() {
        Page nowPage = DrawBoardListener.getInstance().nowPage;
        this.history = nowPage.history;
        this.stack = nowPage.stack;
        this.previous = nowPage.previous;
        this.moveShape = nowPage.moveShape;
        //取得画板对象重绘
        DrawBoardListener.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());

    }

    public void paint(Graphics p) {
        // 该函数是窗口大小变化时自动调用的函数，其中的p默认是this.getGraphics()（也就是绘图区域的画笔）
        // 为父类重新绘制（即添加背景色）
        super.paint(p);

//         如果读取了图片，则先把图片画上
        if (drawBoardListener.nowPage.insertImage != null) {
            p.drawImage(drawBoardListener.nowPage.insertImage, 0, 0, null);
        }
//        DrawBoardListener el = DrawBoardListener.getInstance();
        // 遍历绘图历史，绘制该图形
        for (MyShape item : history) {
            item.draw(p);
        }
//        System.out.println("重绘" + (++II) + "次");
    }
}
