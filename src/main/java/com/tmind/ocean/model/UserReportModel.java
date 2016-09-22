package com.tmind.ocean.model;

/**
 * Created by lijunying on 16/9/18.
 */
public class UserReportModel {

    private String month;
    private String title;
    private String normalScan;
    private String warningScan;
    private String averageScan;
    private int pieChartNormalScan;
    private int pieChartWarningScan;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNormalScan() {
        return normalScan;
    }

    public void setNormalScan(String normalScan) {
        this.normalScan = normalScan;
    }

    public String getWarningScan() {
        return warningScan;
    }

    public void setWarningScan(String warningScan) {
        this.warningScan = warningScan;
    }

    public String getAverageScan() {
        return averageScan;
    }

    public void setAverageScan(String averageScan) {
        this.averageScan = averageScan;
    }

    public int getPieChartNormalScan() {
        return pieChartNormalScan;
    }

    public void setPieChartNormalScan(int pieChartNormalScan) {
        this.pieChartNormalScan = pieChartNormalScan;
    }

    public int getPieChartWarningScan() {
        return pieChartWarningScan;
    }

    public void setPieChartWarningScan(int pieChartWarningScan) {
        this.pieChartWarningScan = pieChartWarningScan;
    }
}
