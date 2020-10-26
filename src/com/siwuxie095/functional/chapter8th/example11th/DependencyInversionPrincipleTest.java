package com.siwuxie095.functional.chapter8th.example11th;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * @author Jiajing Li
 * @date 2020-10-26 22:44:01
 */
@SuppressWarnings("all")
@RunWith(Parameterized.class)
public class DependencyInversionPrincipleTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{new DependencyInversionPrinciple.NoDIP()},
                {new DependencyInversionPrinciple.ExtractedDIP()}};
        return Arrays.asList(data);
    }

    private final DependencyInversionPrinciple.HeadingFinder finder;

    public DependencyInversionPrincipleTest(DependencyInversionPrinciple.HeadingFinder finder) {
        this.finder = finder;
    }

    @Test
    public void correctHeadings() {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("test_file"));
        List<String> headings = finder.findHeadings(reader);
        Assert.assertEquals(Arrays.asList("Improve Content", "Cleanup", "Add Content", "Add to Streams Chapter"), headings);
    }

}

