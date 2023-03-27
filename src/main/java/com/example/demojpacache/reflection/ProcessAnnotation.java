//package com.example.demojpacache.reflection;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Arrays;
//
//@Component
//@AllArgsConstructor
//public class ProcessAnnotation {
//
//    private ApplicationContext applicationContext;
//
//    public void injectBean() throws IllegalAccessException, InvocationTargetException, InstantiationException {
//        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//        for (String beanName : allBeanNames) {
//            Object objectBean = applicationContext.getBean(beanName);
//            Class<?> clazz = objectBean.getClass();
//
//            //Inject by name
//
//            for (Field field : clazz.getDeclaredFields()) {
//                if (field.isAnnotationPresent(MyAnnotation.class)) {
//                    System.out.println("Before ~~" + objectBean);
//                    String fieldName = field.getName();
//                    System.out.println(fieldName);
//                    boolean isExistBean = Arrays.stream(allBeanNames).anyMatch(s -> s.equals(fieldName));
//                    if (!isExistBean) {
//                        continue;
//                    }
//                    Object beanInject = applicationContext.getBean(fieldName);
//                    System.out.println(beanInject);
//                    field.setAccessible(true);
//                    field.set(objectBean, beanInject);
//                    System.out.println("After ~~" + objectBean);
//                }
//            }
//
//            //inject constructor
//            Constructor<?>[] constructors = clazz.getConstructors();
//            for (Constructor constructor : constructors) {
//                if (constructor.isAnnotationPresent(BeanCustom.class)) {
//                    Class[] parameterTypes = constructor.getParameterTypes();
//                    for (Class classOfParam : parameterTypes) {
//                        String className = classOfParam.getSimpleName();
//                        String classNameFormat = formatCamleCase(className);
//                        boolean isExistBean = Arrays.stream(allBeanNames).anyMatch(s -> s.equals(classNameFormat));
//                        if (!isExistBean) {
//                            continue;
//                        }
//                        System.out.println(applicationContext.getBean(classNameFormat));
//                    }
//                    System.out.println(objectBean);
//                }
//            }
//        }
//    }
//
//    private String formatCamleCase(String text) {
//        boolean shouldConvertNextCharToLower = true;
//        char delimiter = ' ';
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < text.length(); i++) {
//            char currentChar = text.charAt(i);
//            if (currentChar == delimiter) {
//                shouldConvertNextCharToLower = false;
//            } else if (shouldConvertNextCharToLower) {
//                builder.append(Character.toLowerCase(currentChar));
//            } else {
//                builder.append(Character.toUpperCase(currentChar));
//                shouldConvertNextCharToLower = true;
//            }
//        }
//        return builder.toString();
//    }
//
//    public void createBean() {
//
////        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
////        for (String beanName : allBeanNames) {
////            Object objectBean = applicationContext.getBean(beanName);
////            Class<?> clazz = objectBean.getClass();
////
////            //Inject by name
////
////            for (Field field : clazz.getDeclaredFields()) {
////                if (field.isAnnotationPresent(MyAnnotation.class)) {
////                    System.out.println("Before ~~" + objectBean);
////                    String fieldName = field.getName();
////                    System.out.println(fieldName);
////                    boolean isExistBean = Arrays.stream(allBeanNames).anyMatch(s -> s.equals(fieldName));
////                    if (!isExistBean) {
////                        continue;
////                    }
////                    Object beanInject = applicationContext.getBean(fieldName);
////                    System.out.println(beanInject);
////                    field.setAccessible(true);
////                    field.set(objectBean, beanInject);
////                    System.out.println("After ~~" + objectBean);
////                }
////            }
////        }
//    }
//}
