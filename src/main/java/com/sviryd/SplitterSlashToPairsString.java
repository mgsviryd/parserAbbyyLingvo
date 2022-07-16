package com.sviryd;

import javafx.util.Pair;

public class SplitterSlashToPairsString {
    private static final String REGEX_SPLITTER = "(-|—|-,—)$";
    private static final String SPLITTER_1 = "—";
    private static final String SPLITTER_2 = "--";
    private static final String SPLITTER_3 = "-";
    private static final String EMPTY = "";

    public Pair<String, String> getPair(String string) {
//        try {
//            String[] split = string.split(SPLITTER);
//            return new Pair<>(split[0].trim(), split[1].trim());
//        } catch (Exception e) {
//        }
//        try {
//            String[] split = string.split(SPLITTER_1);
//            return new Pair<>(split[0].trim(), split[1].trim());
//        } catch (Exception e) {
//        }
//        return new Pair<>(string, EMPTY);
        String first = string;
        String last = EMPTY;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (isCyrillic(c)){
                first = string.substring(0,i).trim();
                first = removeSplittersAndTrim(first, SPLITTER_1,SPLITTER_2,SPLITTER_3);
                last = string.substring(i,string.length()).trim();
                break;
            }
        }
        return new Pair<>(first,last);
    }

    private String removeSplittersAndTrim(String first, String ...splitters) {
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
