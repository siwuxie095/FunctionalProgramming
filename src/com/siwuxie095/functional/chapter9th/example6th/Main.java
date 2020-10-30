package com.siwuxie095.functional.chapter9th.example6th;

/**
 * @author Jiajing Li
 * @date 2020-10-30 07:10:20
 */
@SuppressWarnings("all")
public class Main {

    /**
     * Future
     *
     * 构建复杂并行操作的另外一种方案是使用 Future。Future 像一张欠条，方法不是返回一个值，而是返回一个 Future 对象，
     * 该对象第一次创建时没有值，但以后能拿它 "换回" 一个值。
     *
     * 调用 Future 对象的 get 方法获取值，它会阻塞当前线程，直到返回值。可惜，和回调一样，组合 Future 对象时也有问题，
     * 下面会快速浏览这些可能碰到的问题。
     *
     * 要考虑的场景是从外部网站查找某专辑的信息。需要找出专辑上的曲目列表和艺术家，还要保证有足够的权限访问登录等各项服
     * 务，或者至少确保已经登录。
     *
     * 如下代码使用 Future API 解决了该问题：
     *
     *     @Override
     *     public Album lookupByName(String albumName) {
     *         Future<Credentials> trackLogin = loginTo("track"); // <1>
     *         Future<Credentials> artistLogin = loginTo("artist");
     *
     *         try {
     *             Future<List<Track>> tracks = lookupTracks(albumName, trackLogin.get()); // <2>
     *             Future<List<Artist>> artists = lookupArtists(albumName, artistLogin.get());
     *
     *             return new Album(albumName, tracks.get(), artists.get()); // <3>
     *         } catch (InterruptedException | ExecutionException e) {
     *             throw new AlbumLookupException(e.getCause()); // <4>
     *         }
     *     }
     *
     * 通过 loginTo 方法登录提供曲目和艺术家信息的服务，这时会返回一个 Future<Credentials> 对象，该对象包含登录信息。
     * Future 接口支持泛型，可将 Future<Credentials> 看作是 Credentials 对象的一张欠条。
     *
     * 再通过 lookupTracks 和 lookupArtists 方法使用登录后的凭证查询曲目和艺术家信息，通过调用 Future 对象的 get
     * 方法获取凭证信息。然后构建待返回的专辑对象 Album，这里同样调用 get 方法以阻塞 Future 对象。如果有异常，这里会
     * 将其转化为一个待解问题域内的异常，然后将其抛出。
     *
     * 不难看出，如果要将 Future 对象的结果传给其他任务，会阻塞当前线程的执行。这会成为一个性能问题，任务不是并行执行，
     * 而是（意外地）串行执行了。
     *
     * 这意味着在登录两个服务之前，无法启动任何查找任务。但其实没必要这样，查询操作不必等待所有登录操作完成后才能执行：
     * lookupTracks 只需要自己的登录凭证，lookupArtists 也是一样。
     *
     * 可以将对 get 的调用放到 lookupTracks 和 lookupArtists 方法的中间，这能解决问题，但是代码丑陋，而且无法在多次
     * 调用之间重用登录凭证。
     *
     * 这里真正需要的是不必调用 get 方法阻塞当前线程，就能操作 Future 对象返回的结果。即 需要将 Future 和回调结合起来
     * 使用。
     */
    public static void main(String[] args) {

    }

}
