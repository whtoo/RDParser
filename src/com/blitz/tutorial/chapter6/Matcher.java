package com.blitz.tutorial.chapter6;

import com.blitz.tutorial.chapter5.ast.AstNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matcher {
    protected Map<String,IRuleApplication> rules;
    protected Integer pos;
    protected Boolean isLexical;
    /**
     * 输入的源文件缓冲
     */
    protected String input;
    /**
     * HashMap allow key or value is null.
     * HashTable dosen't share same behaviour.
     */
    protected Map<Integer, Map<String,Object>> memoTable;

    protected Map<Integer,Map<String, AstNode>> memoASTree;
    public Matcher(Map<String,IRuleApplication> rules) {
        this.rules = rules;
        this.isLexical = false;
    }

    protected Object match(String input) throws Exception {
        this.input = input;
        this.pos = 0;
        this.memoTable = new HashMap<>();
        this.memoASTree = new HashMap<>();

        // DONE cst结构需要修改成tree node
        // 所有的规则都必须从start开始
        if(this.rules == null || this.rules.size() == 0) {
            System.out.println("Please set up rules of application before matching!");
            return null;
        }
        Object cst = new RuleApplicaiton("start",(rule,start,offset,parserTree,rawImage)->{
            return parserTree;
        }).eval(this);
        if(this.pos == this.input.length()){
            return  cst;
        }

        return null;
    }

    protected boolean hasMemorizedResult(String ruleName) {
        Map<String,Object> col = this.memoTable.get(this.pos);
        return col != null && col.get(ruleName) != null;
    }

    protected void memorizeResult(Integer pos,String ruleName, Object cst){
        Map<String, Object> col = this.memoTable.computeIfAbsent(pos, k -> new HashMap<>());
        if(cst != null){
            col.put(ruleName,new HashMap(Map.of("cst",cst,"nextPos",this.pos)));
        } else {
            HashMap<String,Object> emptyResult = new HashMap<>();
            emptyResult.put("cst",null);
            col.put(ruleName,emptyResult);

        }
    }

    protected Object useMemorizedResult(String ruleName){
        /*
          获取当前位置的解析缓存
         */
        Map<String,Object> col = this.memoTable.get(this.pos);
        Map<String,Object> result = (Map<String, Object>) col.get(ruleName);
        if(result.get("cst") != null){
            this.pos = (Integer)result.get("nextPos");
            return result.get("cst");
        }
        return null;
    }

    protected boolean consume(String c){
        if(this.pos == this.input.length()) {
            if(c.charAt(0) == (char)-1){
                return true;
            }
            return false;
        }

        if(this.input.substring(this.pos,this.pos+1).charAt(0) == c.charAt(0)){
            this.pos++;
            return true;
        }
        return false;
    }
    protected Boolean isEOF() {
        return this.pos == this.input.length();
    }
    protected IRuleApplication skipActions() {
        return this.rules.get("skipRule");
    }
}
