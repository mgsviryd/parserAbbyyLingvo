package com.sviryd;

import com.sviryd.entity.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class XmlCardWriter {
    private static String XML_0 = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n" +
            "<dictionary formatVersion=\"5\" title=\"";
    private static String XML_1 = "\" sourceLanguageId=\"1033\" destinationLanguageId=\"1049\" nextWordId=\"316\" targetNamespace=\"http://www.abbyy.com/TutorDictionary\" soundfile=\"SoundEn.lsa\">\n" +
            "\t<statistics readyMeaningsQuantity=\"";
    private static String XML_2 = "\" activeMeaningsQuantity=\"2\" />";
    private static String XML_3 = "</dictionary>";
    private static String SPACE = "&#32;";

    public void write(File file, List<Card> cards)throws Exception{
        try(PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.append(XML_0);
            writer.append(transformSpaces(file.getName()));
            writer.append(XML_1);
            writer.append(Integer.toString(cards.size()));
            writer.append(XML_2);
            cards.forEach(x->writer.append(x.getXml().asXML()));
            writer.append(XML_3);
        } catch (Exception e){
            throw new RuntimeException("Something wrong with writing into "+file.getAbsolutePath());
        }
    }

    private String transformSpaces(String name) {
        StringBuilder sb = new StringBuilder();
        for (char c: name.toCharArray()) {
            if (Character.isSpaceChar(c)){
                sb.append(SPACE);
            }else
                sb.append(c);
        }
        return sb.toString();
    }
}
