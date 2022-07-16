package com.sviryd;

import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParserAbbyyTutor {
    private static final String CARD = "card";
    private static final String WORD = "word";
    private static final String MEANINGS = "meanings";
    private static final String MEANING = "meaning";
    private static final String TRANSLATIONS = "translations";
    private static final String EXAMPLES = "examples";
    private static final String EXAMPLE = "example";
    private static final String EMPTY = "";
    private SplitterSlashToPairsString splitter = new SplitterSlashToPairsString();

    public List<Card> getCards(File file) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file + "doesn't exist.");
        }
        List<Card> cards = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        for (Iterator<Element> it = root.elementIterator(CARD); it.hasNext(); ) {
            Element card = it.next();
            String word = getWord(card);
            if (word == EMPTY) {
                continue;
            }
            String meaning = getMeaning(card);
            String example = getExample(card);
            if (example == EMPTY){
                cards.add(new Card(word, meaning, EMPTY,EMPTY));
            }else {
                Pair<String, String> pair = splitter.getPair(example);
                cards.add(new Card(word, meaning, pair.getKey(),pair.getValue()));
            }
        }
        return cards;
    }

    private String getExample(Element card) {
        try {
            return card
                    .element(MEANINGS)
                    .element(MEANING)
                    .element(EXAMPLES)
                    .element(EXAMPLE)
                    .getStringValue();
        } catch (Exception e) {
            return EMPTY;
        }
    }

    private String getMeaning(Element card) {
        try {
            return card
                    .element(MEANINGS)
                    .element(MEANING)
                    .element(TRANSLATIONS)
                    .element(WORD)
                    .getStringValue();
        } catch (Exception e) {
            return EMPTY;
        }
    }

    private String getWord(Element card) {
        try {
            return card
                    .element(WORD)
                    .getStringValue();
        } catch (Exception e) {
            return EMPTY;
        }
    }
}
