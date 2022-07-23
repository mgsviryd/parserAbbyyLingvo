package com.sviryd;

import com.sviryd.entity.Card;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class XmlCardWriter {
    private static String XML_0 = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n" +
            "<dictionary formatVersion=\"5\" title=\"";
    private static String XML_1 = "\" sourceLanguageId=\"1033\" destinationLanguageId=\"1049\" nextWordId=\"316\" targetNamespace=\"http://www.abbyy.com/TutorDictionary\" soundfile=\"SoundEn.lsa\">\n" +
            "\t<statistics readyMeaningsQuantity=\"";
    private static String XML_2 = "\" activeMeaningsQuantity=\"2\" />";
    private static String XML_3 = "</dictionary>";
    private static String SPACE = "&#32;";

    private static Charset CHARSET = Charset.forName("UTF-16");

    public void write(File file, List<Card> cards)throws Exception{
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), CHARSET))) {
            String text = write(file.getName(), cards);
            writer.write(text);
        } catch (Exception e){
            throw new RuntimeException("Something wrong with writing into "+file.getAbsolutePath());
        }
    }

    private String write(String dictName, List<Card> cards) {
        StringBuilder sb = new StringBuilder();
        sb.append(XML_0);
        sb.append(FilenameUtils.getBaseName(dictName));
        sb.append(XML_1);
        sb.append(Integer.toString(cards.size()));
        sb.append(XML_2);
        sb.append("\n");
        cards.forEach(x->{sb.append(x.getXml().asXML());
            sb.append("\n");});
        sb.append(XML_3);
        return sb.toString();
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
