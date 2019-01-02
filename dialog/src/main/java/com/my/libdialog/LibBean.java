package com.my.libdialog;

public class LibBean {

    /**
     * ListView的Item内容
     */
    private String itemStr;

    public LibBean(String str){
        this.itemStr = str;
    }

    public String getItemStr() {
        return itemStr;
    }

    public void setItemStr(String itemStr) {
        this.itemStr = itemStr;
    }
}
