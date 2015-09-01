package com.chinazmob.unlockearn.common;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 晓宇 on 2015/3/17.
 */
public class StringUtils {

    public static String convertListToString(List<String> list){
        StringBuilder retStr = new StringBuilder();
        Iterator<String> iter = list.iterator();
        while(iter.hasNext()){
            retStr.append(iter.next());
        }
        return retStr.toString();
    }
}
