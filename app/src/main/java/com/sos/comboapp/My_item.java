package com.sos.comboapp;

import com.google.gson.annotations.SerializedName;

public class My_item {

    String user_id;
    String id;
    String title;
    @SerializedName("body")
    String text;

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String body)
    {
        this.text = body;
    }
}
