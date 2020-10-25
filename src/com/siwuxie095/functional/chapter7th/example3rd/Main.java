package com.siwuxie095.functional.chapter7th.example3rd;

/**
 * @author Jiajing Li
 * @date 2020-10-25 11:32:01
 */
@SuppressWarnings("all")
public class Main {

    /**
     * Lambda 表达式的单元测试
     *
     * 单元测试是测试一段代码的行为是否符合预期的方式。
     *
     * 通常，在编写单元测试时，怎么在应用中调用该方法，就怎么在测试中调用。给定一些输入或测试替身，调用这些方法，
     * 然后验证结果是否和预期的行为一致。
     *
     * Lambda 表达式给单元测试带来了一些麻烦，Lambda 表达式没有名字，无法直接在测试代码中调用。
     *
     * 可以在测试代码中复制 Lambda 表达式来测试，但这种方式的副作用是测试的不是真正的实现。假设修改了实现代码，
     * 测试仍然通过，而实现可能早已在做另一件事了。
     *
     * PS：这里只针对 Lambda 表达式进行测试。
     *
     * 解决该问题有两种方式。第一种是将 Lambda 表达式放入一个方法中测试，这种方式要测那个方法，而不是 Lambda
     * 表达式本身。如下代码是一个将一组字符串转换成大写的方法：
     *
     *     public static List<String> allToUpperCase(List<String> words) {
     *         return words.stream()
     *                 .map(string -> string.toUpperCase())
     *                 .collect(Collectors.<String>toList());
     *     }
     *
     * 在这段代码中，Lambda 表达式唯一的作用就是调用一个 Java 方法。将该 Lambda 表达式单独测试是不值得的，它
     * 的行为太简单了。
     *
     * 所以应该将重点放在方法的行为上。如下代码测试了流中有多个单词的情况，它们都被转换成对应的大写：
     *
     *     @Test
     *     public void multipleWordsToUppercase() {
     *         List<String> input = Arrays.asList("a", "b", "hello");
     *         List<String> result = Testing.allToUpperCase(input);
     *         Assert.assertEquals(Arrays.asList("A", "B", "HELLO"), result);
     *     }
     *
     * 有时候 Lambda 表达式实现了复杂的功能，它可能包含多个边界情况、使用了多个属性来计算一个非常重要的值。现在
     * 非常想测试该段代码的行为，但它是一个 Lambda 表达式，无法引用。那么此时该怎么办呢？
     *
     * 来看一个比大写转换更复杂一点的例子。要把字符串的第一个字母转换成大写，其他部分保持不变。使用流和 Lambda
     * 表达式，编写的代码如下：
     *
     *     public static List<String> elementFirstToUpperCaseLambdas(List<String> words) {
     *         return words.stream()
     *                 .map(value -> {
     *                     char firstChar = Character.toUpperCase(value.charAt(0));
     *                     return firstChar + value.substring(1);
     *                 })
     *                 .collect(Collectors.<String>toList());
     *     }
     *
     * 如果要测试这段代码，必须创建一个列表，然后将想要测试的各种情况都测试到。如下代码展示了这种方式有多么繁琐：
     *
     *     @Test
     *     public void twoLetterStringConvertedToUppercaseLambdas() {
     *         List<String> input = Arrays.asList("ab");
     *         List<String> result = Testing.elementFirstToUpperCaseLambdas(input);
     *         Assert.assertEquals(Arrays.asList("Ab"), result);
     *     }
     *
     * 要解决这种繁琐，有两种办法。
     *
     * （1）第一种办法：别用 Lambda 表达式。这个建议有点奇怪，因为这样就无法享受 Lambda 表达式带来的便利了，但
     * 方楔子钉不进圆孔，只能如此。
     *
     * 既然如此，怎么才能既能测试代码，同时又享有 Lambda 表达式带来的便利呢？那就要用第二种办法。
     *
     * （2）第二种办法：请用方法引用。任何 Lambda 表达式都能被改写为普通方法，然后使用方法引用直接引用。
     *
     * 将上例中的 Lambda 表达式重构为一个方法，然后在主程序中使用，主程序负责转换字符串。如下：
     *
     *     public static List<String> elementFirstToUppercase(List<String> words) {
     *         return words.stream()
     *                 .map(Testing::firstToUppercase)
     *                 .collect(Collectors.<String>toList());
     *     }
     *
     *     public static String firstToUppercase(String value) {
     *         char firstChar = Character.toUpperCase(value.charAt(0));
     *         return firstChar + value.substring(1);
     *     }
     *
     * 把处理字符串的的逻辑抽取成一个方法后，就可以测试该方法，把所有的边界情况都覆盖到。新的测试用例如下所示：
     *
     *     @Test
     *     public void twoLetterStringConvertedToUppercase() {
     *         String input = "ab";
     *         String result = Testing.firstToUppercase(input);
     *         Assert.assertEquals("Ab", result);
     *     }
     */
    public static void main(String[] args) {

    }

}
