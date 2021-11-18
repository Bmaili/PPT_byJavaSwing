package graph;

import ui.ModuleEditBar;

import java.awt.*;

/**
 * 文本图形类
 *
 * @date 21:35 2021/11/18
 */
public class MyText extends MyShape {
    private String content; //文本内容
    private Font font;//文本字体
    private int size; //文本尺寸

    public MyText(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        ModuleEditBar moduleEditBar = ModuleEditBar.getInstance();
        this.content = moduleEditBar.textContent.getText();
        this.font = moduleEditBar.fontMap.get(moduleEditBar.fontChooser.getSelectedItem());
        this.size = (Integer) moduleEditBar.sizeChooser.getSelectedItem();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D p = (Graphics2D) g;
        p.setColor(this.mainColor);
        p.setFont(this.font.deriveFont((float) size));
        p.drawString(this.content, x2, y2);
    }

    @Override
    public boolean pointInShape(int x, int y) {
        //文本的落点判断方式有点不同
        if ((x >= x2 - 6) && (x <= x2 + 100) && (y >= y2 - 30) && (y <= y2 + 8)) {
            return true;
        }
        return false;
    }


    @Override
    public void setChangeShapeXY(int x1, int y1, int x2, int y2) {
        //文本不支持拉伸
    }
}
