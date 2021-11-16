package listener;

import saveobject.PPT;
import saveobject.Page;
import ui.DragDrawPanel;
import ui.PageListPanel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PageListPanelListener extends MouseInputAdapter {

    private static PageListPanelListener pageListPanelListener = new PageListPanelListener();

    public static PageListPanelListener getInstance() {
        return pageListPanelListener;
    }

    public JList<Page> pageJList;

    public DefaultListModel<Page> pageModel = new DefaultListModel<>();


    public void changeList() {


        Page page = new Page();
        DragDrawPanel.getInstance().ppt.allPage.add(page);
        DragDrawPanel.getInstance().nowPage = page;
        DrawBoardListener.getInstance().setDrawBoardListener();
        pageModel.addElement(page);
        System.out.println("invoked changelist()");


    }


    @Override
    public void mouseReleased(MouseEvent e) {
        boolean flag = e.isPopupTrigger();
        if (flag)
            System.out.println("在pagelistpanel点击了右键");
        else
            System.out.println("在pagelistpanel点击了左肩");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        System.out.println("click");
    }

    private PageListPanelListener() {

        //定义一个JList对象
        //定义一个DefaultListModel对象
//        DefaultListModel<Page> pageModel = new DefaultListModel<>();
        PPT ppt = DragDrawPanel.getInstance().ppt;
        pageModel.addElement(ppt.allPage.get(0));
        pageModel.addElement(ppt.allPage.get(1));
        pageModel.addElement(ppt.allPage.get(2));

        //根据DefaultListModel创建一个JList对象
        pageJList = new JList<>(pageModel);
        //设置最大可视高度
        pageJList.setVisibleRowCount(7);
        //设置只能单选
        pageJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pageJList.setSelectedIndex(0);

        pageJList.setCellRenderer(new PageListPanel());
    }
}
