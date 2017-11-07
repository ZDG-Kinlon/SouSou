package com.sousou.client;



public class Test01 {

    public static void main(String [] args) {

        double a=10.1651;
        double b= round(a);
        double c=b;
    }
    private static double round(double num){
        return (int)(num*100)/100.0;
    }
}
