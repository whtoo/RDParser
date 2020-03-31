package com.blitz.tutorial.common;

public abstract class Lexer {
    public static final char EOF = (char)-1; // EOF字符，即文件的结尾
    public static final int EOF_TYPE= 1;// 表示EOF的词法类型
    protected int p = 0;
    protected String mInput;
    protected char c;

    public Lexer(String input) {
        this.mInput = input;
        c = mInput.charAt(p);
    }

    public abstract Token nextToken();
    public abstract String getTokenName(int tokenType);

    /**
     * 向前移动一个字符，检测输入是否结束
     */
    public void consume() {
        p++;
        if (p >= mInput.length()) c = EOF;
        else c = mInput.charAt(p);
    }

    /**
     * 确保x是输入流中的下一个字符
     */
     public void match(char x) {
         if (c == x) consume();
         else throw new Error("expecting "+ x + "; found "+ c);
     }

    /**
     * WS : (' '|'\n'|'\t'|'\r')*
     */
    public void WS() {
        while(c == ' ' || c == '\n' || c == '\r' || c == '\t') consume();
    }

}
