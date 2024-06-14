package com.service;

import com.bean.admin;

public interface adminSerive {
    admin getAdminByname(String name);
    int updateAdmin(admin admin);
    int deleteAdmin(admin admin);
    int addAdmin(admin admin);
    int login(admin admin);
}
