package com.sousou.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCheck {
    /**
     * 借助正则表达式，确认字符串能否安全强转的方法
     *
     * @param regex   正则表达式
     * @param orginal 待验证字符串
     * @return 返回true，匹配规则
     */
    public static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    //正整数验证
    public static boolean isPInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    //负整数验证
    public static boolean isNInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    //整数验证
    public static boolean isInteger(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPInteger(orginal) || isNInteger(orginal);
    }

    //正浮点数
    public static boolean isPDouble(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    //负浮点数
    public static boolean isNDouble(String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    //浮点数
    public static boolean isDouble(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    //实数
    public static boolean isNumber(String orginal) {
        return isInteger(orginal) || isDouble(orginal);
    }
}
