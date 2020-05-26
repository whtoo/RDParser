package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class AssignNode extends AstNode {
    AstNode left;
    AstNode right;
    AstNode operator;

    public AssignNode() {
        super(null, AstNode.ASSIGNTYPE);
    }

    @Override
    public void eval() {

    }
}
