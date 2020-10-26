package com.siwuxie095.functional.chapter8th.example11th;

/**
 * @author Jiajing Li
 * @date 2020-10-26 22:13:19
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 依赖反转原则
     *
     * 抽象不应依赖细节，细节应该依赖抽象。
     *
     * PS：依赖反转原则，也叫 依赖倒置原则。
     *
     * 让程序变得死板、脆弱、难于改变的方法之一是将上层业务逻辑和底层粘合模块的代码混在一起，因为这两样东西都会随着时间
     * 发生变化。
     *
     * 依赖反转原则的目的是让程序员脱离底层粘合代码，编写上层业务逻辑代码。这就让上层代码依赖于底层细节的抽象，从而可以
     * 重用上层代码。这种模块化和重用方式是双向的：既可以替换不同的细节重用上层代码，也可以替换不同的业务逻辑重用细节的
     * 实现。
     *
     * 来看一个具体的、自动化构建地址簿的例子，实现时使用了依赖反转原则达到上层的解耦。该应用以电子卡片作为输入，使用某
     * 种存储机制编写地址簿。
     *
     * 显然，可以将代码分成如下三个基本模块：
     * （1）一个能解析电子卡片格式的电子卡片阅读器；              -- 电子卡片阅读器
     * （2）能将地址存为文本文件的地址簿存储模块；                -- 地址簿存储模块
     * （3）从电子卡片中获取有效信息并将其写入地址簿的编写模块。    -- 编写模块（即 累加器）
     *
     * 在该系统中，重用编写模块很复杂，但是电子卡片阅读器和地址簿存储模块都不依赖于其他模块，因此很容易在其他系统中重用。
     * 还可以替换它们，比如用一个其他的阅读器，或者从人们的 Twitter 账户信息中读取内容；又比如不想将地址簿存为一个文本
     * 文件，而是使用数据库存储等其他形式。
     *
     * 为了具备能在系统中替换组件的灵活性，必须保证编写模块不依赖阅读器或存储模块的实现细节。因此引入了对阅读信息和输出
     * 信息的抽象，编写模块的实现依赖于这种抽象。在运行时传入具体的实现细节，这就是依赖反转原则的工作原理。
     *
     * 具体到 Lambda 表达式，很多高阶函数都符合依赖反转原则。比如 map 函数重用了在两个集合之间转换的代码。map 函数不
     * 依赖于转换的细节，而是依赖于抽象的概念。在这里，就是依赖函数式接口：Function。
     *
     * 资源管理是依赖反转的另一个更为复杂的例子。显然，可管理的资源很多，比如数据库连接、线程池、文件和网络连接。这里以
     * 文件为例，因为文件是一种相对简单的资源，但是背后的原则可以很容易应用到更复杂的资源中。
     *
     * 先看一段代码，该段代码从一种假想的标记语言中提取标题，其中标题以冒号。这里的方法先读取文件，逐行检查，滤出标题，
     * 然后关闭文件。这里还将和读写文件有关的异常封装成接近待解决问题的异常：HeadingLookupException。如下：
     *
     *         public List<String> findHeadings(Reader input) {
     *             try (BufferedReader reader = new BufferedReader(input)) {
     *                 return reader.lines()
     *                         .filter(line -> line.endsWith(":"))
     *                         .map(line -> line.substring(0, line.length() - 1))
     *                         .collect(toList());
     *             } catch (IOException e) {
     *                 throw new HeadingLookupException(e);
     *             }
     *         }
     *
     * 可惜，这段代码将提取标题和资源管理、文件处理混在一起。这里真正想要的是编写提取标题的代码，而将操作文件相关的细节
     * 交给另一个方法。可以使用 Stream<String> 作为抽象，让代码依赖它，而不是文件。Stream 对象更安全，而且不容易被
     * 滥用。另外还想传入一个函数，在读文件出问题时，可以创建一个问题域里的异常。如下：
     *
     *         public List<String> findHeadings(Reader input) {
     *             return withLinesOf(input,
     *                     lines -> lines.filter(line -> line.endsWith(":"))
     *                             .map(line -> line.substring(0, line.length()-1))
     *                             .collect(toList()),
     *                     HeadingLookupException::new);
     *         }
     *
     *         private <T> T withLinesOf(Reader input,
     *                                   Function<Stream<String>, T> handler,
     *                                   Function<IOException, RuntimeException> error) {
     *             try (BufferedReader reader = new BufferedReader(input)) {
     *                 return handler.apply(reader.lines());
     *             } catch (IOException e) {
     *                 throw error.apply(e);
     *             }
     *         }
     *
     * 这就是整个过程，而且这里将问题域里的异常处理和资源管理的异常处理分开了。
     *
     * withLinesOf 方法接收一个 Reader 参数处理文件读写，然后将其封装进一个 BufferedReader 对象，这样就可以逐行读
     * 取文件了。接收的第二个参数是 handler，该函数代表了想在该方法中执行的代码，它以文件中的每一行组成的 Stream 作为
     * 参数。接收的第三个参数是 error，输入输出有异常时会调用该方法，它会构建出与问题域有关的异常，出问题时就抛出该异常。
     *
     * 总结下来，高阶函数提供了反转控制，这就是依赖反转的一种形式，可以很容易地和 Lambda 表达式一起使用。依赖反转原则另
     * 外值得注意的一点是待依赖的抽象不必是接口。这里使用 Stream 对原始的 Reader 和文件处理做抽象，这种方式也适用于函
     * 数式编程语言中的资源管理通，常使用高阶函数管理资源，接受一个回调函数使用打开的资源，然后再关闭资源。事实上，如果
     * Java 7 就有 Lambda 表达式，那么 Java 7 中的 try-with-resources 功能可能只需要一个库函数就能实现。
     */
    public static void main(String[] args) {

    }

}
