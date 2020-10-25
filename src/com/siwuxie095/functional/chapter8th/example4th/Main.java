package com.siwuxie095.functional.chapter8th.example4th;

/**
 * @author Jiajing Li
 * @date 2020-10-25 19:08:52
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 策略模式
     *
     * 策略模式能在运行时改变软件的算法行为。如何实现策略模式视情况而定，但其主要思想是定义一个通用的问题，使用不同
     * 的算法来实现，然后将这些算法都封装在一个统一接口的背后。
     *
     * 文件压缩就是一个很好的例子。可以提供给用户各种压缩文件的方式，可以使用 zip 算法，也可以使用 gzip 算法。这里
     * 实现一个通用的 Compressor 类，能以任何一种算法压缩文件。
     *
     * 首先，为策略定义 API，叫做 CompressionStrategy。每一种文件压缩算法都要实现该接口。该接口有一个 compress
     * 方法，接收并返回一个 OutputStream 对象，返回的就是压缩后的 OutputStream。
     *
     * CompressionStrategy 接口定义如下：
     *
     * public interface CompressionStrategy {
     *
     *     public OutputStream compress(OutputStream data) throws IOException;
     *
     * }
     *
     * 共有两个类实现了该接口，分别代表 gzip 和 zip 算法，使用 Java 内置的类实现 gzip 和 zip 算法。
     *
     * GzipCompressionStrategy 类实现如下：
     *
     * public class GzipCompressionStrategy implements CompressionStrategy {
     *
     *     @Override
     *     public OutputStream compress(OutputStream data) throws IOException {
     *         return new GZIPOutputStream(data);
     *     }
     *
     * }
     *
     * ZipCompressionStrategy 类实现如下：
     *
     * public class ZipCompressionStrategy implements CompressionStrategy {
     *
     *     @Override
     *     public OutputStream compress(OutputStream data) throws IOException {
     *         return new ZipOutputStream(data);
     *     }
     *
     * }
     *
     * 现在可以动手实现 Compressor 类了，这里就是使用策略模式的地方。该类有一个 compress 方法，读入文件，压缩后
     * 输出。它的构造函数有一个 CompressionStrategy 参数，调用代码可以在运行期使用该参数决定使用哪种压缩策略，
     * 比如，可以等待用户输入选择。
     *
     * Compressor 类实现如下：
     *
     * public class Compressor {
     *
     *     private final CompressionStrategy strategy;
     *
     *     public Compressor(CompressionStrategy strategy) {
     *         this.strategy = strategy;
     *     }
     *
     *     public void compress(Path inFile, File outFile) throws IOException {
     *         try (OutputStream outStream = new FileOutputStream(outFile)) {
     *             Files.copy(inFile, strategy.compress(outStream));
     *         }
     *     }
     *
     * }
     *
     * 如果使用这种传统的策略模式实现方式，可以编写客户代码创建一个新的 Compressor，并且使用任何想要的策略。如下：
     *
     *         Compressor gzipCompressor = new Compressor(new GzipCompressionStrategy());
     *         gzipCompressor.compress(inFile, outFile);
     *
     *         Compressor zipCompressor = new Compressor(new ZipCompressionStrategy());
     *         zipCompressor.compress(inFile, outFile);
     *
     * 这里可以使用 Lambda 表达式或者方法引用可以去掉样板代码。即 可以去掉具体的策略实现，使用一个方法实现算法，这
     * 里的算法由构造函数中对应的 OutputStream 实现。使用这种方式，可以完全舍弃 GzipCompressionStrategy 和
     * ZipCompressionStrategy 类。如下：
     *
     *         Compressor gzipCompressor = new Compressor(GZIPOutputStream::new);
     *         gzipCompressor.compress(inFile, outFile);
     *
     *         Compressor zipCompressor = new Compressor(ZipOutputStream::new);
     *         zipCompressor.compress(inFile, outFile);
     */
    public static void main(String[] args) {

    }

}
