package ui;

import listener.DrawBoardListener;
import listener.TopMenuListener;
import saveobject.PPT;
import saveobject.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        JMenuItem clearFileItem = new JMenuItem("清空文件");
        JMenuItem saveFileItem = new JMenuItem("保存文件");
        JMenuItem openFileItem = new JMenuItem("打开文件");
        JMenuItem newFileItem = new JMenuItem("新建文件");

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
//        JMenuItem deleteShapItem = new JMenuItem("删除选中");

        JMenuItem playNowPageItem = new JMenuItem("从当前页播放");
        JMenuItem playFirstPageItem = new JMenuItem("播放");

        JMenuItem operateItem = new JMenuItem("操作说明");
        JMenuItem aboutItem = new JMenuItem("关于");

        fileJMenu.add(newFileItem);
        fileJMenu.add(saveFileItem);
        fileJMenu.add(openFileItem);
        fileJMenu.add(clearFileItem);

        pageJMenu.add(deletePageItem);
        pageJMenu.add(copyPageItem);
        pageJMenu.add(insertPageItem);

        drawJMenu.add(insertPicItem);
        drawJMenu.add(deletePicItem);
        drawJMenu.add(savePicItem);
        drawJMenu.add(revokeItem);
        drawJMenu.add(clearDrawItem);
//        drawJMenu.add(deleteShapItem);

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

//        // 添加到右边
//        this.add(new Component() {
//            @Override
//            public void setPreferredSize(Dimension preferredSize) {
//                super.setPreferredSize(new Dimension(100, 30));
//            }
//        });
        this.add(fileJMenu);
        this.add(pageJMenu);
        this.add(drawJMenu);
        this.add(playJMenu);
        this.add(helpJMenu);

        newFileItem.addActionListener(topMenuListener);
        newFileItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MyJFrame.getInstance(), "是否保存当前文件", "新建文件确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result != JOptionPane.CLOSED_OPTION) {
                if (result == JOptionPane.YES_OPTION) {
                    PPT ppt = DrawBoardListener.getInstance().nowPPT;
                    ppt.saveFile();
                }
                PPT ppt = new PPT();
                Page page = new Page();
                ppt.allPage.add(page);
                PPT.reset(ppt, 0);
            }
        });
        saveFileItem.addActionListener(topMenuListener);
        openFileItem.addActionListener(topMenuListener);
        clearFileItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MyJFrame.getInstance(), "此操作不可撤销，是否确认清空文件", "清空文件确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                PPT ppt = new PPT();
                Page page = new Page();
                ppt.allPage.add(page);
                PPT.reset(ppt, 0);
            }
        });
        deletePageItem.addActionListener(topMenuListener);
        copyPageItem.addActionListener(topMenuListener);
        insertPageItem.addActionListener(topMenuListener);
        insertPicItem.addActionListener(topMenuListener);
        deletePicItem.addActionListener(topMenuListener);
        savePicItem.addActionListener(topMenuListener);
        revokeItem.addActionListener(topMenuListener);
//        clearDrawItem.addActionListener(topMenuListener);
        clearDrawItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MyJFrame.getInstance(), "此操作不可撤销，是否确认清空画板", "清空画板确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                Page page = DrawBoardListener.getInstance().nowPage;
                page.previous.clear();
                page.history.clear();
                page.moveShape.clear();
                DrawBoardListener.getInstance().revert(false);
            }
        });
        playNowPageItem.addActionListener(topMenuListener);
        playFirstPageItem.addActionListener(topMenuListener);
//        deleteShapItem.addActionListener(topMenuListener);
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
