package com.tmind.ocean.util;

import com.tmind.ocean.model.LotteryModelTo;
import com.tmind.ocean.model.LotteryResultModelTo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lijunying on 16/4/26.
 */
public class LotteryUtilTest {

    @Test
    public void testLotter(){
        int i  = 100;
        while(i>0){
            LotteryModelTo firstLotter = new LotteryModelTo();
            firstLotter.setLotteryType(1);
            firstLotter.setLotteryNumber(2);
            firstLotter.setLotteryDescription("一等奖");

            LotteryModelTo secondLotter = new LotteryModelTo();
            secondLotter.setLotteryType(2);
            secondLotter.setLotteryNumber(4);
            secondLotter.setLotteryDescription("二等奖");

            List<LotteryModelTo> list = Arrays.asList(firstLotter, secondLotter);

            LotterUtil lotterUtil = new LotterUtil();
            List<LotteryResultModelTo> resultModelTos = lotterUtil.getluckyResultList(100, list);
            System.out.println(resultModelTos);

            int totalResult = 0;
            for(LotteryResultModelTo modelTo : resultModelTos){
                totalResult += modelTo.getLotteryIndex().length;
            }

            Assert.assertEquals(totalResult, 6);
            i--;
        }


    }
}
