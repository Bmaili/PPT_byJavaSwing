package saveobject;

import graph.MyShape;
import ui.DrawBoard;
import ui.PageListPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

/**
 * page页面类，即PPT的每一页
 *
 * @date 21:44 2021/11/18
 */
public class Page implements Serializable, Cloneable {

    private static final long serialVersionUID = 1111013L;

    //下面仨用于序列化
    // 所有画过的图
    public LinkedList<MyShape> history;
    // 保存按鼠标时的历史状态（用于笔和橡皮的撤销）
    public LinkedList<MyShape> previous;
    // 保存所有可以移动的对象
    public LinkedList<MyShape> moveShape;

    //transient修饰的变量不会被序列化
    //当前页面的图像数据
    public transient BufferedImage image;
    //当前页面插入的背景图
    public transient BufferedImage insertImage;


    public Page() {
        this.history = new LinkedList<>();
        this.moveShape = new LinkedList<>();
        this.previous = new LinkedList<>();
    }


    /**
     * 序列化Page
     *
     * @param
     * @return
     * @date 22:08 2021/11/18
     */
    public void saveObject() {
        try (//创建一个ObjectOutputStream输出流
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Page.txt"))) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化Page
     *
     * @param
     * @return
     * @date 22:08 2021/11/18
     */
    public void outObject() {
        try (//创建一个ObjectInputStream输入流
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Page.txt"))) {
            Page readPage = (Page) ois.readObject();
            Page page = DrawBoard.getInstance().nowPPT.allPage.get(PageListPanel.selectIndex);
            page.history = readPage.history;
            page.moveShape = readPage.moveShape;
            page.previous = readPage.previous;

            //取得画板对象重绘
            DrawBoard.getInstance().paint(DrawBoard.getInstance().drawPanel.getGraphics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将此页保存为图片
     *
     * @param
     * @return
     * @date 22:09 2021/11/18
     */
    public void savePanelAsImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("pptPage.png"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Dimension imageSize = DrawBoard.getInstance().getSize();
            BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            DrawBoard.getInstance().paint(graphics);
            graphics.dispose();
            try {
                ImageIO.write(image, "png", file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 添加背景图片
     *
     * @param
     * @return
     * @date 22:09 2021/11/18
     */
    public void loadImageToPanel() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        System.out.println(result);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String insertImagePath = fileChooser.getSelectedFile().getAbsolutePath();
        try {
            // 读取该张图片
            this.insertImage = ImageIO.read(new File(insertImagePath));

            //取得画板对象重绘
            DrawBoard.getInstance().paint(DrawBoard.getInstance().drawPanel.getGraphics());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 删除背景图片
     *
     * @param
     * @return
     * @date 22:10 2021/11/18
     */
    public void clearInsertImage() {
        this.insertImage = null;

        //取得画板对象重绘
        DrawBoard.getInstance().paint(DrawBoard.getInstance().drawPanel.getGraphics());
    }

    /**
     * 对当前Page对象进行深拷贝,并将新Page加入到下一页
     *
     * @param
     * @return
     * @date 1:03 2021/11/19
     */
    public void deepCopyNowPage() {
        //使用流操作来复制对象
        ObjectOutputStream oos = null;
        Page nowPage = DrawBoard.getInstance().nowPage;
        PPT nowPPT = DrawBoard.getInstance().nowPPT;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        try {
            //先将对象输出到Byte流
            oos = new ObjectOutputStream(byteStream);
            oos.writeObject(nowPage);

            //然后再将这个Byte流里的数据注入到一个新的Page中
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteStream.toByteArray()));
            Page readPage = (Page) ois.readObject();
            readPage.image = nowPPT.allPage.get(PageListPanel.selectIndex).image;

            //将page加入到ppt和pageModel中
            nowPPT.allPage.add(PageListPanel.selectIndex + 1, readPage);

            //刷新
            PageListPanel.pageModel.clear();
            for (Page p : nowPPT.allPage) {
                PageListPanel.pageModel.addElement(p);
            }
            PageListPanel.pageJList.setSelectedIndex(PageListPanel.selectIndex);

            PPT.resetDrawBoard(nowPPT, PageListPanel.selectIndex);

        } catch (Exception Exception) {
            Exception.printStackTrace();
        }
    }
}
