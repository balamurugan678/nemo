package com.novacroft.nemo.common.comparator;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String string1, String string2) {
        return (string1.toUpperCase()).compareTo(string2.toUpperCase());
    }

}
