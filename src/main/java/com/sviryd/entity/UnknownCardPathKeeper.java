package com.sviryd.entity;

import com.sviryd.exception.FileIsNotExcelException;
import com.sviryd.exception.FileIsNotXmlException;
import com.sviryd.exception.FileNotExistsException;
import com.sviryd.exception.NullException;
import com.sviryd.util.FileExtensionChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class UnknownCardPathKeeper {
    private FileExtensionChecker checker;
    private List<File> filesXml;
    private File fileExcel;
    private File fileXml;

    public void setFileExcel(File file) throws Exception {
        if (file == null) throw new NullException("Excel file is null.");
        if (!file.exists()) throw new FileNotExistsException(file.getAbsolutePath() + " does not exist.");
        if (!checker.isExcel(file.getName())) throw new FileIsNotExcelException(file.getAbsolutePath() + " is not excel file.");
        fileExcel = file;
    }
    public void setFileXml(File file)throws Exception{
        if (file == null) throw new NullException("Xml file is null.");
        if (!file.exists()) throw new FileNotExistsException(file.getAbsolutePath() + " does not exist.");
        if (!checker.isXml(file.getName())) throw new FileIsNotXmlException(file.getAbsolutePath() + " is not excel file.");
        fileXml = file;
    }
    public void setFilesXml(File file)throws Exception{
        if (file == null) throw new NullException("Xml file is null.");
        if (!file.exists()) throw new FileNotExistsException(file.getAbsolutePath() + " does not exist.");
        if (!checker.isXml(file.getName())) throw new FileIsNotXmlException(file.getAbsolutePath() + " is not excel file.");
        if (!filesXml.contains(file)){
            filesXml.add(file);
        }
    }

    public List<File> getNotXmlFiles() {
        List<File> notXml = new ArrayList<>();
        for (File file:filesXml) {
            if (!checker.isXml(file.getName())){
                notXml.add(file);
            }
        }
        return notXml;
    }
    public boolean isExcelFile() {
        return checker.isExcel(fileExcel.getName());
    }

    public boolean isXMLFile() {
        return checker.isXml(fileXml.getName());
    }
    public boolean isFilesXmlAbsent(){
        return filesXml == null || filesXml.isEmpty();
    }
    public boolean isFileXmlAbsent(){
        return fileXml == null;
    }
    public boolean isFileExcelAbsent(){
        return fileExcel == null;
    }
    public void flushFilesXml(){
        filesXml = new ArrayList<>();
    }
    public void flushFileXml(){
        filesXml = null;
    }
    public void flushFileExcel(){
        fileExcel = null;
    }

    public void removeFromXmlFiles(List<File> notXmlFiles) {
        filesXml.removeAll(notXmlFiles);
    }
}
