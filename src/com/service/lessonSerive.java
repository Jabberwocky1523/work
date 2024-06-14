package com.service;

import java.util.List;

import com.bean.lesson;

public interface lessonSerive {
    List<lesson> getLessons();
    int addlesson(lesson lesson);
    int deletelesson(lesson lesson);
    int updatelesson(lesson lesson);
}
