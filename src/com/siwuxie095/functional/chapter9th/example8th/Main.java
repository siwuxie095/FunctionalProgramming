package com.siwuxie095.functional.chapter9th.example8th;

/**
 * @author Jiajing Li
 * @date 2020-10-31 07:51:49
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 响应式编程
     *
     * CompletableFuture 背后的概念可以从单一的返回值推广到数据流，这就是响应式编程。响应式编程其实是一种声明式编程方法，
     * 它让程序员以自动流动的变化和数据流来编程。
     *
     * PS：响应式编程，也叫 反应式编程。
     *
     * 可以将电子表格想象成一个使用响应式编程的例子。如果在单元格 C1 中键入 =B1+5，其实是在告诉电子表格将 B1 中的值加 5，
     * 然后将结果存入 C1。而且，将来 B1 中的值变化后，电子表格会自动刷新 C1 中的值。
     *
     * RxJava 类库将这种响应式的理念移植到了 JVM。这里不会深入类库，只描述其中的一些关键概念。
     *
     * RxJava 类库引入了一个叫作 Observable 的类，该类代表了一组待响应的事件，可以理解为一沓欠条。
     *
     * 在 Observable 对象和 Stream 接口之间有很强的关联。两种情况下，都需要使用 Lambda 表达式将行为和一般的操作关联、
     * 都需要将高阶函数链接起来定义完成任务的规则。实际上，Observable 定义的很多操作都和 Stream 的相同：map、filter、
     * reduce。
     *
     * 最大的不同在于用例。Stream 是为构建内存中集合的计算流程而设计的，而 RxJava 则是为了组合异步和基于事件的系统流程而
     * 设计的。它没有取数据，而是把数据放进去。换个角度理解，RxJava 是处理一组值，而 CompletableFuture 用来处理一个值。
     *
     * 以查找艺术家为例，代码如下：
     *
     *     public Observable<Artist> search(String searchedName,
     *                                      String searchedNationality,
     *                                      int maxResults) {
     *         return getSavedArtistNames()  // <1>
     *                 .filter(name -> name.contains(searchedName)) // <2>
     *                 .flatMap(this::lookupArtist) // <3>
     *                 .filter(artist -> artist.getNationality() // <4>
     *                         .contains(searchedNationality))
     *                 .take(maxResults); // <5>
     *     }
     *
     * search 方法根据名字和国籍过滤结果，它在本地缓存了一份艺术家名单，但必须从外部服务上查询艺术家信息，比如国籍。
     *
     * 即 名字从本地过滤，国籍从外部过滤。
     *
     * 先通过 getSavedArtistNames 方法取得一个包含艺术家姓名的 Observable 对象，该对象的高阶函数和 Stream 类似，然后
     * 根据名字进行过滤，并通过 lookupArtist 方法根据过滤后的名字从外部查找艺术家，然后再根据国籍进行过滤。最后还需要在查
     * 找时限定返回结果的最大值：maxResults，通过调用 Observable 对象的 take 方法来实现该功能。
     *
     * 注意：这里使用的是 flatMap 而不是 map。因为这里需要组合调用一系列外部服务，每种服务都可能在它自己的线程或线程池里
     * 执行。所以将名字替换为 Observable 对象，来表示一个或多个艺术家，而不是直接将名字替换为一个或多个艺术家。
     *
     * 不难发现，这个 API 很像使用 Stream。它和 Stream 的最大区别是：Stream 是为了计算最终结果，而 RxJava 在线程模型
     * 上则像 CompletableFuture。
     *
     * 使用 CompletableFuture 时，通过给 complete 方法一个值来偿还欠条。而 Observable 代表了一个事件流，需要有能力
     * 传入多个值。如下代码展示了该怎么做：
     *
     *         observer.onNext("a");
     *         observer.onNext("b");
     *         observer.onNext("c");
     *         observer.onCompleted();
     *
     * 这里不停地调用 onNext 方法，Observable 对象中的每个值都调用一次。这可以在一个循环里做，也可以在任何想要生成值的
     * 线程里做。一旦完成了产生事件的工作，就调用 onCompleted 方法表示任务完成。和使用 Stream 一样，也有一些静态工厂
     * 方法用来从 Future、迭代器和数组中创建 Observable 对象。
     *
     * 和 CompletableFuture 类似，Observable 也能处理异常。如果出现错误，调用 onError 方法，如下：
     *
     *         observer.onError(new Exception());
     *
     * 这里的功能和 CompletableFuture 略有不同。这里能得到异常发生之前所有的事件，但只能正常或异常地终结程序，两者只能
     * 选其一。
     *
     * 和介绍 CompletableFuture 时一样，这里只给出了如何使用和在什么地方使用 Observable 的一点建议。如果想了解更多细
     * 节，可以参考项目文档（https://github.com/ReactiveX/RxJava/wiki/Getting-Started）。RxJava 已经开始集成进
     * Java 类库的生态系统，比如企业级的集成框架 Apache Camel 已经加入了一个叫作 Camel RX 的模块，该模块使得可以在该
     * 框架中使用 RxJava。Vert.x 项目也启动了一个 Rxify 它的 API 项目（即 使原有 API 可以支持响应式编程）。
     *
     * PS：这里需要引入 RxJava 的一个 jar 包：io.reactivex » rxjava » 1.3.8
     */
    public static void main(String[] args) {

    }

}
