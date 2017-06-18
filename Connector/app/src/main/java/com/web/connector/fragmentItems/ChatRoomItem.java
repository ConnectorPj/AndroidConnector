package com.web.connector.fragmentItems;

import android.graphics.drawable.Drawable;

/**
 * Created by HongUi on 2017-06-18.
 */

public class ChatRoomItem {
    private Drawable imgDrawable;
    private String titleStr;
    private String numberStr;
    private String contentStr;

    public Drawable getImgDrawable() {
        return imgDrawable;
    }

    public void setImgDrawable(Drawable imgDrawable) {
        this.imgDrawable = imgDrawable;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public String getNumberStr() {
        return numberStr;
    }

    public void setNumberStr(String numberStr) {
        this.numberStr = numberStr;
    }


}
