package com.web.connector.reviewList;

import android.graphics.drawable.Drawable;

/**
 * Created by user on 2017-06-18.
 */

public class ReviewListItem {
    private Drawable profile;
    private String name;
    private String date;
    private String detail;

    public Drawable getProfile() {
        return profile;
    }

    public void setProfile(Drawable profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
