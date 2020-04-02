package com.blitz.tutorial.chapter4;

import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Token;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    protected Lexer input;
    protected List<Integer> markers;/// 记录当前解析开始前的向前看缓冲区中的索引值《- 记录现场
    protected List<Token> lookahead;/// 动态线性缓冲
    protected int pos = 0;

    public  Parser(Lexer input) {
        this.input = input;
        this.lookahead = new ArrayList<>(3);
        this.markers = new ArrayList<>(3);
        pos = 0;
        sync(1);
    }

    public  void consume() {
        // 推断成功，向前进一步
        pos++;
        // 已经到缓冲末尾，且没有在进行的推断
        if(pos == lookahead.size() && !isSpeculating()) {
            lookahead.clear();
            pos = 0;
        }
        /// 重新取一个token
        sync(1);
    }

    protected boolean isSpeculating() {
        return markers.size() > 0;
    }

    public void match(int x) {
        if (LA(1) == x) consume();
        else throw new Error("expecting " + input.getTokenName(x)+"; found "+ input.getTokenName(LA(1)));
    }

    /// 预读n个token(注意在pos和i都不变的前提下，反复调用并不会导致lookhead不断增大.)
    public void sync(int i){
        /// 检查是否超过缓冲上界
        if(pos + i - 1 > (lookahead.size() - 1)) {
            int n = (pos+i-1) - (lookahead.size() - 1);
            fill(n);
        }
    }

    public void fill(int n) {
        for(int i = 1; i <=n;i++ ) {
            lookahead.add(input.nextToken());
        }
    }

    public Token LT(int i) {
        sync(i);
        return lookahead.get(pos + i - 1);
    }

    public int LA(int x){
        return LT(x).type;
    }

    public void mark() {
        markers.add(pos);
    }

    public void release() {
        int idx = markers.get(markers.size() - 1);
        markers.remove(markers.size() - 1);
        seek(idx);
    }

    protected void seek(int idx){
        pos = idx;
    }
}
