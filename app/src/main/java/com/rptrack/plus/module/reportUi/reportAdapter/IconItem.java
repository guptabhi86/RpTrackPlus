package com.rptrack.plus.module.reportUi.reportAdapter;



/**
 * Created by Ganesh ɐɯɹɐɥs on 2/23/2021.
 */
public class IconItem {
    private int color;
    private String label;
    private String itemCount;



    public IconItem(int color, String label, String itemCount) {
        this.color = color;
        this.label = label;
        this.itemCount = itemCount;
    }

    public IconItem(int color, String label) {
        this.color = color;
        this.label = label;
    }



    public int getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

    public String getItemCount() {
        return itemCount;
    }

}
