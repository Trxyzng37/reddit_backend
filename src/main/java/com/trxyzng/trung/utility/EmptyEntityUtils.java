package com.trxyzng.trung.utility;

import java.lang.reflect.Field;
import java.time.Instant;

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
                            System.out.println("int is null");
                            continue;
                        }
                        else {
                            return false;
                        }
                    case "String":
                        if (f.get(obj) == null) {
                            System.out.println("String is null");
                        }
                        else {
                            return false;
                        }
                    case "Instant":
                        if (f.get(obj).equals(Instant.parse("1111-11-11T11:11:11.11Z"))) {
                            System.out.println("Instant is null");
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
        System.out.println("--> Empty entity");
        return true;
    }
}
