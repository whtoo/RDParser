package com.blitz.tutorial.chapter5;

import com.blitz.tutorial.chapter4.BackTrackLexer;
import com.blitz.tutorial.chapter4.BackTrackParser;
import com.blitz.tutorial.common.Lexer;

import java.util.HashMap;

public class BackTrackMemoParser extends BackTrackParser {
    /**
     * Pos := Integer : nextPos := Integer,
     *
     */
    protected HashMap<Integer,Integer> memoCSTMap;
    public static Integer FAILED = -1;

    protected void memorize(Integer startTokenIndex,boolean failed) {
        Integer pos = failed ? FAILED : index();
        this.memoCSTMap.put(startTokenIndex,pos);

    }
    public boolean alreadyParsedRule()
            throws PreviousParseFailedException {

        Integer nextPos = memoCSTMap.get(index());

        // Never parse!
        if (nextPos == null) return false;

        // Already parsed!
        int memo = nextPos.intValue();

        System.out.println("parsed list before at index "+index()+
                    "; skip ahead to token index "+memo+": "+
                    lookahead.get(memo).text());

        if (nextPos == FAILED) {
            throw  new PreviousParseFailedException();
        }
        // skip to already parsed index
        seek(memo);

        return true;
    }

    public BackTrackMemoParser(Lexer input) {
        super(input);
        this.memoCSTMap = new HashMap<>(10);
    }

    protected void clearMemo() {
        this.memoCSTMap.clear();
    }

    protected void _list() throws Exception {
        System.out.println("Parsed list rule at token index:"+index());
        match(BackTrackLexer.LBRACK);
        elements();
        match(BackTrackLexer.RBRACK);
    }

    /**
     * list : '[' elements ']'
     */
    @Override
    protected void list() throws Exception {
        // Store enter position for restoring
        boolean falied = false;
        Integer startTokenIndex = index();
        if(isSpeculating() && alreadyParsedRule()) {
            return;
        }
        try {
            _list();
        } catch (RecognitionException re) {
            falied = true;
            throw re;
        } finally {
            if(isSpeculating()) memorize(startTokenIndex,false);
        }
    }

    @Override
    public void consume() {
        pos++;
        if (pos == lookahead.size() && !isSpeculating()) {
            pos = 0;
            lookahead.clear();
            clearMemo();
        }
        sync(1);
    }

    public static void main(String[] args) throws Exception {
        Lexer lexer = new BackTrackLexer(args[0]);

        BackTrackMemoParser parser = new BackTrackMemoParser(lexer);
        parser.stat();
    }
}
