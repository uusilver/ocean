package com.tmind.ocean.model;

import java.util.Arrays;

/**
 * Created by lijunying on 16/4/26.
 */
public class LotteryResultModelTo {
    private Integer lotteryType;
    private Integer[] lotteryIndex;
    private String lotterDescripton;

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Integer[] getLotteryIndex() {
        return lotteryIndex;
    }

    public void setLotteryIndex(Integer[] lotteryIndex) {
        this.lotteryIndex = lotteryIndex;
    }

    public String getLotterDescripton() {
        return lotterDescripton;
    }

    public void setLotterDescripton(String lotterDescripton) {
        this.lotterDescripton = lotterDescripton;
    }

    @Override
    public String toString() {
        return "LotteryResultModelTo{" +
                "lotteryType=" + lotteryType +
                ", lotteryIndex=" + Arrays.toString(lotteryIndex) +
                ", lotterDescripton='" + lotterDescripton + '\'' +
                '}';
    }
}
