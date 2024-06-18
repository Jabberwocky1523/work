package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bean.apply;
import com.bean.lesson;
import com.service.studentSerive;
import com.service.studentSerivelmpl;
import com.util.DbUtil;

public class applyDao {
    DbUtil tool = new DbUtil();
    Connection con = tool.getCon();
    studentSerive serive = new studentSerivelmpl();
    private SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public int deleteapply(String name, int id) {
        int temp = 0;
        String sql = "DELETE from apply where lesson = ? and id = ?";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, name);
            prst.setInt(2, id);
            int a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public int addapply(apply apply) {
        int temp = 0;
        String sql = "INSERT INTO apply (temp,reason,applytime,endtime,feedback,name,id,lesson) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, apply.gettemp());
            prst.setString(2, apply.getreason());
            Date applytime = new Date();
            prst.setString(3, ft.format(applytime));
            prst.setString(4, ft.format(applytime));
            prst.setString(5, apply.getfeedback());
            prst.setString(6, apply.getname());
            prst.setString(7, apply.getid() + "");
            prst.setString(8, apply.getlesson().getname());
            int a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public List<apply> getapplie() {
        String sql = "select * from apply";
        List<apply> ans = new ArrayList<>();
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            ResultSet executeQuery = prst.executeQuery();
            while (executeQuery.next()) {
                apply temp = new apply();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                temp.setid(executeQuery.getInt("id"));
                temp.setname(executeQuery.getString("name"));
                try {
                    temp.setapplytime(ft.parse(executeQuery.getString("applytime")));
                    temp.setendtime(ft.parse(executeQuery.getString("endtime")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                temp.settemp(Integer.parseInt(executeQuery.getString("temp")));
                temp.setreason(executeQuery.getString("reason"));
                String lessonname = executeQuery.getString("lesson");
                int i = 0;
                List<lesson> lessons = serive.findByid(temp.getid()).get(0).getLessons();
                while (!lessons.get(i).getname().equals(lessonname)) {
                    i++;
                }
                temp.setLesson(lessons.get(i));
                temp.setfeedback(executeQuery.getString("feedback"));
                ans.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public int update(apply apply) {
        int temp = 0;
        String sql = "Update apply SET feedback = ?,endtime = ?,temp = 1 where id = ? and lesson = ?";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, apply.getfeedback());
            String c = ft.format(new Date());
            prst.setString(2, c);
            prst.setInt(3, apply.getid());
            prst.setString(4, apply.getlesson().getname());
            int a = prst.executeUpdate();
            sql = "Update student SET " + apply.getlesson().getname() + " = ? where id = ?";
            prst = con.prepareStatement(sql);
            prst.setInt(1, apply.getlesson().getscore());
            prst.setInt(2, apply.getid());
            a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public int overrule(apply apply) {
        int temp = 0;
        String sql = "Update apply SET temp = '2',feedback = ? where id = ? and lesson = ?";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, apply.getfeedback());
            prst.setInt(2, apply.getid());
            prst.setString(3, apply.getlesson().getname());
            int a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public List<apply> getAppliesByid(int id) {
        String sql = "select * from apply where id = ?";
        List<apply> ans = new ArrayList<>();
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setInt(1, id);
            ResultSet executeQuery = prst.executeQuery();
            while (executeQuery.next()) {
                apply temp = new apply();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                temp.setid(executeQuery.getInt("id"));
                temp.setname(executeQuery.getString("name"));
                try {
                    temp.setapplytime(ft.parse(executeQuery.getString("applytime")));
                    temp.setendtime(ft.parse(executeQuery.getString("endtime")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                temp.settemp(Integer.parseInt(executeQuery.getString("temp")));
                temp.setreason(executeQuery.getString("reason"));
                String lessonname = executeQuery.getString("lesson");
                int i = 0;
                List<lesson> lessons = serive.findByid(temp.getid()).get(0).getLessons();
                while (!lessons.get(i).getname().equals(lessonname)) {
                    i++;
                }
                temp.setLesson(lessons.get(i));
                ans.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ans;
    }
}
