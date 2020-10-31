package com.siwuxie095.functional.chapter9th.example10th;

/**
 * @author Jiajing Li
 * @date 2020-10-31 09:41:47
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 要点回顾和巩固练习
     *
     * 1、要点回顾
     *
     * （1）使用基于 Lambda 表达式的回调，很容易实现事件驱动架构。
     * （2）CompletableFuture 代表了 IOU（I Owe You，即 欠条），使用 Lambda 表达式能方便地组合、合并。
     * （3）Observable 继承了 CompletableFuture 的概念，用来处理数据流。
     *
     *
     *
     * 2、巩固练习
     *
     * （1）使用 CompletableFuture 重构代码。
     *
     * 问：
     * 以 BlockingArtistAnalyzer 类开始，该类从两个艺术家的名字中找出成员数更多的那个，如果第一个艺术家的成员多，
     * 返回 true，否则返回 false。该类被注入一个 artistLookupService，因为查找 Artist 的过程可能会耗费一定时间。
     * 由于 BlockingArtistAnalyzer 类要依序调用两次查找服务，分析就会变慢，练习的目标就是加速这一过程。
     *
     * BlockingArtistAnalyzer 类实现如下：
     *
     * public class BlockingArtistAnalyzer {
     *
     *     private final Function<String, Artist> artistLookupService;
     *
     *     public BlockingArtistAnalyzer(Function<String, Artist> artistLookupService) {
     *         this.artistLookupService = artistLookupService;
     *     }
     *
     *     public boolean isLargerGroup(String artistName, String otherArtistName) {
     *         return getNumberOfMembers(artistName) > getNumberOfMembers(otherArtistName);
     *     }
     *
     *     private long getNumberOfMembers(String artistName) {
     *         return artistLookupService.apply(artistName)
     *                 .getMembers()
     *                 .count();
     *     }
     *
     * }
     *
     * 练习分成两部分，第一部分是使用一个回调接口重构阻塞代码。在这里，将使用 Consumer<Boolean>，Consumer 是 JVM 自带
     * 的一个函数式接口，接受一个参数，返回空。这里的任务就是修改 BlockingArtistAnalyzer，并实现 ArtistAnalyzer。
     *
     * ArtistAnalyzer 接口定义如下：
     *
     * public interface ArtistAnalyzer {
     *
     *     public void isLargerGroup(String artistName,
     *                               String otherArtistName,
     *                               Consumer<Boolean> handler);
     *
     * }
     *
     * 现在有了一个符合回调模型的 API，就不需要同时执行两次阻塞式的查找了。使用 CompletableFuture 类重构 isLargerGroup
     * 方法，让其可以并行执行。
     *
     * 答：
     * 见 CallbackArtistAnalyser 类和 CompletableFutureArtistAnalyser 类。前者同步回调，后者异步回调。
     */
    public static void main(String[] args) {

    }

}
