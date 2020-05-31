package com.blitz.tutorial.chapter6;

import com.blitz.stone.ast.ASTList;
import com.blitz.stone.ast.ASTree;
import com.blitz.tutorial.chapter5.ast.AstNode;

import java.util.List;

@FunctionalInterface
public interface IReduction {
    public Object reduceParser(RuleApplicaiton rule, int startPos, int offset, Object cst);
}
