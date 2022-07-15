package com.sviryd;


import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ParserAbbyyLingvoTest
{
    private ParserAbbyyLingvo parserAbbyyLingvo;
    private File file;
    @Before
    public void init(){
        parserAbbyyLingvo = new ParserAbbyyLingvo();
        file = new File("D:\\java\\projects\\parserAbbyyTutor\\src\\main\\resources\\input.xml");
    }
    @Test
    public void test() throws Exception {
        List<Card> cards = parserAbbyyLingvo.getCards(file);
        System.out.println(cards);
    }

}
