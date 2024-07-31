package com.example.duantotnghiep.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class RemoveDiacritics {

    public static String removeDiacritics(String input) {
        input = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(input).replaceAll("");
    }
}
