package ui;

import saveobject.PPT;
import saveobject.Page;

import javax.swing.*;
import java.awt.*;

/**
 * 顶部菜单栏类，定义了 菜单选项的布局排版以及各菜单项的事件绑定
 *
 * @Date 12:28 2021/11/18
 */
public class TopMenuBar extends JMenuBar {

    private static TopMenuBar myJMenuBar = new TopMenuBar();

    public static TopMenuBar getInstance() {
        return myJMenuBar;
    }

    private DrawBoard drawBoard = DrawBoard.getInstance();

    //创建菜单
    private JMenu fileJMenu = new JMenu("文件");
    private JMenu pageJMenu = new JMenu("页面");
    private JMenu drawJMenu = new JMenu("画板");
    private JMenu playJMenu = new JMenu("放映");
    private JMenu helpJMenu = new JMenu("帮助");

    //创建新建菜单项
    private JMenuItem clearFileItem = new JMenuItem("清空文件");
    private JMenuItem saveFileItem = new JMenuItem("保存文件");
    private JMenuItem openFileItem = new JMenuItem("打开文件");
    private JMenuItem newFileItem = new JMenuItem("新建文件");

    private JMenuItem deletePageItem = new JMenuItem("删除当前页");
    private JMenuItem copyPageItem = new JMenuItem("复制当前页");
    private JMenuItem insertPageItem = new JMenuItem("插入空白页");

    private JMenuItem insertPicItem = new JMenuItem("插入背景图");
    private JMenuItem deletePicItem = new JMenuItem("删除背景图");
    private JMenuItem savePicItem = new JMenuItem("此页存图");
    private JMenuItem revokeItem = new JMenuItem("撤销");
    private JMenuItem clearDrawItem = new JMenuItem("清空");
    private JMenuItem xuliehua = new JMenuItem("序列化");
    private JMenuItem fanxulie = new JMenuItem("反序列");

    private JMenuItem playNowPageItem = new JMenuItem("从当前页播放");
    private JMenuItem playFirstPageItem = new JMenuItem("从第一页播放");

    private JMenuItem operateItem = new JMenuItem("操作说明");
    private JMenuItem aboutItem = new JMenuItem("关于");

    //操作说明
    private String operateString = "1、某些菜单项具有快捷键。\n" +
            "2、选择：选择点击页面上已绘制的图形（图形重叠时默认以最下层为准），可以对该图形进行移动、删除、拉伸(点击右下角方框)操作。（无法选中自由绘线条！）\n" +
            "3、文本：在文本编辑区对文本进行编辑，点击绘图模式的\"文本\"后，可以在当前页添加文本。（由于部分字体样式不支持中文，故可能造成中文乱码。）\n" +
            "4、文件：SimplePPT的制作的、能解析的PPT文件的后缀名是: *.sPPT \n" +
            "5、背景图：可以为某页PPT插入或删除一张背景图片（支持png,jpg)，该背景图不会被序列化。\n" +
            "6、撤销：将已绘的图按操作顺序撤销。\n" +
            "7、序列化：将当前页的数据保存在固定目录下，反序列化时读取该数据覆盖当前页。\n" +
            "8、图片加载：当打开新的一个文件或插入一张新的空白页时，默认是没有加载该页的图片数据的，所以放映前先点击目标页以加载图片进内存。\n" +
            "9、放映：播放PPT时，Esc退出播放，上下键切换上一张/下一张，鼠标左键右键切换上一张/下一张。\n" +
            "10、环境：请使用jak11及以上的版本运行，经测试jdk8下运行会使布局有异。\n" +
            "11、其他请见项目描述文件。";

    //关于信息
    private String aboutString = "项目名称： SimplePPT \n" +
            "项目简介：一个基于Java Swing 开发的简易版PPT软件 \n" +
            "开发作者： NEU 物联网19级 “摆起来” 小组 \n" +
            "开发环境： jdk 11.0.12 ，Intellij IDEA 2021.1 \n" +
            "开发时间： 2021/11/12 - 2021/11/19 \n" +
            "项目地址： https://github.com/Bmaili/PPT_byJavaSwing.git ";


    private TopMenuBar() {
        setPreferredSize(new Dimension(0, 40));
        drawUI();
        bindEvents();
    }

    /**
     * 将菜单项添加进对应菜单里
     *
     * @param
     * @return
     * @date 19:57 2021/11/18
     */
    private void drawUI() {

        fileJMenu.add(newFileItem);
        fileJMenu.add(saveFileItem);
        fileJMenu.add(openFileItem);
        fileJMenu.add(clearFileItem);

        pageJMenu.add(deletePageItem);
        pageJMenu.add(copyPageItem);
        pageJMenu.add(insertPageItem);

        drawJMenu.add(insertPicItem);
        drawJMenu.add(deletePicItem);
        drawJMenu.add(savePicItem);
        drawJMenu.add(revokeItem);
        drawJMenu.add(clearDrawItem);
        drawJMenu.add(xuliehua);
        drawJMenu.add(fanxulie);

        playJMenu.add(playNowPageItem);
        playJMenu.add(playFirstPageItem);

        helpJMenu.add(operateItem);
        helpJMenu.add(aboutItem);

        this.add(fileJMenu);
        this.add(pageJMenu);
        this.add(drawJMenu);
        this.add(playJMenu);
        this.add(helpJMenu);
    }

    /**
     * 绑定每个菜单项的事件
     *
     * @param
     * @return
     * @date 19:58 2021/11/18
     */
    public void bindEvents() {


        newFileItem.addActionListener(e -> {  //对应菜单项的事件
            int result = JOptionPane.showConfirmDialog(MainJFrame.getInstance(), "是否保存当前文件", "新建文件确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result != JOptionPane.CLOSED_OPTION) {
                if (result == JOptionPane.YES_OPTION) {
                    PPT ppt = drawBoard.nowPPT;
                    ppt.saveFile();
                }
                PPT ppt = new PPT();
                Page page = new Page();
                ppt.allPage.add(page);
                PPT.resetDrawBoard(ppt, 0);
            }
        });//绑定快捷键
        newFileItem.setAccelerator(KeyStroke.getKeyStroke('N', java.awt.Event.CTRL_MASK));


        saveFileItem.addActionListener(e -> {
            PPT ppt = drawBoard.nowPPT;
            ppt.saveFile();
        });
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK));


        openFileItem.addActionListener(e -> {
            PPT ppt = drawBoard.nowPPT;
            ppt.openFile();
        });
        openFileItem.setAccelerator(KeyStroke.getKeyStroke('O', java.awt.Event.CTRL_MASK));


        clearFileItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MainJFrame.getInstance(), "此操作不可撤销，是否确认清空文件", "清空文件确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                PPT ppt = new PPT();
                Page page = new Page();
                ppt.allPage.add(page);
                PPT.resetDrawBoard(ppt, 0);
            }
        });


        deletePageItem.addActionListener(e -> {
            PPT ppt = drawBoard.nowPPT;
            int index = PageListPanel.selectIndex;
            if (index > 0 || ppt.allPage.size() > 1) {
                ppt.allPage.remove(index);
                if (index == 0)
                    index = 1;
                PPT.resetDrawBoard(ppt, index - 1);
            }
        });


        copyPageItem.addActionListener(e -> {
            Page page = drawBoard.nowPage;
            page.deepCopyNowPage();
        });
        copyPageItem.setAccelerator(KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK));


        insertPageItem.addActionListener(e -> {
            PPT ppt = drawBoard.nowPPT;
            int index = PageListPanel.selectIndex;
            ppt.allPage.add(index + 1, new Page());
            PPT.resetDrawBoard(ppt, index);
        });
        insertPageItem.setAccelerator(KeyStroke.getKeyStroke('I', java.awt.Event.CTRL_MASK));


        insertPicItem.addActionListener(e -> {
            Page page = drawBoard.nowPage;
            page.loadImageToPanel();
        });
        insertPicItem.setToolTipText("为此页PPT插入一张背景图片（支持png,jpg）"); //添加提示信息


        deletePicItem.addActionListener(e -> {
            Page page = drawBoard.nowPage;
            page.clearInsertImage();
        });
        deletePageItem.setToolTipText("删除此页的背景图片");


        savePicItem.addActionListener(e -> {
            Page page = drawBoard.nowPage;
            page.savePanelAsImage();
        });
        savePicItem.setToolTipText("将此页以图片(png,jpg)的形式保存");


        revokeItem.addActionListener(e -> {
            Page page = drawBoard.nowPage;
            if (!page.history.isEmpty() && page.history.peekLast().equals(page.moveShape.peekLast()))
                page.moveShape.pollLast();
            drawBoard.revert(false);
        });
        revokeItem.setToolTipText("撤销此页已添加的图案");
        revokeItem.setAccelerator(KeyStroke.getKeyStroke('Z', java.awt.Event.CTRL_MASK));


        clearDrawItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(MainJFrame.getInstance(), "此操作不可撤销，是否确认清空画板", "清空画板确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                Page page = drawBoard.nowPage;
                page.previous.clear();
                page.history.clear();
                page.moveShape.clear();
                drawBoard.revert(false);
            }
        });
        clearDrawItem.setToolTipText("将此页清空，该操作不可撤销");


        xuliehua.addActionListener(e -> {
            Page page = drawBoard.nowPage;
            page.saveObject();
        });
        xuliehua.setToolTipText("将此页数据序列化(Welcome.txt)，存在SimplePPT目录下");


        fanxulie.addActionListener(e -> {
            Page page = drawBoard.nowPage;
            page.outObject();
            //刷新当前页数据
            drawBoard.flushNowPage(PageListPanel.selectIndex);
        });
        fanxulie.setToolTipText("将上次序列化内容反序列化，输出在此页上");


        playNowPageItem.addActionListener(e -> {
            PageListPanel.flushPageImage();
            int index = PageListPanel.selectIndex;
            if (index < 0 || index > drawBoard.nowPPT.allPage.size())
                index = 0;
            new PlayJFrame(drawBoard.nowPPT, index);
        });


        playFirstPageItem.addActionListener(e -> {
            PageListPanel.flushPageImage();
            new PlayJFrame(drawBoard.nowPPT, 0);
        });
        playFirstPageItem.setAccelerator(KeyStroke.getKeyStroke('P', java.awt.Event.CTRL_MASK));


        operateItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(MainJFrame.getInstance(), operateString, "操作说明", JOptionPane.INFORMATION_MESSAGE);
        });

        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(MainJFrame.getInstance(), aboutString, "关于", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
