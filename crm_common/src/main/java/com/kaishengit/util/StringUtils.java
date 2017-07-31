package com.kaishengit.util;

import java.io.UnsupportedEncodingException;

public class StringUtils {

    public static String IsoToUtf(String str) {
        if(str == null || "".equals(str.trim())) {
            return null;
        }
        try {
            return new String(str.getBytes("ISO8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(str + "转码异常");
        }
    }
}
