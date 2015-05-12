1.识别一个给定的源文件:
BeatModel.java
标识符：去掉另外三类
关键字：import,public,class,implements,new,int,void,this,return,for,if,try,catch
常数：数字
运算符：+,-,*,/,!,<,>,=

2.正规文法转换为正规表达式
2.1 左右线性文法互换
split["::="]
split["|"]
设开始符号为S,非终结符为其余大写字母,终结符为小写字母

