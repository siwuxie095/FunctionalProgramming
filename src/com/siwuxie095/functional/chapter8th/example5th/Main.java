package com.siwuxie095.functional.chapter8th.example5th;

/**
 * @author Jiajing Li
 * @date 2020-10-25 19:29:56
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 观察者模式
     *
     * 观察者模式是另一种可被 Lambda 表达式简化和改进的行为模式。在观察者模式中，被观察者持有一个观察者列表。当被观察者的
     * 状态发生改变，会通知观察者。观察者模式被大量应用于基于 MVC 的 GUI 工具中，以此让模型状态发生变化时，自动刷新视图模
     * 块，达到二者之间的解耦。
     *
     * 观看 GUI 模块自动刷新有点枯燥，这里要观察的对象是月球！NASA 和外星人都对登陆到月球上的东西感兴趣，都希望可以记录这
     * 些信息。NASA 希望确保阿波罗号上的航天员成功登月；而外星人则希望在 NASA 注意力分散之时进犯地球。
     *
     * 先来定义观察者的 API，这里将观察者称作 LandingObserver。它只有一个 observeLanding 方法，当有东西登陆到月球上时
     * 会调用该方法。
     *
     * LandingObserver 接口定义如下：
     *
     * public interface LandingObserver {
     *
     *     public void observeLanding(String name);
     *
     * }
     *
     * 被观察者是月球 Moon，它持有一组 LandingObserver 实例，有东西着陆时会通知这些观察者。当然，如果有需要，还可以继续
     * 增加新的 LandingObserver 实例观测 Moon 对象。
     *
     * Moon 类实现如下：
     *
     * public class Moon {
     *
     *     private final List<LandingObserver> observers = new ArrayList<>();
     *
     *     public void land(String name) {
     *         for (LandingObserver observer : observers) {
     *             observer.observeLanding(name);
     *         }
     *     }
     *
     *     public void startSpying(LandingObserver observer) {
     *         observers.add(observer);
     *     }
     *
     * }
     *
     * 共有两个具体的类实现了 LandingObserver 接口，分别代表外星人和 NASA 检测着陆情况。当监测到登陆后，它们有不同的反应。
     *
     * Aliens 类实现如下：
     *
     * public class Aliens implements LandingObserver {
     *
     *     @Override
     *     public void observeLanding(String name) {
     *         if (name.contains("Apollo")) {
     *             System.out.println("They're distracted, lets invade earth!");
     *         }
     *     }
     *
     * }
     *
     * Nasa 类实现如下：
     *
     * public class Nasa implements LandingObserver {
     *
     *     @Override
     *     public void observeLanding(String name) {
     *         if (name.contains("Apollo")) {
     *             System.out.println("We made it!");
     *         }
     *     }
     *
     * }
     *
     * 在传统的例子中，用户代码需要有一层模板类，如下：
     *
     *         Moon moon = new Moon();
     *         moon.startSpying(new Nasa());
     *         moon.startSpying(new Aliens());
     *
     *         moon.land("An asteroid");
     *         moon.land("Apollo 11");
     *
     * 如果使用 Lambda 表达式，就不用编写这些类了，如下：
     *
     *         Moon moon = new Moon();
     *
     *         moon.startSpying(name -> {
     *             if (name.contains("Apollo")) {
     *                 System.out.println("We made it!");
     *             }
     *         });
     *
     *         moon.startSpying(name -> {
     *             if (name.contains("Apollo")) {
     *                 System.out.println("They're distracted, lets invade earth!");
     *             }
     *         });
     *
     *         moon.land("An asteroid");
     *         moon.land("Apollo 11");
     *
     * 还有一点值得思考，无论使用观察者模式或策略模式，实现时采用 Lambda 表达式还是传统的类，取决于策略和观察者代码的复杂度。
     * 这里所举的例子代码很简单，只是一两个方法调用，很适合展示新的语言特性。然而在有些情况下，观察者本身就是一个很复杂的类，
     * 这时将很多代码塞进一个方法中会大大降低代码的可读性。
     *
     * PS：从某种角度来说，将大量代码塞进一个方法会让可读性变差是决定如何使用 Lambda 表达式的黄金法则。之所以不在这里过分强调，
     * 是因为这也是编写一般方法时的黄金法则。
     */
    public static void main(String[] args) {

    }

}
