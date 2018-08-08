package com.hami.common.BaseController;

public class BaseMenuPermit {
    private int titleResource;
    private int imgResource;
    private String tagName;
    public final static String DOMESTIC_FLIGHT = "DOMESTIC_FLIGHT";
    public final static String INTERNATIONAL_FLIGHT = "INTERNATIONAL_FLIGHT";
    public final static String BUS = "BUS";
    public final static String TRAIN = "TRAIN";
    public final static String DOMESTIC_HOTEL = "DOMESTIC_HOTEL";
    public final static String INTERNATIONAL_HOTEL = "INTERNATIONAL_HOTEL";

    //----------------------------------------

    public BaseMenuPermit(int titleResource, int imgResource, String tagName) {
        this.titleResource = titleResource;
        this.tagName = tagName;
        this.imgResource = imgResource;
    }
    //----------------------------------------

    public int getTitleResource() {
        return titleResource;
    }

    public void setTitleResource(int titleResource) {
        this.titleResource = titleResource;
    }

    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }
}
