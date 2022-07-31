package com.github.qiu.pzip;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ModNDemo {
    /**
     * a demo to record number(from 0 to 9999) can de divide by 3
     */
    public static void main(String[] args) {

        /**
         * init all permit
         */
        List<String> allPermitList = new LinkedList<>();
        for (int i = 0; i < 10_000; i++) {
            allPermitList.add("mod" + i);
        }

        PZipInstance instance = new PZipInstance(allPermitList);

        /**
         * init accessible permit for one account
         */
        long number = 2615456415454000000L;
        List<String> modList = new LinkedList<>();
        modList.add("mod0");
        for (long i = 1; i < 10_000; i++) {
            if (0 == number % i) {
                modList.add("mod" + i);
            }
        }

        /**
         * zip accessible permit for one account
         */
        long[] words = instance.zip(modList);

        List<String> accessibleList = instance.listAccessiblePermit(words);
        System.out.println(number + " mod n = 0, " + Arrays.toString(accessibleList.toArray()));
    }
}
