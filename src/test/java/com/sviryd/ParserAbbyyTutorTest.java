package com.sviryd;


import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ParserAbbyyTutorTest
{
    private ParserAbbyyTutor parserAbbyyTutor;
    private File file;
    @Before
    public void init(){
        parserAbbyyTutor = new ParserAbbyyTutor();
        file = new File("D:\\java\\projects\\parserAbbyyTutor\\src\\main\\resources\\input.xml");
    }
    @Test
    public void test() throws Exception {
        List<Card> cards = parserAbbyyTutor.getCards(file);
        System.out.println(cards);
    }

}
