package com.siwuxie095.functional.chapter9th.example3rd;

/**
 * @author Jiajing Li
 * @date 2020-10-28 08:27:09
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 回调
     *
     * 为了展示非阻塞式 I/O 的原则，这里将运行一个极其简单的聊天应用，没有那些花里胡哨的功能。当用户第一次连接应用时，
     * 需要设定用户名，随后便可通过应用收发信息。
     *
     * 这里将使用 Vert.x 框架实现该应用，并且在实施过程中根据需要，引入其他一些必需的技术。先来写一段接收 TCP 连接
     * 的代码，如下所示：
     *
     * public class ChatVerticle extends Verticle {
     *
     *     @Override
     *     public void start() {
     *         vertx.createNetServer()
     *                 .connectHandler(socket -> {
     *                     container.logger().info("socket connected");
     *                     socket.dataHandler(new User(socket, this));
     *                 }).listen(10_000);
     *         container.logger().info("ChatVerticle started");
     *     }
     *
     * }
     *
     * 可以将 Verticle 看作是 Servlet，它是 Vert.x 框架中部署的原子单元。上述代码的入口是 start 方法，它和普通
     * Java 程序中的 main 方法类似。在聊天应用中，用它来建立一个接收 TCP 连接的服务器。
     *
     * 然后向 connectHandler 方法输入一个 Lambda 表达式，每当有用户连接到聊天应用时，都会调用该 Lambda 表达式。
     * 这就是一个回调，它和 Swing 中的回调类似。这种方式的好处是，应用不必控制线程模型，而由 Vert.x 框架来管理线程，
     * 打理好了一切相关复杂性，程序员只需考虑事件和回调就够了。
     *
     * 同时，应用还通过 dataHandler 方法注册了另外一个回调，每当从网络套接字读取数据时，该回调就会被调用。这里希望
     * 提供更复杂的功能，因此没有使用 Lambda 表达式，而是传入一个常规的 User 类，该类实现了相关的函数式接口。
     *
     * User 类实现如下：
     *
     * public class User implements Handler<Buffer> {
     *
     *     private final Pattern newline = Pattern.compile("\\n");
     *     private final NetSocket socket;
     *     private final Set<String> names;
     *     private final EventBus eventBus;
     *     private Optional<String> name;
     *
     *     public User(NetSocket socket, Verticle verticle) {
     *         Vertx vertx  = verticle.getVertx();
     *         this.socket = socket;
     *         names = vertx.sharedData().getSet("names");
     *         eventBus = vertx.eventBus();
     *         name = Optional.empty();
     *     }
     *
     *     @Override
     *     public void handle(Buffer buffer) {
     *         newline.splitAsStream(buffer.toString())
     *                 .forEach(line -> {
     *                     if (!name.isPresent()) {
     *                         setName(line);
     *                     } else {
     *                         handleMessage(line);
     *                     }
     *                 });
     *     }
     *
     *
     *     public void setName(String name) {
     *         this.name = Optional.of(name);
     *     }
     *
     *     public void handleMessage(String line) {
     *         eventBus.registerHandler(name.get(), (Message<String> msg) -> {
     *             sendClient(msg.body());
     *         });
     *     }
     *
     *     public void sendClient(String message) {
     *         sendMessage(name.get(), message);
     *     }
     *
     *
     *     public void sendMessage(String user, String message) {
     *         eventBus.send(user, name.get() + ">" + message);
     *     }
     *
     *     public void broadcastMessage(String message) {
     *         String name = this.name.get();
     *         eventBus.publish(name + ".followers", name + ">" + message);
     *     }
     *
     *     public void followUser(String user) {
     *         eventBus.registerHandler(user + ".followers", (Message<String> message) -> {
     *             sendClient(message.body());
     *         });
     *     }
     *
     * }
     *
     * 变量 buffer 包含了网络连接写入的数据，这里使用的是一个分行的文本协议，因此需要先将其转换成一个字符串，然后依
     * 换行符分割。
     *
     * 这里使用了正则表达式 Pattern 的一个实例 newline 来匹配换行符。尤为方便的是，Java 8 为 Pattern 类新增了一
     * 个 splitAsStream 方法，该方法使用正则表达式将字符串分割好后，生成一个包含分割结果的流对象。
     *
     * 用户连上聊天服务器后，首先要做的事是设置用户名。如果用户名未知，则执行设置用户名的逻辑;否则正常处理聊天消息。
     *
     * 还需要接收来自其他用户的消息，并且将它们传递给聊天程序客户端，让接收者能够读取消息。为了实现该功能，在设置当前
     * 用户用户名的同时，注册了另外一个回调，用来写入消息。如下：
     *
     *     public void handleMessage(String line) {
     *         eventBus.registerHandler(name.get(), (Message<String> msg) -> {
     *             sendClient(msg.body());
     *         });
     *     }
     *
     * 上述代码使用了 Vert.x 的事件总线，它允许在 verticle 对象之间以非阻塞式 I/O 的方式传递消息。registerHandler
     * 方法将一个处理程序和一个地址关联，有消息发送给该地址时，就将之作为参数传递给处理程序，并且自动调用处理程序。这
     * 里使用用户名作为地址。
     *
     * 通过为地址注册处理程序并发消息的方式，可以构建非常复杂和解耦的服务，它们之间完全以非阻塞式 I/O 方式响应。需要
     * 注意的是，这里的设计中没有共享状态。
     *
     * Vert.x 的事件总线允许发送多种类型的消息，但是它们都要使用 Message 对象进行封装。点对点的消息传递由 Message
     * 对象本身完成，它们可能持有消息发送方的应答处理程序。在这种情况下，想要的是消息体，也就是文字本身，则只需调用
     * body 方法。这里通过将消息写入 TCP 连接，把消息发送给了用户聊天客户端。
     *
     * 当应用想要把消息从一个用户发送给另一个用户时，就使用代表另一个用户的地址，这里使用了用户的用户名。如下：
     *
     *     public void sendMessage(String user, String message) {
     *         eventBus.send(user, name.get() + ">" + message);
     *     }
     *
     * 下面扩展一下这个基础聊天服务器，向关注你的用户群发消息，为此，需要实现两个新命令。
     * （1）代表群发命令的感叹号，它能将信息群发给关注你的用户。如果 Bob 键入 "!hello followers"，则所有关注 Bob
     * 的用户都会收到该条信息 "Bob>hello followers"。
     * （2）关注命令，用来关注一个用户，比如 "follow Bob"。
     *
     * 一旦解析了命令，就可以着手实现 broadcastMessage 和 followUser 方法，它们分别代表了这两个命令。
     *
     * 这里的通信模式略有不同，除了给单个用户发消息，现在还拥有了群发信息的能力。幸好，Vert.x 的事件总线可以将一条信
     * 息发布给多个处理程序。
     *
     * 代码的唯一变化是使用了事件总线的 publish 方法，而不是先前的 send 方法。为了避免用户使用 ! 命令时和已有的地
     * 址冲突，在用户名后紧跟 .followers。比如 Bob 发布一条消息时，所有注册到 bob.followers 的处理程序都会收到
     * 消息。如下：
     *
     *     public void broadcastMessage(String message) {
     *         String name = this.name.get();
     *         eventBus.publish(name + ".followers", name + ">" + message);
     *     }
     *
     * 在处理程序里，和早先的操作一样：将消息传递给客户。如下：
     *
     *     public void followUser(String user) {
     *         eventBus.registerHandler(user + ".followers", (Message<String> message) -> {
     *             sendClient(message.body());
     *         });
     *     }
     *
     * PS：如果将消息发送到有多个处理程序监听的地址，则会轮询决定哪个处理程序会接收到消息。这意味着在注册地址时要多加
     * 小心。
     */
    public static void main(String[] args) {

    }

}
