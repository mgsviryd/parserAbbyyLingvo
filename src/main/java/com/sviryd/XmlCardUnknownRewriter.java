package com.sviryd;

import com.sviryd.config.ExcelColumnCardConfig;
import com.sviryd.entity.Card;
import com.sviryd.entity.UnknownCardPathKeeper;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class XmlCardUnknownRewriter {
    private UnknownCardPathKeeper keeper;
    public void write(int cellNumRedZeroBased, IndexedColors color)throws Exception{
        XmlCardExtractor xmlCardExtractor = new XmlCardExtractor();
        ExcelColumnCardConfig config = ExcelColumnCardConfig.getDefault();
        ExcelCardExtractor excelCardExtractor = new ExcelCardExtractor(config);
        XmlCardWriter xmlCardWriter = new XmlCardWriter();
        List<Card> all = xmlCardExtractor.getCards(keeper.getFilesXml());
        List<Card> unknown = excelCardExtractor.extractByColor(keeper.getFileExcel(), cellNumRedZeroBased, color);
        List<Card> selected = new ArrayList<>();
        for (Card u: unknown) {
            all.stream().filter(x -> Card.compareByWordTranslation.test(x,u)).findFirst().ifPresent(i->{
                Card clone = i.clone();
                i.setExample(u.getExample());
                i.setExampleTranslation(u.getExampleTranslation());
                new CardXmlChanger().adjustExampleTranslationToXml(clone);
                selected.add(clone);
            });
        }
        xmlCardWriter.write(keeper.getFileXml(), selected);
    }
}
