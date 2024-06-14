package com.bean;

public class lesson {
    private int id;
    private String name;
    private int credit;
    private int score = 0;

    public lesson() {
    }

    public lesson(int id, String name, int credit) {
        this.id = id;
        this.name = name;
        this.credit = credit;
    }

    public int getid() {
        return this.id;
    }

    public String getname() {
        return this.name;
    }

    public int getcredit() {
        return this.credit;
    }

    public int getscore() {
        return this.score;
    }

    public void setid(int id) {
        this.id = id;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setcredit(int credit) {
        this.credit = credit;
    }

    public void setscore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return new String(id + "" + name + credit + "" + score);
    }
}
