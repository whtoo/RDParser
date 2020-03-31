package com.blitz.tutorial.chapter2;

import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Token;

public class ListLexer extends Lexer {
    public static int NAME = 2;
    public static int COMMA = 3;
    public static int LBRACK = 4;
    public static int RBRACK = 5;
    public static String[] tokenNames =
            {"n/a","<EOF>","NAME","COMMA","LBRACK","RBRACK"};

    public ListLexer(String input) {
        super(input);
    }

    @Override
    public String getTokenName(int tokenType) {
        return tokenNames[tokenType];
    }

    @Override
    public Token nextToken() {
        while(c != EOF){
            switch (c) {
                case ' ': case '\t': case '\n': case '\r': WS();continue;
                case ',':consume();return new Token(COMMA,",");
                case '[':consume();return new Token(LBRACK,"[");
                case ']':consume();return new Token(RBRACK,"]");
                default:
                    if(isLETTER()) return NAME();
                    throw new Error("invalid character: "+c);
            }
        }

        return new Token(EOF_TYPE,"<EOF>");
    }


    protected Token NAME() {
        StringBuilder buf = new StringBuilder();
        do { buf.append(c);consume();} while (isLETTER());
        return new Token(NAME,buf.toString());
    }

    protected Boolean isLETTER() { return c >= 'a' && c <= 'z' || c>= 'A' && c<='Z';}
}
