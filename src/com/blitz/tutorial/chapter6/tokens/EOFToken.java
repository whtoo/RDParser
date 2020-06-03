package com.blitz.tutorial.chapter6.tokens;

import com.blitz.tutorial.chapter6.TokenEnum;
import com.blitz.tutorial.chapter6.tokens.AToken;

public class EOFToken  extends AToken {
    public EOFToken() {
        super(String.valueOf((char)-1), TokenEnum.EOF);
    }

    @Override
    public Boolean test() {
        return null;
    }
}
