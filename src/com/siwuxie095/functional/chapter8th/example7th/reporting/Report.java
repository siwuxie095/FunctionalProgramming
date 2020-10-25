package com.siwuxie095.functional.chapter8th.example7th.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:51:07
 */
@SuppressWarnings("all")
public final class Report {

    private final List<SuiteReport> suites;

    private SuiteReport currentSuite;

    public Report() {
        suites = new ArrayList<>();
    }

    private void newSuite(String name) {
        currentSuite = new SuiteReport(name);
        suites.add(currentSuite);
    }

    public void newSpecification(String suiteName, SpecificationReport report) {
        if (noSuite() || seenNewSuite(suiteName)) {
            newSuite(suiteName);
        }
        currentSuite.add(report);
    }

    private boolean seenNewSuite(String suite) {
        return !currentSuite.getName().equals(suite);
    }

    private boolean noSuite() {
        return currentSuite == null;
    }

    public Stream<SuiteReport> suites() {
        return suites.stream();
    }

}
