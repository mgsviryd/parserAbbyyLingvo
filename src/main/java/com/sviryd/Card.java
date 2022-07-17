package com.sviryd;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private String word;
    private String translation;
    private String example;
    private String exampleTranslation;
}
