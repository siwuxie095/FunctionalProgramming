package com.siwuxie095.functional.chapter8th.example7th;

/**
 * 每个测试套件都由一个实现该接口的 Lambda 表达式实现
 *
 * @author Jiajing Li
 * @date 2020-10-25 20:46:51
 */
@SuppressWarnings("all")
public interface Suite {

    public void specifySuite(Description description);

}
