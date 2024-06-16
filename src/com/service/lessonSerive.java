package com.service;

import java.util.List;

import com.bean.lesson;

public interface lessonSerive {
    List<lesson> getLessons();
    List<lesson> findById(int id);
    List<lesson> findByName(String name);
    int addlesson(lesson lesson);
    int deletelesson(lesson lesson);
    int updatelesson(lesson lesson);
}
