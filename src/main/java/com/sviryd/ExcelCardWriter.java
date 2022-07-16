package com.sviryd;

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
        sheet.setColumnWidth(0, excelSheetDimension.getWidthWord());
        sheet.setColumnWidth(1, excelSheetDimension.getWidthTranslation());
        sheet.setColumnWidth(2, excelSheetDimension.getWidthExample());
        sheet.setColumnWidth(3, excelSheetDimension.getWidthExampleTranslation());
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < cards.size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(cards.get(i).getWord());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(cards.get(i).getTranslation());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(cards.get(i).getExample());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(cards.get(i).getExampleTranslation());
            cell.setCellStyle(style);
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
    }
}
