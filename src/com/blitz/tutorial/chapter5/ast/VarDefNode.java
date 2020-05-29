package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

public class VarDefNode extends AstNode{

    public VarDefNode(Token token, int type) {
        super(token, type);
    }

    public VarDefNode(Token token,int type,int start,int end) {
        super(token,type);
        token.setSrcPos(start,end);
    }

    @Override
    public void eval() {

    }


}
