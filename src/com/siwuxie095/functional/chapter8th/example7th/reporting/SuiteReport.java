package com.siwuxie095.functional.chapter8th.example7th.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:53:08
 */
@SuppressWarnings("all")
public final class SuiteReport {

    private final String name;
    private final List<SpecificationReport> specifications;

    public SuiteReport(String name) {
        this.name = name;
        specifications = new ArrayList<>();
    }

    public void add(SpecificationReport specification) {
        specifications.add(specification);
    }

    public Stream<SpecificationReport> specifications() {
        return specifications.stream();
    }

    public String getName() {
        return name;
    }

}
