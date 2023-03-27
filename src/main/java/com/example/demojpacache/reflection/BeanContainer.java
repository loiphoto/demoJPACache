package com.example.demojpacache.reflection;

import com.example.demojpacache.reflection.BeanCustom;
import com.example.demojpacache.reflection.MyAnnotation;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class BeanContainer {
    private static Map<String, Object> beans = new HashMap<>();

    static {
        System.out.println("--- Put beans--");

        Reflections reflections = new Reflections("com.example.demojpacache");
        Set<Class<?>> clazzs = reflections.getTypesAnnotatedWith(BeanCustom.class);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (Class clazz : clazzs) {
            executorService.execute(() -> {
                Field[] declaredFields = clazz.getDeclaredFields();
                String simpleName = formatCamleCase(clazz.getSimpleName());
                System.out.println("----simple name " + simpleName);
                if (declaredFields.length == 0) {
                    try {
                        Object newBean = clazz.getDeclaredConstructor().newInstance();
                        beans.put(simpleName, newBean);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                List<Object> values = new LinkedList<>();
                List<Class> types = new LinkedList<>();


                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(MyAnnotation.class)) {
                        String fieldName = field.getName();
                        System.out.println(fieldName);
                        while (beans.get(fieldName) == null) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        Object bean = beans.get(fieldName);
                        values.add(bean);
                        types.add(field.getType());
                        System.out.println(values);
                        field.setAccessible(true);
                    }
                }

                Class[] parameterTypes = new Class[types.size()];

                int i = 0;
                for (Class type : types) {
                    parameterTypes[i++] = type;
                }

                try {
                    Object object = clazz.getConstructor(parameterTypes).newInstance(values.toArray());
                    beans.put(simpleName, object);
                    System.out.println(beans);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static String formatCamleCase(String text) {
        boolean shouldConvertNextCharToLower = true;
        char delimiter = ' ';
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar == delimiter) {
                shouldConvertNextCharToLower = false;
            } else if (shouldConvertNextCharToLower) {
                builder.append(Character.toLowerCase(currentChar));
            } else {
                builder.append(Character.toUpperCase(currentChar));
                shouldConvertNextCharToLower = true;
            }
        }
        return builder.toString();
    }
}
