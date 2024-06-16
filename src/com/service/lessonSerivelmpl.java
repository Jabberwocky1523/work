package com.service;

import java.util.List;

import com.bean.lesson;
import com.dao.lessonDao;
import com.dao.studentDao;

public class lessonSerivelmpl implements lessonSerive {
    lessonDao serive = new lessonDao();
    studentDao dao = new studentDao();

    public List<lesson> getLessons() {
        return serive.getlesson();
    }

    public List<lesson> findById(int id) {
        return serive.findById(id);
    }

    public List<lesson> findByName(String name) {
        return serive.findByName(name);
    }

    public int addlesson(lesson lesson) {
        int temp = serive.addlesson(lesson);
        int a = dao.addColoumn(lesson);
        return temp;
    }

    public int deletelesson(lesson lesson) {
        int temp = serive.deletelesson(lesson);
        int a = dao.deleteColoumn(lesson);
        return temp;
    }

    public int updatelesson(lesson lesson) {
        lesson cur = serive.getLessonByid(lesson.getid());
        int temp = serive.updatelesson(lesson);
        int a = dao.updateColoumn(cur, lesson);
        return temp;
    }
}
