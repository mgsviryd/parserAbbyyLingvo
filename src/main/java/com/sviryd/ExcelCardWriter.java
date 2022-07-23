package com.sviryd;

import com.sviryd.config.ExcelColumnCardConfig;
import com.sviryd.entity.Card;
import com.sviryd.entity.ExcelSheetDimension;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@AllArgsConstructor
public class ExcelCardWriter {
    private ExcelSheetDimension excelSheetDimension;
    private ExcelColumnCardConfig config;

    public void write(List<Card> cards, File file, String sheetName) throws Exception {
        FileInputStream fip = new FileInputStream(file);
        XSSFWorkbook workbook = null;
        try{
            workbook = new XSSFWorkbook(fip);
        }catch (Exception e){
            fip.close();
            workbook = new XSSFWorkbook();
        }
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if (sheetIndex != -1){
            workbook.removeSheetAt(sheetIndex);
        }
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setColumnWidth(config.getWord(), excelSheetDimension.getWidthWord());
        sheet.setColumnWidth(config.getTranslation(), excelSheetDimension.getWidthTranslation());
        sheet.setColumnWidth(config.getEmpty(), excelSheetDimension.getWidthWord());
        sheet.setColumnWidth(config.getExample(), excelSheetDimension.getWidthExample());
        sheet.setColumnWidth(config.getExampleTranslation(), excelSheetDimension.getWidthExampleTranslation());
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < cards.size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(config.getWord());
            cell.setCellValue(cards.get(i).getWord());
            cell.setCellStyle(style);
            cell = row.createCell(config.getTranslation());
            cell.setCellValue(cards.get(i).getTranslation());
            cell.setCellStyle(style);
            cell = row.createCell(config.getEmpty());
            cell.setCellStyle(style);
            cell = row.createCell(config.getExample());
            cell.setCellValue(cards.get(i).getExample());
            cell.setCellStyle(style);
            cell = row.createCell(config.getExampleTranslation());
            cell.setCellValue(cards.get(i).getExampleTranslation());
            cell.setCellStyle(style);
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
    }
}
