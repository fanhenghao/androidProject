package com.fhh.technology.network.bean;

import java.util.List;

/**
 * desc:自己造的Bean类，用于适用CustomView类使用
 * Created by fhh on 2018/7/17
 */

public class CustomViewBean {
    private String name;
    private String percent;
    private List<Integer> mColorList;
    private List<Float> mRateList;
    private List<String> mLabelList;
    private List<Integer> mValueList;

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getColorList() {
        return mColorList;
    }

    public void setColorList(List<Integer> colorList) {
        mColorList = colorList;
    }

    public List<Float> getRateList() {
        return mRateList;
    }

    public void setRateList(List<Float> rateList) {
        mRateList = rateList;
    }

    public List<String> getLabelList() {
        return mLabelList;
    }

    public void setLabelList(List<String> labelList) {
        mLabelList = labelList;
    }

    public List<Integer> getValueList() {
        return mValueList;
    }

    public void setValueList(List<Integer> valueList) {
        mValueList = valueList;
    }
}
