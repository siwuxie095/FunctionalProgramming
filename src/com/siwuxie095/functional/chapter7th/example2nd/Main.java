package com.siwuxie095.functional.chapter7th.example2nd;

import com.siwuxie095.functional.chapter4th.example2nd.MyLogger;
import com.siwuxie095.functional.common.Album;

/**
 * @author Jiajing Li
 * @date 2020-10-25 10:07:52
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 重构候选项
     *
     * 使用 Lambda 表达式重构代码有个时髦的称呼：Lambda 化（读作 lambda-fi-cation，执行重构的程序员叫作 lamb-di-fiers
     * 或者有责任心的程序员）。Java 8 中的核心类库就曾经历过这样一场重构。在选择内部设计模型时，想想以何种形式向外展示 API
     * 是大有裨益的。
     *
     * 这里有一些要点，可以帮助程序员确定什么时候应该 Lambda 化自己的应用或类库。其中的每一条都可看作一个局部的反模式或代码
     * 异味，借助于 Lambda 化可以修复。
     *
     *
     *
     * 进进出出、摇摇晃晃
     *
     * 如下代码是关于如何在程序中记录日志的。这段代码先调用 isDebugEnabled 方法抽取布尔值，用来检查是否启用调试级别，如果
     * 启用，则调用 MyLogger 对象的相应方法记录日志。如果发现自己的代码不断地查询和操作某对象，目的只为了在最后给该对象设
     * 个值，那么这段代码就本该属于你所操作的对象。
     *
     *         MyLogger logger = new MyLogger();
     *         if (logger.isDebugEnabled()) {
     *             logger.debug("Look at this: " + expensiveOperation());
     *         }
     *
     * 记录日志本来就是一直以来很难实现的目标，因为地方不同，所需的行为也不一样。本例中，需要根据程序中记录日志的不同位置和
     * 要记录的内容生成不同的信息。
     *
     * 这种反模式通过传入代码即数据的方式很容易解决。与其查询并设置一个对象的值，不如传入一个 Lambda 表达式，该表达式按照
     * 计算得出的值执行相应的行为。如下：
     *
     *         MyLogger logger = new MyLogger();
     *         logger.debug(() -> "Look at this: " + expensiveOperation());
     *
     * 当程序处于调试级别，并且检查是否使用 Lambda 表达式的逻辑被封装在 MyLogger 对象中时，才会调用 Lambda 表达式。
     *
     * 上述记录日志的例子也展示了如何使用 Lambda 表达式更好地面向对象编程（OOP），面向对象编程的核心之一是封装局部状态，比
     * 如日志的级别。通常这点做得不是很好，isDebugEnabled 方法暴露了内部状态。如果使用 Lambda 表达式，外面的代码根本不需
     * 要检查日志级别。
     *
     *
     *
     * 孤独的覆盖
     *
     * 这个代码异味是使用继承，其目的只是为了覆盖一个方法。ThreadLocal 就是一个很好的例子。ThreadLocal 能创建一个工厂，
     * 为每个线程最多只产生一个值。这是确保非线程安全的类在并发环境下安全使用的一种简单方式。假设要在数据库中查询一个艺术家，
     * 但希望每个线程只做一次这种查询，写出的代码可能如下所示：
     *
     *         ThreadLocal<Album> thisAlbum = new ThreadLocal<Album> () {
     *             @Override
     *             protected Album initialValue() {
     *             return database.lookupCurrentAlbum();
     *             }
     *         };
     *
     * 在 Java 8 中，可以使用工厂方法 withInitial 传入一个 Supplier 对象的实例来创建对象，如下所示：
     *
     *         ThreadLocal<Album> thisAlbum
     *                 = ThreadLocal.withInitial(() -> database.lookupCurrentAlbum());
     *
     * 这里认为第二个例子优于前一个有以下几个原因。
     *
     * 首先，任何已有的 Supplier<Album> 实例不需要重新封装，就可以在此使用，这鼓励了重用和组合。
     *
     * 其次，在其他都一样的情况下，代码短小精悍就是个优势。更重要的是，这是代码更加清晰的结果，阅读代码时，信噪比降低了。这
     * 意味着有更多时间来解决实际问题，而不是把时间花在继承的样板代码上。
     *
     * 最后，这样做还有一个优点，JVM 会少加载一个类。
     *
     * 对每个试图阅读代码，弄明白代码意图的人来说，也清楚了很多。如果你试着大声念出第二个例子中的单词，能很容易听出是干嘛的，
     * 但第一个例子就不行了。
     *
     * 有趣的是，在 Java 8 以前，这并不是一个反模式，而是惯用的代码编写方式，就像使用匿名内部类传递行为一样，都不是反模式，
     * 而是在 Java 中表达你所想的唯一方式。随着语言的演进，编程习惯也要与时俱进。
     *
     *
     *
     * 同样的东西写两遍
     *
     * 不要重复你劳动（Don’t Repeat Yourself，DRY）是一个众所周知的模式，它的反面是同样的东西写两遍（Write Everything
     * Twice，WET）。这种代码异味多见于重复的样板代码，产生了更多需要测试的代码，这样的代码难于重构，一改就坏。
     *
     * 不是所有 WET 的情况都适合 Lambda 化。有时，重复是唯一可以避免系统过紧耦合的方式。什么时候该将 WET 的代码 Lambda
     * 化？这里有一个信号可以参考。如果有一个整体上大概相似的模式，只是行为上有所不同，就可以试着加入一个 Lambda 表达式。
     *
     * 来看一个更具体的例子。回到有关音乐的问题，现在想增加一个简单的 Order 类来计算用户购买专辑的一些有用属性，如计算音乐
     * 家人数、曲目和专辑时长等。如果使用命令式 Java，编写出的代码见 OrderImperative 类。其中的方法如下：
     *
     *     @Override
     *     public long countRunningTime() {
     *         long count = 0;
     *         for (Album album : albums) {
     *             for (Track track : album.getTrackList()) {
     *                 count += track.getLength();
     *             }
     *         }
     *         return count;
     *     }
     *
     *     @Override
     *     public long countMusicians() {
     *         long count = 0;
     *         for (Album album : albums) {
     *             count += album.getMusicianList().size();
     *         }
     *         return count;
     *     }
     *
     *     @Override
     *     public long countTracks() {
     *         long count = 0;
     *         for (Album album : albums) {
     *             count += album.getTrackList().size();
     *         }
     *         return count;
     *     }
     *
     * 其中每个方法里，都有样板代码将每个专辑里的属性和总数相加，比如每首曲目的长度或音乐家的人数。这里没有重用共有的概念，
     * 写出了更多需要测试和维护的代码。
     *
     * 可以使用 Stream 来抽象，使用 Java 8 中的集合类库来重写上述代码，使之更紧凑。如果直接将上述命令式的代码翻译成使用
     * 流的形式，编写出的代码见 OrderStream 类。其中的方法如下：
     *
     *     @Override
     *     public long countRunningTime() {
     *         return albums.stream()
     *                 .mapToLong(album -> album.getTracks()
     *                         .mapToLong(track -> track.getLength())
     *                         .sum())
     *                 .sum();
     *     }
     *
     *     @Override
     *     public long countMusicians() {
     *         return albums.stream()
     *                 .mapToLong(album -> album.getMusicians().count())
     *                 .sum();
     *     }
     *
     *     @Override
     *     public long countTracks() {
     *         return albums.stream()
     *                 .mapToLong(album -> album.getTracks().count())
     *                 .sum();
     *     }
     *
     * 然而这段代码仍然有重用可读性的问题，因为有一些抽象和共性只能使用领域内的知识来表达。流不会提供一个方法统计每张专辑上
     * 的信息，这是程序员要自己编写的领域知识。这也是在 Java 8 出现之前很难编写的领域方法，因为每个方法都不一样。
     *
     * 想一下如何实现这样一个函数。返回一个 long，统计所有专辑的某些特征，还需要一个 Lambda 表达式，告诉统计专辑上的什么
     * 信息。也就是说该函数需要一个参数，这个参数为每张专辑返回一个 long。方便的是，Java 8 核心类库中已经有了这样一个类型
     * ToLongFunction。它的类型随参数类型，因此要使用的类型为 ToLongFunction<Album>。
     *
     * 这些都定下来之后，方法体就自然定下来了。这里将专辑转换成流，然后将专辑映射为 long，然后求和。在实现直接面对客户的代
     * 码时，比如 countTracks，传入一个代表了领域知识的 Lambda 表达式，即 将专辑映射为其中的曲目。
     *
     * 编写出的代码见 OrderDomain 类。其中的方法如下：
     *
     *     public long countFeature(ToLongFunction<Album> function) {
     *         return albums.stream()
     *                 .mapToLong(function)
     *                 .sum();
     *     }
     *
     *     @Override
     *     public long countRunningTime() {
     *         return countFeature(album -> album.getTracks()
     *                 .mapToLong(track -> track.getLength())
     *                 .sum());
     *     }
     *
     *     @Override
     *     public long countMusicians() {
     *         return countFeature(album -> album.getMusicians().count());
     *     }
     *
     *     @Override
     *     public long countTracks() {
     *         return countFeature(album -> album.getTracks().count());
     *     }
     */
    public static void main(String[] args) {
        debugLogWithIf();
        debugLogWithoutIf();
        threadLocalWithOverride();
        threadLocalWithSupplier();
    }

    /**
     * logger 对象使用 isDebugEnabled 属性避免不必要的性能开销
     */
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

    /**
     * 使用 Lambda 表达式简化记录日志代码
     */
    private static void debugLogWithoutIf() {
        MyLogger logger = new MyLogger();
        logger.debug(() -> "Look at this: " + expensiveOperation());
    }

    /**
     * 使用继承和覆盖的方式在数据库中查找艺术家
     */
    private static void threadLocalWithOverride() {
        Database database = new Database();
        ThreadLocal<Album> thisAlbum = new ThreadLocal<Album> () {
            @Override
            protected Album initialValue() {
            return database.lookupCurrentAlbum();
            }
        };
    }

    /**
     * 使用工厂方法在数据库中查找艺术家
     */
    private static void threadLocalWithSupplier() {
        Database database = new Database();
        ThreadLocal<Album> thisAlbum
                = ThreadLocal.withInitial(() -> database.lookupCurrentAlbum());
    }

}
