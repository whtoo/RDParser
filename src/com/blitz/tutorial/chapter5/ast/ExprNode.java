package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class ExprNode extends AstNode {
    public ExprNode(Token token, int type) {
        super(token, type);
    }

    @Override
    public void eval() {

    }
}
