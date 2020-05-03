package com.blitz.tutorial.chapter3;

import com.blitz.tutorial.chapter2.ListLexer;
import com.blitz.tutorial.common.Token;

public class LLKLexer extends ListLexer {
    public static int EQUALS = 6;

    public LLKLexer(String input) {
        super(input);
    }

    @Override
    public Token nextToken() {
        int startPos = this.p;
        while(c != EOF){
            switch (c) {
                case ' ': case '\t': case '\n': case '\r': WS();continue;
                case ',':consume();return new Token(COMMA,",",startPos,p);
                case '[':consume();return new Token(LBRACK,"[",startPos,p);
                case ']':consume();return new Token(RBRACK,"]",startPos,p);
                case '=':consume();return new Token(EQUALS,"=",startPos,p);
                default:
                    if(isLETTER()) return NAME(startPos);
                    throw new Error("invalid character: "+c);
            }
        }

        return new Token(EOF_TYPE,"<EOF>");
    }
}
