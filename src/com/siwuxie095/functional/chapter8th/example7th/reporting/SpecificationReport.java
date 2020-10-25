package com.siwuxie095.functional.chapter8th.example7th.reporting;

/**
 * @author Jiajing Li
 * @date 2020-10-25 20:51:35
 */
@SuppressWarnings("all")
public final class SpecificationReport {

    private final String description;
    private final Result result;
    private final String message;

    public SpecificationReport(String description, Result result, String message) {
        this.description = description;
        this.result = result;
        this.message = message;
    }

    public SpecificationReport(String specification) {
        this(specification, Result.SUCCESS, null);
    }

    public String getDescription() {
        return description;
    }

    public Result getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

}
