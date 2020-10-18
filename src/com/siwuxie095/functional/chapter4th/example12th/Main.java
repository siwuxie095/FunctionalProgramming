package com.siwuxie095.functional.chapter4th.example12th;

import com.siwuxie095.functional.common.Artist;
import com.siwuxie095.functional.common.SampleData;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-18 20:28:02
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 要点回顾与巩固练习
     *
     * 1、要点回顾
     *
     * （1）使用为基本类型定制的 Lambda 表达式和 Stream，如 IntStream 可以显著提升系统性能。
     * （2）默认方法是指接口中定义的包含方法体的方法，方法名有 default 关键字做前缀。
     * （3）在一个值可能为空的建模情况下，使用 Optional 对象能替代使用 null 值。
     *
     *
     *
     * 2、巩固练习
     *
     * （1）
     * 问：
     * 在如下所示的 Performance 接口基础上，添加 getAllMusicians 方法，该方法返回包含所有艺术家名字的 Stream，
     * 如果对象是乐队，则返回返回乐队名和每个乐队成员的名字。
     *
     * public interface Performance {
     *
     *     public String getName();
     *
     *     public Stream<Artist> getMusicians();
     *
     * }
     *
     * 答：
     *
     * public interface PerformanceFixed {
     *
     *     public String getName();
     *
     *     public Stream<Artist> getMusicians();
     *
     *     public default Stream<Artist> getAllMusicians() {
     *         return getMusicians()
     *                 .flatMap(artist -> Stream.concat(Stream.of(artist), artist.getMembers()));
     *     }
     *
     * }
     *
     *
     * （2）
     * 问：
     * 根据重载解析规则，能否重写 equals 或 hashCode 方法作为默认方法？
     * 答：
     * 不能。因为它们定义在 java.lang.Object 中，并且类中重写的方法总是胜出，这样做毫无意义。所以报编译错误，如下：
     * Default method 'equals' overrides a member of 'java.lang.Object'
     * 和
     * Default method 'hashCode' overrides a member of 'java.lang.Object'
     *
     *
     * （3）
     * 问：
     * 如下所示的 Artists 类表示了一组艺术家，重构该类，使得 getArtist 方法返回一个 Optional<Artist> 对象。如果
     * 索引在有效范围内，返回对应的元素，否则返回一个空Optional 对象。此外，还需重构 getArtistName 方法，保持相同
     * 的行为。
     *
     * public class Artists {
     *
     *     private List<Artist> artists;
     *
     *     public Artists(List<Artist> artists) {
     *         this.artists = artists;
     *     }
     *
     *     public Artist getArtist(int index) {
     *         if (index < 0 || index >= artists.size()) {
     *             indexException(index);
     *         }
     *         return artists.get(index);
     *     }
     *
     *     private void indexException(int index) {
     *         throw new IllegalArgumentException(index + " doesn't correspond to an Artist");
     *     }
     *
     *     public String getArtistName(int index) {
     *         try {
     *             Artist artist = getArtist(index);
     *             return artist.getName();
     *         } catch (IllegalArgumentException e) {
     *             return "unknown";
     *         }
     *     }
     *
     * }
     *
     * 答：
     *
     * public class ArtistsFixed {
     *
     *     private List<Artist> artists;
     *
     *     public ArtistsFixed(List<Artist> artists) {
     *         this.artists = artists;
     *     }
     *
     *     public Optional<Artist> getArtist(int index) {
     *         if (index < 0 || index >= artists.size()) {
     *             return Optional.empty();
     *         }
     *         return Optional.of(artists.get(index));
     *     }
     *
     *     public String getArtistName(int index) {
     *         Optional<Artist> artist = getArtist(index);
     *         return artist.map(Artist::getName)
     *                 .orElse("unknown");
     *     }
     *
     * }
     *
     */
    public static void main(String[] args) {
        testPerformanceFixed();
        testArtistsFixed();
    }

    private static void testPerformanceFixed() {
        PerformanceFixed stub = new PerformanceFixed() {
            @Override
            public String getName() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Stream<Artist> getMusicians() {
                return Stream.of(SampleData.theBeatles);
            }
        };

        List<Artist> allMusicians = stub.getAllMusicians().collect(Collectors.toList());

        MatcherAssert.assertThat(allMusicians, CoreMatchers.hasItem(SampleData.theBeatles));
        //// There really must be a better way than this
        MatcherAssert.assertThat(allMusicians, CoreMatchers.hasItems(SampleData.membersOfTheBeatles.toArray(new Artist[0])));
    }

    private static void testArtistsFixed() {
        ArtistsFixed optionalExamples = new ArtistsFixed(SampleData.getThreeArtists());
        indexWithinRange(optionalExamples);
        indexOutsideRange(optionalExamples);
        nameIndexInsideRange(optionalExamples);
        nameIndexOutsideRange(optionalExamples);
    }

    private static void indexWithinRange(ArtistsFixed optionalExamples) {
        Optional<Artist> artist = optionalExamples.getArtist(0);
        Assert.assertTrue(artist.isPresent());
    }

    private static void indexOutsideRange(ArtistsFixed optionalExamples) {
        Optional<Artist> artist = optionalExamples.getArtist(4);
        Assert.assertFalse(artist.isPresent());
    }

    private static void nameIndexInsideRange(ArtistsFixed optionalExamples) {
        String artist = optionalExamples.getArtistName(0);
        Assert.assertEquals("John Coltrane", artist);
    }

    private static void nameIndexOutsideRange(ArtistsFixed optionalExamples) {
        String artist = optionalExamples.getArtistName(4);
        Assert.assertEquals("unknown", artist);
    }

}
