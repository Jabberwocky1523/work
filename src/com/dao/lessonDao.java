package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bean.lesson;
import com.util.DbUtil;

public class lessonDao {
    DbUtil tool = new DbUtil();
    Connection con = tool.getCon();

    public List<lesson> getlesson() {
        String sql = "select * from lesson";
        List<lesson> ans = new ArrayList<>();
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            ResultSet executeQuery = prst.executeQuery();
            while (executeQuery.next()) {
                lesson temp = new lesson();
                temp.setid(executeQuery.getInt("id"));
                temp.setname(executeQuery.getString("name"));
                temp.setcredit(executeQuery.getInt("credit"));
                temp.setyear(executeQuery.getString("year"));
                temp.setsemester(executeQuery.getInt("semester"));
                ans.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public lesson getLessonByid(int id) {
        String sql = "select * from lesson where id = ?";
        lesson temp = new lesson();
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, id);
            ResultSet executeQuery = prst.executeQuery();
            if (executeQuery.next()) {
                temp.setid(executeQuery.getInt("id"));
                temp.setname(executeQuery.getString("name"));
                temp.setcredit(executeQuery.getInt("credit"));
                temp.setyear(executeQuery.getString("year"));
                temp.setsemester(executeQuery.getInt("semester"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public int deletelesson(lesson lesson) {
        int temp = 0;
        String sql = "DELETE from lesson where id = ?";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, lesson.getid());
            int a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public int updatelesson(lesson lesson) {
        int temp = 0;
        String sql = "Update lesson SET name = ?,credit = ?,year = ?,semester = ? where id = ?";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, lesson.getname());
            prst.setInt(2, lesson.getcredit());
            prst.setString(3, lesson.getyear());
            prst.setInt(4, lesson.getsemester());
            prst.setInt(5, lesson.getid());
            int a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public int addlesson(lesson lesson) {
        int temp = 0;
        String sql = "INSERT INTO lesson (id,name,credit,year,semester) VALUES (?,?,?,?,?)";
        try {
            lesson b = getLessonByid(lesson.getid());
            if (b.getname() == null) {
                PreparedStatement prst = con.prepareStatement(sql);
                prst.setInt(1, lesson.getid());
                prst.setString(2, lesson.getname());
                prst.setInt(3, lesson.getcredit());
                prst.setString(4, lesson.getyear());
                prst.setInt(5, lesson.getsemester());
                int a = prst.executeUpdate();
                if (a != 0) {
                    temp = 1;
                }
            } else {
                temp = 2;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
