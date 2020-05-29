package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class NumberNode extends AstNode {

    public NumberNode(Token token,int type) {
        super(token,type);
    }

    public NumberNode(Token token,int type,int start,int end) {
        super(token,type);
        token.setSrcPos(start,end);
    }

    @Override
    public void eval() {

    }
}
