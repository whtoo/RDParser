package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class AssignNode extends AstNode {
    public AssignNode(Token token, int type) {
        super(token, type);
    }

    @Override
    public void eval() {

    }
}
