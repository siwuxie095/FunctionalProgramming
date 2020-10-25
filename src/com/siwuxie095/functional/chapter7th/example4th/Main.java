package com.siwuxie095.functional.chapter7th.example4th;

/**
 * @author Jiajing Li
 * @date 2020-10-25 12:22:55
 */
@SuppressWarnings("all")
public class Main {

    /**
     * 在测试替身时使用 Lambda 表达式
     *
     * 编写单元测试的常用方式之一是使用测试替身描述系统中其他模块的期望行为。这种方式很有用，因为单元测试可以脱离其他模块
     * 来测试类或方法，而测试替身可以让单元测试来实现这种隔离。
     *
     * PS：测试替身也常被称为模拟，事实上测试存根和模拟都属于测试替身。区别是模拟可以验证代码的行为。若想了解更多有关这方
     * 面的信息，可以参考 Martin Fowler 的相关文章(http://martinfowler.com/articles/mocksArentStubs.html)。
     *
     * 测试代码时，使用 Lambda 表达式的最简单方式是实现轻量级的测试存根。如果交互的类本身就是一个函数式接口，实现这样的
     * 存根就非常简单和自然。
     *
     * 在 example2nd 中，讨论过如何将通用的领域逻辑重构为一个 countFeature 方法，然后使用 Lambda 表达式来实现不同的
     * 统计行为。那么就可以对此进行单元测试，如下：
     *
     *     @Test
     *     public void canCountFeatures() {
     *         OrderDomain order = new OrderDomain(Arrays.asList( newAlbum("Exile on Main St."),
     *                 newAlbum("Beggars Banquet"),
     *                 newAlbum("Aftermath"),
     *                 newAlbum("Let it Bleed")));
     *         Assert.assertEquals(8, order.countFeature(album -> 2));
     *     }
     *
     *     private Album newAlbum(String name) {
     *         return new Album(name, Collections.emptyList(), Collections.emptyList());
     *     }
     *
     * 对于 countFeature 方法的期望行为是为传入的专辑返回某个数值。这里传入 4 张专辑，测试存根中为每张专辑返回 2，然后
     * 断言该方法返回 8，即 2×4。如果要向代码传入一个 Lambda 表达式，最好确保 Lambda 表达式也通过测试。
     *
     * 多数的测试替身都很复杂，使用 Mockito 这样的框架有助于更容易地产生测试替身。现在考虑一种简单情形，为 List 生成测
     * 试替身。现在不想返回当前 List 上的长度，而是返回另一个 List 的长度，为了模拟 List 的 size 方法，不想只给出答案，
     * 还想做一些操作，因此传入一个 Lambda 表达式。如下所示：
     *
     *     private List<Integer> otherList = Arrays.asList(1, 2, 3);
     *
     *     @Test
     *     public void mockitoLambda() {
     *         List<String> list = Mockito.mock(List.class);
     *         Mockito.when(list.size()).thenAnswer(inv -> otherList.size());
     *         Assert.assertEquals(3, list.size());
     *     }
     *
     * Mockito 使用 Answer 接口允许用户提供其他行为，换句话说，这是一位老朋友：代码即数据。之所以在这里能使用 Lambda
     * 表达式，是因为 Answer 本身就是一个函数式接口。
     *
     * PS：这里需要引入 Mockito 相关的 jar 包，共 4 个：
     * （1）org.mockito » mockito-core » 2.25.0
     * （2）net.bytebuddy » byte-buddy » 1.9.7
     * （3）net.bytebuddy » byte-buddy-agent » 1.9.7
     * （4）org.objenesis » objenesis » 2.6
     */
    public static void main(String[] args) {

    }

}
