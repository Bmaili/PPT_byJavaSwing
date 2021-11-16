package listener;

import saveobject.PPT;
import saveobject.Page;
import ui.PageListPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TopMenuListener implements ActionListener, ChangeListener, ItemListener {
    private static TopMenuListener topMenuListener = new TopMenuListener();
    private DrawBoardListener drawBoard = DrawBoardListener.getInstance();

    private TopMenuListener() {
    }

    public static TopMenuListener getInstance() {
        return topMenuListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String content = "";
        if (e.getSource() instanceof JButton) {
            JButton jButton = (JButton) e.getSource();
            content = jButton.getText();
        } else {
            JMenuItem source = (JMenuItem) e.getSource();
            content = source.getText();

        }
        System.out.println("点击按钮： " + content);
        if ("序列化".equals(content)) {
//            Page page = DrawBoardListener.getInstance().nowPage;
//            System.out.println(page);
//            page.saveObject();
            PPT ppt = DrawBoardListener.getInstance().nowPPT;
            System.out.println(ppt);
            ppt.saveObject("nowPPT.txt");
        }
        if ("反序列".equals(content)) {
//            Page page = DrawBoardListener.getInstance().nowPage;
//            page.outObject();
            PPT ppt = DrawBoardListener.getInstance().nowPPT;
            ppt.outObject("nowPPT.txt");
        }
        if ("保存文件".equals(content)) {
            PPT ppt = DrawBoardListener.getInstance().nowPPT;
            System.out.println(ppt);
            ppt.saveFile();
        }
        if ("打开文件".equals(content)) {
            PPT ppt = DrawBoardListener.getInstance().nowPPT;
            ppt.openFile();
        }
        if ("清空文件".equals(content)) {
            PPT ppt = new PPT();
            Page page = new Page();
            ppt.allPage.add(page);

            PPT.reset(ppt, 0);
        }
        if ("删除当前页".equals(content)) {
            PPT ppt = DrawBoardListener.getInstance().nowPPT;
            int index = PageListPanel.selectIndex;
            if (index > 0 || ppt.allPage.size() > 1) {
                ppt.allPage.remove(index);
                PPT.reset(ppt, index - 1);
            }
        }
        if ("复制当前页".equals(content)) {
//            PPT ppt = DrawBoardListener.getInstance().nowPPT;
//            int index = PageListPanel.selectIndex;
////            ppt.allPage.add(index + 1, new Page(ppt.allPage.get(index)));
////            ppt.allPage.add(index + 1, ppt.allPage.get(index).clone());
//            PPT.reset(ppt, index + 1);
        }
        if ("插入空白页".equals(content)) {
            PPT ppt = DrawBoardListener.getInstance().nowPPT;
            int index = PageListPanel.selectIndex;
            ppt.allPage.add(index + 1, new Page());
//            PPT.reset(ppt, index + 1);
            PPT.reset(ppt, index);

        }
        if ("插入背景图".equals(content)) {
            Page page = DrawBoardListener.getInstance().nowPage;
            page.loadImageToPanel();
        }
        if ("删除背景图".equals(content)) {
            Page page = DrawBoardListener.getInstance().nowPage;
            page.clearInsertImage();
        }

        if ("此页存图".equals(content)) {
            Page page = DrawBoardListener.getInstance().nowPage;
            page.savePanelAsImage();
        }
        if ("删除选中".equals(content)) {
            Page page = DrawBoardListener.getInstance().nowPage;
            page.outObject();
        }
        if ("清空".equals(content)) {
            Page page = DrawBoardListener.getInstance().nowPage;
            page.outObject();
        }
//        if ("保存".equals(content)) {
//            PPT nowPPT = DrawBoardListener.getInstance().nowPPT;
//            nowPPT.saveObject();
//        }
//        if ("文件".equals(content)) {
//            PPT nowPPT = DrawBoardListener.getInstance().nowPPT;
//            nowPPT.outObject();
//        }
        if ("新增".equals(content)) {
            PageListPanel.changeList();

        }


    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider jslider = (JSlider) e.getSource();
        drawBoard.penWidth = jslider.getValue();

        System.out.println("调节画笔尺寸： " + jslider.getValue());
        //将焦点还给画板Panel
        drawBoard.drawPanel.requestFocus();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
//        JRadioButton e1 = (JRadioButton) e;
        JRadioButton jRadioButton = (JRadioButton) e.getSource();
        drawBoard.selectModule = jRadioButton.getText();

        System.out.println("选择模式： " + jRadioButton.getText());
        //将焦点还给画板Panel
        drawBoard.drawPanel.requestFocus();
    }
}
