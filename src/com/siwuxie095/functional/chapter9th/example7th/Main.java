package com.siwuxie095.functional.chapter9th.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-30 07:45:29
 */
@SuppressWarnings("all")
public class Main {

    /**
     * CompletableFuture
     *
     * 不必调用 get 方法阻塞当前线程，就能操作 Future 对象返回的结果。这个问题的解决之道是 CompletableFuture，它结合了
     * Future 对象打欠条的主意和使用回调处理事件驱动的任务。其要点是可以组合不同的实例，而不用担心末日金字塔问题。
     *
     * PS：CompletableFuture 对象背后的概念，在其他语言中被叫作延迟对象或约定。在 Google Guava 类库和 Spring 框架中，
     * 这被叫作 ListenableFuture。
     *
     * 这里将使用 CompletableFuture 重写 example6th 中的示例，以此来展示 CompletableFuture 的用法，如下：
     *
     *     @Override
     *     public Album lookupByName(String albumName) {
     *         CompletableFuture<List<Artist>> artistLookup
     *                 = loginTo("artist")
     *                 .thenCompose(artistLogin -> lookupArtists(albumName, artistLogin));  // <1>
     *
     *         return loginTo("track")
     *                 .thenCompose(trackLogin -> lookupTracks(albumName, trackLogin)) // <2>
     *                 .thenCombine(artistLookup, (tracks, artists)
     *                         -> new Album(albumName, tracks, artists)) // <3>
     *                 .join(); // <4>
     *     }
     *
     * 其中，loginTo、lookupArtists 和 lookupTracks 方法均返回 CompletableFuture，而不是 Future。
     *
     * CompletableFuture API 的技巧是注册 Lambda 表达式，并且把高阶函数链接起来。方法不同，但道理和 Stream API 的设计
     * 是相通的。
     *
     *
     * 先是使用 thenCompose 方法将 Credentials 对象转换成包含艺术家信息的 CompletableFuture 对象，这就像和朋友借了点
     * 钱，然后在亚马逊上花了。你不会马上拿到新买的书，亚马逊会发给你一封电子邮件，告诉你新书正在运送途中，又是一张欠条。
     *
     * 然后又使用了 thenCompose 方法，通过登录 Track API，将 Credentials 对象转换成包含曲目信息的 CompletableFuture
     * 对象。这里引入了一个新方法 thenCombine，该方法将一个 CompletableFuture 对象的结果和另一个 CompletableFuture
     * 对象组合起来。组合操作是由用户提供的 Lambda 表达式完成，这里要使用曲目信息和艺术家信息构建一个 Album 对象。
     *
     * 这时有必要提醒大家，和使用 Stream API 一样，现在还没真正开始做事，只是定义好了做事的规则。在调用最终的方法之前，无法
     * 保证 CompletableFuture 对象已经生成结果。CompletableFuture 对象实现了 Future 接口，可以调用 get 方法获取值。
     * CompletableFuture 对象包含 join 方法，这里调用了该方法，它的作用和 get 方法是一样的，而且它没有使用 get 方法时令
     * 人倒胃口的检查异常。
     *
     * 现在可能已经掌握了使用 CompletableFuture 的基础，但是如何创建它们又是另外一回事。创建 CompletableFuture 对象分
     * 两部分：创建对象和传给它欠客户代码的值。
     *
     * 创建 CompletableFuture 对象非常简单，调用它的构造函数就够了。现在就可以将该对象传给客户代码，用来将操作链接在一起。
     * 同时保留了对该对象的引用，以便在另一个线程里继续执行任务。如下：
     *
     *     CompletableFuture<Artist> createFuture(String id) {
     *         CompletableFuture<Artist> future = new CompletableFuture<>();
     *         startJob(future);
     *         return future;
     *     }
     *
     * 一旦任务完成，不管是在哪个线程里执行的，都需要告诉 CompletableFuture 对象那个值，这份工作可以由各种线程模型完成。
     * 比如，可以 submit 一个任务给 ExecutorService，或者使用类似 Vert.x 这样基于事件循环的系统，或者直接启动一个线程
     * 来执行任务。为了告诉 CompletableFuture 对象值已就绪，需要调用 complete 方法，是时候还债了。如下：
     *
     *     private void startJob(CompletableFuture<Artist> future) {
     *         future.complete(artist);
     *     }
     *
     * 这里为 Future 提供一个值，完成工作。一个可完成的 Future 是一张可以被处理的欠条。
     *
     * 当然，CompletableFuture 的常用情境之一是异步执行一段代码，该段代码计算并返回一个值。为了避免重复实现同样的代码，
     * 有一个工厂方法 supplyAsync，用来创建 CompletableFuture 实例。如下：
     *
     *     CompletableFuture<Track> lookupTrack(String id) {
     *         return CompletableFuture.supplyAsync(() -> {
     *             // Some expensive work is done here <1>
     *             // ...
     *             return track; // <2>
     *         }, SERVICE); // <3>
     *     }
     *
     * supplyAsync 方法接受一个 Supplier 对象作为参数，然后执行它。这里的要点是能执行一些耗时的任务，同时不会阻塞当前线程。
     * 这就是方法名中 Async 的含义。方法的返回值用来完成 CompletableFuture。这里提供了一个叫作 SERVICE 的 Executor，告
     * 诉 CompletableFuture 对象在哪里执行任务。如果没有提供 Executor，就会使用相同的 fork/join 线程池并行执行。
     *
     * 当然，不是所有的欠条都能兑现。有时候碰上异常，就无力偿还了。CompletableFuture 为此提供了 completeExceptionally
     * 方法，用于处理异常情况。该方法可以视作 complete 方法的备选项，但不能同时调用 complete 和 completeExceptionally
     * 方法。如下：
     *
     *     private void processExceptionally(CompletableFuture<Album> future, String name) {
     *         future.completeExceptionally(new AlbumLookupException("Unable to find " + name));
     *     }
     *
     * 完整讨论 CompletableFuture 接口已经超出了这里的范围，很多时候它是一个隐藏大礼包。该接口有很多有用的方法，可以用想到
     * 的任何方式组合 CompletableFuture 实例。下面简单看一下其中的一些用例：
     *
     * （1）如果想在链的末端执行一些代码而不返回任何值，比如 Consumer 和 Runnable，那就看看 thenAccept 和 thenRun 方法。
     * （2）可使用 thenApply 方法转换 CompletableFuture 对象的值，有点像使用 Stream 的 map 方法。
     * （3）在 CompletableFuture 对象出现异常时，可使用 exceptionally 方法恢复，将一个函数注册到该方法，返回一个替代值。
     * （4）如果想有一个 map，包含异常情况和正常情况，可使用 handle 方法。
     * （5）要找出 CompletableFuture 对象到底出了什么问题，可使用 isDone 和 isCompletedExceptionally 方法辅助调查。
     *
     * CompletableFuture 对于处理并发任务非常有用，但这并不是唯一的办法。
     */
    public static void main(String[] args) {

    }

}
