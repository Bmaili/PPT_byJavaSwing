package saveobject;

import listener.DrawBoardListener;
import ui.DragDrawPanel;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class PPT implements Serializable {
    private static final long serialVersionUID = 1111010L;

    //所有的页面的序列化数据
    public ArrayList<Page> allPage = new ArrayList<>();

//    //每页即时图,用于左侧预览列表
//    public transient ArrayList<BufferedImage> allPageImage;


    public PPT() {
    }

    public PPT(ArrayList<Page> allPage, ArrayList<BufferedImage> allPageImage) {
        this.allPage = allPage;
    }

//    public void getBufferedImage(int index, BufferedImage bufferedImage) {
//        allPage.get(index).image = bufferedImage;
//    }

    //    序列化
    public void saveObject() {
        try (//创建一个ObjectOutputStream输出流
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("myppt.txt"))) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反序列化
    public void outObject() {
        try (//创建一个ObjectInputStream输入流
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("myppt.txt"))) {
            PPT readPPT = (PPT) ois.readObject();
            DragDrawPanel.getInstance().ppt = readPPT;
            DragDrawPanel.getInstance().nowPage = readPPT.allPage.get(0);


            DrawBoardListener db = DrawBoardListener.getInstance();
            db.history = DragDrawPanel.getInstance().nowPage.history;
            db.moveShape = DragDrawPanel.getInstance().nowPage.moveShape;
            db.stack = DragDrawPanel.getInstance().nowPage.stack;
            db.previous = DragDrawPanel.getInstance().nowPage.previous;


            //取得画板对象重绘
            DragDrawPanel.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
