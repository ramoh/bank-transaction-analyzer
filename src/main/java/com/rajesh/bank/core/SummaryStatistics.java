package com.rajesh.bank.core;

public class SummaryStatistics {

    private final long count;
    private final double sum;
    private final double max;
    private final double min;
    private final double average;

    public SummaryStatistics(final long count, double sum, double max, double min, double average) {
        this.count = count;
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.average = average;
    }

    public long getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getAverage() {
        return average;
    }

}
