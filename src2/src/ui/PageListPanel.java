package ui;

import listener.DrawBoardListener;
import listener.TopMenuListener;
import saveobject.PPT;
import saveobject.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//    左侧列表
public class PageListPanel extends JPanel implements ListCellRenderer {

    public static PageListPanel pageListPanel;
    public static JList<Page> pageJList;

    public static DefaultListModel<Page> pageModel = new DefaultListModel<>();

    static {
        PPT ppt = DrawBoardListener.getInstance().nowPPT;

        for (Page p : ppt.allPage) {
            pageModel.addElement(p);
        }


//        pageModel.addElement(nowPPT.allPage.get(2));

        //根据DefaultListModel创建一个JList对象
        pageJList = new JList<>(pageModel);
        //设置最大可视高度
        pageJList.setVisibleRowCount(7);
        //设置只能单选
        pageJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pageJList.setSelectedIndex(0);


        pageListPanel =  new PageListPanel();
        pageJList.setCellRenderer(pageListPanel);


    }

    public static int selectIndex = -1;

    public int indexID;
    private Color background;
    private Color foreground;

    public PageListPanel() {
        System.out.println("实例化；pagepanel");
//        PageListPanelListener instance = PageListPanelListener.getInstance();
//        this.addMouseListener(instance);
    }


    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        indexID = index;
        Page page = (Page) value;
//        background = isSelected ? list.getSelectionBackground() : list.getBackground();
        background = isSelected ? list.getSelectionBackground() : Color.LIGHT_GRAY;
        foreground = isSelected ? list.getSelectionForeground() : list.getForeground();
//        if (isSelected && selectIndex != index) {

        if (isSelected && selectIndex != index) {

            if (selectIndex != -1) {
                flushPageList();
            }

//            DrawBoardListener.getInstance().windowChange = false;

            selectIndex = index;
//            System.out.println(index + "is true");
            DrawBoardListener.getInstance().nowPage = DrawBoardListener.getInstance().nowPPT.allPage.get(index);
            DrawBoardListener.getInstance().setDrawBoardListener();


            MyJFrame.getInstance().repaint();

//            //取得画板对象重绘
//            DrawBoardListener.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());
//            DrawBoardListener.getInstance().nowPPT.allPage.set(index, nowPage);


        }

        return this;
    }


    @Override
    protected void paintComponent(Graphics g) {
        //填充背景矩形
        g.setColor(background);
//        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.drawRect(0, 0, getWidth(), getHeight());

        g.setColor(foreground);


        BufferedImage image = DrawBoardListener.getInstance().nowPPT.allPage.get(indexID).image;
        if (image != null) {
            g.drawImage(image.getScaledInstance(154, 86, 0), 15, 15, null);
        }
//        Graphics2D graphics = image.createGraphics();
//        DrawBoardListener.getInstance().paint(graphics);
//        graphics.dispose();
//        //绘制好友昵称
//        g.setFont(new Font("SansSerif", Font.BOLD, 18));
//        g.drawString(name, getWidth() / 2 - name.length() * 10, 40);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(180, 120);
    }

    public static void changeList() {
        Page page = new Page();
        DrawBoardListener.getInstance().nowPPT.allPage.add(page);
        DrawBoardListener.getInstance().nowPage = page;
        DrawBoardListener.getInstance().setDrawBoardListener();
        pageJList.setSelectedIndex(0);
        pageModel.addElement(page);
        System.out.println("invoked changelist()");
    }

    public static void flushPageList() {
        DrawBoardListener.getInstance().drawPanel.requestFocus();
        Dimension imageSize = DrawBoardListener.getInstance().getSize();
        BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
        DrawBoardListener.getInstance().nowPage.image = image;

        Graphics2D graphics = image.createGraphics();
        DrawBoardListener.getInstance().paint(graphics);
        graphics.dispose();
    }
}
