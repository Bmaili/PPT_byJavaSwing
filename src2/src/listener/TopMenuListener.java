package listener;

import saveobject.PPT;
import saveobject.Page;
import ui.DragDrawPanel;

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
        JButton jButton = (JButton) e.getSource();
        String content = jButton.getText();
        System.out.println("点击按钮： " + content);
        if ("序列化".equals(content)) {
            Page page = DragDrawPanel.getInstance().nowPage;
            System.out.println(page);
            page.saveObject();
        }
        if ("反序列".equals(content)) {
            Page page = DragDrawPanel.getInstance().nowPage;
            page.outObject();
        }
        if ("此页存图".equals(content)) {
            Page page = DragDrawPanel.getInstance().nowPage;
            page.savePanelAsImage();
        }
        if ("插入背景".equals(content)) {
            Page page = DragDrawPanel.getInstance().nowPage;

            page.loadImageToPanel();
        }
        if ("删除选中".equals(content)) {
            Page page = DragDrawPanel.getInstance().nowPage;

            page.outObject();
        }
        if ("清空".equals(content)) {
            Page page = DragDrawPanel.getInstance().nowPage;
            page.outObject();
        }
        if ("保存".equals(content)) {
            PPT ppt = DragDrawPanel.getInstance().ppt;
            ppt.saveObject();
        }
        if ("文件".equals(content)) {
            PPT ppt = DragDrawPanel.getInstance().ppt;
            ppt.outObject();
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
