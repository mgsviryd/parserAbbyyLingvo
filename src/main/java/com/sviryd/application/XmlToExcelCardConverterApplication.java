package com.sviryd.application;

import com.sviryd.*;
import com.sviryd.config.ExcelColumnCardConfig;
import com.sviryd.entity.Card;
import com.sviryd.entity.ExcelSheetDimension;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlToExcelCardConverterApplication extends Application {
    private static final String DONE = "DONE";
    private static final String ABORT = "ABORT";
    private static final String IN_PROGRESS = "IN PROGRESS";
    private static final String EMPTY = "";
    private static final String YOU_CAN_CHOOSE_ONLY_ONE_FILE = "You can choose only one file.";
    private static final String SELECT_XML_FILE = "Select XML file";
    private static final String SELECT_EXCEL_FILE = "Select Excel file";
    private static final String IS_NOT_XML_FILE = "is not XML file";
    private static final String IS_NOT_EXCEL_FILE = "is not Excel file";
    private static final String SHEET_NAME = "Dic";

    private Desktop desktop = Desktop.getDesktop();
    private File fileXML = null;
    private File fileExcel = null;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        final FileChooser fileChooser = new FileChooser();

        TextArea textAreaXML = new TextArea();
        textAreaXML.setMinHeight(30);

        TextArea textAreaExcel = new TextArea();
        textAreaExcel.setMinHeight(30);

        TextArea textAreaStatus = new TextArea();
        textAreaExcel.setMinHeight(10);

        Button buttonXML = new Button("Select XML");
        Button buttonExcel = new Button("Select Excel");
        Button buttonConvert = new Button("CONVERT");
        Button buttonOpenExcel = new Button("OPEN EXCEL");
//        buttonConvert.setStyle("-fx-background-color: #00ff00");


        buttonXML.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setInitialDirectory(fileChooser, fileXML);
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file == null) return;
                if (file.isDirectory()) {
                    printLog(textAreaStatus, YOU_CAN_CHOOSE_ONLY_ONE_FILE);
                } else {
                    printLog(textAreaXML, file);
                    printLog(textAreaStatus, EMPTY);
                    fileXML = file;
                }
            }
        });

        buttonExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setInitialDirectory(fileChooser, fileExcel);
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file == null) return;
                if (file.isDirectory()) {
                    printLog(textAreaStatus, YOU_CAN_CHOOSE_ONLY_ONE_FILE);
                } else {
                    printLog(textAreaExcel, file);
                    printLog(textAreaStatus, EMPTY);
                    fileExcel = file;
                }
            }
        });

        buttonConvert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printLog(textAreaStatus, IN_PROGRESS);
                if (fileXML == null && fileExcel == null) {
                    printLog(textAreaStatus, SELECT_XML_FILE + "\n" + SELECT_EXCEL_FILE);
                    return;
                } else if (fileExcel == null) {
                    printLog(textAreaStatus, SELECT_EXCEL_FILE);
                    return;
                } else if (fileXML == null) {
                    printLog(textAreaStatus, SELECT_XML_FILE);
                    return;
                }
                if (isNotXMLFile(fileXML) && isNotExcelFile(fileExcel)) {
                    printLog(textAreaStatus, fileXML + ": " + IS_NOT_XML_FILE + "\n" + fileXML + ": " + IS_NOT_EXCEL_FILE);
                    fileXML = null;
                    fileExcel = null;
                    return;
                }
                if (isNotXMLFile(fileXML)) {
                    printLog(textAreaStatus, fileXML + ": " + IS_NOT_XML_FILE);
                    fileXML = null;
                    return;
                }
                if (isNotExcelFile(fileExcel)) {
                    printLog(textAreaStatus, fileXML + ": " + IS_NOT_EXCEL_FILE);
                    fileExcel = null;
                    return;
                }
                try {
                    XmlCardExtractor cardExtractor = new XmlCardExtractor();
                    ExcelCardWriter excelCardWriter = new ExcelCardWriter(new ExcelSheetDimension(6000, 6000, 12000, 12000), ExcelColumnCardConfig.getDefault());
                    List<Card> cards = cardExtractor.getCards(fileXML);
                    excelCardWriter.write(cards, fileExcel, SHEET_NAME);
                    printLog(textAreaStatus, DONE);
                } catch (Exception e) {
                    printLog(textAreaStatus, ABORT);
                }

            }
        });
        buttonOpenExcel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (fileExcel!= null && fileExcel.exists()){
                    openFile(fileExcel);
                }
            }
        });

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);

        root.getChildren().addAll(
                textAreaXML,
                buttonXML,
                textAreaExcel,
                buttonExcel,
                buttonConvert,
                textAreaStatus,
                buttonOpenExcel
        );

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Abbyy Tutor: XML to Excel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setInitialDirectory(FileChooser fileChooser, File file) {
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

    private boolean isNotExcelFile(File fileExcel) {
        return !fileExcel.getName().endsWith(".xlsx");
    }

    private boolean isNotXMLFile(File fileXML) {
        return !fileXML.getName().endsWith(".xml");
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

    private void printLog(TextArea textArea, File file) {
        if (file == null) {
            return;
        }
        textArea.clear();
        textArea.appendText(file.getAbsolutePath() + "\n");

    }

    private void printLog(TextArea textArea, String message) {
        textArea.clear();
        textArea.appendText(message);
    }

    private void openFile(File file) {
        try {
            this.desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}