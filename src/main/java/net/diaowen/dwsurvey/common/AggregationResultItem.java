package net.diaowen.dwsurvey.common;

import java.util.HashMap;
import java.util.Map;

public class AggregationResultItem {

    private long docCount;
    private double statsAvg;
    private long statsCount;
    private double statsMax;
    private double statsMin;
    private double statsSum;

    AggregationResultItem(){

    }

    public AggregationResultItem(long docCount) {
        this.docCount = docCount;
    }

    public AggregationResultItem(long docCount, double statsAvg, long statsCount, double statsMax, double statsMin, double statsSum) {
        this.docCount = docCount;
        this.statsAvg = statsAvg;
        this.statsCount = statsCount;
        this.statsMax = statsMax;
        this.statsMin = statsMin;
        this.statsSum = statsSum;
    }

    public long getDocCount() {
        return docCount;
    }

    public void setDocCount(long docCount) {
        this.docCount = docCount;
    }

    public double getStatsAvg() {
        return statsAvg;
    }

    public void setStatsAvg(double statsAvg) {
        this.statsAvg = statsAvg;
    }

    public long getStatsCount() {
        return statsCount;
    }

    public void setStatsCount(long statsCount) {
        this.statsCount = statsCount;
    }

    public double getStatsMax() {
        return statsMax;
    }

    public void setStatsMax(double statsMax) {
        this.statsMax = statsMax;
    }

    public double getStatsMin() {
        return statsMin;
    }

    public void setStatsMin(double statsMin) {
        this.statsMin = statsMin;
    }

    public double getStatsSum() {
        return statsSum;
    }

    public void setStatsSum(double statsSum) {
        this.statsSum = statsSum;
    }
}
