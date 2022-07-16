package com.sviryd;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SplitterSlashToPairsStringTest {
    private SplitterSlashToPairsString splitterSlashToPairsString;
    private String text;
    private String first;
    private String last;

    @Before
    public void init() {
        splitterSlashToPairsString = new SplitterSlashToPairsString();
        text = "word -- слово";
        first = "word";
        last = "слово";
    }

    @Test
    public void split() {
        Pair<String, String> pair = splitterSlashToPairsString.getPair(text);
        System.out.println(pair);
        Assert.assertTrue(pair.getKey().equals(first));
        Assert.assertTrue(pair.getValue().equals(last));
    }
}