package com.siwuxie095.functional.chapter9th.example5th;

/**
 * @author Jiajing Li
 * @date 2020-10-29 21:18:24
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 末日金字塔
     *
     * 在 example3rd 中已经看到了如何使用回调和事件编写非阻塞的并发代码，但是还没提起 "房间里的大象"。如果编写
     * 代码时使用了大量的回调，代码会变得难于阅读，即便使用了 Lambda 表达式也是如此。下面通过一个具体例子来更好
     * 地理解这个问题。
     *
     * 在编写聊天程序服务器端代码时，写了很多测试，从客户端的角度描述了 verticle 对象的行为。如下：
     *
     *     @Test
     *     public void messageFriend() {
     *         withModule(() -> {
     *             withConnection(richard -> {
     *                 richard.dataHandler(data -> {
     *                     assertEquals("bob>oh its you!", data.toString());
     *                     moduleTestComplete();
     *                 });
     *
     *                 richard.write("richard\n");
     *                 withConnection(bob -> {
     *                     bob.dataHandler(data -> {
     *                         assertEquals("richard>hai", data.toString());
     *                         bob.write("richard<oh its you!");
     *                     });
     *                     bob.write("bob\n");
     *                     vertx.setTimer(6, id -> richard.write("bob<hai"));
     *                 });
     *             });
     *         });
     *     }
     *
     * 这里连上两个客户端，分别是 Richard 和 Bob，Richard 对 Bob 说 "嗨"，Bob 回答 "哦，是你啊"。这里已经将
     * 建立连接的通用代码重构，即使这样，依然不难注意到那些嵌套的回调形成了一个末日金字塔。代码不断地向屏幕右方挤
     * 过去，就像一座金字塔。这是一个众所周知的反模式，让代码难于阅读和理解。同时，将代码的逻辑分散在了多个方法里。
     *
     * PS：末日金字塔，也叫 厄运金字塔。
     *
     * 重构代码，让它变得易于理解，如下：
     *
     *     @Test
     *     public void canMessageFriend() {
     *         withModule(this::messageFriendWithModule);
     *     }
     *
     *     private void messageFriendWithModule() {
     *         withConnection(richard -> {
     *             checkBobReplies(richard);
     *             richard.write("richard\n");
     *             messageBob(richard);
     *         });
     *     }
     *
     *     private void messageBob(NetSocket richard) {
     *         withConnection(messageBobWithConnection(richard));
     *     }
     *
     *     private Handler<NetSocket> messageBobWithConnection(NetSocket richard) {
     *         return bob -> {
     *             checkRichardMessagedYou(bob);
     *             bob.write("bob\n");
     *             vertx.setTimer(6, id -> richard.write("bob<hai"));
     *         };
     *     }
     *
     *     private void checkRichardMessagedYou(NetSocket bob) {
     *         bob.dataHandler(data -> {
     *             assertEquals("richard>hai", data.toString());
     *             bob.write("richard<oh its you!");
     *         });
     *     }
     *
     *     private void checkBobReplies(NetSocket richard) {
     *         richard.dataHandler(data -> {
     *             assertEquals("bob>oh its you!", data.toString());
     *             moduleTestComplete();
     *         });
     *     }
     *
     *
     * 这里的重构将测试逻辑分散在了多个方法里，解决了末日金字塔问题。不再是一个方法只能有一个功能，而是将一个功能
     * 分散在了多个方法里。然而代码还是难于阅读，不过这次换了一个方式。
     *
     * 想要链接或组合的操作越多，问题就会越严重，所以需要一个更好的解决方案。
     */
    public static void main(String[] args) {

    }

}
