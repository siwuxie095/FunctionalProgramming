package com.siwuxie095.functional.chapter9th.example9th;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:30:50
 */
public class Main {

    /**
     * 何时何地使用新技术
     *
     * 这里讲解了如何使用非阻塞式和基于事件驱动的系统。这是否意味着大家明天就要扔掉现有的 Java EE 或者 Spring
     * 企业级 Web 应用呢？答案当然是否定的。
     *
     * 即使不去考虑 CompletableFuture 和 RxJava 相对较新，使用它们依然有一定的复杂度。它们用起来比到处显式使
     * 用 Future 和回调简单，但对很多问题来说，传统的阻塞式 Web 应用开发技术就足够了。如果还能用，就别修理。
     *
     * 当然，事件驱动和响应式应用正在变得越来越流行，而且经常会是为你的问题建模的最好方式之一。响应式编程宣言鼓励
     * 大家使用这种方式编写更多应用，如果它适合你的待解问题，那么就应该使用。相比阻塞式设计，有两种情况可能特别适
     * 合使用响应式或事件驱动的方式来思考。
     *
     * 第一种情况是业务逻辑本身就使用事件来描述。Twitter 就是一个经典例子。Twitter 是一种订阅文字流信息的服务，
     * 用户彼此之间推送信息。使用事件驱动架构编写应用，能准确地为业务建模。图形化展示股票价格可能是另一个例子，每
     * 一次价格的变动都可认为是一个事件。
     *
     * 第二种情况显然的用例是应用需要同时处理大量 I/O 操作。阻塞式 I/O 需要同时使用大量线程，这会导致大量锁之间
     * 的竞争和太多的上下文切换。如果想要处理成千上万的连接，非阻塞式 I/O 通常是更好的选择。
     *
     * PS：响应式编程宣言链接：https://www.reactivemanifesto.org/
     */
    public static void main(String[] args) {

    }

}
