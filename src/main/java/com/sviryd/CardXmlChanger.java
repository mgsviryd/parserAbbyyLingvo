package com.sviryd;

import com.sviryd.entity.Card;
import org.dom4j.Element;

import java.util.List;

public class CardXmlChanger {
    private static final String WORD = "word";
    private static final String MEANINGS = "meanings";
    private static final String MEANING = "meaning";
    private static final String EXAMPLES = "examples";
    private static final String EXAMPLE = "example";

    public void adjustExampleTranslationToXml(Card card) {
        Element xml = card.getXml().createCopy();
        List<Element> meanings = xml.element(MEANINGS).elements(MEANING);
        for (int i = 1; i < meanings.size(); i++) {
            xml.remove(meanings.get(i));
        }
        Element meaning = meanings.get(0);
        Element examples = meaning.element(EXAMPLES);
        meaning.remove(examples);
        meaning.addElement(EXAMPLES);
        meaning.element(EXAMPLES).addElement(EXAMPLE);
        meaning.element(EXAMPLES).element(EXAMPLE).setText(card.getExampleAndTranslation());
        card.setXml(xml);
    }
}
