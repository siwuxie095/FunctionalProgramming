package com.siwuxie095.functional.chapter8th.example7th.reporting;

import java.io.PrintStream;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:54:08
 */
@SuppressWarnings("all")
public class ConsoleFormatter implements ReportFormatter {

    @Override
    public void format(Report report) {
        report.suites().forEach(suite -> {
            System.out.print(suite.getName());
            System.out.println();
            suite.specifications().forEach(this::printSpecification);
        });
    }

    private void printSpecification(SpecificationReport specification) {
        boolean isSuccess = specification.getResult() == Result.SUCCESS;
        PrintStream out = isSuccess ? System.out : System.err;

        out.print("\tshould ");
        out.print(specification.getDescription());
        if (!isSuccess) {
            out.print("[");
            out.print(specification.getMessage());
            out.print("]");
        }
        out.println();
    }

}

