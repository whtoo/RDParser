package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.chapter6.TokenEnum;
import com.blitz.tutorial.common.Token;

public class IdentifierNode extends AstNode {

    public IdentifierNode(Token token, int type) {
        super(token, type);
    }

    public IdentifierNode(Token token) {
        super(token, AstNode.LEAFLTYPE);
    }

    @Override
    public void eval() {

    }
}
