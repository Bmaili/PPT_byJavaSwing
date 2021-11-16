package ui;

import listener.TopMenuListener;

import javax.swing.*;
import java.awt.*;

public class MyJMenuBar extends JMenuBar {
    TopMenuListener topMenuListener = TopMenuListener.getInstance();

    private static MyJMenuBar myJMenuBar = new MyJMenuBar();

    private MyJMenuBar() {
        setPreferredSize(new Dimension(0, 40));
        init();
    }

    public static MyJMenuBar getInstance() {
        return myJMenuBar;
    }

    private void init() {
        JMenu fileJMenu = new JMenu("文件");
        JMenu pageJMenu = new JMenu("页面");
        JMenu drawJMenu = new JMenu("画板");
        JMenu playJMenu = new JMenu("放映");
        JMenu helpJMenu = new JMenu("帮助");


        //创建新建菜单项
        JMenuItem clearItem = new JMenuItem("清空文件");
        JMenuItem saveItem = new JMenuItem("保存文件");
        JMenuItem openItem = new JMenuItem("打开文件");

        JMenuItem deletePageItem = new JMenuItem("删除当前页");
        JMenuItem copyPageItem = new JMenuItem("复制当前页");
        JMenuItem insertPageItem = new JMenuItem("插入空白页");


        JMenuItem insertPicItem = new JMenuItem("插入背景图");
        JMenuItem deletePicItem = new JMenuItem("删除背景图");
        JMenuItem savePicItem = new JMenuItem("此页存图");
        savePicItem.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK));
        JMenuItem revokeItem = new JMenuItem("撤销");
        revokeItem.setAccelerator(KeyStroke.getKeyStroke('Z', java.awt.Event.CTRL_MASK));
        JMenuItem clearDrawItem = new JMenuItem("清空");
        JMenuItem deleteShapItem = new JMenuItem("删除选中");

        JMenuItem playNowPageItem = new JMenuItem("从当前页播放");
        JMenuItem playFirstPageItem = new JMenuItem("首页播放");

        JMenuItem operateItem = new JMenuItem("操作说明");
        JMenuItem aboutItem = new JMenuItem("关于");

        fileJMenu.add(clearItem);
        fileJMenu.add(saveItem);
        fileJMenu.add(openItem);

        pageJMenu.add(deletePageItem);
        pageJMenu.add(copyPageItem);
        pageJMenu.add(insertPageItem);

        drawJMenu.add(insertPicItem);
        drawJMenu.add(deletePicItem);
        drawJMenu.add(savePicItem);
        drawJMenu.add(revokeItem);
        drawJMenu.add(clearDrawItem);
        drawJMenu.add(deleteShapItem);

        playJMenu.add(playNowPageItem);
        playJMenu.add(playFirstPageItem);

        helpJMenu.add(operateItem);
        helpJMenu.add(aboutItem);

        JMenuItem xuliehua = new JMenuItem("序列化");
        JMenuItem fanxu = new JMenuItem("反序列");
        xuliehua.addActionListener(topMenuListener);
        fanxu.addActionListener(topMenuListener);

        helpJMenu.add(xuliehua);
        helpJMenu.add(fanxu);

        // 添加到右边
        this.add(new Component() {
            @Override
            public void setPreferredSize(Dimension preferredSize) {
                super.setPreferredSize(new Dimension(100, 30));
            }
        });
        this.add(fileJMenu);
        this.add(pageJMenu);
        this.add(drawJMenu);
        this.add(playJMenu);
        this.add(helpJMenu);

        saveItem.addActionListener(topMenuListener);
        openItem.addActionListener(topMenuListener);
        clearItem.addActionListener(topMenuListener);
        deletePageItem.addActionListener(topMenuListener);
        copyPageItem.addActionListener(topMenuListener);
        insertPageItem.addActionListener(topMenuListener);
        insertPicItem.addActionListener(topMenuListener);
        deletePicItem.addActionListener(topMenuListener);
        savePicItem.addActionListener(topMenuListener);
//        .addActionListener(topMenuListener);
//        .addActionListener(topMenuListener);
//        .addActionListener(topMenuListener);

//        for (int i = 0; i < helpJMenu.getSubElements().length; i++) {
//            JMenuItem subElement1 = helpJMenu.getItem(i);
//
//            subElement1.addActionListener(topMenuListener);
//        }


    }
}
