package saveobject;

import graph.MyShape;
import listener.DrawBoardListener;
import ui.DragDrawPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Page implements Serializable {
    private static final long serialVersionUID = 1111013L;
    // 所有画过的图
    public LinkedList<MyShape> history;
    // 保存实时按键的栈
    public Deque<Integer> stack;
    // 保存按鼠标时的历史状态（用于笔和橡皮的撤销）
    public Deque<MyShape> previous;
    // 保存所有可以移动的对象
    public LinkedList<MyShape> moveShape;

    //transient修饰的变量不会被序列化
    public transient BufferedImage image;

    private String imagePath = "";

    public Page() {
        //将对象序列化到文件s
//        DrawBoardListener db = DrawBoardListener.getInstance();
//        this.history = db.history;
//        this.moveShape = db.moveShape;
//        this.stack = db.stack;
//        this.previous = db.previous;

        this.history = new LinkedList<>();
        this.moveShape = new LinkedList<>();
        this.stack = new LinkedList<>();
        this.previous = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "nowPage{" +
                "history=" + history +
                ", stack=" + stack +
                ", previous=" + previous +
                ", moveShape=" + moveShape +
                ", imagePath=" + imagePath +
                '}';
    }

    public Page(LinkedList<MyShape> history, Deque<Integer> stack, Deque<MyShape> previous, LinkedList<MyShape> moveShape, String imagePath) {
        this.history = history;
        this.stack = stack;
        this.previous = previous;
        this.moveShape = moveShape;
        this.imagePath = imagePath;
    }


    public void setPage() {
        DrawBoardListener db = DrawBoardListener.getInstance();
        this.history = db.history;
        this.stack = db.stack;
        this.previous = db.previous;
        this.moveShape = db.moveShape;
//        this.imagePath = db.imagePath;

    }

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
            db.stack = readPage.stack;
            db.previous = readPage.previous;

            //取得画板对象重绘
            DragDrawPanel.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 此页保存为图片
    public void savePanelAsImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("saved.png"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // String.split的参数是正则表达式，为了匹配纯文本的.需要把参数用方括号括起来
            String[] tmp = file.getName().split("[.]");
            if (tmp.length <= 1) {
                JOptionPane.showMessageDialog(null, "保存文件没有拓展名，保存失败...");
                return;
            }
            String extension = tmp[tmp.length - 1];
            // HELP:文档内说imageio支持jpg，但本地测试保存无反应
            if (!extension.equals("png") && !extension.equals("jpg")) {
                JOptionPane.showMessageDialog(null, "拓展名非法（允许使用：png/jpg），保存失败...");
                return;
            }
            Dimension imageSize = DragDrawPanel.getInstance().getSize();
            BufferedImage image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
//            DragDrawPanel.getInstance().nowPage.image = image;//这里用于左侧列表
            Graphics2D graphics = image.createGraphics();
            DragDrawPanel.getInstance().paint(graphics);
            graphics.dispose();
            try {
                ImageIO.write(image, extension, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void drawToImage() {
//        保存当前图
        Dimension imageSize = DragDrawPanel.getInstance().getSize();
        image = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
//        DragDrawPanel.getInstance().nowPage.image = image;//这里用于左侧列表
    }

    // 从图片打开
    public void loadImageToPanel() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        System.out.println(result);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        imagePath = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println("文件绝对路径：" + imagePath);
        try {
            // 读取该张图片
            image = ImageIO.read(new File(imagePath));
            DragDrawPanel.getInstance().image = image;
            // 清除所有历史
//            EventListener.getInstance().clear(false);
            //取得画板对象重绘
            DragDrawPanel.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void clearFile() {
        // 移除内部的图片缓存
        this.image = null;
    }
}
