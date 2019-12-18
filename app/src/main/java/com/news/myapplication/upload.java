package com.news.myapplication;

public class upload {
    private String imageuri;
    private String mname;
    private String uname;

    public upload()
    {

    }
    public upload(String name ,String imageuri,String user)
    {

        uname=user;
        mname=name;
        this.imageuri=imageuri;

    }
    public void setname(String name)

    {
        this.mname=name;
    }
    public void setimageuri (String imageuri)

    {
        this.imageuri=imageuri;
    }
    public String getimageuri()
    {
        return this.imageuri;
    }
    public String getname() {
        return this.mname;
    }
    public void setuser (String user)

    {
        this.uname=user;
    }
    public String getuser()
    {
        return this.uname;
    }
}
