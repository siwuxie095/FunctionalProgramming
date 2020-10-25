package com.siwuxie095.functional.chapter7th.example4th;

import com.siwuxie095.functional.chapter7th.example2nd.OrderDomain;
import com.siwuxie095.functional.common.Album;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Jiajing Li
 * @date 2020-10-25 12:38:28
 */
@SuppressWarnings("all")
public class OrderDomainTest {

    /**
     * 使用 Lambda 表达式编写测试替身，传给 countFeature 方法
     */
    @Test
    public void canCountFeatures() {
        OrderDomain order = new OrderDomain(Arrays.asList( newAlbum("Exile on Main St."),
                newAlbum("Beggars Banquet"),
                newAlbum("Aftermath"),
                newAlbum("Let it Bleed")));
        Assert.assertEquals(8, order.countFeature(album -> 2));
    }

    private Album newAlbum(String name) {
        return new Album(name, Collections.emptyList(), Collections.emptyList());
    }

}
