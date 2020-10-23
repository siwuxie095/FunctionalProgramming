package com.siwuxie095.functional.chapter6th.example4th;

import com.siwuxie095.functional.common.Album;
import com.siwuxie095.functional.common.SampleData;
import com.siwuxie095.functional.common.Track;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-23 20:55:41
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 并行化流操作
     *
     * 并行化操作流只需改变一个方法调用。如果已经有一个 Stream 对象，调用它的 parallel 方法就能让其拥有并行操作的能力。
     * 如果想从一个集合类创建一个流，调用 parallelStream 就能立即获得一个拥有并行能力的流。
     *
     * 以计算一组专辑的曲目总长度为例。先拿到每张专辑的曲目信息，然后得到曲目长度，最后相加得出曲目总长度。如下：
     *
     *     private static int serialArraySum(List<Album> albums) {
     *         return albums.stream()
     *             .flatMap(Album::getTracks)
     *             .mapToInt(Track::getLength)
     *             .sum();
     *     }
     *
     * 调用 parallelStream 方法即能并行处理，而剩余代码都是一样的，如下：
     *
     *     private static int parallelArraySum(List<Album> albums) {
     *         return albums.stream()
     *                 .flatMap(Album::getTracks)
     *                 .mapToInt(Track::getLength)
     *                 .sum();
     *     }
     *
     * 并行化就是这么简单！
     *
     * 到这里，大家的第一反应可能是立即将手头代码中的 stream 方法替换为 parallelStream 方法，因为这样做简直太简单了！
     * 先别忙，为了将硬件物尽其用，利用好并行化非常重要，但流类库提供的数据并行化只是其中的一种形式。
     *
     * 先要问自己一个问题：并行化运行基于流的代码是否比串行化运行更快？这不是一个简单的问题。因为哪种方式花的时间更多取决
     * 于串行或并行化运行时的环境。
     *
     * 以上面两段代码为例，在一个四核电脑上：
     * （1）如果有 10 张专辑，串行化代码的速度是并行化代码速度的 8 倍；
     * （2）如果将专辑数量增至 100 张，串行化和并行化速度相当；
     * （3）如果将专辑数量增值 10 000 张，则并行化代码的速度是串行化代码速度的 2.5 倍。
     *
     * PS：这里的对比基准只是为了说明问题，如果尝试在自己的机器上重现这些实验，得到的结果可能会大相径庭。
     *
     * 输入流的大小并不是决定并行化是否会带来速度提升的唯一因素，性能还会受到编写代码的方式和核的数量的影响。
     */
    public static void main(String[] args) {
        serialArraySum(Arrays.asList(SampleData.aLoveSupreme));
        parallelArraySum(Arrays.asList(SampleData.aLoveSupreme));
    }

    /**
     * 串行化计算专辑曲目长度
     */
    private static int serialArraySum(List<Album> albums) {
        return albums.stream()
            .flatMap(Album::getTracks)
            .mapToInt(Track::getLength)
            .sum();
    }

    /**
     * 并行化计算专辑曲目长度
     */
    private static int parallelArraySum(List<Album> albums) {
        return albums.stream()
                .flatMap(Album::getTracks)
                .mapToInt(Track::getLength)
                .sum();
    }



}
