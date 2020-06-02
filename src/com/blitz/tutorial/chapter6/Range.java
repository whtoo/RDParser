package com.blitz.tutorial.chapter6;

public class Range implements IRuleApplication {
    String start;
    String end;

    public Range(String start,String end){
        this.start = start;
        this.end = end;
    }

    @Override
    public Object eval(Matcher matcher) {
        for (char i = this.start.charAt(0); i <= this.end.charAt(0); i++) {
            String c = String.valueOf(i);
            if(matcher.consume(c)){
                return c;
            }
        }
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

    @Override
    public String toString() {
        return "<"+this.start+","+this.end+">";
    }
}
