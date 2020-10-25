package com.siwuxie095.functional.chapter8th.example7th;

import com.siwuxie095.functional.chapter8th.example7th.example.StackSpec;
import com.siwuxie095.functional.chapter8th.example7th.reporting.*;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:48:03
 */
@SuppressWarnings("all")
public enum Runner {

    current;

    private final Report report;

    private Runner() {
        report = new Report();
    }

    void recordSuccess(String suite, String specification) {
        report.newSpecification(suite, new SpecificationReport(specification));
    }

    void recordFailure(String suite, String specification, AssertionError cause) {
        SpecificationReport specificationReport = new SpecificationReport(specification, Result.FAILURE, cause.getMessage());
        report.newSpecification(suite, specificationReport);
    }

    void recordError(String suite, String specification, Throwable cause) {
        cause.printStackTrace();
        SpecificationReport specificationReport = new SpecificationReport(specification, Result.ERROR, cause.getMessage());
        report.newSpecification(suite, specificationReport);
    }

    public static void main(String[] args) {
        current.run(StackSpec.class);
        current.printReport();
    }

    private void printReport() {
        ReportFormatter formatter = new ConsoleFormatter();
        formatter.format(report);
    }

    private void run(Class<StackSpec> stackSpecClass) {
        try {
            stackSpecClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
