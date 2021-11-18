package saveobject;

import ui.DrawBoard;
import ui.PageListPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * PPT对象
 *
 * @date 22:11 2021/11/18
 */
public class PPT implements Serializable {
    private static final long serialVersionUID = 1111010L;

    //所有的页面 ，序列化此项
    public ArrayList<Page> allPage = new ArrayList<>();

    public PPT() {
    }

    public PPT(ArrayList<Page> allPage, ArrayList<BufferedImage> allPageImage) {
        this.allPage = allPage;
    }

    /**
     * 序列化此PPT
     *
     * @param fileName 文件的绝对路径
     * @return
     * @date 22:13 2021/11/18
     */
    public void saveObject(String fileName) {
        try (//创建一个ObjectOutputStream输出流
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化此PPT
     *
     * @param fileName 文件的绝对路径
     * @return
     * @date 22:14 2021/11/18
     */
    public void outObject(String fileName) {
        DrawBoard drawBoard = DrawBoard.getInstance();
        try (//创建一个ObjectInputStream输入流
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            PPT readPPT = (PPT) ois.readObject();
            drawBoard.nowPPT = readPPT;

            PageListPanel.pageModel.clear();
            for (Page p : readPPT.allPage) {
                PageListPanel.pageModel.addElement(p);
            }
            PageListPanel.pageJList.setSelectedIndex(0);

            for (int i = 0; i < drawBoard.nowPPT.allPage.size() - 1; i++) {
                drawBoard.nowPage = drawBoard.nowPPT.allPage.get(i);
                PageListPanel.flushPageImage();
            }

            drawBoard.nowPPT = readPPT;
            //重绘
            drawBoard.flushNowPage(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存文件
     *
     * @param
     * @return
     * @date 22:13 2021/11/18
     */
    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("myPPT.sPPT"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            saveObject(file.getAbsolutePath());
        }
    }

    /**
     * 打开文件
     *
     * @param
     * @return
     * @date 22:14 2021/11/18
     */
    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        System.out.println(result);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        outObject(filePath);
    }


    /**
     * 将当前画板（nowPPT）正在编辑的PPT和Page更新
     *
     * @param ppt        替换的新PPT
     * @param selectPage 当前选择的页码
     * @return
     * @Date 15:50 2021/11/18
     */
    public static void resetDrawBoard(PPT ppt, int selectPage) {
        DrawBoard.getInstance().nowPPT = ppt;
        DrawBoard.getInstance().nowPage = ppt.allPage.get(selectPage);

        PageListPanel.pageModel.clear();
        for (Page p : ppt.allPage) {
            PageListPanel.pageModel.addElement(p);
        }
        PageListPanel.pageJList.setSelectedIndex(selectPage);

        DrawBoard.getInstance().flushNowPage(selectPage);

        //取得画板对象重绘
        DrawBoard.getInstance().paint(DrawBoard.getInstance().drawPanel.getGraphics());

        PageListPanel.flushPageImage();
    }

}
