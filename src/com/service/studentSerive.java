package com.service;

import java.util.List;

import com.bean.student;

public interface studentSerive {
    List<student> getStudents();

    List<student> findByid(int id);

    List<student> findByName(String name);

    int addstudent(student student);

    int deletestudent(student student);

    int updateStudent(student studen, student nstudent);

    int update(List<student> students);
}
