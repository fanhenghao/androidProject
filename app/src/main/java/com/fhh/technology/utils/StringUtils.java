package com.fhh.technology.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanhenghao
 */
public class StringUtils {

    public static String splitString(String text) {
        String s = "";
        if (text.contains("高温")) {
            s = text.replace("高温", "");
        }
        if (text.contains("低温")) {
            s = text.replace("低温", "");
        }
        return s;
    }

}
