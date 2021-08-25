package com.fhh.technology.network.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by fhh on 2018/10/15
 */

public class DiscoverDataBean {
    private List<String> itemData;

    public DiscoverDataBean() {
        itemData = new ArrayList<>();
        itemData.add("CardView");
        itemData.add("Annular Percentage");
        itemData.add("EditText");
        itemData.add("SQLite Date Base");
        itemData.add("Progress Step");
        itemData.add("Humor");
        itemData.add("Weather");
        itemData.add("Pager");
        itemData.add("Item8");
        itemData.add("Item9");
        itemData.add("Item10");
        itemData.add("Item11");
        itemData.add("Item12");
        itemData.add("Item13");
        itemData.add("Item14");
        itemData.add("Item15");
        itemData.add("Item16");
    }

    public void setItemData(List<String> itemData) {
        this.itemData = itemData;
    }

    public List<String> getItemData() {
        return itemData;
    }
}
