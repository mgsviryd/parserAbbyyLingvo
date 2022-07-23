package com.sviryd.util;

import javafx.util.Pair;

public class LatinCyrillicSplitter {
    private static final String SUFFIX_1 = "—";
    private static final String SUFFIX_2 = "--";
    private static final String SUFFIX_3 = "-";
    private static final String EMPTY = "";

    public Pair<String, String> getPair(String string) {
        String first = string;
        String last = EMPTY;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (isCyrillic(c)){
                first = string.substring(0,i).trim();
                first = removeSuffixesAndTrim(first, SUFFIX_1, SUFFIX_2, SUFFIX_3);
                last = string.substring(i,string.length()).trim();
                break;
            }
        }
        return new Pair<>(first,last);
    }

    private String removeSuffixesAndTrim(String first, String ...splitters) {
        for (String splitter: splitters) {
            if (first != null && first.endsWith(splitter)){
                first = first.substring(0,first.length()-splitter.length()).trim();
            }
        }
        return first;
    }

    boolean isCyrillic(char c) {
        return Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(c));
    }
}
