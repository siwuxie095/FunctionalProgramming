package com.siwuxie095.functional.chapter4th.example11th;

import org.junit.Assert;

import java.util.Optional;

/**
 * @author Jiajing Li
 * @date 2020-10-18 20:01:14
 */
@SuppressWarnings("all")
public class Main {

    /**
     * Optional
     *
     * reduce 方法有两种形式：
     * （1）一种需要有一个初始值；
     * （2）另一种则不需要有初始值。
     *
     * 分别对应如下代码：
     * （1）T reduce(T identity, BinaryOperator<T> accumulator);
     * （2）Optional<T> reduce(BinaryOperator<T> accumulator);
     *
     * 没有初始值的情况下，reduce 的第一步使用 Stream 中的前两个元素。有时，reduce 操作不存在有意义的初始值，
     * 这样做就是有意义的（指的是第一步使用前两个元素）。此时，reduce 方法返回一个 Optional 对象。
     *
     * Optional 是为核心类库新设计的一个数据类型，用来替换 null 值。人们对原有的 null 值有很多抱怨，甚至连
     * 发明这一概念的 Tony Hoare 也是如此，他曾说这是一个 "价值连城的错误"。作为一名有影响力的计算机科学家就
     * 是这样：虽然连一毛钱也见不到，却也可以犯一个 "价值连城的错误"。
     *
     * 人们常常使用 null 值表示值不存在，Optional 对象能更好地表达这个概念。使用 null 代表值不存在的最大问
     * 题在于 NullPointerException。一旦引用一个存储 null 值的变量，程序会立即崩溃。使用 Optional 对象有
     * 两个目的：
     * （1）首先，Optional 对象鼓励程序员适时检查变量是否为空，以避免代码缺陷；
     * （2）其次，它将一个类的 API 中可能为空的值文档化，这比阅读实现代码要简单很多。
     *
     * 下面举例说明 Optional 对象的 API，从而切身体会一下它的使用方法。使用工厂方法 of，可以从某个值创建出
     * 一个 Optional 对象。Optional 对象相当于值的容器，而该值可以通过 get 方法提取。
     *
     * 如下是创建某个值的 Optional 对象：
     *
     *         Optional<String> a = Optional.of("a");
     *         Assert.assertEquals("a", a.get());
     *
     * Optional 对象也可能为空，因此还有一个对应的工厂方法 empty，另外一个工厂方法 ofNullable 则可将一个
     * 空值转换成 Optional 对象。而 isPresent 方法表示一个 Optional 对象里是否有值。
     *
     * 如下是创建一个空的 Optional 对象，并检查其是否有值：
     *
     *         Optional emptyOptional = Optional.empty();
     *         Optional alsoEmpty = Optional.ofNullable(null);
     *         Assert.assertFalse(emptyOptional.isPresent());
     *         Assert.assertFalse(alsoEmpty.isPresent());
     *         Assert.assertTrue(a.isPresent());
     *
     * 使用 Optional 对象的方式之一是在调用 get() 方法前，先使用 isPresent 检查 Optional 对象是否有值。
     * 但使用 orElse 方法则更简洁，当 Optional 对象为空时，该方法提供了一个备选值。如果计算备选值在计算上
     * 太过繁琐，即可使用 orElseGet 方法。该方法接受一个 Supplier 对象，只有在 Optional 对象真正为空时
     * 才会调用。
     *
     * 如下是 orElse 和 orElseGet 方法的使用：
     *
     *         Assert.assertEquals("b", emptyOptional.orElse("b"));
     *         Assert.assertEquals("c", emptyOptional.orElseGet(() -> "c"));
     *
     * Optional 对象不仅可以用于新的 Java 8 API，也可用于具体领域类中，和普通的类别无二致。当试图避免空值
     * 相关的缺陷，如未捕获的异常时，可以考虑一下是否可使用 Optional 对象。
     */
    public static void main(String[] args) {
        Optional<String> a = Optional.of("a");
        Assert.assertEquals("a", a.get());

        Optional emptyOptional = Optional.empty();
        Optional alsoEmpty = Optional.ofNullable(null);
        Assert.assertFalse(emptyOptional.isPresent());
        Assert.assertFalse(alsoEmpty.isPresent());
        Assert.assertTrue(a.isPresent());

        Assert.assertEquals("b", emptyOptional.orElse("b"));
        Assert.assertEquals("c", emptyOptional.orElseGet(() -> "c"));
    }

}
