package com.sviryd.application;

import com.sviryd.util.FileExtensionChecker;
import com.sviryd.entity.UnknownCardPathKeeper;
import com.sviryd.XmlCardUnknownRewriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelToXmlUnknownWordsDictionaryBuilderApplication extends Application {
    private static final String DONE = "DONE";
    private static final String ABORT = "ABORT";
    private static final String IN_PROGRESS = "IN PROGRESS";
    private static final String EMPTY = "";
    private static final String YOU_CAN_CHOOSE_ONLY_ONE_FILE = "You can choose only one file.";
    private static final String SELECT_XML_FILE = "Select XML file";
    private static final String SELECT_XML_FILES = "Select XML files";
    private static final String SELECT_EXCEL_FILE = "Select Excel file";
    private static final String IS_NOT_XML_FILE = " is not XML file.";
    private static final String IS_NOT_EXCEL_FILE = " It is not Excel file.";
    private static final String NOT_XML_FILES_WERE_DELETED = "Not xml files were deleted.";
    private static final String USE_CONVERT_AGAIN = "Use button CONVERT again.";
    private static IndexedColors RED = IndexedColors.RED;
    private static int CELL_NUMBER_BASED_ZERO = 2;

    private Desktop desktop = Desktop.getDesktop();
    private FileExtensionChecker checker = new FileExtensionChecker();
    private UnknownCardPathKeeper keeper = new UnknownCardPathKeeper(checker, new ArrayList<>(), null, null);

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final FileChooser fileChooser = new FileChooser();

        TextArea textAreaXmls = new TextArea();
        textAreaXmls.setMinHeight(30);

        TextArea textAreaExcel = new TextArea();
        textAreaExcel.setMinHeight(30);

        TextArea textAreaXml = new TextArea();
        textAreaXml.setMinHeight(30);

        TextArea textAreaStatus = new TextArea();
        textAreaExcel.setMinHeight(10);

        Button buttonXMLs = new Button("Select XMLs");
        Button buttonFlushXMLs = new Button("Flush XMLs");
        Button buttonExcel = new Button("Select Excel");
        Button buttonXml = new Button("Select XML");
        Button buttonConvert = new Button("CONVERT");
        Button buttonOpenExcel = new Button("OPEN EXCEL");
//        buttonConvert.setStyle("-fx-background-color: #00ff00");


        buttonXMLs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
                if (files == null) return;
                addXmlFiles(files, textAreaXmls, fileChooser, textAreaStatus);
                printLog(textAreaXmls, keeper.getFilesXml());
            }
        });

        buttonFlushXMLs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                keeper.flushFilesXml();
                printLog(textAreaXmls, EMPTY);
            }
        });

        buttonExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setInitialDirectoryForFile(fileChooser, keeper.getFileExcel());
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file == null) return;
                if (file.isDirectory()) {
                    printLog(textAreaStatus, YOU_CAN_CHOOSE_ONLY_ONE_FILE);
                } else {
                    setExcelFile(file, textAreaStatus);
                    printLog(textAreaExcel, file);
                }
            }
        });

        buttonXml.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setInitialDirectoryForFile(fileChooser, keeper.getFileXml());
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file == null) return;
                if (file.isDirectory()) {
                    printLog(textAreaStatus, YOU_CAN_CHOOSE_ONLY_ONE_FILE);
                } else {
                    setXmlFile(file, textAreaStatus);
                    printLog(textAreaXml, file);
                }
            }
        });

        buttonConvert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printLog(textAreaStatus, IN_PROGRESS + "\n");
                boolean absent = checkAbsenceAndLog(textAreaStatus);
                if (absent) return;
                boolean wrong = checkExtensionAndLog(textAreaStatus);
                if (wrong) return;
                try {
                    XmlCardUnknownRewriter rewriter = new XmlCardUnknownRewriter(keeper);
                    rewriter.write(CELL_NUMBER_BASED_ZERO, RED);
                    printLog(textAreaStatus, DONE);
                } catch (Exception e) {
                    printLog(textAreaStatus, ABORT);
                }

            }
        });
        buttonOpenExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (keeper.getFileExcel() != null && keeper.getFileExcel().exists()) {
                    openFile(keeper.getFileExcel(), textAreaStatus);
                }
            }
        });

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);

        root.getChildren().addAll(
                textAreaXmls,
                buttonXMLs,
                buttonFlushXMLs,
                textAreaExcel,
                buttonExcel,
                textAreaXml,
                buttonXml,
                buttonConvert,
                textAreaStatus,
                buttonOpenExcel
        );

        Scene scene = new Scene(root, 400, 500);

        primaryStage.setTitle("Abbyy Tutor: Unknown excel words to xml");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addXmlFiles(List<File> files, TextArea textAreaXmls, FileChooser fileChooser, TextArea textAreaStatus) {
        for (File file : files) {
            if (file != null) {
                if (file.isDirectory()) {
                    File[] a = file.listFiles();
                    if (a != null && a.length != 0) {
                        addXmlFiles(Arrays.asList(a), textAreaXmls, fileChooser, textAreaStatus);
                    }
                } else {
                    addXmlFile(file, textAreaXmls);
                    setInitialDirectoryForFile(fileChooser, file);
                }
            }
        }
    }

    private boolean checkExtensionAndLog(TextArea textAreaStatus) {
        boolean wrong = false;
        List<File> notXmlFiles = keeper.getNotXmlFiles();
        if (!notXmlFiles.isEmpty()) {
            for (File file : notXmlFiles) {
                appendLog(textAreaStatus, file.getAbsolutePath() + IS_NOT_XML_FILE + "\n");
            }
            keeper.removeFromXmlFiles(notXmlFiles);
            appendLog(textAreaStatus, NOT_XML_FILES_WERE_DELETED + "\n");
            appendLog(textAreaStatus, USE_CONVERT_AGAIN + "\n");
            wrong = true;
        }
        if (!keeper.isXMLFile()) {
            appendLog(textAreaStatus, IS_NOT_XML_FILE + "\n");
            keeper.flushFileXml();
            wrong = true;
        }
        if (!keeper.isExcelFile()) {
            appendLog(textAreaStatus, IS_NOT_EXCEL_FILE + "\n");
            keeper.flushFileExcel();
            wrong = true;
        }
        return wrong;
    }

    private boolean checkAbsenceAndLog(TextArea textAreaStatus) {
        boolean absent = false;
        if (keeper.isFilesXmlAbsent()) {
            absent = true;
            appendLog(textAreaStatus, SELECT_XML_FILES + "\n");
        } else if (keeper.isFileXmlAbsent()) {
            absent = true;
            appendLog(textAreaStatus, SELECT_XML_FILE + "\n");
        } else if (keeper.isFileExcelAbsent()) {
            absent = true;
            appendLog(textAreaStatus, SELECT_EXCEL_FILE + "\n");
        }
        return absent;
    }

    private void setXmlFile(File file, TextArea textAreaStatus) {
        try {
            keeper.setFileXml(file);
            printLog(textAreaStatus, EMPTY);
        } catch (Exception e) {
            printLog(textAreaStatus, e.getMessage());
        }
    }

    private void addXmlFile(File file, TextArea textAreaStatus) {
        try {
            keeper.setFilesXml(file);
            printLog(textAreaStatus, EMPTY);
        } catch (Exception e) {
            printLog(textAreaStatus, e.getMessage());
        }
    }

    private void setExcelFile(File file, TextArea textAreaStatus) {
        try {
            keeper.setFileExcel(file);
            printLog(textAreaStatus, EMPTY);

        } catch (Exception e) {
            printLog(textAreaStatus, e.getMessage());
        }
    }

    private void setInitialDirectoryForFile(FileChooser fileChooser, File file) {
        if (file != null) {
            if (file.exists() || file.getParentFile().exists()) {
                fileChooser.setInitialDirectory(
                        file.getParentFile()
                );
                return;
            }
        }
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")
                ));
    }

    private void setInitialDirectoryForDirectory(FileChooser fileChooser, File file) {
        if (file != null) {
            if (file.exists()) {
                fileChooser.setInitialDirectory(
                        file
                );
                return;
            }
        }
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")
                ));
    }

    private void printLog(TextArea textArea, File file) {
        if (file == null) {
            return;
        }
        textArea.clear();
        textArea.appendText(file.getAbsolutePath() + "\n");

    }

    private void printLog(TextArea textArea, List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        textArea.clear();
        for (File file : files) {
            textArea.appendText(file.getAbsolutePath() + "\n");
        }
    }

    private void printLog(TextArea textArea, String message) {
        textArea.clear();
        textArea.appendText(message);
    }

    private void appendLog(TextArea textArea, String message) {
        textArea.appendText(message);
    }

    private void openFile(File file, TextArea textAreaStatus) {
        try {
            this.desktop.open(file);
        } catch (IOException e) {
            printLog(textAreaStatus, e.getMessage());
        }
    }

}
