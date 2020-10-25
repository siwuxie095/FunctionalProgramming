package com.siwuxie095.functional.chapter8th.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:30:13
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 使用 Lambda 表达式的领域专用语言
     *
     * 领域专用语言（Domain Specific Language，DSL）是针对软件系统中某特定部分的编程语言。它们通常比较小巧，表达能力
     * 也不如 Java 这样能应对大多数编程任务的通用语言强。DSL 高度专用：不求面面俱到，但求有所专长。
     *
     * 通常将 DSL 分为两类：内部 DSL 和外部 DSL。
     *
     * 外部 DSL 脱离程序源码编写，然后单独解析和实现。比如级联样式表（CSS）和正则表达式，就是常用的外部 DSL。
     *
     * 内部 DSL 嵌入编写它们的编程语言中。如果使用过 JMock 和 Mockito 等模拟类库，或用过 SQL 构建 API，如 JOOQ 或
     * Querydsl，那么就知道什么是内部 DSL。从某种角度上说，内部 DSL 就是普通的类库，提供 API 方便使用。虽然简单，内部
     * DSL 却功能强大，能让代码变得更加精炼、易读。理想情况下，使用 DSL 编写的代码读起来就像描述问题所使用的语言。
     *
     * 有了 Lambda 表达式，实现 DSL 就更简单了，那些想尝试 DSL 的程序员又多了一件趁手的工具。
     *
     * 这里将通过实现一个用于行为驱动开发（BDD）的 DSL：LambdaBehave，来探索其中遇到的各种问题。
     *
     * BDD 是测试驱动开发（TDD）的一个变种，它的重点是描述程序的行为，而非一组需要通过的单元测试。这里的设计灵感源于一个
     * 叫 Jasmine 的 JavaScript BDD 框架，前端开发中会大量使用该框架。如下代码展示了如何使用 Jasmine 创建测试用例：
     *
     *     describe("A suite is just a function", function() {
     *         it("and so is a spec", function() {
     *             var a = true;
     *             expect(a).toBe(true);
     *         });
     *     });
     *
     * 如果不熟悉 JavaScript，阅读这段代码可能会稍感疑惑。下面使用 Java 8 实现一个类似的框架时，会一步一步来，只需要
     * 记住，在 JavaScript 中使用 function() { ... } 来表示 Lambda 表达式。
     *
     * 先来看看这些概念：
     * （1）每一个规则描述了程序的一种行为；
     * （2）期望是描述应用行为的一种方式，在规则中定义；
     * （3）多个规则合在一起，形成一个套件。
     *
     * 这些概念在传统的测试框架，比如 JUnit 中，都有对应的概念。规则对应一个测试方法，期望对应断言，套件对应一个测试类。
     *
     *
     *
     * 使用 Java 编写 DSL
     *
     * 先看一下实现后的 Java BDD 框架长什么样子，如下代码描述了一个 Stack 的某些行为：
     *
     * public class StackSpec {{
     *
     *     describe("a stack", it -> {
     *
     *         it.should("be empty when created", expect -> {
     *             expect.that(new Stack()).isEmpty();
     *         });
     *
     *         it.should("push new elements onto the top of the stack", expect -> {
     *             Stack<Integer> stack = new Stack<>();
     *             stack.push(1);
     *
     *             expect.that(stack.get(0)).isEqualTo(1);
     *         });
     *
     *         it.should("pop the last element pushed onto the stack", expect -> {
     *             Stack<Integer> stack = new Stack<>();
     *             stack.push(2);
     *             stack.push(1);
     *
     *             expect.that(stack.pop()).isEqualTo(2);
     *         });
     *
     *     });
     *
     * }}
     *
     * 首先使用动词 describe 为套件起头，然后定义一个名字表明这是描述什么东西的行为，这里使用了 "a stack"。
     *
     * 每一条规则读起来尽可能接近英语中的句子。它们均以 it.should 打头，其中 it 指正在描述的对象。然后用一句简单的英语
     * 描述行为，最后使用 expect.that 做前缀，描述期待的行为。
     *
     * 检查规则时，会从命令行得到一个简单的报告，表明是否有规则失败。会发现 pop 操作期望的返回值是 2，而不是 1，因此如下
     * 这条规则就失败了：
     *
     * pop the last element pushed onto the stack
     *
     * 输出如下：
     *
     * a stack
     * 	should pop the last element pushed onto the stack[expected:<2> but was:<1>]
     * 	should be empty when created
     * 	should push new elements onto the top of the stack
     *
     * PS：运行 Runner 中的 main 方法即可。
     *
     *
     *
     * 实现
     *
     * 领略了使用 Lambda 表达式的 DSL 所带来的便利后，现在该看看是如何实现该框架的。希望让大家看到，自己实现一个这样的
     * 框架是多么简单。
     *
     * 描述行为首先看到的是 describe 这个动词，简单导入一个静态方法就够了。为套件创建一个 Description 实例，在此处理
     * 各种各样的规则。Description 类就是定义的 DSL 中 的 it。即 如下代码中的 Description：
     *
     * public final class Lets {
     *
     *     public static void describe(String name, Suite behavior) {
     *         Description description = new Description(name);
     *         behavior.specifySuite(description);
     *     }
     *
     * }
     *
     * 每个套件的规则描述由用户使用一个 Lambda 表达式实现，因此需要一个 Suite 函数式接口来表示规则组成的套件。该接口
     * 接收一个 Description 对象作为参数，在 Lets 类的 describe 方法里将其传入。
     *
     * Suite 函数式接口的定义如下：
     *
     * public interface Suite {
     *
     *     public void specifySuite(Description description);
     *
     * }
     *
     * 在定义的 DSL 中，不仅套件由 Lambda 表达式实现，每一条规则也是一个 Lambda 表达式。它们也需要定义一个函数式接口，
     * 即 Specification。
     *
     * Specification 函数式接口的定义如下：
     *
     * public interface Specification {
     *
     *     public void specifyBehaviour(Expect expect);
     *
     * }
     *
     * 这里希望用户可以使用 it.should 命名他们的规则，这就是说 Description 类需要有一个 should 方法。这里是真正做事
     * 的地方，该方法通过调用 specifySuite 执行 Lambda 表达式。如果规则失败，会抛出一个标准的 Java AssertionError，
     * 而其他任何 Throwable 对象则认为是一个错误。
     *
     * should 方法实现如下：
     *
     *     public void should(String description, Specification specification) {
     *         try {
     *             Expect expect = new Expect();
     *             specification.specifyBehaviour(expect);
     *             Runner.current.recordSuccess(suite, description);
     *         } catch (AssertionError cause) {
     *             Runner.current.recordFailure(suite, description, cause);
     *         } catch (Throwable cause) {
     *             Runner.current.recordError(suite, description, cause);
     *         }
     *     }
     *
     * 规则通过 expect.that 描述期望的行为，也就是说 Expect 类需要一个 that 方法供用户调用。这里可以封装传入的对象，
     * 然后暴露一些常用的方法，如 isEqualTo。如果规则失败，抛出相应的断言。
     *
     * public final class Expect {
     *
     *     public BoundExpectation that(Object value) {
     *         return new BoundExpectation(value);
     *     }
     *
     *
     *     public CollectionExpectation that(Collection<?> collection) {
     *         return new CollectionExpectation(collection);
     *     }
     *
     * }
     *
     * 有人可能会注意到，上面一直忽略了一个细节，该细节与 Lambda 表达式无关。StackSpec 类并没有直接实现任何方法，直接
     * 将代码写在里边。这里其实是偷了个懒，在类定义的开头和结尾使用了双括号。如下：
     *
     * public class StackSpec {{
     *     // ...
     * }}
     *
     * 这其实是一个匿名构造函数，可以执行任意的 Java 代码块，所以这等价于一个完整的构造函数，只是少了一些样板代码。这段
     * 代码也可以写作：
     *
     * public class StackSpec {
     *     public StackSpec() {
     *         // ...
     *     }
     * }
     *
     * 要实现一个完整的 BDD 框架还有很多工作要做，这里只是为了展示如何使用 Lambda 表达式创建领域专用语言。在这里讲解了
     * 与 DSL 中 Lambda 表达式交互的部分，以期能够管中窥豹，了解如何实现这种类型的 DSL。
     *
     *
     *
     * 评估
     *
     * 流畅性的一方面表现在 DSL 是否是 IDE 友好的。换句话说，只需记住少量知识，然后用代码自动补全功能补齐代码。这就是使
     * 用 Description 和 Expect 对象的原因。当然也可以导入静态方法 it 或 expect，一些 DSL 中就使用了这种方式。如果
     * 选择向 Lambda 表达式传入对象，而不是导入一个静态方法，就能让 IDE 的使用者轻松补全代码。
     *
     * 用户唯一要记住的是调用 describe 方法，这种方式的好处通过单纯阅读可能无法体会，建议大家创建一个示例项目，亲自体验
     * 这个框架。
     *
     * 另一个值得注意的是大多数测试框架提供了大量注解，或者很多外部 "魔法"，或者借助于反射。这里不需要这些技巧，就能直接
     * 使用 Lambda 表达式在 DSL 中表达行为，就和使用普通的 Java 方法一样。
     */
    public static void main(String[] args) {

    }


}
