package com.trxyzng.trung.utility;

import java.lang.reflect.Field;

public final class EmptyObjectUtils {
    public static boolean is_empty(Object obj) {
        Class<?> clas = obj.getClass();
        Field[] fields = clas.getDeclaredFields();
        for(Field f: fields) {
            f.setAccessible(true);
            try {
                if (f.get(obj) != null) {
                    System.out.println("field not null");
                    System.out.println(f);
                    System.out.println(f.get(obj));
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
