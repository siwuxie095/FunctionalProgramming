package com.siwuxie095.functional.chapter5th.example2nd;

/**
 * @author Jiajing Li
 * @date 2020-10-19 20:51:26
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 方法引用
     *
     * Lambda 表达式有一个常见的用法：Lambda 表达式经常调用参数。比如，想得到艺术家的姓名，Lambda 的表达式如下：
     *
     * artist -> artist.getName()
     *
     * 这种用法如此普遍，因此 Java 8 为其提供了一个简写语法，叫作方法引用，帮助程序员重用已有方法。用方法引用重写
     * 上面的 Lambda 表达式，代码如下：
     *
     * Artist::getName
     *
     * 标准语法为 Classname::methodName。
     *
     * 需要注意的是，虽然这是一个方法，但不需要在后面加括号，因为这里并不调用该方法。这里只是提供了和 Lambda 表达
     * 式等价的一种结构，在需要时才会调用。凡是使用 Lambda 表达式的地方，就可以使用方法引用。
     *
     * 构造函数也有同样的缩写形式，如果你想使用 Lambda 表达式创建一个 Artist 对象，可能会写出如下代码：
     *
     * (name, nationality) -> new Artist(name, nationality)
     *
     * 使用方法引用，上述代码可写为：
     *
     * Artist::new
     *
     * 这段代码不仅比原来的代码短，而且更易阅读。Artist::new 能立刻告诉程序员这是在创建一个 Artist 对象，程序员
     * 无需看完整行代码，就能弄明白代码的意图。另一个要注意的地方是方法引用自动支持多个参数，前提是选对了正确的函数
     * 式接口。
     *
     * 还可以用这种方式创建数组，下面的代码创建了一个字符串型的数组：
     *
     * String[]::new
     *
     * 有人说，方法引用看起来 "就像在作弊"。意思是说，了解如何使用 Lambda 表达式让代码像数据一样在对象间传递之后，
     * 这种直接引用方法的方式就像 "作弊"。
     *
     * 放心，这不是在作弊。只要记住，每次写出形如 x -> foo(x) 的 Lambda 表达式时，和直接调用方法 foo 是一样的。
     * 方法引用只不过是基于这样的事实，提供了一种简短的语法而已。
     */
    public static void main(String[] args) {

    }

}
