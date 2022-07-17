package com.sviryd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Meaning implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SPLITTER = "; ";
    private static final String EMPTY = "";
    private String combineWords = EMPTY;
    private String example;
    public void setCombineWords(List<String> words){
        if (words == null || words.isEmpty()) return;
        if (combineWords == EMPTY){
            combineWords = words.get(0);
            for (int i = 1; i < words.size(); i++) {
                combineWords = combineWords + SPLITTER + words.get(i);
            }
        }else {
            for (String word: words) {
                combineWords = combineWords + SPLITTER + word;
            }
        }
    }
}
