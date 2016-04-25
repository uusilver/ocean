package com.tmind.ocean.util;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lijunying on 16/4/25.
 */
public class UniqueKeyGeneratorTest {

    @Test
    public void testKey(){
        String key = UniqueKeyGenerator.generateShortUuid();
        System.out.println(key);
        Assert.assertNotNull(key);
    }
}
