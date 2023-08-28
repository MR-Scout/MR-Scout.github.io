package com.mr.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    private String fieldName = "fieldName";

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldName2() {
        return fieldName;
    }

    public static void main(String[] args) {
        System.out.println( "Hello World!" );
        System.out.println( SinX(Math.PI/2.0)  );

        String a = "ssssss";
        String b = "ssssss";
        System.out.println(a==b);
        System.out.println( a.equals(b) );

        List<String> a1 = new ArrayList<>( Arrays.asList("1234","234") );
        List<String> a2 = new ArrayList<>( Arrays.asList("1234","234") );
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a1==a2);
        System.out.println( a1.equals(a2) );


        // byte[] data = new byte[10];
        // Arrays.fill(data, (byte) 1);
        // byte[] data = {2,3,5,8};
        // Arrays.fill(data, (byte) 1);

    }

    public static double SinX(double a) {
        return Math.sin(a);
    }

    public static double CosX(double a) {
        return Math.cos(a);
    }

    public static boolean testB(int a, int c, int b) {
        return true;
    }
}
