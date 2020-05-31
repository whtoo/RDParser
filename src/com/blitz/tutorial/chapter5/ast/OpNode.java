package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class OpNode extends AstNode {
    AstNode left;
    AstNode right;

    OpNode(Token operator, AstNode left, AstNode right){
        super(operator,AstNode.ElEMENTTEPE);
        this.left = left;
        this.right = right;
    }
    @Override
    public void eval() {

    }

}
