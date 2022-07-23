package com.sviryd;

import com.sviryd.config.ExcelColumnCardConfig;
import com.sviryd.entity.Card;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class ExcelCardExtractor {
    private ExcelColumnCardConfig config;

    public List<Card> extractByColor(File file, int cellNumZeroBased, IndexedColors color) throws Exception{
        try(FileInputStream fip = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fip)){
            List<Card> cards = new ArrayList<>();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                List<Card> sheetCards = extractFromSheet(sheet,cellNumZeroBased, color);
                cards.addAll(sheetCards);
            }
            return cards;
        }catch (Exception e){
            throw new IllegalArgumentException("Something wrong with "+file.getAbsolutePath());
        }
    }

    private List<Card> extractFromSheet(XSSFSheet sheet, int cellNumZeroBased, IndexedColors color) {
        List<Card> cards = new ArrayList<>();
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum()+1; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;
            XSSFCell cell = row.getCell(cellNumZeroBased);
            if (cell == null) continue;
            XSSFCellStyle cellStyle = cell.getCellStyle();
            if (cellStyle == null) continue;
            XSSFColor fillForegroundXSSFColor = cellStyle.getFillForegroundXSSFColor();
            if (fillForegroundXSSFColor == null) continue;
            if (Arrays.equals((new XSSFColor(color, null)).getRGB(), fillForegroundXSSFColor.getRGB())){
                Card card = null;
                try {
                    card = getCard(row);
                } catch (Exception e) {
                    continue;
                }
                cards.add(card);
            }
        }
        return cards;
    }

    private Card getCard(XSSFRow row) {
        return new Card(
                getCellOrException(row,config.getWord()),
                getCellOrEmptyValue(row,config.getTranslation()),
                getCellOrEmptyValue(row,config.getExample()),
                getCellOrEmptyValue(row, config.getExampleTranslation()),
                null
        );
    }

    private String getCellOrEmptyValue(XSSFRow row, int index) {
        try {
            return row.getCell(index).getStringCellValue();
        } catch (Exception e) {
            return Strings.EMPTY;
        }
    }

    private String getCellOrException(XSSFRow row, int index) {
            return row.getCell(index).getStringCellValue();
    }
}
