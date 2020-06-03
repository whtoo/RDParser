package com.blitz.tutorial.chapter6.ast;

import com.blitz.tutorial.chapter6.tokens.AToken;

import java.util.List;

public abstract class ASTNode {
    List<ASTNode> children;
    ASTEnum type;
    AToken token;

    public ASTNode(AToken token,ASTEnum type){
        this.token = token;
        this.type = type;
    }

}
