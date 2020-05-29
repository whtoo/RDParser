package com.blitz.tutorial.chapter7;

import com.blitz.tutorial.chapter6.IRuleApplication;
import com.blitz.tutorial.chapter6.Matcher;

public class IncrementalRuleApplication implements IRuleApplication {
    @Override
    public Object eval(Matcher matcher) {
        return null;
    }

    /**
     * 测试是否跳过当前模式
     *
     * @return
     */
    @Override
    public Boolean shouldSkip() {
        return false;
    }

    @Override
    public String ruleName() {
        return null;
    }
}
