package ui;


import graph.MyShape;
import listener.DrawBoardListener;
import saveobject.Page;
import saveobject.PPT;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;


public class DragDrawPanel extends JPanel {

    private static DragDrawPanel dragDrawPanel = new DragDrawPanel();
    public BufferedImage image = null;
    public PPT ppt;
    public Page nowPage;


    private DragDrawPanel() {
        ppt = new PPT();
        ppt.allPage.add(new Page());
        ppt.allPage.add(new Page());
        ppt.allPage.add(new Page());

        nowPage = ppt.allPage.get(0);

//		我自己设置的大小 ——11月13日
        setPreferredSize(new Dimension(864, 648));
        Border border = new LineBorder(Color.black);
        setBorder(border);
        setBackground(Color.white);
        bindEvent();

//        saveObject();
//        addMouseListener(this);
//        addMouseMotionListener(this);
    }

    public static DragDrawPanel getInstance() {
        return dragDrawPanel;
    }

    private void bindEvent() {
        // 得到监听器实例
//        DrawBoardListener topMenuListener = new DrawBoardListener(this);
        DrawBoardListener el = DrawBoardListener.getInstance();
        DrawBoardListener.getInstance().drawPanel = this;
        // 添加鼠标/鼠标移动/键盘的监听器
        // （因为el实现了鼠标MouseListener、鼠标移动MouseMotionListener、键盘KeyListener的接口，所以可以这样写）
        this.addMouseListener(el);
        this.addMouseMotionListener(el);
//        this.addKeyListener(topMenuListener);
        // 将绘图区域的“笔”传给监听器，在监听器内进行绘制
//        topMenuListener.setPen(this.getGraphics());
    }

    public void paint(Graphics p) {
        // 该函数是窗口大小变化时自动调用的函数，其中的p默认是this.getGraphics()（也就是绘图区域的画笔）
        // 为父类重新绘制（即添加背景色）
        super.paint(p);
//         如果读取了图片，则先把图片画上
        if (image != null) {
            p.drawImage(image, 0, 0, null);
        }
        DrawBoardListener el = DrawBoardListener.getInstance();
        // 遍历绘图历史，绘制该图形
        for (MyShape item : el.history) {
            item.draw(p);
        }
    }


}
