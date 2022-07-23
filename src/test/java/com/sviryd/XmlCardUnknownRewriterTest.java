package com.sviryd;

import com.sviryd.entity.UnknownCardPathKeeper;
import com.sviryd.util.FileExtensionChecker;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlCardUnknownRewriterTest {
    private static int CELL_NUMBER_BASED_ZERO = 2;
    private static IndexedColors RED = IndexedColors.RED;

    private List<File> filesXml;
    private File fileExcel;
    private File fileXml;
    private FileExtensionChecker checker;
    private UnknownCardPathKeeper keeper;
    private XmlCardUnknownRewriter rewriter;

    @Before
    public void init() {
        filesXml = new ArrayList<>();
        for (File file: new File("./src/main/resources/TutorDict").listFiles()) {
            filesXml.add(file);
        }
        fileExcel = new File("./src/main/resources/unknown.xlsx");
        fileXml = new File("./src/main/resources/unknown.xml");
        checker = new FileExtensionChecker();
        keeper = new UnknownCardPathKeeper(checker,
                filesXml,
                fileExcel,
                fileXml);
        rewriter = new XmlCardUnknownRewriter(keeper);
    }

    @Test
    public void rewrite() throws Exception {
        rewriter.write(CELL_NUMBER_BASED_ZERO, RED);
    }
}