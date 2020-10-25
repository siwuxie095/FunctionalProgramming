package com.siwuxie095.functional.chapter8th.example6th.lambda;

import com.siwuxie095.functional.chapter8th.example6th.ApplicationDenied;

/**
 * 如果申请失败，函数式接口 Criteria 抛出异常
 *
 * @author Jiajing Li
 * @date 2020-10-25 20:05:02
 */
@SuppressWarnings("all")
public interface Criteria {

    public void check() throws ApplicationDenied;

}
