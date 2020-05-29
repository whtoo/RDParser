package com.blitz.tutorial.chapter5.ast;

import com.blitz.tutorial.common.Token;

import java.lang.annotation.ElementType;
import java.util.ArrayList;

public abstract class AstNode {
    Token token;
    String synVal;
    int type;
    ArrayList<AstNode> childNodes;
    /**
     * 定义4中不同的节点类型
     */
    public static int STATTYPE = 0x1000;
    public static int LISTTYPE = 0x1001;
    public static int ASSIGNTYPE = 0x1002;
    public static int ElEMENTSTYPE = 0x1003;
    public static int ElEMENTTEPE = 0x1004;
    public static int LEAFLTYPE = 0x1006;
    public static int EOFTYPE = 0x1009;

    public AstNode(Token token,int type){
        this.token = token;
        this.type = type;
        this.childNodes = new ArrayList<>(3);
    }

    // TODO 定义计算综合属性synVal的值
    public abstract void eval();

    public void addChildNode(AstNode childNode) {
        this.childNodes.add(childNode);
    }

    /**
     *
     * @param type 节点文法类型
     */
    public void removeChildByType(int type) {
        int foundIdx = -1;
        for (int i = 0; i < this.childNodes.size(); i++) {
            AstNode astNode = this.childNodes.get(i);
            if(astNode.type == type) {
                foundIdx = i;
                break;
            }
        }

        if(foundIdx == -1) return;

        this.removeChildByIndex(foundIdx);
    }

    public void removeChildByIndex(int idx){
        this.childNodes.remove(idx);
    }
    public String typeName() {
        if(type == ASSIGNTYPE) {
            return "AssingNode";
        } else if(type == LISTTYPE) {
            return "ListNode";
        }
        else if(type == STATTYPE){
            return "StatNode";
        } else if(type == ElEMENTTEPE){
            return "ElementNode";
        } else if(type == ElEMENTSTYPE) {
            return "ElementsNode";
        } else if(type == LEAFLTYPE) {
            return "TerminalNode";
        } else {
            return "UnanmedNode";
        }

    }

    @Override
    public String toString() {
        return "AstNode{" +
                "token=" + token+
                '}';
    }
}
