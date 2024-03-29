package com.sviryd;

import com.sviryd.entity.Card;
import com.sviryd.entity.Meaning;
import com.sviryd.util.LatinCyrillicSplitter;
import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class XmlCardExtractor {
    private static final String CARD = "card";
    private static final String WORD = "word";
    private static final String MEANINGS = "meanings";
    private static final String MEANING = "meaning";
    private static final String TRANSLATIONS = "translations";
    private static final String EXAMPLES = "examples";
    private static final String EXAMPLE = "example";
    private static final String EMPTY = "";
    private static final String EXTENSION_XML = ".xml";
    private LatinCyrillicSplitter splitter = new LatinCyrillicSplitter();

    public List<Card> getCards(List<File>files) throws Exception {
        List<Card> cards = new ArrayList<>();
        for (File file:files) {
            cards.addAll(getCards(file));
        }
        return cards;
    }
    public List<Card> getCards(File file) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file + "doesn't exist.");
        }
        if (!file.getName().endsWith(EXTENSION_XML)){
            throw new IllegalArgumentException("File " + file + "doesn't xml.");
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
            List<Meaning> meanings = getMeanings(card);
            if (meanings.isEmpty()){
                cards.add(new Card (word, EMPTY, EMPTY,EMPTY, card));
            }else {
                for (int i = 0; i < meanings.size(); i++) {
                Meaning m = meanings.get(i);
                    String example = m.getExample();
                    String translation = m.getCombineWords();
                    if (example == null || example == EMPTY){
                        cards.add(new Card(word, translation, EMPTY,EMPTY, getCardWithMeaning(card, i)));
                    }else {
                        Pair<String, String> pair = splitter.getPair(example);
                        cards.add(new Card(word, translation, pair.getKey(),pair.getValue(),getCardWithMeaning(card, i)));
                    }
                }
            }
        }
        return cards;
    }

    private Element getCardWithMeaning(Element card, int iSave) {
        Element copy = card.createCopy();
        Element m = copy.element(MEANINGS);
        List<Element> meanings = m.elements(MEANING);
        Element meaning = meanings.get(iSave);
        for (Element meaning1 : meanings) {
            m.remove(meaning1);
        }
        m.add(meaning);
        return copy;
    }

    private List<Meaning> getMeanings(Element card) {
        List<Meaning> meanings = Collections.emptyList();
        try{
            List<Element> elements = card.element(MEANINGS).elements(MEANING);
            meanings = new ArrayList<>();
            for (Element e: elements) {
                List<Element> elementWords = e.element(TRANSLATIONS).elements(WORD);
                List<String> words = new ArrayList<>();
                if (!elementWords.isEmpty()){
                    for (Element element:elementWords) {
                        String stringValue = element.getStringValue();
                        if (stringValue == null)continue;
                        words.add(stringValue);
                    }
                }
                String example = null;
                Element elementExample = null;
                try {
                    elementExample = e.element(EXAMPLES).element(EXAMPLE);
                } catch (Exception e1) {
                }
                if (elementExample !=null){
                    example = elementExample.getStringValue();
                }
                Meaning meaning = new Meaning();
                meaning.setCombineWords(words);
                meaning.setExample(example);
                meanings.add(meaning);
            }
        } catch (Exception e) {
        }
        return meanings;
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
