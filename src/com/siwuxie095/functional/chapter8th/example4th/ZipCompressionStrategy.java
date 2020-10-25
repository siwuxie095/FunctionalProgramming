package com.siwuxie095.functional.chapter8th.example4th;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

/**
 * 使用 zip 算法压缩数据
 *
 * @author Jiajing Li
 * @date 2020-10-25 19:16:12
 */
@SuppressWarnings("all")
public class ZipCompressionStrategy implements CompressionStrategy {

    @Override
    public OutputStream compress(OutputStream data) throws IOException {
        return new ZipOutputStream(data);
    }

}
