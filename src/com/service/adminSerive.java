package com.service;

import java.util.List;

import com.bean.admin;

public interface adminSerive {
    admin getAdminByname(String name);

    int updateAdmin(admin admin);

    int deleteAdmin(admin admin);

    int addAdmin(admin admin);

    int login(admin admin);

    int update(List<admin> adminList);
}
