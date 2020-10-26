package com.siwuxie095.functional.chapter8th.example11th;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Jiajing Li
 * @date 2020-10-26 22:16:54
 */
@SuppressWarnings("all")
public class DependencyInversionPrinciple {


    public static interface HeadingFinder {

        public List<String> findHeadings(Reader reader);

    }


    public static class NoDIP implements HeadingFinder {
        /**
         * 解析文件中的标题
         */
        @Override
        public List<String> findHeadings(Reader input) {
            try (BufferedReader reader = new BufferedReader(input)) {
                return reader.lines()
                        .filter(line -> line.endsWith(":"))
                        .map(line -> line.substring(0, line.length() - 1))
                        .collect(toList());
            } catch (IOException e) {
                throw new HeadingLookupException(e);
            }
        }

    }


    public static class ExtractedDIP implements HeadingFinder {
        /**
         * 剥离了文件处理功能后的业务逻辑
         */
        @Override
        public List<String> findHeadings(Reader input) {
            return withLinesOf(input,
                    lines -> lines.filter(line -> line.endsWith(":"))
                            .map(line -> line.substring(0, line.length()-1))
                            .collect(toList()),
                    HeadingLookupException::new);
        }

        private <T> T withLinesOf(Reader input,
                                  Function<Stream<String>, T> handler,
                                  Function<IOException, RuntimeException> error) {
            try (BufferedReader reader = new BufferedReader(input)) {
                return handler.apply(reader.lines());
            } catch (IOException e) {
                throw error.apply(e);
            }
        }

    }


}
