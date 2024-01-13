package com.trxyzng.trung.utility;

import java.lang.reflect.Field;

public class EmptyEntityUtils {
    public static boolean isEmptyEntity(Object obj) {
        Class<?> clas = obj.getClass();
        Field[] fields = clas.getDeclaredFields();
        System.out.println("Check empty entity");
        for(Field f: fields) {
            f.setAccessible(true);
            String field_type = f.getType().getSimpleName();
            try {
                switch (field_type) {
                    case "int":
                        if (f.get(obj).equals(0)) {
                            System.out.println("Equal 0");
                            continue;
                        }
                        else {
                            return false;
                        }
                    case "String":
                        if (f.get(obj) == null) {
                            System.out.println("equal null");
                        }
                        else {
                            return false;
                        }
                }
            }
            catch (IllegalAccessException e) {
                return false;
            }
        }
        System.out.println("Empty entity");
        return true;
    }
}
