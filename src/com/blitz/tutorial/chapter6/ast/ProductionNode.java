package com.blitz.tutorial.chapter6.ast;

import com.blitz.tutorial.chapter6.tokens.AToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A -> aBC...X
 */
public class ProductionNode extends ASTNode {
    public ProductionNode(AToken token, List<ASTNode> nodes) {
        super(token, ASTEnum.PRODUCTION);
        this.children = new ArrayList<ASTNode>();
        this.children.addAll(nodes);
    }
}
