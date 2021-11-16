package ui;

import listener.DrawBoardListener;
import listener.TopMenuListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TopMenu extends JPanel {
    private static TopMenu myTopMenu = new TopMenu();

    private TopMenu() {
//        PageListPanelListener instance = PageListPanelListener.getInstance();
//        this.addMouseListener(instance);
        this.setPreferredSize(new Dimension(400, 100));
        init();
    }

    public static TopMenu getInstance() {
        return myTopMenu;
    }


    // 文本输入文本框
    public JTextField textContent = new JTextField("你好世界!", 20);
    // 字体选择器
    public JComboBox<String> fontChooser = new JComboBox<>();
    // 字号选择器
    public JComboBox<Integer> sizeChooser = new JComboBox<>();

    public Map<String, Font> fontMap = new HashMap<>();

    TopMenuListener topMenuListener = TopMenuListener.getInstance();


    public Box panelOne() {

        Box setBox1 = Box.createHorizontalBox();
//        String[] setBoxBtn1 = {"序列化", "反序列", "此页存图", "清空", "文件"};
        String[] setBoxBtn1 = {"序列化", "新增"};
        // 添加所有的按钮并添加按钮点击事件监听
        for (String item : setBoxBtn1) {
            JButton tmp = new JButton(item);
            tmp.addActionListener(topMenuListener);
            setBox1.add(tmp);
            setBox1.add(Box.createHorizontalStrut(10));
        }

        Box setBox2 = Box.createHorizontalBox();
//        String[] setBoxBtn2 = {"插入背景", "删除选中", "关于", "保存","新增"};
        String[] setBoxBtn2 = {};
        for (String item : setBoxBtn2) {
            JButton tmp = new JButton(item);
            tmp.addActionListener(topMenuListener);
            setBox2.add(tmp);
            setBox2.add(Box.createHorizontalStrut(10));
        }

        Box setBox = Box.createVerticalBox();
        setBox.add(setBox1);
        setBox.add(Box.createVerticalStrut(8));
        setBox.add(setBox2);

        //边框设计
        Border lb = BorderFactory.createLineBorder(Color.CYAN, 5);
        TitledBorder tb = new TitledBorder(lb, "功能选择", TitledBorder.LEFT, TitledBorder.TOP, new Font("StSong", Font.BOLD, 15), Color.BLUE);
        setBox.add(new JLabel());
        setBox.setBorder(tb);
        return setBox;

    }


    public Box panelTwo() {
        ButtonGroup cbg = new ButtonGroup();

        Box panel1 = Box.createHorizontalBox();
        String[] selectModule1 = {"选择", "画笔", "橡皮", "文本"};
        for (String item : selectModule1) {
            JRadioButton tmp = new JRadioButton(item, false);
            panel1.add(tmp);
            panel1.add(Box.createHorizontalStrut(10));

            cbg.add(tmp);
            tmp.addItemListener(topMenuListener);
        }

        Box penWifthSelcet = Box.createHorizontalBox();
        penWifthSelcet.add(new JLabel("线条粗细"));
        JSlider slider = new JSlider(2, 30, 10);
        slider.setMajorTickSpacing(4);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.addChangeListener(topMenuListener);
        penWifthSelcet.add(slider);

        Box leftBox = Box.createVerticalBox();
        leftBox.add(panel1);
        leftBox.add(Box.createHorizontalStrut(3));
        leftBox.add(penWifthSelcet);

        //边框设计
        Border leftbd = BorderFactory.createLineBorder(Color.ORANGE, 2);
        leftBox.setBorder(leftbd);

        Box panel2 = Box.createHorizontalBox();
        String[] selectModule2 = {"直线", "星星", "八边形"};
        for (String item : selectModule2) {
            JRadioButton tmp = new JRadioButton(item, false);
            panel2.add(tmp);
            panel2.add(Box.createHorizontalStrut(10));

            cbg.add(tmp);
            tmp.addItemListener(topMenuListener);
        }

        Box panel3 = Box.createHorizontalBox();
        String[] selectModule3 = {"矩形", "圆形", "三角形"};
        // 添加所有的按钮并添加按钮点击事件监听
        for (String item : selectModule3) {
            JRadioButton tmp = null;
            if ("矩形".equals(item))
                tmp = new JRadioButton(item, true);
            else
                tmp = new JRadioButton(item, false);
            panel3.add(tmp);
            panel3.add(Box.createHorizontalStrut(10));

            cbg.add(tmp);
            tmp.addItemListener(topMenuListener);
        }

        Box rightBox = Box.createVerticalBox();
        rightBox.add(panel2);
        rightBox.add(panel3);
        //边框设计
        Border rightbd = BorderFactory.createLineBorder(Color.ORANGE, 2);
        rightBox.setBorder(rightbd);

        Box panel5 = Box.createHorizontalBox();
        panel5.add(leftBox);
        panel5.add(rightBox);


        //边框设计
        Border lb = BorderFactory.createLineBorder(Color.CYAN, 5);
        TitledBorder tb = new TitledBorder(lb, "绘图模式", TitledBorder.LEFT, TitledBorder.TOP, new Font("StSong", Font.BOLD, 15), Color.BLUE);
        panel5.add(new JLabel());
        panel5.setBorder(tb);
        return panel5;
    }


    public Box panelThree() {

        // 最后一个按钮是自定义颜色
        JButton mainColor = new JButton("主笔色");
        mainColor.setBackground(Color.CYAN);

        mainColor.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(null, "颜色选择器", Color.BLACK);
            if (selectedColor == null) {
                selectedColor = Color.BLACK;
            }
            mainColor.setBackground(selectedColor);
            DrawBoardListener.getInstance().mainColor = selectedColor;
        });

        JButton fillColor = new JButton("填充色");
        fillColor.setBackground(Color.PINK);
        fillColor.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(null, "颜色选择器", Color.BLACK);
            if (selectedColor == null) {
                selectedColor = Color.WHITE;
            }
            fillColor.setBackground(selectedColor);
            DrawBoardListener.getInstance().fillColor = selectedColor;

        });

        JCheckBox isFillSelect = new JCheckBox("图形填充（实心）？", true);
        isFillSelect.addChangeListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            DrawBoardListener.getInstance().isFillShape = checkBox.isSelected();
        });

        Box colourSelect = Box.createHorizontalBox();
        colourSelect.add(Box.createHorizontalStrut(25));
        colourSelect.add(mainColor);
        colourSelect.add(Box.createHorizontalStrut(25));
        colourSelect.add(fillColor);

        Box box = Box.createVerticalBox();
        box.add(colourSelect);
        box.add(isFillSelect);

        //边框设计
        Border lb = BorderFactory.createLineBorder(Color.CYAN, 5);
        TitledBorder tb = new TitledBorder(lb, "颜色编辑", TitledBorder.LEFT, TitledBorder.TOP, new Font("StSong", Font.BOLD, 15), Color.BLUE);
        box.setBorder(tb);
        return box;
    }

    public Box panelFour() {

        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        List<Font> fontList = Arrays.asList(fonts);
        // 按照fontfamily进行去重
        Set<Font> set = new TreeSet<>((first, second) -> first.getFamily().compareTo(second.getFamily()));
        set.addAll(fontList);
        // 使用映射

        for (var item : set) {
            fontMap.put(item.getFamily(), item);
        }
        List<String> fontList2 = set.stream().map(item -> item.getFamily()).collect(Collectors.toList());
        for (int i = fontList2.size() - 1; i >= 0; i--) {
            this.fontChooser.addItem(fontList2.get(i));
        }


        // 添加字号选择器到toolbar的第二行
        for (int i = 9; i <= 72; i++) {
            sizeChooser.addItem(Integer.valueOf(i));
        }
        sizeChooser.setSelectedIndex(7);

        JPanel sizeSelect = new JPanel();
        sizeSelect.add(new JLabel("字体样式："));
        sizeSelect.add(fontChooser);
        sizeSelect.add(new JLabel("字体大小："));
        sizeSelect.add(sizeChooser);

        // 添加文本框到toolbar的第二行
        Box textEditBox = Box.createVerticalBox();
        textEditBox.add(sizeSelect);
        textEditBox.add(textContent);

        //边框设计
        Border lb = BorderFactory.createLineBorder(Color.CYAN, 5);
        TitledBorder tb = new TitledBorder(lb, "文本编辑", TitledBorder.LEFT, TitledBorder.TOP, new Font("StSong", Font.BOLD, 15), Color.BLUE);
        textEditBox.add(new JLabel());
        textEditBox.setBorder(tb);

        return textEditBox;
    }

    public void init() {
        this.setLayout(new BorderLayout());

        Box setBox = panelOne();
        Box drawSelectBox = panelTwo();
        Box colourSelect = panelThree();
        Box textEditBox = panelFour();

        Box allBoxs = Box.createHorizontalBox();
//        allBoxs.add(setBox);
        allBoxs.add(drawSelectBox);
        allBoxs.add(colourSelect);
        allBoxs.add(textEditBox);

        this.add(allBoxs);

    }
}




