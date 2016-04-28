package com.tmind.ocean.model;

/**
 * Created by lijunying on 16/4/26.
 */
public class LotteryModelTo {

    private Integer lotteryType; //奖励的类型，当前用户唯一
    private Integer lotteryNumber;  //奖励的数量
    private String  lotteryDescription; //奖励的描述

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Integer getLotteryNumber() {
        return lotteryNumber;
    }

    public void setLotteryNumber(Integer lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

    public String getLotteryDescription() {
        return lotteryDescription;
    }

    public void setLotteryDescription(String lotteryDescription) {
        this.lotteryDescription = lotteryDescription;
    }

    @Override
    public String toString() {
        return "LotteryModelTo{" +
                "lotteryType=" + lotteryType +
                ", lotteryNumber=" + lotteryNumber +
                ", lotteryDescription='" + lotteryDescription + '\'' +
                '}';
    }
}
