package com.blitz.tutorial.chapter4;

import com.blitz.tutorial.chapter5.PreviousParseFailedException;
import com.blitz.tutorial.chapter5.ast.*;
import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Token;

/**
 * 单行模式
 */
// stat : list EOF | assign EOF ;
// assign : list '=' list ;
// list : '[' elements ']'
// elements : element (',', element)*
// element : NAME = NAME | NAME | list;

public class BackTrackParser extends Parser {
    AstNode rootNode;
    public BackTrackParser(Lexer input) {
        super(input);
    }

    /**
     * stat : list EOF | assign EOF
     */
    public AstNode stat() throws Exception {
        AstNode statNode = new StatNode(null,AstNode.STATTYPE);
        if (speculate_stat_alt1()) {
            AstNode list = list();
            statNode.addChildNode(list);
            statNode.addChildNode(new TerminalNode(peekLT(1)));
            match(Lexer.EOF_TYPE);
        } else if (speculate_stat_alt2()) {
            AstNode assignNode = assign();
            statNode.addChildNode(assignNode);
            statNode.addChildNode(new TerminalNode(peekLT(1)));
            match(Lexer.EOF_TYPE);
        }
        else {
            throw new Error("expecting stat found " + LT(1));
        }
        return statNode;
    }

    protected boolean speculate_stat_alt1() {
        System.out.println("attempt alternative 1");
        boolean success = true;
        mark();

        try {
            list();
            match(Lexer.EOF_TYPE);
        } catch (Exception ex) {
            System.out.println("Try with alt1 but failed");
            success = false;
        }
        release();


        return success;
    }

    protected boolean speculate_stat_alt2() {
        System.out.println("attempt alternative 2");
        boolean success = true;
        mark();

        try {
            assign();
            match(Lexer.EOF_TYPE);
        } catch (Exception ex) {
            System.out.println("Try with alt2 but failed");
            success = false;
        }
        release();


        return success;
    }

    // list : '[' elements ']'
    protected AstNode list() throws Exception {
        System.out.println("Parse list rule at index"+index());
        AstNode list = new ListNode();
        list.addChildNode(new TerminalNode(peekLT(1)));
        match(BackTrackLexer.LBRACK);

        AstNode elems = elements();
        list.addChildNode(elems);

        list.addChildNode(new TerminalNode(peekLT(1)));
        match(BackTrackLexer.RBRACK);

        return list;
    }

    // assign : list '=' list ;
    protected AstNode assign() throws Exception {
        AstNode lNode = list();
        AstNode equalToken = new TerminalNode(peekLT(1));
        match(BackTrackLexer.EQUALS);
        AstNode rNode = list();
        AstNode currentNode = new AssignNode();
        currentNode.addChildNode(lNode);
        currentNode.addChildNode(equalToken);
        currentNode.addChildNode(rNode);
        return currentNode;
    }

    // elements : element (',', element)*
    protected AstNode elements() throws Exception {
        AstNode elems = new ElementsNode(null,AstNode.ElEMENTSTYPE);
        AstNode fNode = element();
        elems.addChildNode(fNode);
        while (LA(1) == BackTrackLexer.COMMA) {
            elems.addChildNode(new TerminalNode(peekLT(1),AstNode.LEAFLTYPE));
            match(BackTrackLexer.COMMA);
            AstNode sNode = element();
            elems.addChildNode(sNode);
        }

        return elems;
    }

    // element : NAME = NAME | NAME | list;
    protected AstNode element() throws Exception {
        AstNode ele = new ElementNode(null,AstNode.ElEMENTTEPE);
        if(LA(1) == BackTrackLexer.NAME && LA(2) == BackTrackLexer.EQUALS) {
            AstNode lNode = new TerminalNode(this.peekLT(1));
            match(BackTrackLexer.NAME);
            AstNode eqNode = new TerminalNode(this.peekLT(1));
            match(BackTrackLexer.EQUALS);
            AstNode rNode = new TerminalNode(this.peekLT(1));
            match(BackTrackLexer.NAME);
            ele.addChildNode(lNode);
            ele.addChildNode(eqNode);
            ele.addChildNode(rNode);
        } else if(LA(1) == BackTrackLexer.NAME) {
            AstNode nameNode = new TerminalNode(this.peekLT(1));
            match(BackTrackLexer.NAME);
            ele.addChildNode(nameNode);
        } else if(LA(1) == BackTrackLexer.LBRACK) {
            AstNode list = list();
            ele.addChildNode(list);
        } else {
            throw new Error("expecting element found "+ input.getTokenName(LA(1)));
        }
        return ele;
    }

    public static void main(String[] args) throws Exception {
        Lexer lexer = new BackTrackLexer(args[0]);
        System.out.println(args[0]);
        BackTrackParser parser = new BackTrackParser(lexer);
        AstNode parserTree = parser.stat();
        System.out.print(parserTree);
    }
}
