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
    public static final int COUNT = 3;
    private String word;
    private String translation;
    private String example;
}
