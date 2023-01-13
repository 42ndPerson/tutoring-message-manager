package com.fariseducation.Data;

import java.util.function.Function;

public class StringManager {
    public static String listify(String[] parts) {
        String sum = "";

        if(parts.length==1) sum += parts[0];
        else if(parts.length==2) sum += parts[0] + " and " + parts[1];
        else {
            for(int i = 0; i<parts.length; i++) {
                sum += parts[i] + ", ";
                if((parts.length-i)==2) sum += "and ";
            }
        }

        return sum;
    }

    public static String listify(Object[] parts, Function<Object,String> s) {
        String[] strings = new String[parts.length];

        for(int i = 0; i<parts.length; i++) {
            strings[i] = s.apply(strings[i]);
        }

        return listify(strings);
    }
}
