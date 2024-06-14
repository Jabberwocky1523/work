package com.service;

import java.util.List;

import com.bean.student;
import com.dao.studentDao;

public class studentSerivelmpl implements studentSerive {
    studentDao serive = new studentDao();

    public List<student> getStudents() {
        return serive.getStudent();
    }

    public List<student> findByid(int id) {
        return serive.findByid(id);
    }

    public List<student> findByName(String name){
        return serive.findByname(name);
    }

    public int addstudent(student student) {
        return serive.addstudent(student);
    }

    public int deletestudent(student student) {
        return serive.deletestudent(student);
    }

    public int updateStudent(student student,student nstudent) {
        return serive.updateStudent(student,nstudent);
    }
}
