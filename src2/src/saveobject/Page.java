package saveobject;

import graph.MyShape;
import listener.DrawBoardListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

public class Page implements Serializable ,Cloneable{
    private static final long serialVersionUID = 1111013L;
    // 所有画过的图
    public LinkedList<MyShape> history;
    // 保存按鼠标时的历史状态（用于笔和橡皮的撤销）
    public LinkedList<MyShape> previous;
    // 保存所有可以移动的对象
    public LinkedList<MyShape> moveShape;

    //transient修饰的变量不会被序列化
    public transient BufferedImage image;

//    byte[]

    public transient BufferedImage insertImage;

    private String insertImagePath = "";

    public Page() {
        this.history = new LinkedList<>();
        this.moveShape = new LinkedList<>();
        this.previous = new LinkedList<>();
        this.image = DrawBoardListener.getInstance().defaultImage;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Page newPage = (Page)super.clone();
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);

            out.writeObject(history);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            newPage.history =  (LinkedList<MyShape>)in.readObject();

            out.writeObject(previous);
              byteIn = new ByteArrayInputStream(byteOut.toByteArray());
              in = new ObjectInputStream(byteIn);
            newPage.previous =  (LinkedList<MyShape>)in.readObject();

            out.writeObject(moveShape);
              byteIn = new ByteArrayInputStream(byteOut.toByteArray());
              in = new ObjectInputStream(byteIn);
            newPage.moveShape =  (LinkedList<MyShape>)in.readObject();

            newPage.image = this.image;
        }
        catch (Exception e){
            System.out.println("深拷贝出错");
        }




//        newPage.history = (LinkedList<MyShape>) history.clone();
//        newPage.previous = (LinkedList<MyShape>) previous.clone();
//        newPage.moveShape= (LinkedList<MyShape>) moveShape.clone();
        return newPage ;

    }

    @Override
    public String toString() {
        return "nowPage{" +
                "history=" + history +
                ", previous=" + previous +
                ", moveShape=" + moveShape +
                ", insertImagePath=" + insertImagePath +
                '}';
    }


//    public Page(Page page) {
//        this.history = page.history;
//        this.stack = page.stack;
//        this.previous = page.previous;
//        this.moveShape = page.moveShape;
//        this.imagePath = page.imagePath;
//        this.image = page.image;
//    }


    //    序列化
    public void saveObject() {
        try (//创建一个ObjectOutputStream输出流
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.txt"))) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反序列化
    public void outObject() {
        try (//创建一个ObjectInputStream输入流
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.txt"))) {
            Page readPage = (Page) ois.readObject();
            DrawBoardListener db = DrawBoardListener.getInstance();
            db.history = readPage.history;
            db.moveShape = readPage.moveShape;
            db.previous = readPage.previous;

            //取得画板对象重绘
            DrawBoardListener.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 此页保存为图片
    public void savePanelAsImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("pptPage.png"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Dimension imageSize = DrawBoardListener.getInstance().getSize();
            BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
//            DrawBoardListener.getInstance().nowPage.image = image;//这里用于左侧列表
            Graphics2D graphics = image.createGraphics();
            DrawBoardListener.getInstance().paint(graphics);
            graphics.dispose();
            try {
                ImageIO.write(image, "png", file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // 添加背景图片
    public void loadImageToPanel() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        System.out.println(result);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        insertImagePath = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println("文件绝对路径：" + insertImagePath);
        try {
            // 读取该张图片
            this.insertImage = ImageIO.read(new File(insertImagePath));

            //取得画板对象重绘
            DrawBoardListener.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    // 删除背景图片
    public void clearInsertImage() {
        this.insertImagePath = "";
        this.insertImage = null;

        //取得画板对象重绘
        DrawBoardListener.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());
    }


}
