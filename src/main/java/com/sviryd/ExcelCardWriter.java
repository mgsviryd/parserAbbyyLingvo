package com.sviryd;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@AllArgsConstructor
public class ExcelCardWriter {
    private ExcelSheetDimension excelSheetDimension;

    public void write(List<Card> cards, File file, String sheetName) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setColumnWidth(0, excelSheetDimension.getWidthWord());
        sheet.setColumnWidth(1, excelSheetDimension.getWidthTranslation());
        sheet.setColumnWidth(2, excelSheetDimension.getWidthExample());
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
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
    }
}
