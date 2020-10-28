package com.siwuxie095.functional.chapter9th.example3rd;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.platform.Verticle;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 处理用户连接
 *
 * @author Jiajing Li
 * @date 2020-10-28 21:21:37
 */
@SuppressWarnings("all")
public class User implements Handler<Buffer> {

    private final Pattern newline = Pattern.compile("\\n");
    private final NetSocket socket;
    private final Set<String> names;
    private final EventBus eventBus;
    private Optional<String> name;

    public User(NetSocket socket, Verticle verticle) {
        Vertx vertx  = verticle.getVertx();
        this.socket = socket;
        names = vertx.sharedData().getSet("names");
        eventBus = vertx.eventBus();
        name = Optional.empty();
    }

    @Override
    public void handle(Buffer buffer) {
        newline.splitAsStream(buffer.toString())
                .forEach(line -> {
                    if (!name.isPresent()) {
                        setName(line);
                    } else {
                        handleMessage(line);
                    }
                });
    }


    public void setName(String name) {
        this.name = Optional.of(name);
    }

    public void handleMessage(String line) {
        eventBus.registerHandler(name.get(), (Message<String> msg) -> {
            sendClient(msg.body());
        });
    }

    public void sendClient(String message) {
        sendMessage(name.get(), message);
    }


    public void sendMessage(String user, String message) {
        eventBus.send(user, name.get() + ">" + message);
    }

    public void broadcastMessage(String message) {
        String name = this.name.get();
        eventBus.publish(name + ".followers", name + ">" + message);
    }

    public void followUser(String user) {
        eventBus.registerHandler(user + ".followers", (Message<String> message) -> {
            sendClient(message.body());
        });
    }

}
