package ui;


import listener.DrawBoardListener;
import listener.TopMenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyJFrame extends JFrame {
//    TopMenuListener t = TopMenuListener.getInstance();
//    DrawBoardListener el = DrawBoardListener.getInstance();
    private static MyJFrame mainWin = new MyJFrame("工作页面");

    public static MyJFrame getInstance() {
        return mainWin;
    }

    private MyJFrame(String name) {
        super(name);
    }

    private void init() {


        this.setJMenuBar(MyJMenuBar.getInstance());

        JPanel down = new JPanel();

        //我电脑1920x1080
        down.setPreferredSize(new Dimension(768, 432));
//        down.setLayout(new GridBagLayout());
        down.add(DrawBoardListener.getInstance());

        //创建一个水平分隔面板
        JSplitPane drawSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(PageListPanel.pageJList), down);
        //设置支持连续布局
        drawSplit.setContinuousLayout(true);

        Box drawBox = Box.createHorizontalBox();
        drawBox.add(drawSplit);


        Box MainBox = Box.createVerticalBox();
        MainBox.add(TopMenu.getInstance());
        MainBox.add(drawBox);

        mainWin.add(MainBox);


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();

        // 全屏显示
        mainWin.setExtendedState(Frame.MAXIMIZED_BOTH);
        // 非全屏后自适应屏幕
//        mainWin.pack();

        mainWin.setBounds(0, 0, dimension.width, dimension.height);

        // 定义关闭程序
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置可见
        mainWin.setVisible(true);

        for (int i = 0; i < DrawBoardListener.getInstance().nowPPT.allPage.size() - 1; i++) {
            DrawBoardListener.getInstance().nowPage = DrawBoardListener.getInstance().nowPPT.allPage.get(i);
            PageListPanel.flushPageList();
        }
        DrawBoardListener.getInstance().nowPage = DrawBoardListener.getInstance().nowPPT.allPage.get(0);



    }


    public static void main(String[] args) {
        MyJFrame.getInstance().init();
    }
}
