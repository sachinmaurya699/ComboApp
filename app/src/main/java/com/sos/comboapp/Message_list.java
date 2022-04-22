package com.sos.comboapp;

public class Message_list {

   private String name,mobile,last_message;
   private int onseenmessage;

    public Message_list(String name, String mobile, String last_message, int onseenmessage)
    {
        this.name = name;
        this.mobile = mobile;
        this.last_message = last_message;
        this.onseenmessage = onseenmessage;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getLast_message()
    {
        return last_message;
    }

    public void setLast_message(String last_message)
    {
        this.last_message = last_message;
    }

    public int getOnseenmessage()
    {
        return onseenmessage;
    }

    public void setOnseenmessage(int onseenmessage)
    {
        this.onseenmessage = onseenmessage;
    }
}
