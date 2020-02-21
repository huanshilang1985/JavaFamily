package com.zh.java.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: he.zhang
 * @Date: 2019/9/26 10:35
 *
 * 泛型，上界
 */
public class GenericTest2 {

    private <T> void test(List<? super T> dst, List<T> src){
        for (T t : src) {
            dst.add(t);
        }
    }

    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        List<Animal> animals = new ArrayList<>();
        new GenericTest2().test(animals,dogs);
    }

}
