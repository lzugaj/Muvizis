package com.luv2code.android.muvizis.db.models;

public class SimpleDataObject {

    public String title;
    public String data;

    public SimpleDataObject(String title, String data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
