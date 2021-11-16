package saveobject;

import listener.DrawBoardListener;
import ui.PageListPanel;

import javax.swing.*;
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
    public void saveObject(String fileName) {
        try (//创建一个ObjectOutputStream输出流
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    保存文件
    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("myPPT.txt"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            saveObject(file.getAbsolutePath());
//            try (//创建一个ObjectOutputStream输出流
//                 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
//                oos.writeObject(this);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    //openFile
    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        System.out.println(result);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println("文件绝对路径：" + filePath);
        outObject(filePath);


    }

    //反序列化
    public void outObject(String fileName) {
        try (//创建一个ObjectInputStream输入流
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            PPT readPPT = (PPT) ois.readObject();
            DrawBoardListener.getInstance().nowPPT = readPPT;
            DrawBoardListener.getInstance().nowPage = readPPT.allPage.get(0);


            DrawBoardListener db = DrawBoardListener.getInstance();
            db.history = DrawBoardListener.getInstance().nowPage.history;
            db.moveShape = DrawBoardListener.getInstance().nowPage.moveShape;
            db.stack = DrawBoardListener.getInstance().nowPage.stack;
            db.previous = DrawBoardListener.getInstance().nowPage.previous;


            PageListPanel.pageModel.clear();
            for (Page p: readPPT.allPage ) {
                PageListPanel.pageModel.addElement(p);
            }
            PageListPanel.pageJList.setSelectedIndex(0);

            //取得画板对象重绘
            DrawBoardListener.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //重置PPT
    public static void reset(PPT ppt,int selectPage){
        DrawBoardListener.getInstance().nowPPT = ppt;
        DrawBoardListener.getInstance().nowPage = ppt.allPage.get(selectPage);


        DrawBoardListener db = DrawBoardListener.getInstance();
        db.history = DrawBoardListener.getInstance().nowPage.history;
        db.moveShape = DrawBoardListener.getInstance().nowPage.moveShape;
        db.stack = DrawBoardListener.getInstance().nowPage.stack;
        db.previous = DrawBoardListener.getInstance().nowPage.previous;


        PageListPanel.pageModel.clear();
        for (Page p: ppt.allPage ) {
            PageListPanel.pageModel.addElement(p);
        }
        PageListPanel.pageJList.setSelectedIndex(selectPage);

        //取得画板对象重绘
        DrawBoardListener.getInstance().paint(DrawBoardListener.getInstance().drawPanel.getGraphics());
    }
}
