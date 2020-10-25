package com.siwuxie095.functional.chapter8th.example5th;

/**
 * @author Jiajing Li
 * @date 2020-10-25 19:35:36
 */
@SuppressWarnings("all")
public class Client {

    public static void main(String[] args) {
        classBasedExample();
        lambdaBasedExample();
    }

    /**
     * 使用类的方式构建用户代码
     */
    private static void classBasedExample() {
        Moon moon = new Moon();
        moon.startSpying(new Nasa());
        moon.startSpying(new Aliens());

        moon.land("An asteroid");
        moon.land("Apollo 11");
    }

    /**
     * 使用 Lambda 表达式构建用户代码
     */
    private static void lambdaBasedExample() {
        Moon moon = new Moon();

        moon.startSpying(name -> {
            if (name.contains("Apollo")) {
                System.out.println("We made it!");
            }
        });

        moon.startSpying(name -> {
            if (name.contains("Apollo")) {
                System.out.println("They're distracted, lets invade earth!");
            }
        });

        moon.land("An asteroid");
        moon.land("Apollo 11");
    }

}
