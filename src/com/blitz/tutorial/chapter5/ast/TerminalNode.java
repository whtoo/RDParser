package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class TerminalNode extends AstNode{
    public TerminalNode(Token token) {
        super(token,AstNode.LEAFLTYPE);
    }
    public TerminalNode(Token token, int type) {
        super(token, type);
    }

    @Override
    public void eval() {

    }
}
