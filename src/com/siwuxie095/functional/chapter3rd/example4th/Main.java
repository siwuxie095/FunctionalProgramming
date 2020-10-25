package com.siwuxie095.functional.chapter3rd.example4th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.Track;
import org.junit.Assert;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-11 22:12:08
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 常用的流操作
     *
     * 为了更好地理解 Stream API，掌握一些常用的 Stream 操作十分必要。
     *
     *
     *
     * 1、collect(toList())
     *
     * 通过 collect(toList()) 方法，可以由 Stream 里的值生成一个列表，是一个及早求值方法。
     *
     * Stream 的 of 方法使用一组初始值生成新的 Stream。事实上，collect 的用法不仅限于此，它是一个非常通用的强大结构，
     * 这里先按下不表。如下是使用 collect 方法的一个例子：
     *
     *         List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());
     *         Assert.assertEquals(Arrays.asList("a", "b", "c"), collected);
     *
     * 这段代码展示了如何使用 collect(toList()) 方法从 Stream 中生成一个列表。由于很多 Stream 操作都是惰性求值，因
     * 此调用 Stream 上一系列方法之后，还需要最后再调用一个类似 collect 的及早求值方法。
     *
     * 这段代码首先由列表生成一个 Stream，然后进行一些 Stream 上的操作，继而是 collect 操作，由 Stream 生成列表，最
     * 后使用断言判断结果是否和预期一致。
     *
     * 形象一点儿的话，可以将 Stream 想象成汉堡，将最前和最后对 Stream 操作的方法想象成两片面包，这两片面包有助于认清
     * 操作的起点和终点。
     *
     *
     *
     * 2、map
     *
     * 如果有一个函数可以将一种类型的值转换成另外一种类型，map 操作就可以使用该函数，将一个流中的值转换成一个新的流。
     *
     * 不难发现，以前编程时已经或多或少使用过类似 map 的操作。比如编写一段 Java 代码，将一组字符串转换成对应的大写形式。
     * 在一个循环中，对每个字符串调用 toUppercase 方法，然后将得到的结果加入一个新的列表。如下是使用 for 循环将字符串
     * 转换为大写：
     *
     *         List<String> collected = new ArrayList<>();
     *         for (String string : Arrays.asList("a", "b", "hello")) {
     *             String upperCaseString = string.toUpperCase();
     *             collected.add(upperCaseString);
     *         }
     *         Assert.assertEquals(Arrays.asList("A", "B", "HELLO"), collected);
     *
     * 如果经常实现这样的 for 循环，就不难猜出 map 是 Stream 上最常用的操作之一。如下是使用 map 操作将字符串转换为大
     * 写形式：
     *
     *         List<String> collected = Stream.of("a", "b", "hello")
     *                 .map(string -> string.toUpperCase())
     *                 .collect(Collectors.toList());
     *         Assert.assertEquals(Arrays.asList("A", "B", "HELLO"), collected);
     *
     * 这段代码展示了如何使用新的流框架将一组字符串转换成大写形式。传给 map 的 Lambda 表达式只接受一个 String 类型的
     * 参数，返回一个新的 String。参数和返回值不必属于同一种类型，但是 Lambda 表达式必须是 Function 接口的一个实例，
     * Function 接口是只包含一个参数的函数式接口。
     *
     *
     *
     * 3、filter
     *
     * 遍历数据并检查（即 过滤）其中的元素时，可尝试使用 Stream 中提供的新方法 filter。
     *
     * 假设要找出一组字符串中以数字开头的字符串，比如，字符串 "1abc" 和 "abc"，其中 "1abc" 就是符合条件的字符串。可以
     * 使用一个 for 循环，内部用 if 条件语句判断字符串的第一个字符来解决这个问题。如下是使用循环遍历列表，并使用条件语句
     * 做判断：
     *
     *         List<String> beginningWithNumbers = new ArrayList<>();
     *         for(String value : Arrays.asList("a", "1abc", "abc1")) {
     *             if (Character.isDigit(value.charAt(0))) {
     *                 beginningWithNumbers.add(value);
     *             }
     *         }
     *         Assert.assertEquals(Arrays.asList("1abc"), beginningWithNumbers);
     *
     * 这被称为 filter 模式。该模式的核心思想是保留 Stream 中的一些元素，而过滤掉其他的。如下代码展示了如何使用函数式
     * 风格编写相同的代码：
     *
     *         List<String> beginningWithNumbers = Stream.of("a", "1abc", "abc1")
     *                 .filter(value -> Character.isDigit(value.charAt(0)))
     *                 .collect(Collectors.toList());
     *         Assert.assertEquals(Arrays.asList("1abc"), beginningWithNumbers);
     *
     * 和 map 很像，filter 接受一个函数作为参数，该函数用 Lambda 表达式表示。该函数和前面示例中 if 条件判断语句的功能
     * 一样，如果字符串首字母为数字，则返回 true。若要重构遗留代码，for 循环中的 if 条件语句就是一个很强的信号，可用
     * filter 方法替代。
     *
     * 由于此方法和 if 条件语句的功能相同，因此其返回值肯定是 true 或者 false。经过过滤，Stream 中符合条件的，即 Lambda
     * 表达式值为 true 的元素被保留下来。该 Lambda 表达式的函数式接口正是 Predicate。
     *
     *
     *
     * 4、flatMap
     *
     * flatMap 方法可用 Stream 替换值，然后将多个 Stream 连接成一个 Stream。
     *
     * map 操作，它可用一个新的值代替 Stream 中的值。但有时，用户希望让 map 操作有点变化，生成一个新的 Stream 对象取而
     * 代之。用户通常不希望结果是一连串的流，此时 flatMap 最能派上用场。
     *
     * 看一个简单的例子。假设有一个包含多个列表的流，现在希望得到所有数字的序列。如下：
     *
     *         List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
     *                 .flatMap(numbers -> numbers.stream())
     *                 .collect(Collectors.toList());
     *         Assert.assertEquals(Arrays.asList(1, 2, 3, 4), together);
     *
     *
     *
     * 调用 stream 方法，将每个列表转换成 Stream 对象，其余部分由 flatMap 方法处理。flatMap 方法的相关函数式接口和
     * map 方法的一样，都是 Function 接口，只是方法的返回值限定为 Stream 类型罢了。
     *
     * PS：flatMap 可将多个 Stream 合并成一个 Stream。具体过程是将 Stream 中的每一个列表替换成一个新的 Stream 对象，
     * 看起来就像是多个 Stream 合并成了一个 Stream，此时 Stream 中的类型也发生了转换，转换成了对应列表中的类型。如下：
     *
     * Stream<Object[]>   	 -> flatMap  ->   Stream<Object>
     * Stream<Set<Object>>   -> flatMap  ->   Stream<Object>
     * Stream<List<Object>>  -> flatMap  ->   Stream<Object>
     *
     * 所以 flatMap 的作用可以用 "将流打平"、"将流展平"、"将流扁平化"、"将流合并起来"、"将流接续起来"、"将流连接起来"
     * 等词汇来进行描述。
     *
     *
     *
     * 5、max 和 min
     *
     * Stream 上常用的操作之一是求最大值和最小值。Stream API 中的 max 和 min 操作足以解决这一问题。假设现在要找到所有
     * 曲目中长度最短的一个，代码如下，展示了如何使用 max 和 min 操作：
     *
     *         List<Track> tracks = Arrays.asList(
     *                 new Track("Bakai", 524),
     *                 new Track("Violets for Your Furs", 378),
     *                 new Track("Time Was", 451));
     *         Track shortestTrack = tracks.stream()
     *                 .min(Comparator.comparing(track -> track.getLength()))
     *                 .get();
     *         Assert.assertEquals(tracks.get(1), shortestTrack);
     *
     * 查找 Stream 中的最大或最小元素，首先要考虑的是用什么作为排序的指标。以查找最短曲目为例，排序的指标就是曲目的长度。
     *
     * 为了让 Stream 对象按照曲目长度进行排序，需要传一个 Comparator 对象。Java 8 提供了一个新的静态方法 comparing，
     * 使用它可以方便地实现一个比较器。放在以前，需要比较两个对象的某项属性的值，现在只需要提供一个存取方法就够了。本例中
     * 使用 getLength 方法。
     *
     * 花点时间研究一下 comparing 方法是值得的。实际上这个方法接受一个函数并返回另一个函数。这听起来像句废话，但却很有用。
     * 这个方法本该早已加入 Java 标准库，但由于匿名内部类可读性差且书写冗长，一直未能实现。现在有了 Lambda 表达式，代码
     * 就变得简洁易懂。
     *
     * 此外，还可以调用空 Stream 的 max/min 方法，返回 Optional 对象，然后通过调用 get 方法可以取出 Optional 对象中
     * 的值。Optional 对象或许有点陌生，它代表一个可能存在也可能不存在的值。如果 Stream 为空，那么该值不存在，如果不为空，
     * 则该值存在。
     *
     *
     *
     * 6、通用模式
     *
     * max 和 min 方法都属于更通用的一种编程模式。要看到这种编程模式，最简单的方法是使用 for 循环重写查找最短曲目，如下：
     *
     *         List<Track> tracks = Arrays.asList(
     *                 new Track("Bakai", 524),
     *                 new Track("Violets for Your Furs", 378),
     *                 new Track("Time Was", 451));
     *         Track shortestTrack = tracks.get(0);
     *         for (Track track : tracks) {
     *             if (track.getLength() < shortestTrack.getLength()) {
     *                 shortestTrack = track;
     *             }
     *         }
     *         Assert.assertEquals(tracks.get(1), shortestTrack);
     *
     * 这里先使用第一个元素初始化变量 shortestTrack，然后遍历曲目列表，如果找到更短的曲目，则更新 shortestTrack，最后
     * 变量 shortestTrack 保存的正是最短曲目。程序员们无疑已写过成千上万次这样的 for 循环，其中很多都属于这个模式。这种
     * 通用模式被称为 reduce 模式，其伪代码如下：
     *
     *         Object accumulator = initialValue;
     *         for (Object element : collection) {
     *             accumulator = combine(accumulator, element);
     *         }
     *
     * 首先赋给 accumulator 一个初始值：initialValue，然后在循环体中，通过调用 combine 函数，拿 accumulator 和集合
     * 中的每一个元素做运算，再将运算结果赋给 accumulator，最后 accumulator 的值就是想要的结果。
     *
     * 这个模式中的两个可变项是 initialValue 初始值和 combine 函数。在查找最短曲目的示例中，是选了列表中的第一个元素为
     * 初始值，但也不一定如此。为了找出最短曲目，combine 函数返回当前元素和 accumulator 中较短的那个。
     *
     * 接下来看一下 Stream API 中的 reduce 操作是怎么工作的。
     *
     *
     *
     * 7、reduce
     *
     * reduce 操作可以实现从一组值中生成一个值。事实上，count、min 和 max 等方法都属于 reduce 操作，只不过因为比较常用，
     * 所以被纳入标准库中。
     *
     * 如下代码展示了如何通过 reduce 操作对 Stream 中的数字求和：
     *
     *         int count = Stream.of(1, 2, 3)
     *                 .reduce(0, (acc, element) -> acc + element);
     *         Assert.assertEquals(6, count);
     *
     * 以 0 作起点，一个空 Stream 的求和结果，每一步都将 Stream 中的元素累加至 accumulator，遍历至 Stream 中的最后一
     * 个元素时，accumulator 的值就是所有元素的和。
     *
     * Lambda 表达式就是 reducer，它执行求和操作，有两个参数：传入 Stream 中的当前元素和 acc。将两个参数相加，acc 是累
     * 加器，保存着当前的累加结果。
     *
     * Lambda 表达式的返回值是最新的 acc，是上一轮 acc 的值和当前元素相加的结果。reducer 的类型是 BinaryOperator。
     *
     * 事实上，可以将 reduce 操作展开，得到如下代码：
     *
     *         BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
     *         int count = accumulator.apply(
     *                 accumulator.apply(
     *                         accumulator.apply(0, 1),
     *                         2),
     *                 3);
     *         Assert.assertEquals(6, count);
     *
     * 当然，也可以使用命令式代码实现，即 使用 for 循环，如下：
     *
     *         int acc = 0;
     *         for (Integer element : Arrays.asList(1, 2, 3)) {
     *             acc = acc + element;
     *         }
     *         Assert.assertEquals(6, acc);
     *
     * 不难看出，函数式编程和命令式编程的区别很明显。在命令式编程方式下，每一次循环将集合中的元素和累加器相加，用相加后的结
     * 果更新累加器的值。对于集合来说，循环在外部，且需要手动更新变量。
     *
     * PS：可以结合大名鼎鼎的 MapReduce 进行理解，Map 即 映射，Reduce 即 归约。
     *
     *
     *
     * 8、整合操作
     *
     * Stream 接口的方法如此之多，有时会让人难以选择，像闯入一个迷宫，不知道该用哪个方法更好。这里将举例说明如何将问题分解
     * 为简单的 Stream 操作。
     *
     * 第一个要解决的问题是，找出某张专辑上所有乐队的国籍。艺术家列表里既有个人，也有乐队。利用一点领域知识，假定一般乐队名
     * 以定冠词 The 开头。当然这不是绝对的，但也差不多。
     *
     * 需要注意的是，这个问题绝不是简单地调用几个 API 就足以解决。这既不是使用 map 将一组值映射为另一组值，也不是过滤，更
     * 不是将 Stream 中的元素最终归约为一个值。首先，可将这个问题分解为如下几个步骤：
     * （1）找出专辑上的所有表演者；
     * （2）分辨出哪些表演者是乐队；
     * （3）找出每个乐队的国籍；
     * （4）将找出的国籍放入一个集合。
     *
     * 现在，找出每一步对应的 Stream API 就相对容易了：
     * （1）Album 类有个 getMusicians 方法，该方法返回一个 Stream 对象，包含整张专辑中所有的表演者；
     * （2）使用 filter 方法对表演者进行过滤，只保留乐队；
     * （3）使用 map 方法将乐队映射为其所属国家；
     * （4）使用 collect(Collectors.toList()) 方法将国籍放入一个列表。
     *
     * 最后，整合所有的操作，就得到如下代码：
     *
     *         Set<String> nationalities = album.getMusicians()
     *                 .filter(artist -> artist.getName().startsWith("The"))
     *                 .map(artist -> artist.getNationality())
     *                 .collect(Collectors.toSet());
     *
     * 这个例子将 Stream 的链式操作展现得淋漓尽致，调用 getMusicians、filter 和 map 方法都返回 Stream 对象，因此都属
     * 于惰性求值，而 collect 方法属于及早求值。map 方法接受一个 Lambda 表达式，使用该 Lambda 表达式对 Stream 上的每
     * 个元素做映射，形成一个新的 Stream。
     *
     * 这个问题处理起来很方便，使用 getMusicians 方法获取专辑上的艺术家列表时得到的是一个 Stream 对象。然而，处理其他实
     * 际遇到的问题时未必也能如此方便，很可能没有方法可以返回一个 Stream 对象，反而得到像 List 或 Set 这样的集合类。别担
     * 心，只要调用 List 或 Set 的 stream 方法就能得到一个 Stream 对象。
     *
     * 现在是个思考的好机会，真的需要对外暴露一个 List 或 Set 对象吗？可能一个 Stream 工厂才是更好的选择。通过 Stream
     * 暴露集合的最大优点在于，它很好地封装了内部实现的数据结构。仅暴露一个 Stream 接口，用户在实际操作中无论如何使用，都
     * 不会影响内部的 List 或 Set。
     *
     * 同时这也鼓励程序员在编程中使用更现代的 Java 8 风格。当然，这不是一蹴而就的，可以对已有代码渐进性地重构，保留原有的
     * 取值函数，添加返回 Stream 对象的函数，时间长了，就可以删掉所有返回 List 或 Set 的取值函数。清理了所有遗留代码之后，
     * 就会感觉这种重构方式非常好用。
     */
    public static void main(String[] args) {
        of();
        mapByFor();
        mapByStream();
        filterByFor();
        filterByStream();
        flatMapByStream();
        minByStream();
        minByFor();
        //reducePattern(null, null);
        reduceByStream();
        reduceSplit();
        redeceByFor();
        integration();
    }

    private static void of() {
        List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("a", "b", "c"), collected);
    }

    private static void mapByFor() {
        List<String> collected = new ArrayList<>();
        for (String string : Arrays.asList("a", "b", "hello")) {
            String upperCaseString = string.toUpperCase();
            collected.add(upperCaseString);
        }
        Assert.assertEquals(Arrays.asList("A", "B", "HELLO"), collected);
    }

    private static void mapByStream() {
        List<String> collected = Stream.of("a", "b", "hello")
                .map(string -> string.toUpperCase())
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("A", "B", "HELLO"), collected);
    }

    private static void filterByFor() {
        List<String> beginningWithNumbers = new ArrayList<>();
        for(String value : Arrays.asList("a", "1abc", "abc1")) {
            if (Character.isDigit(value.charAt(0))) {
                beginningWithNumbers.add(value);
            }
        }
        Assert.assertEquals(Arrays.asList("1abc"), beginningWithNumbers);
    }

    private static void filterByStream() {
        List<String> beginningWithNumbers = Stream.of("a", "1abc", "abc1")
                .filter(value -> Character.isDigit(value.charAt(0)))
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("1abc"), beginningWithNumbers);
    }

    private static void flatMapByStream() {
        List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4), together);
    }

    private static void minByStream() {
        List<Track> tracks = Arrays.asList(
                new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));
        Track shortestTrack = tracks.stream()
                .min(Comparator.comparing(track -> track.getLength()))
                .get();
        Assert.assertEquals(tracks.get(1), shortestTrack);
    }

    private static void minByFor() {
        List<Track> tracks = Arrays.asList(
                new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));
        Track shortestTrack = tracks.get(0);
        for (Track track : tracks) {
            if (track.getLength() < shortestTrack.getLength()) {
                shortestTrack = track;
            }
        }
        Assert.assertEquals(tracks.get(1), shortestTrack);
    }

    private static void reducePattern(Object initialValue, List<Object> collection) {
        Object accumulator = initialValue;
        for (Object element : collection) {
            accumulator = combine(accumulator, element);
        }
    }

    private static Object combine(Object accumulator, Object element) {
        return null;
    }

    private static void reduceByStream() {
        int count = Stream.of(1, 2, 3)
                .reduce(0, (acc, element) -> acc + element);
        Assert.assertEquals(6, count);
    }

    private static void reduceSplit() {
        BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
        int count = accumulator.apply(
                accumulator.apply(
                        accumulator.apply(0, 1),
                        2),
                3);
        Assert.assertEquals(6, count);
    }

    private static void redeceByFor() {
        int acc = 0;
        for (Integer element : Arrays.asList(1, 2, 3)) {
            acc = acc + element;
        }
        Assert.assertEquals(6, acc);
    }

    private static void integration() {
        Album album = new Album("Test", new ArrayList<>(), new ArrayList<>());
        Set<String> nationalities = album.getMusicians()
                .filter(artist -> artist.getName().startsWith("The"))
                .map(artist -> artist.getNationality())
                .collect(Collectors.toSet());
    }

}
