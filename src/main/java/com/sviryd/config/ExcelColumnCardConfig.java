package com.sviryd.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExcelColumnCardConfig {
    private int word;
    private int translation;
    private int empty;
    private int example;
    private int exampleTranslation;

    public static ExcelColumnCardConfig getDefault(){
        return new ExcelColumnCardConfig(0,1,2,3,4);
    }
}
