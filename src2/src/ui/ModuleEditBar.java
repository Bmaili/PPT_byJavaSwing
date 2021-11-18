package ui;

import listener.DrawBoardListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定义了模式选择、颜色编辑、文本编辑的相关布局与事件
 *
 * @Date 15:11 2021/11/18
 */
public class ModuleEditBar extends JPanel implements ChangeListener, ItemListener {
    private static ModuleEditBar myModuleEditBar = new ModuleEditBar();
    DrawBoardListener drawBoardListener = DrawBoardListener.getInstance();

    private ModuleEditBar() {
        this.setPreferredSize(new Dimension(400, 100));
        init();
    }

    public static ModuleEditBar getInstance() {
        return myModuleEditBar;
    }


    // 文本输入文本框
    public JTextField textContent = new JTextField("你好世界!", 20);
    // 字体选择器
    public JComboBox<String> fontChooser = new JComboBox<>();
    // 字号选择器
    public JComboBox<Integer> sizeChooser = new JComboBox<>();
    // 字体与字体名称匹配map
    public Map<String, Font> fontMap = new HashMap<>();


    /**
     * 绘画模式选择框的排版设计与事件绑定
     *
     * @param
     * @return
     * @date 16:11 2021/11/18
     */
    public Box drawSelectBox() {
        //定义复选组合框，放置“选择”，“画笔”等选项，并绑定事件，布局就是一层盒子（Box）套着一层盒子这样
        ButtonGroup cbg = new ButtonGroup();

        Box panel1 = Box.createHorizontalBox();
        String[] selectModule1 = {"选择", "画笔", "橡皮", "文本"};
        for (String item : selectModule1) { //循环加入布局，并且绑定事件
            JRadioButton tmp = new JRadioButton(item, false);
            panel1.add(tmp);
            panel1.add(Box.createHorizontalStrut(10));

            cbg.add(tmp);
            //绑定事件
            tmp.addItemListener(this);
        }

        //设置线条粗细的样式、布局、绑定事件
        Box penWifthSelcet = Box.createHorizontalBox();
        penWifthSelcet.add(new JLabel("线条粗细"));
        JSlider slider = new JSlider(2, 30, 10);
        slider.setMajorTickSpacing(4);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.addChangeListener(this);
        penWifthSelcet.add(slider);

        Box leftBox = Box.createVerticalBox();
        leftBox.add(panel1);
        leftBox.add(Box.createHorizontalStrut(3));
        leftBox.add(penWifthSelcet);

        //边框设计
        Border leftbd = BorderFactory.createLineBorder(Color.ORANGE, 2);
        leftBox.setBorder(leftbd);

        //思路与上面一致，之所以分开这些选项分别设置，是为了布局好看
        Box panel2 = Box.createHorizontalBox();
        String[] selectModule2 = {"直线", "星星", "八边形"};
        for (String item : selectModule2) {
            JRadioButton tmp = new JRadioButton(item, false);
            panel2.add(tmp);
            panel2.add(Box.createHorizontalStrut(10));

            cbg.add(tmp);
            tmp.addItemListener(this);
        }

        Box panel3 = Box.createHorizontalBox();
        String[] selectModule3 = {"矩形", "圆形", "三角形"};
        for (String item : selectModule3) {
            JRadioButton tmp = null;
            if ("矩形".equals(item))  //默认选择是“矩形”
                tmp = new JRadioButton(item, true);
            else
                tmp = new JRadioButton(item, false);
            panel3.add(tmp);
            panel3.add(Box.createHorizontalStrut(10));

            cbg.add(tmp);
            tmp.addItemListener(this);

        }

        //下面是排版设计，盒子层层嵌套
        Box rightBox = Box.createVerticalBox();
        rightBox.add(panel2);
        rightBox.add(panel3);
        //边框设计
        Border rightbd = BorderFactory.createLineBorder(Color.ORANGE, 2);
        rightBox.setBorder(rightbd);

        //将左右两部分盒子再放进一个大盒子里，即“绘图模式”
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

    /**
     * 颜色选择框的排版设计与事件绑定
     *
     * @param
     * @return
     * @date 16:11 2021/11/18
     */
    public Box colourSelect() {
        //思路与drawSelectBox()类似

        //俩按钮
        JButton mainColor = new JButton("主笔色");
        mainColor.setBackground(Color.CYAN);
        JButton fillColor = new JButton("填充色");
        fillColor.setBackground(Color.PINK);

        //绑定颜色编辑事件
        mainColor.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(null, "颜色选择器", Color.BLACK);
            if (selectedColor == null) {
                selectedColor = Color.BLACK;
            }
            mainColor.setBackground(selectedColor);
            drawBoardListener.mainColor = selectedColor;
        });

        //绑定颜色编辑事件
        fillColor.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(null, "颜色选择器", Color.BLACK);
            if (selectedColor == null) {
                selectedColor = Color.WHITE;
            }
            fillColor.setBackground(selectedColor);
            drawBoardListener.fillColor = selectedColor;
        });

        //图形填充选择复选框
        JCheckBox isFillSelect = new JCheckBox("图形填充（实心）？", true);
        isFillSelect.addChangeListener(e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            drawBoardListener.isFillShape = checkBox.isSelected();
        });

        //下面是排版设计，盒子层层嵌套
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

    /**
     * 文本编辑框的排班设计与事件绑定
     *
     * @param
     * @return
     * @date 16:11 2021/11/18
     */
    public Box textEditBox() {

        //得到系统的所有字体
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        List<Font> fontList = Arrays.asList(fonts);
        // 去重
        Set<Font> set = new TreeSet<>((first, second) -> first.getFamily().compareTo(second.getFamily()));
        set.addAll(fontList);
        // 使用字体名字来映射字体类
        for (Font item : set) {
            fontMap.put(item.getFamily(), item);
        }
        //再依次添加到列表框中，倒序添加是为了让中文字体在前
        List<String> fontList2 = set.stream().map(item -> item.getFamily()).collect(Collectors.toList());
        for (int i = fontList2.size() - 1; i >= 0; i--) {
            this.fontChooser.addItem(fontList2.get(i));
        }

        // 添加字号选择器
        for (int i = 9; i <= 72; i++) {
            sizeChooser.addItem(Integer.valueOf(i));
        }
        sizeChooser.setSelectedIndex(7);

        //下面是排版设计，盒子层层嵌套
        JPanel sizeSelect = new JPanel();
        sizeSelect.add(new JLabel("字体样式："));
        sizeSelect.add(fontChooser);
        sizeSelect.add(new JLabel("字体大小："));
        sizeSelect.add(sizeChooser);

        // 添加文本框
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

        Box drawSelectBox = drawSelectBox();
        Box colourSelect = colourSelect();
        Box textEditBox = textEditBox();

        Box allBoxs = Box.createHorizontalBox();
        allBoxs.add(drawSelectBox);
        allBoxs.add(colourSelect);
        allBoxs.add(textEditBox);

        this.add(allBoxs);
    }


    /**
     * 当线条尺寸条发生变化时调用，调节画笔大小
     *
     * @param
     * @return
     * @date 16:12 2021/11/18
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider jslider = (JSlider) e.getSource();
        drawBoardListener.penWidth = jslider.getValue();
        System.out.println("调节画笔尺寸： " + jslider.getValue());
    }

    /**
     * 绘图模式发生变化时调用，将绘图模式切换为所选状态
     *
     * @param
     * @return
     * @date 16:12 2021/11/18
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        JRadioButton jRadioButton = (JRadioButton) e.getSource();
        drawBoardListener.selectModule = jRadioButton.getText();
        System.out.println("选择模式： " + jRadioButton.getText());
    }
}




