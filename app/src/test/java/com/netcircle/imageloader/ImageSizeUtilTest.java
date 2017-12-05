package com.netcircle.imageloader;


import android.graphics.BitmapFactory;

import com.netcircle.imageloader.util.ImageSizeUtil;

import junit.framework.Assert;

public class ImageSizeUtilTest {
    ImageSizeUtil imageSizeUtil = new ImageSizeUtil();

    @org.junit.Test
    public void testCalculateInSampleSize(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        int wid = 720;
        int high =1280;
        int result = imageSizeUtil.calculateInSampleSize(options,720,1280);
        Assert.assertEquals(result,1);

    }
}
