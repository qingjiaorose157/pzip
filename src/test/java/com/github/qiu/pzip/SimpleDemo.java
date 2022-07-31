package com.github.qiu.pzip;


import java.util.Arrays;

public class SimpleDemo {
    public static void main(String[] args) throws Exception{

        /**
         * a simple demo to record permit on crud
         */
        PZipInstance pZipInstance = new PZipInstance(Arrays.asList("create", "retrieve", "update", "delete"));
        long[] words = pZipInstance.zip(Arrays.asList("create", "retrieve", "update"));
        System.out.println("words: " + Arrays.toString(words));
        System.out.println("Have permit on create   ? " + pZipInstance.accessible(words, "create"));
        System.out.println("Have permit on retrieve ? " + pZipInstance.accessible(words, "retrieve"));
        System.out.println("Have permit on update   ? " + pZipInstance.accessible(words, "update"));
        System.out.println("Have permit on delete   ? " + pZipInstance.accessible(words, "delete"));
    }
}
