package com.blitz.tutorial.chapter6;

/**
 * 语法规则推导
 */
public interface IRuleApplication{
    /**
     * 返回解析得到的cst
     * @param matcher
     * @return
     */
    Object eval(Matcher matcher);

    /**
     * 测试是否跳过当前模式
     * @return
     */
    Boolean shouldSkip();
    /**
     *
     * @return
     */
    String ruleName();
}
