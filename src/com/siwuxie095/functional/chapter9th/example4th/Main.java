package com.siwuxie095.functional.chapter9th.example4th;

/**
 * @author Jiajing Li
 * @date 2020-10-28 22:14:44
 */
public class Main {

    /**
     * 消息传递架构
     *
     * 这里要讨论的是一种基于消息传递的架构，在 example3rd 中用它实现了一个简单的聊天客户端。聊天客户端的细节并不重要，
     * 重要的是这个模式，下面就来谈谈消息传递本身吧。
     *
     * 首先要注意的是这里的设计里不共享任何状态。verticle 对象之间通过向事件总线发送消息通信，这就是说不需要保护任何共
     * 享状态，因此根本不需要在代码中添加锁或使用 synchronized 关键字，编写并发程序变得更加简单。
     *
     * 为了确保不在 verticle 对象之间共享状态，这里对事件总线上传递的消息做了某些限制。例子中使用的消息是普通的 Java
     * 字符串，它们天生就是不可变的，因此可以安全地在 verticle 对象之间传递。接收处理程序无法改变 String 对象的状态，
     * 因此不会和消息发送者互相干扰。
     *
     * Vert.x 没有限制只能使用字符串传递消息，可以使用更复杂的 JSON 对象，甚至使用 Buffer 类构建自己的消息。这些消息
     * 是可变的，也就是说如果使用不当，消息发送者和接收者可以通过读写消息共享状态。
     *
     * Vert.x 框架通过在发送消息时复制消息的方式来避免这种问题。这样既保证接收者得到了正确的结果，又不会共享状态。无论
     * 是否使用 Vert.x，确保消息不会共享状态都是最重要的。不可变消息是最简单的解决方式，但通过复制消息也能解决该问题。
     *
     * 使用 verticle 对象模型开发的并发系统易于测试，因为每个 verticle 对象都可以通过发送消息、验证返回值的方式单独测
     * 试。然后使用这些经过测试的模块组合成一个复杂系统，而不用担心使用共享的可变状态通信在集成时会遇到大量问题。当然，点
     * 对点的测试还是必须的，以确保系统和预期的行为一致。
     *
     * 基于消息传递的系统让隔离错误变得简单，也便于编写可靠的代码。如果一个消息处理程序发生错误，可以选择重启本地 verticle
     * 对象，而不用去重启整个 JVM。
     *
     * 通过 Lambda 表达式和 Stream 类库可以编写并行处理数据代码。并行机制让处理海量数据的速度更快，但线程不可能无限增加，
     * 这里希望的是在有限的并行运行的线程里，执行更多的 I/O 操作，比如连接更多的聊天客户端。无论哪种情况，解决方案都是一样
     * 的：使用 Lambda 表达式表示行为，构建 API 来管理并发。聪明的类库意味着简单的应用代码。
     */
    public static void main(String[] args) {

    }

}
