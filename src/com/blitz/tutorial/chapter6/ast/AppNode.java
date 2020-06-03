package com.blitz.tutorial.chapter6.ast;

import com.blitz.tutorial.chapter6.tokens.AToken;

public class AppNode extends ASTNode {
    public AppNode(AToken token) {
        super(token, ASTEnum.APPLICATION);
    }
}
