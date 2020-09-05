package com.mengxuegu.security;

import org.junit.Test;

import java.util.Arrays;

public class TestOne {

    @Test
    public void testAsciiSort() {
        System.out.println(ASCIISort("bca"));
    }

    // 字符串ASCII码排序
    private String ASCIISort(String str) {
        char[] test = new char[str.length()];
        StringBuilder sb = new StringBuilder();
        while (true) {
            String a = str;//直接读取这行当中的字符串。
            for (int i = 0; i < str.length(); i++) {
                //字符串处理每次读取一位。
                test[i] = a.charAt(i);
                System.out.println(test[i] + "=====" + Integer.valueOf(test[i]));
            }
            Arrays.sort(test);
            for (int i = 0; i < test.length; i++) {
                sb.append(test[i]);
            }
            String trim = sb.toString().trim();
            return trim;
        }
    }
}
