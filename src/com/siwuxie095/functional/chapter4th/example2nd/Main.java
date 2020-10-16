package com.siwuxie095.functional.chapter4th.example2nd;

/**
 * @author Jiajing Li
 * @date 2020-10-15 22:28:52
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 在代码中使用 Lambda 表达式
     *
     * 从调用 Lambda 表达式的代码的角度来看，它和调用一个普通接口方法没什么区别。
     *
     * 来看一个日志系统中的具体案例。在 slf4j 和 log4j 等几种常用的日志系统中，有一些记录日志的方法，当日志级别
     * 不低于某个固定级别时就会开始记录日志。如此一来，在日志框架中设置类似 void debug(String message) 这样的
     * 方法，当级别为 debug 时，它们就开始记录日志消息。
     *
     * 问题在于，频繁计算消息是否应该记录日志会对系统性能产生影响。程序员可以通过显式调用 isDebugEnabled 方法来
     * 优化系统性能。如下：
     *
     *     private static void debugLogWithIf() {
     *         MyLogger logger = new MyLogger();
     *         if (logger.isDebugEnabled()) {
     *             logger.debug("Look at this: " + expensiveOperation());
     *         }
     *     }
     *
     * MyLogger 类中对应的方法如下：
     *
     *     private static final Logger logger = LoggerFactory.getLogger("MyLogger");
     *
     *     public boolean isDebugEnabled() {
     *         return logger.isDebugEnabled();
     *     }
     *
     *     public void debug(String msg) {
     *         logger.debug(msg);
     *     }
     *
     * 当日志级别高于 DEBUG 时，此时直接调用 debug 方法不会打出日志，但仍然需要调用 expensiveOperation 方法，
     * 并且需要将执行结果和已有字符串连接起来。因此，使用 if 语句显式判断，可以让程序跑得更快。
     *
     * （8 个日志级别，参考链接：https://blog.csdn.net/shiyong1949/article/details/52643711）
     *
     * 这里想做的是传入一个 Lambda 表达式，生成一条用作日志信息的字符串。只有日志级别在 DEBUG 或以下级别时，才
     * 会执行该 Lambda 表达式。用这个方式重写上面的代码，如下：
     *
     *     private static void debugLogWithoutIf() {
     *         MyLogger logger = new MyLogger();
     *         logger.debug(() -> "Look at this: " + expensiveOperation());
     *     }
     *
     * 那么在 MyLogger 类中该方法是如何实现的呢？从类库的角度看，可以使用内置的 Supplier 函数式接口，它只有一个
     * get 方法。然后通过调用 isDebugEnabled 判断是否需要记录日志，是否需要调用 get 方法，如果需要，就调用 get
     * 方法并将结果传给 debug 方法。
     *
     * MyLogger 类中对应的方法如下：
     *
     *     public void debug(Supplier<String> supplier) {
     *         if (isDebugEnabled()) {
     *             debug(supplier.get());
     *         }
     *     }
     *
     * 通过调用 get() 方法，相当于调用传入的 Lambda 表达式。这种方式也能和匿名内部类一起工作，如果暂时无法升级到
     * Java 8，这种方式可以实现向后兼容。
     *
     * 值得注意的是，不同的函数式接口有不同的方法。如果使用 Predicate，就应该调用 test 方法，如果使用 Function，
     * 就应该调用 apply 方法。
     */
    public static void main(String[] args) {
        debugLogWithIf();
        debugLogWithoutIf();
    }

    private static void debugLogWithIf() {
        MyLogger logger = new MyLogger();
        if (logger.isDebugEnabled()) {
            logger.debug("Look at this: " + expensiveOperation());
        }
    }

    private static String expensiveOperation() {
        // do some expensive operation
        return "expensiveOperation";
    }

    private static void debugLogWithoutIf() {
        MyLogger logger = new MyLogger();
        logger.debug(() -> "Look at this: " + expensiveOperation());
    }




}
