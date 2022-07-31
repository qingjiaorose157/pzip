package com.github.qiu.pzip;


import com.sun.istack.internal.Nullable;

import java.util.*;
import java.util.function.Supplier;

public class PZipInstance {
    private final Map<String, Integer> nameIndexMap;
    private final Integer length;
    /**
     * identity for pZip
     */
    private final Long version;


    public PZipInstance(List<String> nameList) {
        this(null, nameList);
    }

    public PZipInstance(@Nullable Supplier<Long> versionSupplier, List<String> nameList) {
        Collections.sort(nameList);
        if (Objects.isNull(versionSupplier)) {
            versionSupplier = () -> Long.valueOf(nameList.hashCode());
        }
        version = versionSupplier.get();
        length = nameList.size();
        nameIndexMap = new HashMap<>(nameList.size(), 3f);
        for (int i = 0; i < nameList.size(); i++) {
            nameIndexMap.put(nameList.get(i), i);
        }
    }

    public long[] zip(List<String> list) {
        long[] words = new long[((length - 1) >> 6) + 2];
        words[0] = version;
        list.forEach(s -> {
            int index = nameIndexMap.get(s);
            words[(index >> 6) + 1] |= 1L << (index % 64);
        });
        return words;
    }

    public boolean accessible(long[] words, String name) throws PZipException{
        if (words[0] != version) {
            throw  new PZipException("version mismatch");
        }
        int index = nameIndexMap.get(name);
        return (words[(index >> 6) + 1] & 1L << (index % 64)) != 0;
    }

    public List<String> listAccessiblePermit(long[] words) throws PZipException {
        List<String> permitList = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : nameIndexMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (accessible(words, key)) {
                permitList.add(key);
            }
        }
        return permitList;
    }
}

