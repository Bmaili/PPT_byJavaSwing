package ui;

import listener.DrawBoardListener;
import saveobject.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//    左侧列表
class ImageCellRenderer extends JPanel implements ListCellRenderer {


    public int indexID;
    private Color background;
    private Color foreground;
    public static int selectIndex = -1;


    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        indexID = index;
        Page page = (Page) value;
//        background = isSelected ? list.getSelectionBackground() : list.getBackground();
        background = isSelected ? list.getSelectionBackground() : Color.LIGHT_GRAY;
        foreground = isSelected ? list.getSelectionForeground() : list.getForeground();
        if (isSelected && selectIndex != index) {

            if (selectIndex != -1) {
                Dimension imageSize = DragDrawPanel.getInstance().getSize();
                BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
                DragDrawPanel.getInstance().nowPage.image = image;

                //
                Graphics2D graphics = image.createGraphics();
                DragDrawPanel.getInstance().paint(graphics);
                graphics.dispose();
            }

            selectIndex = index;
            System.out.println(index + "is true");
            DragDrawPanel.getInstance().nowPage = DragDrawPanel.getInstance().ppt.allPage.get(index);
            DrawBoardListener.getInstance().setDrawBoardListener();
//            //取得画板对象重绘
//            DragDrawPanel.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());
//            DragDrawPanel.getInstance().ppt.allPage.set(index, nowPage);


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


        BufferedImage image = DragDrawPanel.getInstance().ppt.allPage.get(indexID).image;
        if (image != null) {
            System.out.println("绘制预览图");
            g.drawImage(image.getScaledInstance(154, 86, 0), 15, 15, null);
        } else {
            System.out.println("预览图为null");
        }

//        //绘制好友昵称
//        g.setFont(new Font("SansSerif", Font.BOLD, 18));
//        g.drawString(name, getWidth() / 2 - name.length() * 10, 40);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(180, 120);
    }
}
