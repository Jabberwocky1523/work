package com.bean;

public class admin {
    private String adminname;
    private String adminpwd;
    private int temp;

    public admin() {
    }

    public admin(String name, String pwd) {
        this.adminname = name;
        this.adminpwd = pwd;
    }

    public String getname() {
        return adminname;
    }

    public String getpwd() {
        return adminpwd;
    }

    public int gettemp() {
        return this.temp;
    }

    public void setname(String name) {
        this.adminname = name;
    }

    public void setpwd(String pwd) {
        this.adminpwd = pwd;
    }

    public void settemp(int temp) {
        this.temp = temp;
    }
}
