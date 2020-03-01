package com.zh.java.stream;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * @Author: he.zhang
 * @Date: 2019/9/29 9:27
 */
public class StreamTest {

    /**
     * 创建Stream
     */
    public static void createStream(){
        //利用Stream.of方法创建流
        Stream<String> stream = Stream.of("hello", "world", "Java8");
        stream.forEach(System.out::println);
        System.out.println("##################");
        //利用Stream.iterate方法创建流

        List<Integer> stream2 = Stream.iterate(10, n -> n + 1)
                .limit(5)
                .collect(Collectors.toList());
        stream2.forEach(System.out::println);
        System.out.println("##################");

        //利用Stream.generate方法创建流
        List<Double> stream3 = Stream.generate(Math::random).limit(5).collect(Collectors.toList());
        stream3.forEach(System.out::println);
        System.out.println("##################");

        //从现有的集合中创建流
        List<String> strings = Arrays.asList("hello", "world", "Java8");
        String string = strings.stream().collect(Collectors.joining(","));
        System.out.println(string);
    }

    /**
     * 字符串转换成流
     */
    public void testString2Stream() {
        //使用codePoints把字符串转成Stream，在使用collect转成字符串
        String s = "hello world Java8".codePoints()
                .collect(StringBuffer::new, StringBuffer::appendCodePoint, StringBuffer::append)
                .toString();
        //使用chars把字符串转成Stream，在使用collect转成字符串
        String s1 = "hello world Java8".chars()
                .collect(StringBuffer::new, StringBuffer::appendCodePoint, StringBuffer::append)
                .toString();
    }

    public void mapTest() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Java5"));
        list.add(new Person("Java6"));
        list.add(new Person("Java7"));

        List<String> strings = list.stream().map(Person::getName).collect(Collectors.toList());
    }

    class Person {
        private String name;

        public Person(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
//        createStream();
        StreamTest t = new StreamTest();
        t.mapTest();


//        List<Double> list = DoubleStream.of(1.0, 2.0, 3.0)
//                .collect(ArrayList<Double>::new, ArrayList::add, ArrayList::addAll);
//        list.forEach(System.out::println);
    }


}
