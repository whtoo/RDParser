package com.blitz.tutorial.chapter6.ast;

import java.util.ArrayList;
import java.util.List;

public class ChoiceNode extends ASTNode{
    public ChoiceNode(List<ASTNode> children) {
        super(null, ASTEnum.REPETITION);
        this.children = new ArrayList<>(children.size());
        this.children.addAll(children);
    }
}
