package com.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.admin;
import com.bean.student;
import com.util.DbUtil;

public class adminDao {
    DbUtil tool = new DbUtil();
    Connection con = tool.getCon();

    public admin getAdminByname(String name) {
        String sql = "select * from admin where aname = ?";
        admin ans = new admin();
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, name);
            ResultSet executeQuery = prst.executeQuery();
            if (executeQuery.next()) {
                ans = new admin();
                ans.setname(executeQuery.getString("aname"));
                ans.setpwd(executeQuery.getString("password"));
                ans.settemp(executeQuery.getInt("temp"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public int deleteAdmin(admin admin) {
        int temp = 0;
        String sql = "DELETE from admin where aname = ?";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, admin.getname());
            int a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public int update(List<admin> adminList) {
        int temp = 0;
        for (admin curadmin : adminList) {
            admin a = getAdminByname(curadmin.getname());
            if (a.getname() == null) {
                addAdmin(curadmin);
                temp = 1;
            }
        }
        return temp;
    }

    public int updateAdmin(admin admin) {
        int temp = 0;
        String sql = "Update admin SET password = ?,temp = '1' where aname = ?";
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, admin.getpwd());
            prst.setString(2, admin.getname());
            int a = prst.executeUpdate();
            if (a != 0) {
                temp = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public int addAdmin(admin admin) {
        int temp = 0;
        String sql = "INSERT INTO admin (aname,password,temp) VALUES (?,'0','0')";
        try {
            admin b = getAdminByname(admin.getname());
            if (b.getname() == null && b.getpwd() == null) {
                PreparedStatement prst = con.prepareStatement(sql);
                prst.setString(1, admin.getname());
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
