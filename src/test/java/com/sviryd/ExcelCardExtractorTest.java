package com.sviryd;

import com.sviryd.config.ExcelColumnCardConfig;
import com.sviryd.entity.Card;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ExcelCardExtractorTest {
    private static int CELL_NUMBER_BASED_ZERO = 2;
    private static IndexedColors RED = IndexedColors.RED;

    private ExcelCardExtractor extractor;
    private File file;

    @Before
    public void init() {
        extractor = new ExcelCardExtractor(ExcelColumnCardConfig.getDefault());
        file = new File("./src/main/resources/vocabulary.xlsx");
    }

    @Test
    public void extract() throws Exception {
        List<Card> cards = extractor.extractByColor(
                file,
                CELL_NUMBER_BASED_ZERO,
                RED
        );
        System.out.println(cards);
    }

}