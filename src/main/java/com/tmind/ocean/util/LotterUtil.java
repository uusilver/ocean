package com.tmind.ocean.util;

import com.tmind.ocean.model.LotteryModelTo;
import com.tmind.ocean.model.LotteryResultModelTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by lijunying on 16/4/26.
 */
public class LotterUtil {

    /*
     * 一等奖：1个
     * 二等奖：3个
     * 三等奖：5个
     */

    /**
     * @Desc 根据传入的二维码数量，以及对用的奖项设置，来生成具体的中奖信息
     * @param qrcodeNum 整体的二维码数量
     * @param lotteryModelToList //奖项的设置模型
     * @return List<LotteryResultModelTo>
     */
    public List<LotteryResultModelTo> getluckyResultList(Integer qrcodeNum, List<LotteryModelTo> lotteryModelToList){

        //初始化所有二维码下标的数组
        Integer[] qrcodeNumberPool = initLotteryNumberPool(qrcodeNum);

        //结果列
        List<LotteryResultModelTo> resultModel = new ArrayList<LotteryResultModelTo>();

        //loop
        for(LotteryModelTo lotteryModelTo : lotteryModelToList){
                LotteryResultModelTo resultModelTo = new LotteryResultModelTo();

                resultModelTo.setLotterDescripton(lotteryModelTo.getLotteryDescription());//添加基本信息
                resultModelTo.setLotteryType(lotteryModelTo.getLotteryType());//添加基本信息
                //从类中获得能中奖当前奖项的个数
                Integer currentLotteryNum = lotteryModelTo.getLotteryNumber();
                //当前中奖类型的中奖号码数组
                Integer[] currentLuckNumberPool = new Integer[currentLotteryNum];

                for(int i = 0; i<currentLotteryNum; i++){
                    Random random = new Random();
                    //根据数组长度，获取一个不超过qrcodeNumberPool长度的一个随机下标，用来表示中奖, eg :8
                    Integer luckNumberIndex = random.nextInt(qrcodeNumberPool.length);
                    //将其放入结果数组
                    currentLuckNumberPool[i] = luckNumberIndex;
                    //将其从原数组中移除，获得新数组
                    qrcodeNumberPool = removeIndexFromNumberPool(qrcodeNumberPool, luckNumberIndex);
                }//第一组循环完毕
                resultModelTo.setLotteryIndex(currentLuckNumberPool);
                resultModel.add(resultModelTo);
        }
        return  resultModel;
    }


    private Integer[] initLotteryNumberPool(Integer qrcodeNum){
        Integer[] integers = new Integer[qrcodeNum];
        for(int i=0;i<qrcodeNum;i++)
            integers[i] = i;
        return integers;
    }

    //从数组中移除指定下标的数字,返回一个新数组
    private Integer[] removeIndexFromNumberPool(Integer[] integers, Integer removeIndex){
        Integer[] numberPoolAfterRemoveIndex = new Integer[integers.length];
        for(int i=0;i<integers.length; i++){
            if(i!= removeIndex)
                numberPoolAfterRemoveIndex[i] = integers[i];
        }
        return numberPoolAfterRemoveIndex;
    }
}
