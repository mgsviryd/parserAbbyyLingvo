package com.sviryd;


import com.sviryd.entity.Card;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class XmlCardExtractorTest
{
    private XmlCardExtractor xmlCardExtractor;
    private File file;
    @Before
    public void init(){
        xmlCardExtractor = new XmlCardExtractor();
        file = new File(("./src/main/resources/input.xml"));
    }
    @Test
    public void test() throws Exception {
        List<Card> cards = xmlCardExtractor.getCards(file);
        System.out.println(cards);
    }

}
