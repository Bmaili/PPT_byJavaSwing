package ui;

import listener.DrawBoardListener;
import saveobject.PPT;
import saveobject.Page;

import javax.swing.*;
import java.awt.*;

/**
 * 顶部菜单栏类，定义了 菜单选项的布局排版以及各菜单项的事件绑定
 *
 * @Date 12:28 2021/11/18
 */
public class TopMenuBar extends JMenuBar {

    //饿汉模式
    private static TopMenuBar myJMenuBar = new TopMenuBar();

    public static TopMenuBar getInstance() {
        return myJMenuBar;
    }

    private DrawBoardListener drawBoardListener = DrawBoardListener.getInstance();

    //创建菜单
    private JMenu fileJMenu = new JMenu("文件");
    private JMenu pageJMenu = new JMenu("页面");
    private JMenu drawJMenu = new JMenu("画板");
    private JMenu playJMenu = new JMenu("放映");
    private JMenu helpJMenu = new JMenu("帮助");

    //创建新建菜单项
    private JMenuItem clearFileItem = new JMenuItem("清空文件");
    private JMenuItem saveFileItem = new JMenuItem("保存文件");
    private JMenuItem openFileItem = new JMenuItem("打开文件");
    private JMenuItem newFileItem = new JMenuItem("新建文件");

    private JMenuItem deletePageItem = new JMenuItem("删除当前页");
    private JMenuItem copyPageItem = new JMenuItem("复制当前页");
    private JMenuItem insertPageItem = new JMenuItem("插入空白页");

    private JMenuItem insertPicItem = new JMenuItem("插入背景图");
    private JMenuItem deletePicItem = new JMenuItem("删除背景图");
    private JMenuItem savePicItem = new JMenuItem("此页存图");
    private JMenuItem revokeItem = new JMenuItem("撤销");
    private JMenuItem clearDrawItem = new JMenuItem("清空");
    private JMenuItem xuliehua = new JMenuItem("序列化");
    private JMenuItem fanxulie = new JMenuItem("反序列");

    private JMenuItem playNowPageItem = new JMenuItem("从当前页播放");
    private JMenuItem playFirstPageItem = new JMenuItem("从第一页播放");

    private JMenuItem operateItem = new JMenuItem("操作说明");
    private JMenuItem aboutItem = new JMenuItem("关于");


    private TopMenuBar() {
        setPreferredSize(new Dimension(0, 40));
        drawUI();
        bindEvents();
    }

    private void drawUI() {

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
        drawJMenu.add(xuliehua);
        drawJMenu.add(fanxulie);

        playJMenu.add(playNowPageItem);
        playJMenu.add(playFirstPageItem);

        helpJMenu.add(operateItem);
        helpJMenu.add(aboutItem);

        this.add(fileJMenu);
        this.add(pageJMenu);
        this.add(drawJMenu);
        this.add(playJMenu);
        this.add(helpJMenu);
    }

    public void bindEvents() {

        newFileItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MainJFrame.getInstance(), "是否保存当前文件", "新建文件确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result != JOptionPane.CLOSED_OPTION) {
                if (result == JOptionPane.YES_OPTION) {
                    PPT ppt = drawBoardListener.nowPPT;
                    ppt.saveFile();
                }
                PPT ppt = new PPT();
                Page page = new Page();
                ppt.allPage.add(page);
                PPT.resetDrawBoard(ppt, 0);
            }
        });
        newFileItem.setAccelerator(KeyStroke.getKeyStroke('N', java.awt.Event.CTRL_MASK));


        saveFileItem.addActionListener(e -> {
            PPT ppt = drawBoardListener.nowPPT;
            ppt.saveFile();
        });
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK));


        openFileItem.addActionListener(e -> {
            PPT ppt = drawBoardListener.nowPPT;
            ppt.openFile();
        });
        openFileItem.setAccelerator(KeyStroke.getKeyStroke('O', java.awt.Event.CTRL_MASK));


        clearFileItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MainJFrame.getInstance(), "此操作不可撤销，是否确认清空文件", "清空文件确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                PPT ppt = new PPT();
                Page page = new Page();
                ppt.allPage.add(page);
                PPT.resetDrawBoard(ppt, 0);
            }
        });


        deletePageItem.addActionListener(e -> {
            PPT ppt = drawBoardListener.nowPPT;
            int index = PageListPanel.selectIndex;
            if (index > 0 || ppt.allPage.size() > 1) {
                ppt.allPage.remove(index);
                if (index == 0)
                    index = 1;
                PPT.resetDrawBoard(ppt, index - 1);
            }
        });


        copyPageItem.addActionListener(e -> {
            PPT ppt = drawBoardListener.nowPPT;
            int index = PageListPanel.selectIndex;
            try {
                ppt.allPage.add(index + 1, (Page) ppt.allPage.get(index).clone());
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
            PPT.resetDrawBoard(ppt, index);
        });
        copyPageItem.setAccelerator(KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK));


        insertPageItem.addActionListener(e -> {
            PPT ppt = drawBoardListener.nowPPT;
            int index = PageListPanel.selectIndex;
            ppt.allPage.add(index + 1, new Page());
            PPT.resetDrawBoard(ppt, index);
        });
        insertPageItem.setAccelerator(KeyStroke.getKeyStroke('I', java.awt.Event.CTRL_MASK));


        insertPicItem.addActionListener(e -> {
            Page page = drawBoardListener.nowPage;
            page.loadImageToPanel();
        });
        insertPicItem.setToolTipText("为此页PPT插入一张背景图片（支持png,jpg）");


        deletePicItem.addActionListener(e -> {
            Page page = drawBoardListener.nowPage;
            page.clearInsertImage();
        });
        deletePageItem.setToolTipText("删除此页的背景图片");


        savePicItem.addActionListener(e -> {
            Page page = drawBoardListener.nowPage;
            page.savePanelAsImage();
        });
        savePicItem.setToolTipText("将此页以图片(png,jpg)的形式保存");


        revokeItem.addActionListener(e -> {
            Page page = drawBoardListener.nowPage;
            if (!page.history.isEmpty() && page.history.peekLast().equals(page.moveShape.peekLast()))
                page.moveShape.pollLast();
            drawBoardListener.revert(false);
        });
        revokeItem.setToolTipText("撤销此页已添加的图案");
        revokeItem.setAccelerator(KeyStroke.getKeyStroke('Z', java.awt.Event.CTRL_MASK));


        clearDrawItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MainJFrame.getInstance(), "此操作不可撤销，是否确认清空画板", "清空画板确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                Page page = drawBoardListener.nowPage;
                page.previous.clear();
                page.history.clear();
                page.moveShape.clear();
                drawBoardListener.revert(false);
            }
        });
        clearDrawItem.setToolTipText("将此页清空，该操作不可撤销");


        xuliehua.addActionListener(e -> {
            Page page = drawBoardListener.nowPage;
            page.saveObject();
        });
        xuliehua.setToolTipText("将此页数据序列化(Page.txt)，存在SimplePPT目录下");


        fanxulie.addActionListener(e -> {
            Page page = drawBoardListener.nowPage;
            page.outObject();
        });
        fanxulie.setToolTipText("将上次序列化内容反序列化，输出在此页上");


        playNowPageItem.addActionListener(e -> {
            PageListPanel.flushPageImage();
            new PlayJFrame(drawBoardListener.nowPPT, 0);
        });


        playFirstPageItem.addActionListener(e -> {
            PageListPanel.flushPageImage();
            new PlayJFrame(drawBoardListener.nowPPT, 0);
        });
        playFirstPageItem.setAccelerator(KeyStroke.getKeyStroke('P', java.awt.Event.CTRL_MASK));

    }
}
