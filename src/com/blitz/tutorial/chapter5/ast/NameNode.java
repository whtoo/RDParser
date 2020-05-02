package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class NameNode extends AstNode {

    public NameNode(Token token, int type) {
        super(token, type);
    }

    @Override
    public void eval() {

    }
}
