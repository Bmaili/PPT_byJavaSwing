package ui;

import graph.MyShape;
import saveobject.PPT;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class PlayJFrame extends JFrame {
    private BufferedImage image = null;
    private static PlayJFrame playJFrame = null;
    private PPT playPPT;
    private int indexPPT;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension dimension = kit.getScreenSize();


    public PlayJFrame(PPT ppt, int index) {
        super("播放");
        playPPT = ppt;
        indexPPT = index;
        playJFrame = this;
//        Toolkit kit = Toolkit.getDefaultToolkit();
//        Dimension dimension = kit.getScreenSize();


        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, dimension.width, dimension.height);
        setUndecorated(true);
        setVisible(true);
        draw();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    playJFrame.dispose();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    --indexPPT;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    ++indexPPT;
                }
                draw();

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                boolean popupTrigger = e.isPopupTrigger();
                if (popupTrigger) {
                    --indexPPT;
                } else {
                    ++indexPPT;
                }
                draw();
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw();
    }


    private void draw() {

//        Toolkit kit = Toolkit.getDefaultToolkit();
//        Dimension dimension = kit.getScreenSize();

        if (!playPPT.allPage.isEmpty()) {
            if (indexPPT < 0)
                indexPPT = 0;
            if (indexPPT > playPPT.allPage.size() - 1)
                indexPPT = playPPT.allPage.size() - 1;
            image = playPPT.allPage.get(indexPPT).image;
            if (this.image != null)
                this.getGraphics().drawImage(this.image, 0, 0, dimension.width, dimension.height, null);
        }

    }

    private void draw2() {
//        super.repaint();
//        Toolkit kit = Toolkit.getDefaultToolkit();
//        Dimension dimension = kit.getScreenSize();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, dimension.width, dimension.height);
//        setUndecorated(true);
        setVisible(true);

        if (!playPPT.allPage.isEmpty()) {
            if (indexPPT < 0)
                indexPPT = 0;
            if (indexPPT > playPPT.allPage.size() - 1)
                indexPPT = playPPT.allPage.size() - 1;
            super.paint(playJFrame.getGraphics());

////         如果读取了图片，则先把图片画上
//            if (drawBoardListener.nowPage.insertImage != null) {
//                p.drawImage(drawBoardListener.nowPage.insertImage, 0, 0, null);
//            }
//        DrawBoardListener el = DrawBoardListener.getInstance();
            // 遍历绘图历史，绘制该图形
            for (MyShape item : playPPT.allPage.get(indexPPT).history) {
                item.draw(playJFrame.getGraphics());
            }

        }
    }


}

