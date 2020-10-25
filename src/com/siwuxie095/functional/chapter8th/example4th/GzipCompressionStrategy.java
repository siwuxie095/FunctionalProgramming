package com.siwuxie095.functional.chapter8th.example4th;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 使用 gzip 算法压缩数据
 *
 * @author Jiajing Li
 * @date 2020-10-25 19:15:35
 */
@SuppressWarnings("all")
public class GzipCompressionStrategy implements CompressionStrategy {

    @Override
    public OutputStream compress(OutputStream data) throws IOException {
        return new GZIPOutputStream(data);
    }

}
