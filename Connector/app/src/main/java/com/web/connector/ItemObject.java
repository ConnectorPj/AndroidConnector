package com.web.connector;

/**
 * Created by HongUi on 2017-06-21.
 */

public class ItemObject {

    private String name;
    private String photo;

    public ItemObject(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
