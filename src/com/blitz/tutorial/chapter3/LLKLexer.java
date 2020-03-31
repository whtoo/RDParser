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
        while(c != EOF){
            switch (c) {
                case ' ': case '\t': case '\n': case '\r': WS();continue;
                case ',':consume();return new Token(COMMA,",");
                case '[':consume();return new Token(LBRACK,"[");
                case ']':consume();return new Token(RBRACK,"]");
                case '=':consume();return new Token(EQUALS,"=");
                default:
                    if(isLETTER()) return NAME();
                    throw new Error("invalid character: "+c);
            }
        }

        return new Token(EOF_TYPE,"<EOF>");
    }
}
