package com.siwuxie095.functional.chapter5th.example10th;

/**
 * @author Jiajing Li
 * @date 2020-10-21 22:09:14
 */
@SuppressWarnings("all")
public class StringCombiner {

    private final String delimiter;
    private final String prefix;
    private final String suffix;
    private final StringBuilder builder;

    public StringCombiner(String delimiter, String prefix, String suffix) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
        this.builder = new StringBuilder();
    }

    public StringCombiner add(String element) {
        if(!this.isAtStart()) {
            this.builder.append(delimiter);
        }
        this.builder.append(element);
        return this;
    }

    public StringCombiner merge(StringCombiner other) {
        if(!other.equals(this)) {
            if(!other.isAtStart() && !this.isAtStart()){
                other.builder.insert(0, this.delimiter);
            }
            this.builder.append(other.builder);
        }
        return this;
    }

    @Override
    public String toString() {
        return this.prefix + this.builder.toString() + this.suffix;
    }

    private boolean isAtStart() {
        return this.builder.length() == 0;
    }
}
