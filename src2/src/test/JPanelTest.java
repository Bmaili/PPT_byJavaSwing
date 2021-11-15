package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

class JF extends JFrame{
    JF()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, dimension.width, dimension.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setUndecorated(true);
        setUndecorated(false);
        setVisible(true);
    }
}
public class JPanelTest extends JPanel {
    private BufferedImage image = null;

    public static void main(String[] args) {
        JF jf = new JF();
        JPanelTest jPanelTest = new JPanelTest();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
//        jf.setSize(900, 675);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.setLayout(new BorderLayout());
        jf.add(jPanelTest, BorderLayout.CENTER);
//        jf.setVisible(true);
        jPanelTest.shuchu();
        jPanelTest.loadImageToPanel();
        if (jPanelTest.image != null) {
            jPanelTest.getGraphics().drawImage(jPanelTest.image, 0, 0,dimension.width, dimension.height, null);
        }
    }

    public void shuchu() {
        System.out.println((Graphics2D)(this).getGraphics());
    }

    // 从图片打开
    public void loadImageToPanel() {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setFileFilter(new FileNameExtensionFilter("images", "*.png", "*.jpg"));
//        int result = fileChooser.showOpenDialog(null);
//        if (result != JFileChooser.APPROVE_OPTION) {
//            return;
//        }
//        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
//        System.out.println(filePath);
        String filePath = "D:\\Desktop\\saved.png";
        try {
            // 读取该张图片
            image = ImageIO.read(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
