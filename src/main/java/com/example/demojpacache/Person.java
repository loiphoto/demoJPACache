package com.example.demojpacache;

import com.example.demojpacache.reflection.BeanCustom;
import com.example.demojpacache.reflection.MyAnnotation;
import lombok.Data;

import java.time.LocalDate;

@BeanCustom
@Data
public class Person {
    private String name;
    private int age;
    private int gender;
    private LocalDate birthday;
    private String address;

    @MyAnnotation
    private Car car;

    public Person(String address) {
        this.address = address;
    }

    public Person(String name, int age, int gender, LocalDate birthday, String address, Car car) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.car = car;
    }

    public Person(Car car) {
        this.car = car;
    }
}

