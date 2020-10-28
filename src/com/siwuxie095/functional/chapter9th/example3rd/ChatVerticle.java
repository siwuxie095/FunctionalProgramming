package com.siwuxie095.functional.chapter9th.example3rd;


import org.vertx.java.platform.Verticle;


/**
 * 接收 TCP 连接
 *
 * @author Jiajing Li
 * @date 2020-10-28 08:31:18
 */
@SuppressWarnings("all")
public class ChatVerticle extends Verticle {

    @Override
    public void start() {
        vertx.createNetServer()
                .connectHandler(socket -> {
                    container.logger().info("socket connected");
                    socket.dataHandler(new User(socket, this));
                }).listen(10_000);
        container.logger().info("ChatVerticle started");
    }

}
