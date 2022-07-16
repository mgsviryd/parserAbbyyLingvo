package com.sviryd;


import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ExcelCardWriterTest
{
    private ExcelSheetDimension excelSheetDimension;
    private ExcelCardWriter excelCardWriter;
    private ParserAbbyyTutor parserAbbyyTutor;
    private File input;
    private File output;
    private String sheetName = "Dic";
    @Before
    public void init(){
        excelSheetDimension = new ExcelSheetDimension(6000, 6000, 12000,12000);
        excelCardWriter = new ExcelCardWriter(excelSheetDimension);
        parserAbbyyTutor = new ParserAbbyyTutor();
        input = new File("./src/main/resources/input.xml");
        output = new File("./src/main/resources/output.xlsx");
    }
    @Test
    public void test() throws Exception {
        List<Card> cards = parserAbbyyTutor.getCards(input);
        excelCardWriter.write(cards, output, sheetName);
    }

}
