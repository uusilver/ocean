package com.tmind.ocean.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lijunying on 16/4/16.
 */
public class IPAnalyzerTest {

    @Test
    public void testIpAnalyze(){
        String ipInfo = IPAnalyzer.queryAddressByIp("58.213.20.42");
        Assert.assertNotNull(ipInfo);
        String expectResult = "[\"中国\",\"江苏\",\"南京\",\"\",\"电信\"]";

    }
}
