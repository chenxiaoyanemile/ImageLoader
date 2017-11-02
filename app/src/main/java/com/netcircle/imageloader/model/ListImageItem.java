package com.netcircle.imageloader.model;

/**
 * Created by sweetgirl on 2017/11/2
 */

public class ListImageItem {

    private String image_url_01;
    private String image_url_02;
    private String image_url_03;

    public ListImageItem(String image_url_01, String image_url_02, String image_url_03){
        this.image_url_01 = image_url_01;
        this.image_url_02 = image_url_02;
        this.image_url_03 = image_url_03;
    }

    public String getImage_url_01() {
        return image_url_01;
    }

    public void setImage_url_01(String image_url_01) {
        this.image_url_01 = image_url_01;
    }

    public String getImage_url_02() {
        return image_url_02;
    }

    public void setImage_url_02(String image_url_02) {
        this.image_url_02 = image_url_02;
    }

    public String getImage_url_03() {
        return image_url_03;
    }

    public void setImage_url_03(String image_url_03) {
        this.image_url_03 = image_url_03;
    }
}
