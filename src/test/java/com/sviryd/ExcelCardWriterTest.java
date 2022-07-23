package com.sviryd;


import com.sviryd.config.ExcelColumnCardConfig;
import com.sviryd.entity.Card;
import com.sviryd.entity.ExcelSheetDimension;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ExcelCardWriterTest
{
    private ExcelSheetDimension excelSheetDimension;
    private ExcelColumnCardConfig config;
    private ExcelCardWriter excelCardWriter;
    private XmlCardExtractor xmlCardExtractor;
    private File input;
    private File output;
    private String sheetName = "Dic";
    @Before
    public void init(){
        excelSheetDimension = new ExcelSheetDimension(6000, 6000, 12000,12000);
        config = ExcelColumnCardConfig.getDefault();
        excelCardWriter = new ExcelCardWriter(excelSheetDimension, config);
        xmlCardExtractor = new XmlCardExtractor();
        input = new File("./src/main/resources/input.xml");
        output = new File("./src/main/resources/output.xlsx");
    }
    @Test
    public void test() throws Exception {
        List<Card> cards = xmlCardExtractor.getCards(input);
        excelCardWriter.write(cards, output, sheetName);
    }

}
