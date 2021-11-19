package ui;

import saveobject.PPT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * 播放幻灯片时的独立的一个JFrame页面
 *
 * @date 16:34 2021/11/18
 */
public class PlayJFrame extends JFrame {
    private static PlayJFrame playJFrame;

    private BufferedImage image;  //播放的图像
    private PPT playPPT;  //播放的ppt
    private int indexPPT; //page定位

    //定义全屏尺寸
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension dimension = kit.getScreenSize();


    /**
     * @param ppt   传入的待播放ppt
     * @param index 从index页开始播放
     * @return
     * @date 16:36 2021/11/18
     */
    public PlayJFrame(PPT ppt, int index) {
        super("播放");
        playPPT = ppt;
        indexPPT = index;
        playJFrame = this;

        //设置全屏展示
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, dimension.width, dimension.height);
        setUndecorated(true);
        setVisible(true);

        //播放并绑定鼠标键盘事件
        draw();
        bindEvents();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw();
    }


    /**
     * 绘画当前页
     *
     * @param
     * @return
     * @date 16:39 2021/11/18
     */
    private void draw() {
        if (!playPPT.allPage.isEmpty()) {
            if (indexPPT < 0)
                indexPPT = 0;
            if (indexPPT > playPPT.allPage.size() - 1)
                indexPPT = playPPT.allPage.size() - 1;
            image = playPPT.allPage.get(indexPPT).image;
            if (this.image != null)
                try {
                    this.getGraphics().drawImage(this.image, 0, 0, dimension.width, dimension.height, null);
                } catch (NullPointerException e) {
                }
        }
    }

    /**
     * 绑定鼠标键盘事件
     *
     * @param
     * @return
     * @date 16:40 2021/11/18
     */
    private void bindEvents() {
        //“Esc”键退出播放，上下键
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

        //左键下一张，右键上一张
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    --indexPPT;
                } else {
                    ++indexPPT;
                }
                draw();
            }
        });
    }
}

