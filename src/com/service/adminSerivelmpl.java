package com.service;

import com.bean.admin;
import com.dao.adminDao;

public class adminSerivelmpl implements adminSerive {
    adminDao serive = new adminDao();

    public admin getAdminByname(String name) {
        return serive.getAdminByname(name);
    }

    public int updateAdmin(admin admin) {
        return serive.updateAdmin(admin);
    }

    public int deleteAdmin(admin admin) {
        return serive.deleteAdmin(admin);
    }

    public int addAdmin(admin admin) {
        return serive.addAdmin(admin);
    }

    public int login(admin admin) {
        int temp = 0;
        admin cur = serive.getAdminByname(admin.getname());
        if (cur.getpwd().equals(admin.getpwd()) && cur.gettemp() != 0) {
            temp = 1;
            if (cur.gettemp() == 2) {
                temp = 2;
            }
        }
        return temp;
    }

    public int register(admin admin) {
        int temp = 0;
        String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$";
        admin cur = serive.getAdminByname(admin.getname());
        if (cur.gettemp() == 1) {
            temp = 1;
        }
        return temp;
    }
}
