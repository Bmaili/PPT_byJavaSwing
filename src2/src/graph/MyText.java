package graph;

import ui.TopMenu;

import java.awt.*;

public class MyText extends MyShape {
    private String content;
    private Font font;
    private int size;

    public MyText(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        TopMenu topMenu = TopMenu.getInstance();
        this.content = topMenu.textContent.getText();
        this.font = topMenu.fontMap.get(topMenu.fontChooser.getSelectedItem());
        this.size = (Integer) topMenu.sizeChooser.getSelectedItem();
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
        if ((x >= x2 - 6) && (x <= x2 + 100) && (y >= y2 - 30) && (y <= y2 + 8)) {
            return true;
        }
        return false;
    }
}
