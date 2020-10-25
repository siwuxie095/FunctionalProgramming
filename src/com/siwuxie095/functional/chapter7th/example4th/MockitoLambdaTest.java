package com.siwuxie095.functional.chapter7th.example4th;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Jiajing Li
 * @date 2020-10-25 12:41:03
 */
@SuppressWarnings("all")
public class MockitoLambdaTest {

    private List<Integer> otherList = Arrays.asList(1, 2, 3);

    /**
     * 结合 Mockito 框架使用 Lambda 表达式
     */
    @Test
    public void mockitoLambda() {
        List<String> list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenAnswer(inv -> otherList.size());
        Assert.assertEquals(3, list.size());
    }

}
