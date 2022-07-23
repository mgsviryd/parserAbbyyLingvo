package com.sviryd.entity;

import lombok.*;
import org.dom4j.Element;

import java.io.Serializable;
import java.util.function.BiPredicate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Card implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    public static BiPredicate<Card, Card> compareByWordTranslation = (x, y) -> x.getWord().equals(y.getWord()) && x.getTranslation().equals(y.getTranslation());
    private String word;
    private String translation;
    private String example;
    private String exampleTranslation;
    private Element xml;

    public Card clone() {
        return new Card()
                .builder()
                .word(word)
                .translation(translation)
                .example(example)
                .exampleTranslation(exampleTranslation)
                .xml(xml.createCopy())
                .build();
    }

    public String getExampleAndTranslation() {
        return example + " — " + exampleTranslation;
    }
}
