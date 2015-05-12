package test;

import utils.ClearComment;
import utils.Convert;
import utils.DFA;
import utils.Matcher;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by jhcpokemon on 05/11/15.
 */
public class Main {
    ClearComment clearComment = new ClearComment();
    Convert convert = new Convert();
    int row = 1;
    int line = 1;
    int begin = 0;
    int end = 0;
    JPanel cards;
    JMenuItem item, item1, item2, item3, item4;
    CardLayout cardLayout;
    JTextField path = new JTextField("", 40);
    JTextArea oldFile = new JTextArea();
    JTextArea newFile = new JTextArea();
    JTextArea LeftG = new JTextArea("左线性文法");
    JTextArea RightG = new JTextArea("右线性文法");
    JTextArea defineL = new JTextArea("正规表达式");
    JTextArea DFAArea = new JTextArea("DFA");
    JTextArea ruleArea = new JTextArea("正规文法");
    JTextField ruleFiled = new JTextField("输入正规表达式", 40);
    JTextArea inputField = new JTextArea("输入待匹配串");
    JTextArea resultField = new JTextArea("输出结果");

    public static void main(String args[]) {
        Main test = new Main();
        test.initialize();
    }

    public void initialize() {
        JFrame frame = new JFrame();
        frame.setTitle("First Experiment");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Action");
        JMenu menu1 = new JMenu("Other");
        item = new JMenuItem("ShowFile");
        item1 = new JMenuItem("Convert");
        item2 = new JMenuItem("DFA");
        item3 = new JMenuItem("Match");
        item4 = new JMenuItem("About");
        menu.add(item);
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu1.add(item4);

        menuBar.add(menu);
        menuBar.add(menu1);
        frame.setJMenuBar(menuBar);
        frame.setLayout(new BorderLayout());
        /**菜单栏*/

        JPanel card1 = new JPanel(new BorderLayout());
        JPanel panel11 = new JPanel();
        JButton button11 = new JButton("打开");
        JButton button12 = new JButton("分类(含报错)");
        JButton button13 = new JButton("查找");
        panel11.add(path);
        panel11.add(button11);
        panel11.add(button12);
        panel11.add(button13);
        JPanel panel12 = new JPanel(new GridLayout(1, 2));
        oldFile.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        oldFile.setLineWrap(true);
        newFile.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        newFile.setLineWrap(true);
        JScrollPane old = new JScrollPane(oldFile);
        old.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        old.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane news = new JScrollPane(newFile);
        news.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        news.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel12.add(old);
        panel12.add(news);
        card1.add(panel11, BorderLayout.NORTH);
        card1.add(panel12, BorderLayout.CENTER);
        button11.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            File file = chooser.getSelectedFile();
            path.setText(file.getAbsolutePath());
            printFile(file);
        });/**清除格式*/
        button12.addActionListener(e -> analyse(oldFile.getText()));/**分类&报错*/

        button13.addActionListener(e -> {
            String source = oldFile.getText();
            String str = newFile.getText();
            if (search(source, str)) {
                analyse(str);
            } else {
                newFile.setText("没有找到");
            }
        });/**查找*/

        /**
         * 第一个页面
         */

        JPanel card2 = new JPanel(new BorderLayout());
        JButton button21 = new JButton("左->右");
        JButton button22 = new JButton("右->左");
        JButton button23 = new JButton("正规表达式");
        JPanel panel21 = new JPanel();
        panel21.add(button21);
        panel21.add(button22);
        panel21.add(button23);
        JPanel panel22 = new JPanel(new GridLayout(1, 2));
        LeftG.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        RightG.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        defineL.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        defineL.setRows(10);
        panel22.add(LeftG);
        panel22.add(RightG);
        card2.add(panel21, BorderLayout.NORTH);
        card2.add(panel22, BorderLayout.CENTER);
        card2.add(defineL, BorderLayout.SOUTH);
        button21.addActionListener(e -> {
            String[] results = convert.convertLeftToRight(LeftG.getText());
            RightG.setText("");
            for (String str : results) {
                if (str != null) {
                    RightG.append(str + "\n");
                }
            }
        });/**左线性文法转右线性*/

        button22.addActionListener(e -> {
            String[] results = convert.convertRightToLeft(RightG.getText());
            LeftG.setText("");
            for (String str : results) {
                if (str != null) {
                    LeftG.append(str + "\n");
                }
            }
        });/**右线性文法转左线性*/

        button23.addActionListener(e -> {
            String result = convert.convertToDefine(RightG.getText());
            defineL.setText(result);
        });/**右线性文法转正规表达式*/

        /**
         * 第二个页面
         */
        JPanel card3 = new JPanel(new BorderLayout());
        JPanel panel31 = new JPanel();
        JButton button31 = new JButton("正规文法转DFA");
        JButton button32 = new JButton("DFA转正规文法");
        panel31.add(button31);
        panel31.add(button32);
        JPanel panel32 = new JPanel(new GridLayout(1, 2));
        DFAArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        ruleArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        panel32.add(ruleArea);
        panel32.add(DFAArea);
        card3.add(panel31, BorderLayout.NORTH);
        card3.add(panel32, BorderLayout.CENTER);
        button31.addActionListener(e -> {
            DFA dfa = new DFA(ruleArea.getText());
            String result = dfa.convertRuleToDFA();
            DFAArea.setText(result);
        });

        button32.addActionListener(e -> {
            DFA dfa = new DFA(DFAArea.getText());
            String result = dfa.convertDFAToRule();
            ruleArea.setText(result);
        });
        /**
         * 第三个页面
         */

        JPanel card4 = new JPanel(new BorderLayout());
        JPanel panel41 = new JPanel();
        panel41.add(ruleFiled);
        ruleFiled.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        JButton button41 = new JButton("匹配");
        panel41.add(button41);
        card4.add(panel41, BorderLayout.NORTH);
        inputField.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        resultField.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        JPanel panel42 = new JPanel(new GridLayout(1, 2));
        panel42.add(inputField);
        panel42.add(resultField);
        card4.add(panel42, BorderLayout.CENTER);
        button41.addActionListener(e -> {
            String forMatch = ruleFiled.getText();
            String waitMatch = inputField.getText();
            Matcher matcher = new Matcher(forMatch, waitMatch);
            if (matcher.match()) {
                resultField.setText("匹配成功");
            } else {
                resultField.setText("匹配失败");
            }
        });

        JPanel card5 = new JPanel(new BorderLayout());
        card5.add(new TextArea("Coded by B12040328"), BorderLayout.CENTER);
        /**
         * 第五个页面
         */

        cards = new JPanel(new CardLayout());
        cards.add(card1, "1");
        cards.add(card2, "2");
        cards.add(card3, "3");
        cards.add(card4, "4");
        cards.add(card5, "5");
        frame.add(cards);
        frame.setVisible(true);
        cardLayout = (CardLayout) (cards.getLayout());

        item.addActionListener(e -> cardLayout.show(cards, "1"));
        item1.addActionListener(e -> cardLayout.show(cards, "2"));
        item2.addActionListener(e -> cardLayout.show(cards, "3"));
        item3.addActionListener(e -> cardLayout.show(cards, "4"));
        item4.addActionListener(e -> cardLayout.show(cards, "5"));
        /**切换功能**/
    }

    public void printFile(File file) {
        String source = clearComment.getSource(file);
        oldFile.setText(source);
        source = clearComment.getNewSource(source);
        newFile.setText(source);
    }

    public void analyse(String content) {
        row = 0;
        line = 1;
        newFile.setText("");
        if (content.equals("")) {
            newFile.setText("Text is empty! You havn't input any code!\n");
        } else {
            int i = 0;
            int N = content.length();
            int state = 0;
            for (i = 0; i < N; i++) {
                row++;
                char c = content.charAt(i);//当前读取字符
                switch (state) {
                    case 0:
                        if (c == ',' || c == ' ' || c == '\t' || c == '{' || c == '}' || c == '(' || c == ')' || c == ';' || c == '[' || c == ']' || c == '.') {
                            if (isDigit(content.charAt(i - 1)) && isDigit(content.charAt(begin))) {
                                end = i;
                                newFile.append("info:0 数值表达式：    " + content.substring(begin, end) + '\n');
                            }
                            state = 0;
                        } else if (c == '+') state = 1;
                        else if (c == '-') state = 2;
                        else if (c == '*') state = 3;
                        else if (c == '/') state = 4;
                        else if (c == '!') state = 5;
                        else if (c == '>') state = 6;
                        else if (c == '<') state = 7;
                        else if (c == '=') state = 8;
                        else if (((int) c) == 10) state = 9;//输入为回车
                        else if (isLetter(c)) {
                            state = 10;
                            begin = i;
                        }
                        //isDigit(int)
                        else if (isDigit(c)) {
                            begin = i;
                            state = 11;
                        } else if (c == '#') state = 12;
                        else if (c == '&') state = 14;
                        else if (c == '|') state = 15;
                        else if (c == '"') state = 16;
                        else
                            newFile.append("line: " + line + " row: " + row + " error: '" + c + "' Undefined character! \n");
                        break;
                    case 1://标识符为 +
                        //row++;
                        if (c == '+') {
                            state = 0;
                            newFile.append("info:1 运算符:'++'\n");
                        } else if (c == '=') {
                            state = 0;
                            newFile.append("info:1 运算符:'+='\n");
                        } else {
                            state = 0;
                            newFile.append("info:1 运算符:'+'\n");
                            i--;
                            row--;
                        }
                        break;
                    case 2://标志符为 -
                        if (c == '-')
                            newFile.append("info:2 运算符:'--'\n");
                        else if (c == '=')
                            newFile.append("info:2 运算符:'-='\n");
                        else {
                            newFile.append("info:2 运算符:'-'\n");
                            i--;
                            row--;
                        }
                        state = 0;
                        break;
                    case 3://运算符 *
                        if (c == '=')
                            newFile.append("info:3 运算符:'*='\n");
                        else {
                            newFile.append("info:3 运算符:'*'\n");
                            i--;
                            row--;
                        }
                        state = 0;
                        break;
                    case 4://运算符 /
                        if (c == '/') {
                            if ((c = content.charAt(i + 1)) == '*')
                                state = 3;

                            else {
                                while ((c) != '\n') {
                                    c = content.charAt(i);
                                    i++;
                                }
                                System.out.println("");
                                c = content.charAt(i);
                                state = 0;
                                newFile.append("info:4 注释部分:// \n");
                            }
                        } else if (c == '=') {
                            state = 0;
                            newFile.append("info:4 运算符:/= \n");
                        } else {
                            state = 0;
                            newFile.append("info:4 运算符:/\n");
                            i--;
                            row--;
                        }
                        break;
                    case 5://运算符 !
                        if (c == '=') {
                            newFile.append("info:5 运算符:!=\n");
                            state = 0;
                        } else {
                            state = 0;
                            i--;
                            row--;
                            newFile.append("info:5 运算符:!\n");
                        }
                        break;
                    case 6://运算符 >
                        if (c == '=') {
                            newFile.append("info:6 运算符:>=\n");
                            state = 0;
                        } else {
                            state = 0;
                            newFile.append("info:6 运算符:>\n");
                        }
                        break;
                    case 7://运算符 <
                        if (c == '=') {
                            newFile.append("info:7 运算符:<=\n");
                            state = 0;
                        } else {
                            state = 0;
                            newFile.append("info:7 运算符:<\n");
                        }
                        break;
                    case 8://运算符 =
                        if (c == '=') {
                            newFile.append("info:8 运算符:==\n");
                            state = 0;
                        } else {
                            state = 0;
                            newFile.append("info:8 运算符:=\n");
                        }
                        break;
                    case 9://回车
                        state = 0;
                        row = 1;
                        line++;
                        newFile.append("info:9:回车\n");
                        break;
                    case 10:// 字母
                        if (isLetter(c) || isDigit(c)) {
                            state = 10;
                        } else {
                            end = i;
                            String id = content.substring(begin, end);
                            if (isKey(id))
                                newFile.append("info：关键字 : " + id + '\n');
                                //error_text.appendText("info  ? :   10" + id + '\n');
                            else
                                newFile.append("info：标识符  :" + id + '\n');
                            i--;
                            row--;
                            state = 0;
                        }
                        break;
                    case 11:// 数字
                        if (c == 'e' || c == 'E')
                            state = 13;
                        else if (isDigit(c) || c == '.') {

                        } else {
                            if (isLetter(c)) {
                                newFile.append("error: line " + line + " row " + row + " ????\n");
                            }
                            //i--;
                            //row--;
                            int temp = i;
                            i = find(i, content);
                            row += (i - temp);
                            state = 0;
                        }
                        break;
                    case 12://标识符为#
                        String id = "";
                        while (c != '<') {
                            id += c;
                            i++;
                            c = content.charAt(i);
                            System.out.print(c);
                        }
                        if (id.trim().equals("include")) {
                            while (c != '>' && (c != '\n')) {
                                i++;
                                c = content.charAt(i);
                                System.out.print(c);
                            }
                            if (c == '>')
                                newFile.append("info # :12 :  \n");
                        } else
                            newFile.append("error: " + "line " + line + ", row " + row + " ?\n");
                        state = 0;
                        break;
                    case 13:// 检测指数表示方式
                        if (c == '+' || c == '-' || isDigit(c)) {
                            i++;
                            c = content.charAt(i);
                            while (isDigit(c)) {
                                i++;
                                c = content.charAt(i);
                            }
                            if (isLetter(c) || c == '.') {
                                newFile.append("error line " + line + " row " + row + "指数格式错误！\n");
                                state = 0;
                                int temp = i;
                                i = find(i, content);
                                row += (i - temp);

                            } else {
                                end = i;
                                newFile.append("info:13 指数: " + content.substring(begin, end) + '\n');
                            }
                            state = 0;
                        }
                        break;
                    case 14://&&
                        if (c == '&')
                            newFile.append("info:14 '&' :   \n");
                        else {
                            i--;
                            newFile.append("info:14 '&&' : \n");
                        }
                        state = 0;
                        break;
                    case 15://||
                        if (c == '|')
                            newFile.append("info:15 '||': \n");
                        else {
                            i--;
                            newFile.append("info:15 '|':  \n");
                        }
                        state = 0;
                        break;
                    case 16://""
                        newFile.append("info:16 引号 :" + '"' + '\n');
                        i--;
                        state = 0;
                        break;
                }
            }
        }
        newFile.append("分析完成  \r\n");
    }
    /**进行分类*/

    /**
     * 判定是否字母
     */
    boolean isLetter(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_');
    }

    /**
     * 判定是否数字
     */
    boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    /**
     * 判定是否关键字
     */
    boolean isKey(String str) {
        return (
                str.equals("char") || str.equals("double") || str.equals("enum") || str.equals("float")
                        || str.equals("int") || str.equals("long") || str.equals("short") || str.equals("signed")
                        || str.equals("struct") || str.equals("void") || str.equals("unsigned") || str.equals("union")
                        || str.equals("for") || str.equals("do") || str.equals("while") || str.equals("break")
                        || str.equals("continue") || str.equals("if") || str.equals("else") || str.equals("goto")
                        || str.equals("switch") || str.equals("case") || str.equals("default") || str.equals("return")
                        || str.equals("auto") || str.equals("extern") || str.equals("register") || str.equals("static")
                        || str.equals("const") || str.equals("sizeof") || str.equals("typedef") || str.equals("volatile")
                        || str.equals("package") || str.equals("import") || str.equals("public") || str.equals("class")
                        || str.equals("new") || str.equals("implements") || str.equals("this") || str.equals("try") || str.equals("catch")
        );
    }

    /**
     * 寻找分隔符空格、括号、回车
     */
    int find(int begin, String str) {
        if (begin >= str.length())
            return str.length();
        for (int i = begin; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\n' || c == ',' || c == ' ' || c == '\t' || c == '{' || c == '}' || c == '(' || c == ')' || c == ';' || c == '=' || c == '+' || c == '-' || c == '*' || c == '/')
                return i - 1;
        }
        return str.length();
    }

    public Boolean search(String source, String str) {
        return source.contains(str);
    }/**判断是否在原字符串内*/
}
