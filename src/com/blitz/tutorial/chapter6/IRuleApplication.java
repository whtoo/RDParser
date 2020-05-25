package com.blitz.tutorial.chapter6;

/**
 * 语法规则推导
 */
public interface IRuleApplication{
    Object eval(Matcher matcher);
    String ruleName();
}
