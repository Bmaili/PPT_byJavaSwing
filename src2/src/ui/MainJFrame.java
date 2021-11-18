package ui;


import listener.DrawBoardListener;

import javax.swing.*;
import java.awt.*;

/**
 * 项目启动入口，主界面UI，负责各部件的布局与启动
 *
 * @Date 12:25 2021/11/18
 */
public class MainJFrame extends JFrame {
    private static MainJFrame mainWin = new MainJFrame("SimplePPT");

    public static MainJFrame getInstance() {
        return mainWin;
    }

    private MainJFrame(String name) {
        super(name);
    }

    private void init() {
        //初始化顶部菜单栏
        this.setJMenuBar(TopMenuBar.getInstance());

        //设置一个存放画板jpanel的容器
        JPanel down = new JPanel();
        down.setPreferredSize(new Dimension(768, 432));
        down.add(DrawBoardListener.getInstance());

        //创建一个水平分隔面板
        JSplitPane drawSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(PageListPanel.pageJList), down);
        //设置支持连续布局
        drawSplit.setContinuousLayout(true);

        Box drawBox = Box.createHorizontalBox();
        drawBox.add(drawSplit);

        //mainBox 放置左侧栏、画板、顶部栏
        Box MainBox = Box.createVerticalBox();
        MainBox.add(ModuleEditBar.getInstance());
        MainBox.add(drawBox);

        mainWin.add(MainBox);

        // 全屏显示
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        mainWin.setExtendedState(Frame.MAXIMIZED_BOTH);
        mainWin.setBounds(0, 0, dimension.width, dimension.height);
        // 定义关闭程序
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置可见
        mainWin.setVisible(true);
    }

    public static void main(String[] args) {
        MainJFrame.getInstance().init();
    }
}
