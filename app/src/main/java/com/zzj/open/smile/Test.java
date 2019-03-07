package com.zzj.open.smile;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.crypto.KeyGenerator;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/1/16 10:19
 * @desc :
 * @version: 1.0
 */
public class Test {


    public static void main(String[] args){

        B b = new B();
        b.setName("22222");
        Adapter adapter = new Adapter(b);

       System.out.print("5555555555555555"+ adapter.getName());
    }

    static class Adapter{
        A a;
        public Adapter(A member){
            a = (A) member;
        }
        public String getName(){
           return a.getName();
        }
    }

    interface A {

        String getName();

    }


    static class B implements A{
        String name;

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getName() {

            return this.name;
        }
    }

    class C implements A{

        String name ;
        @Override
        public String getName() {
            this.name = name;
            return name;
        }
    }

    /**
     * 学习集合的stream()操作
     */
    @SuppressLint("NewApi")
    private static void testStream(){
        List<Integer> list = Arrays.asList(1, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9);
        //过滤
        List<Integer> filter = list.stream().filter(integer  -> integer >3).collect(Collectors.toList());
        System.out.println(filter.toString());
        //去重
        List<Integer> distinct = list.stream().filter(integer -> integer>3).distinct().collect(Collectors.toList());
        System.out.println(distinct.toString());
        //限制
        List<Integer> limit = list.stream().filter(integer -> integer>3).distinct().limit(3).collect(Collectors.toList());
        System.out.println(limit.toString());
        //跳过
        List<Integer> skip = list.stream().filter(integer -> integer>3).distinct().skip(3).collect(Collectors.toList());
        System.out.println(skip.toString());
        //映射
        List<String> maps = list.stream().filter(integer -> integer>3).distinct().map(integer -> integer+"-").collect(Collectors.toList());
        System.out.println(maps.toString());
        //查找
        Optional optional = list.stream().filter(integer -> integer>4).findAny();

        System.out.println(optional.orElse(4));

        boolean allMatch = list.stream().allMatch(integer -> integer<10);
        boolean noneMatch = list.stream().noneMatch(integer -> integer>10);
        boolean anyMatch = list.stream().anyMatch(integer -> integer>8);
        System.out.println(allMatch);
        System.out.println(noneMatch);
        System.out.println(anyMatch);

        List<String> stringList = Arrays.asList("a", "b", "c", "d", "e", "f");
        String reduce = stringList.stream().reduce("-", new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                System.out.println("reduce"+"---s--->"+s+"----s2---->"+s2);
                return s+s2;
            }
        });
        System.out.println(reduce);
    }
}
