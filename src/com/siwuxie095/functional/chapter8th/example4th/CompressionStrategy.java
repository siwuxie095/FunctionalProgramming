package com.siwuxie095.functional.chapter8th.example4th;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 定义压缩数据的策略接口
 *
 * @author Jiajing Li
 * @date 2020-10-25 19:13:20
 */
@SuppressWarnings("all")
public interface CompressionStrategy {

    public OutputStream compress(OutputStream data) throws IOException;

}
