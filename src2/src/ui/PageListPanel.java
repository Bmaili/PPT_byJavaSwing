package ui;

import listener.DrawBoardListener;
import saveobject.PPT;
import saveobject.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 左侧的列表框，定义了列表框的事件
 *
 * @date 16:13 2021/11/18
 */
public class PageListPanel extends JPanel implements ListCellRenderer {

    public static JList<Page> pageJList;   //PPT的每一页（page）列表

    //列表的数据Model，用于列表数据的增删改
    public static DefaultListModel<Page> pageModel = new DefaultListModel<>();

    static {
        PPT ppt = DrawBoardListener.getInstance().nowPPT;

        //往model里注入ppt数据
        for (Page p : ppt.allPage) {
            pageModel.addElement(p);
        }

        //根据DefaultListModel创建一个JList对象
        pageJList = new JList<>(pageModel);
        //设置最大可视高度
        pageJList.setVisibleRowCount(7);
        //设置只能单选
        pageJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //默认选择第一个
        pageJList.setSelectedIndex(0);
        //设置ppt列表的委托（即当前PageListPanel的一个对象），用于响应事件
        pageJList.setCellRenderer(new PageListPanel());
    }

    public static int selectIndex = -1;//当前的page页码

    public int indexID;//每一页的页码ID
    private Color background;//背景色
    private Color foreground;//前景色

    public PageListPanel() {
    }


    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        indexID = index;
        background = isSelected ? list.getSelectionBackground() : Color.LIGHT_GRAY;
        foreground = isSelected ? list.getSelectionForeground() : list.getForeground();

        //当且仅当此页被选中，而且选择页发生变化时，才执行，这个判断很重要，不信你删删看
        if (isSelected && selectIndex != index) {

            if (selectIndex != -1) {
                flushPageImage();
            }

            selectIndex = index;
            DrawBoardListener.getInstance().nowPage = DrawBoardListener.getInstance().nowPPT.allPage.get(index);
            DrawBoardListener.getInstance().setDrawBoardListener();

            //重绘主窗口，非常细节、重要、不起眼
            MainJFrame.getInstance().repaint();
        }
        return this;
    }


    @Override
    protected void paintComponent(Graphics g) {
        //填充每一页背景矩形
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.drawRect(0, 0, getWidth(), getHeight());
        g.setColor(foreground);

        //将当前page里的image，缩小展示在左侧列表上
        BufferedImage image = DrawBoardListener.getInstance().nowPPT.allPage.get(indexID).image;
        if (image != null) {
            g.drawImage(image.getScaledInstance(154, 86, 0), 15, 15, null);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(180, 120);
    }


    /**
     * 刷新当前Page里的Image数据
     *
     * @param
     * @return
     * @date 16:28 2021/11/18
     */
    public static void flushPageImage() {
        DrawBoardListener.getInstance().drawPanel.requestFocus();//将焦点还给画板

        // 将当前主画板的样子“截图”,存放于当前Page的image里
        Dimension imageSize = DrawBoardListener.getInstance().getSize();
        BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
        DrawBoardListener.getInstance().nowPage.image = image;

        Graphics2D graphics = image.createGraphics();
        DrawBoardListener.getInstance().paint(graphics);
        graphics.dispose();
    }
}
