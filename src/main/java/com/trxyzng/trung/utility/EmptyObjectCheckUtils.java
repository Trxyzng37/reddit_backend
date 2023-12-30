package com.trxyzng.trung.utility;

import java.lang.reflect.Field;

public final class EmptyObjectCheckUtils {
    public static boolean is_empty(Object obj) {
        Class<?> clas = obj.getClass();
        Field[] fields = clas.getDeclaredFields();
        for(Field f: fields) {
            f.setAccessible(true);
            try {
                if (f.get(obj) != null) {
                    System.out.println("field not null");
                    return false;
                }
            }
            catch (IllegalAccessException e) {
                continue;
            }
        }
        return true;
    }
}
