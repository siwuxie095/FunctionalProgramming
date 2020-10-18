package com.siwuxie095.functional.chapter4th.example10th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 19:51:36
 */
public class Main {

    /**
     * 接口的静态方法
     *
     * 以 Stream 接口为例。Stream 是个接口， Stream.of 是接口的静态方法。这也是 Java 8 中添加的一个新的语言特性，
     * 旨在帮助编写类库的开发人员，但对于日常应用程序的开发人员也同样适用。
     *
     * 人们在编程过程中积累了这样一条经验，那就是一个包含很多静态方法的类。有时，类是一个放置工具方法的好地方，比如
     * Java 7 中引入的 Objects 类，就包含了很多工具方法，这些方法不是具体属于某个类的。
     *
     * 当然，如果一个方法有充分的语义原因和某个概念相关，那么就应该将该方法和相关的类或接口放在一起，而不是放到另一个
     * 工具类中。这有助于更好地组织代码，阅读代码的人也更容易找到相关方法。
     *
     * 比如，如果想创建一个由简单值组成的 Stream，自然希望 Stream 中能有一个这样的方法。这在以前很难达成，引入重接
     * 口的 Stream 对象，最后促使 Java 为接口加入了静态方法。
     *
     * Stream 和其他几个子类还包含另外几个静态方法。特别是 range 和 iterate 方法提供了产生 Stream 的其他方式。
     */
    public static void main(String[] args) {

    }

}
