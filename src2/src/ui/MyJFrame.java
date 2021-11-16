package ui;


import listener.PageListPanelListener;

import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JFrame {
    private static MyJFrame mainWin = new MyJFrame("工作页面");

    public static MyJFrame getInstance() {
        return mainWin;
    }

    private MyJFrame(String name) {
        super(name);
    }

    private void init() {

        JPanel down = new JPanel();

        //我电脑1920x1080
        down.setPreferredSize(new Dimension(768, 432));
//        down.setLayout(new GridBagLayout());
        down.add(DragDrawPanel.getInstance());

        //创建一个水平分隔面板
        JSplitPane drawSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(PageListPanelListener.getInstance().pageJList), down);
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
    }

    public static void main(String[] args) {
        MyJFrame.getInstance().init();
    }
}
