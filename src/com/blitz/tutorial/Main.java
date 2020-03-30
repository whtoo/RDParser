package com.blitz.tutorial;

import com.blitz.tutorial.chapter1.ProtoZeroParser;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ProtoZeroParser parser = new ProtoZeroParser("return x + 1;");
        try {
            System.out.println(parser.parse());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
