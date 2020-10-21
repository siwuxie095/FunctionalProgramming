package com.siwuxie095.functional.chapter5th.example10th;

import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import org.junit.Assert;

import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-21 21:41:32
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 使用收集器：重构和定制收集器
     *
     * 尽管在常用流操作里，Java 内置的收集器已经相当好用，而且收集器框架本身是极其通用的。但 JDK 提供的收集器没有什么
     * 特别的，完全可以定制自己的收集器，而且定制起来相当简单。
     *
     * 以格式化艺术家名称为例，在 Java 7 中可能是这样实现的：
     *
     *         StringBuilder builder = new StringBuilder("[");
     *         for (Artist artist : artists) {
     *             if (builder.length() > 1) {
     *                 builder.append(", ");
     *             }
     *             String name = artist.getName();
     *             builder.append(name);
     *         }
     *         builder.append("]");
     *         String result = builder.toString();
     *
     * 显然，这种形式并不优雅。下面将逐步重构这段代码，最终用合适的收集器实现原有代码功能。在工作中没有必要这样做，JDK
     * 已经提供了一个完美的收集器 joining。这里只是为了展示如何定制收集器，以及如何使用 Java 8 提供的新功能来重构遗留
     * 代码。
     *
     * 首先，可以使用 map 操作，将包含艺术家的流映射为包含艺术家姓名的流。如下展示了使用了流的 map 操作重构后的代码：
     *
     *         StringBuilder builder = new StringBuilder("[");
     *         artists.stream()
     *                 .map(Artist::getName)
     *                 .forEach(name -> {
     *                     if (builder.length() > 1) {
     *                         builder.append(", ");
     *                     }
     *                     builder.append(name);
     *                 });
     *         builder.append("]");
     *         String result = builder.toString();
     *
     * 将艺术家映射为姓名，就能更快看出最终是要生成什么，这样代码看起来更清楚一点。可惜 forEach 方法看起来还是有点笨重，
     * 这与通过组合高级操作让代码变得易读的目标不符。
     *
     * 暂且不必考虑定制一个收集器，先想想怎么通过流上已有的操作来解决该问题。和生成字符串目标最近的操作就是 reduce，使
     * 用它来重构代码，如下：
     *
     *         StringBuilder reduced =
     *                 artists.stream()
     *                         .map(Artist::getName)
     *                         .reduce(new StringBuilder(), (builder, name) -> {
     *                             if (builder.length() > 0) {
     *                                 builder.append(", ");
     *                             }
     *                             builder.append(name);
     *                             return builder;
     *                         }, (left, right) -> left.append(right));
     *         reduced.insert(0, "[");
     *         reduced.append("]");
     *         String result = reduced.toString();
     *
     * 本以为上面的重构会让代码变得更清晰，可惜恰好相反，代码看起来比以前更糟糕。
     *
     * 先来看看怎么回事。和前面的例子一样，都调用了 stream 和 map 方法，reduce 操作生成艺术家姓名列表，艺术家与艺术
     * 家之间用 "," 分隔。首先创建一个 StringBuilder 对象，该对象是 reduce 操作的初始状态，然后使用 Lambda 表达式
     * 将姓名连接到 builder 上。reduce 操作的第三个参数也是一个 Lambda 表达式，接受两个 StringBuilder 对象做参数，
     * 将两者连接起来。最后添加前缀和后缀。
     *
     * 在接下来的重构中，还是使用 reduce 操作，不过需要将杂乱无章的代码隐藏掉，即 使用一个 StringCombiner 类对细节
     * 进行抽象。如下：
     *
     *         StringCombiner combined =
     *                 artists.stream()
     *                         .map(Artist::getName)
     *                         .reduce(new StringCombiner(", ", "[", "]"),
     *                                 StringCombiner::add,
     *                                 StringCombiner::merge);
     *         String result = combined.toString();
     *         System.out.println(result);
     *
     * 尽管代码看起来和上个例子大相径庭，但其实背后做的工作是一样的。上个例子使用 reduce 操作将姓名和分隔符连接成一个
     * StringBuilder 对象。而在这个例子中，连接姓名操作被 StringCombiner.add 方法代理，连接两个连接器操作被
     * StringCombiner.merge 方法代理。
     *
     * 对于 StringCombiner.add 方法，实现如下：
     *
     *     public StringCombiner add(String element) {
     *         if(!this.isAtStart()) {
     *             this.builder.append(delimiter);
     *         }
     *         this.builder.append(element);
     *         return this;
     *     }
     *
     * add 方法在内部其实将操作代理给一个 StringBuilder 对象。如果刚开始进行连接，则直接添加新元素，否则先添加分隔符，
     * 再添加新元素。这里返回一个 StringCombiner 对象，因为这是传给 reduce 操作所需要的类型。
     *
     * 对于 StringCombiner.merge 方法，实现如下：
     *
     *     public StringCombiner merge(StringCombiner other) {
     *         if(!other.equals(this)) {
     *             if(!other.isAtStart() && !this.isAtStart()){
     *                 other.builder.insert(0, this.delimiter);
     *             }
     *             this.builder.append(other.builder);
     *         }
     *         return this;
     *     }
     *
     * merge 方法也是同样的道理，内部将操作代理给 StringBuilder 对象。
     *
     * reduce 阶段的重构还差一小步就差不多结束了。现在要在最后调用 toString 方法，将整个步骤串成一个方法链。这很简单，
     * 只需要排列好 reduce 代码，准备好将其转换为 Collector API 就行了。如下：
     *
     *         String result =
     *                 artists.stream()
     *                         .map(Artist::getName)
     *                         .reduce(new StringCombiner(", ", "[", "]"),
     *                                 StringCombiner::add,
     *                                 StringCombiner::merge)
     *                         .toString();
     *
     * 现在的代码看起来已经差不多完美了，但是在程序中还是不能重用。因此想将 reduce 操作重构为一个收集器，在程序中的任何
     * 地方都能使用。不妨将这个收集器叫作 StringCollector，重构代码并使用这个新的收集器，如下：
     *
     *         String result =
     *                 artists.stream()
     *                         .map(Artist::getName)
     *                         .collect(new StringCollector(", ", "[", "]"));
     *
     * 既然已经将所有对字符串的连接操作代理给了定制的收集器，应用程序就不需要关心 StringCollector 对象的任何内部细节，
     * 它和框架中其他 Collector 对象用起来是一样的。
     *
     * 先来实现 Collector 接口，由于 Collector 接口支持泛型，因此先得确定一些具体的类型：
     * （1）待收集元素的类型，这里是 String；
     * （2）累加器的类型StringCombiner；
     * （3）最终结果的类型，这里依然是String。
     *
     * 然后就可以定义字符串收集器，如下：
     *
     * public class StringCollector implements Collector<String, StringCombiner, String>
     *
     * 一个收集器由四部分组成。第一部分是 Supplier，是一个工厂方法，用来创建容器，本例中要创建的就是 StringCombiner。
     * 和 reduce 操作中的第一个参数类似，它是后续操作的初值。如下：
     *
     *     @Override
     *     public Supplier<StringCombiner> supplier() {
     *         return () -> new StringCombiner(delimiter, prefix, suffix);
     *     }
     *
     * 第二部分是 Accumulator，它的作用和 reduce 操作的第二个参数一样，它结合之前操作的结果和当前值，生成并返回新的值。
     * 这一逻辑已经在 StringCombiner 的 add 方法中得以实现，直接引用即可，如下：
     *
     *     @Override
     *     public BiConsumer<StringCombiner, String> accumulator() {
     *         return StringCombiner::add;
     *     }
     *
     * 第三部分是 Combiner，它的作用很像 reduce 操作的第三个参数。如果有两个容器，需要将其合并。同样，在前面的重构中
     * 已经实现了该功能，直接使用 StringCombiner.merge 方法即可，如下：
     *
     *     @Override
     *     public BinaryOperator<StringCombiner> combiner() {
     *         return StringCombiner::merge;
     *     }
     *
     * 第四部分是 Finisher，在使用 StringCollector 收集器之前，重构的最后一步将 toString 方法内联到方法链的末端，
     * 这就将 StringCombiner 转换成了想要的字符串。而 Finisher 作用与之相同。
     *
     * 已经将流中的值叠加入一个可变容器中，但这还不是想要的最终结果。这里调用了 finisher 方法，以便进行转换。在想创建
     * 字符串等不可变的值时特别有用，而这里容器是可变的。
     *
     * 为了实现 finisher 方法，只需将该操作代理给已经实现的 StringCombiner.toString 方法即可，如下：
     *
     *     @Override
     *     public Function<StringCombiner, String> finisher() {
     *         return StringCombiner::toString;
     *     }
     *
     * 总体来说，收集操作一开始，Supplier 先创建出新的容器。然后 Accumulator 将流中的值叠加入容器中。在收集阶段，
     * 容器被 Combiner 成对合并进一个容器，直到最后只剩一个容器为止。最后，通过 Finisher 从最后剩下的容器中得到
     * 最终结果。
     *
     * 关于收集器，还有一点一直没有提及，那就是特征。特征是一组描述收集器的对象，框架可以对其适当优化。
     *
     * PS：characteristics 方法定义了特征。
     *
     * 注意，这里重构的收集器和 joining 收集器的内部实现略有出入。StringCombiner 看起来非常有用，但没必要亲自去编写，
     * Java 8 有一个 java.util.StringJoiner 类，它的作用和 StringCombiner 一样，并且有类似的 API。
     *
     * 做这些练习的主要目的不仅在于展示定制收集器的工作原理，而且还在于帮助程序员编写自己的收集器。特别是有自己特定领域
     * 内的类，希望从集合中构建一个操作，而标准的集合类并没有提供这种操作时，就需要定制自己的收集器。
     *
     * 以 StringCombiner 为例，收集值的容器和想要创建的值（字符串）不一样。如果想要收集的是不可变对象，而不是可变对象，
     * 那么这种情况就非常普遍，否则收集操作的每一步都需要创建一个新值。
     *
     * PS：即 如果想收集的是不可变对象，那么只需要在最后一步创建出这个不可变对象，中间操作使用容器（可变对象）即可。
     *
     * 想要收集的最终结果和容器一样是完全有可能的。事实上，如果收集的最终结果是集合，比如 toList 收集器，就属于这种情况。
     *
     * 此时，finisher 方法不需要对容器做任何操作。更确切地说，此时的 finisher 方法其实是一个 identity 函数：它返回传
     * 入参数的值。如果这样，收集器就展现出 IDENTITY_FINISH 的特征，需要使用 characteristics 方法声明。
     */
    public static void main(String[] args) {
        formatByFor(SampleData.membersOfTheBeatles);
        formatByMap(SampleData.membersOfTheBeatles);
        formatByReduce(SampleData.membersOfTheBeatles);
        formatByCombine(SampleData.membersOfTheBeatles);
        formatByCombineToString(SampleData.membersOfTheBeatles);
        formatByStringCollector(SampleData.membersOfTheBeatles);
    }

    /**
     * 使用 for 循环和 StringBuilder 格式化艺术家姓名
     */
    private static void formatByFor(List<Artist> artists) {
        StringBuilder builder = new StringBuilder("[");
        for (Artist artist : artists) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            String name = artist.getName();
            builder.append(name);
        }
        builder.append("]");
        String result = builder.toString();
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

    /**
     * 使用 forEach 和 StringBuilder 格式化艺术家姓名
     */
    private static void formatByMap(List<Artist> artists) {
        StringBuilder builder = new StringBuilder("[");
        artists.stream()
                .map(Artist::getName)
                .forEach(name -> {
                    if (builder.length() > 1) {
                        builder.append(", ");
                    }
                    builder.append(name);
                });
        builder.append("]");
        String result = builder.toString();
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

    /**
     * 使用 reduce 和 StringBuilder 格式化艺术家姓名
     */
    private static void formatByReduce(List<Artist> artists) {
        StringBuilder reduced =
                artists.stream()
                        .map(Artist::getName)
                        .reduce(new StringBuilder(), (builder, name) -> {
                            if (builder.length() > 0) {
                                builder.append(", ");
                            }
                            builder.append(name);
                            return builder;
                        }, (left, right) -> left.append(right));
        reduced.insert(0, "[");
        reduced.append("]");
        String result = reduced.toString();
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

    /**
     * 使用 reduce 和 StringCombiner 格式化艺术家姓名
     */
    private static void formatByCombine(List<Artist> artists) {
        StringCombiner combined =
                artists.stream()
                        .map(Artist::getName)
                        .reduce(new StringCombiner(", ", "[", "]"),
                                StringCombiner::add,
                                StringCombiner::merge);
        String result = combined.toString();
        System.out.println(result);
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

    /**
     * 使用 reduce 操作，将工作代理给 StringCombiner 对象
     */
    private static void formatByCombineToString(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .reduce(new StringCombiner(", ", "[", "]"),
                                StringCombiner::add,
                                StringCombiner::merge)
                        .toString();
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }

    /**
     * 使用定制的收集器 StringCollector 收集字符串
     */
    private static void formatByStringCollector(List<Artist> artists) {
        String result =
                artists.stream()
                        .map(Artist::getName)
                        .collect(new StringCollector(", ", "[", "]"));
        Assert.assertEquals("[John Lennon, Paul McCartney, George Harrison, Ringo Starr]", result);
    }
}
