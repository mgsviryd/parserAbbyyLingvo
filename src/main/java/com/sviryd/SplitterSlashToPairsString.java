package com.sviryd;

import javafx.util.Pair;

public class SplitterSlashToPairsString {
    private static final String SPLITTER = "--";
    private static final String SPLITTER_1 = "—";
    private static final String EMPTY = "";

    public Pair<String, String> getPair(String string) {
        try {
            String[] split = string.split(SPLITTER);
            return new Pair<>(split[0].trim(), split[1].trim());
        } catch (Exception e) {
        }
        try {
            String[] split = string.split(SPLITTER_1);
            return new Pair<>(split[0].trim(), split[1].trim());
        } catch (Exception e) {
        }
        return new Pair<>(string, EMPTY);
    }
}
