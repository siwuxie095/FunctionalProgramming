package com.siwuxie095.functional.chapter8th.example4th;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Jiajing Li
 * @date 2020-10-25 19:27:19
 */
@SuppressWarnings("all")
public class Client {

    /**
     * 使用具体的策略类初始化 Compressor
     */
    public static void classBasedExample(Path inFile, File outFile) throws IOException {
        Compressor gzipCompressor = new Compressor(new GzipCompressionStrategy());
        gzipCompressor.compress(inFile, outFile);

        Compressor zipCompressor = new Compressor(new ZipCompressionStrategy());
        zipCompressor.compress(inFile, outFile);
    }

    /**
     * 使用方法引用初始化 Compressor
     */
    public static void lambdaBasedExample(Path inFile, File outFile) throws IOException {
        Compressor gzipCompressor = new Compressor(GZIPOutputStream::new);
        gzipCompressor.compress(inFile, outFile);

        Compressor zipCompressor = new Compressor(ZipOutputStream::new);
        zipCompressor.compress(inFile, outFile);
    }

}
