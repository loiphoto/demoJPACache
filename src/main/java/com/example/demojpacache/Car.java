package com.example.demojpacache;

import com.example.demojpacache.reflection.BeanCustom;

@BeanCustom
public class Car {
    private String color = "Đỏ";

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                '}';
    }
}
