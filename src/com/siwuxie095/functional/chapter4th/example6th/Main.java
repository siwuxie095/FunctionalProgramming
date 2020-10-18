package com.siwuxie095.functional.chapter4th.example6th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 12:53:43
 */
public class Main {

    /**
     * 二进制接口的兼容性
     *
     * Java 8 中对 API 最大的改变在于集合类。虽然 Java 在持续演进，但它一直在保持着向后二进制兼容。具体来说，使用 Java 1
     * 到 Java 7 编译的类库或应用，可以直接在 Java 8 上运行。
     *
     * 当然，错误也难免会时有发生，但和其他编程平台相比，二进制兼容性一直被视为 Java 的关键优势所在。除非引入新的关键字，如
     * enum，达成源代码向后兼容也不是没有可能实现。可以保证，只要是 Java 1 到 Java 7 写出的代码，在 Java 8 中依然可以编
     * 译通过。
     *
     * 事实上，修改了像集合类这样的核心类库之后，这一保证也很难实现。这里用具体的例子作为思考练习。Java 8 中为 Collection
     * 接口增加了 stream 方法，这意味着所有实现了 Collection 接口的类都必须增加这个新方法。对核心类库里的类来说，实现这个
     * 新方法（比如为 ArrayList 增加新的 stream 方法）就能就能使问题迎刃而解。
     *
     * 缺憾在于，这个修改依然打破了二进制兼容性，在 JDK 之外实现 Collection 接口的类，例如 MyCustomList，也仍然需要实现
     * 新增的 stream 方法（这里默认 MyCustomList 是在 Java 8 之前的版本实现的）。这个 MyCustomList 在 Java 8 中无法
     * 通过编译，即使已有一个编译好的版本，在 JVM 加载 MyCustomList 类时，类加载器仍然会引发异常。
     *
     * 这是所有使用第三方集合类库的梦魇，要避免这个糟糕情况，则需要在 Java 8 中添加新的语言特性：默认方法。
     */
    public static void main(String[] args) {

    }

}
