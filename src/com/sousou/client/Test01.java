package com.sousou.client;

public class Test01 {
    public static void main(String[] args) {
        String a = "16511dad";
        String b = Hash.sha1(a);
        System.out.println(b);

    }
}
