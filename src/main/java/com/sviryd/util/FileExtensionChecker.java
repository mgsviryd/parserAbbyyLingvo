package com.sviryd.util;

public class FileExtensionChecker {
    private static String EXCEL_SUFFIX = ".xlsx";
    private static String XML_SUFFIX = ".xml";
    public boolean isExcel(String name) {
        return name != null && name.endsWith(EXCEL_SUFFIX);
    }

    public boolean isXml(String name) {
        return name != null && name.endsWith(XML_SUFFIX);
    }
}
