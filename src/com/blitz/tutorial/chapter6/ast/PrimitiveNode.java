package com.blitz.tutorial.chapter6.ast;

import com.blitz.tutorial.chapter6.tokens.AToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有基础数据类型的ast wrapper
 */
public class PrimitiveNode extends ASTNode {
    public PrimitiveNode(AToken token) {
        super(token, ASTEnum.PRIMITIVE);
    }
}
