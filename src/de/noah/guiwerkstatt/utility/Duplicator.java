package de.noah.guiwerkstatt.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Duplicator {

    public static Object duplicate(Object object) {
        Object newObject = null;

        try {
            Class<?> clazz = object.getClass();
            newObject = clazz.newInstance();

            while (clazz != null) {
                for (int f = 0; f < clazz.getDeclaredFields().length; f++) {
                    Field field = clazz.getDeclaredFields()[f];

                    if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    field.setAccessible(true);
                    field.set(newObject, field.get(object));
                }

                clazz = clazz.getSuperclass();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newObject;
    }

}