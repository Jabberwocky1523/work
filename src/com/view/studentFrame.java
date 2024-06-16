package com.view;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.bean.lesson;
import com.bean.student;
import com.service.lessonSerive;
import com.service.lessonSerivelmpl;
import com.service.studentSerive;
import com.service.studentSerivelmpl;
import com.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class studentFrame extends JFrame {
    private JTable staffListTable;
    private JScrollPane jscrollpane;
    private JTextField searchField;
    private JButton searchButton;
    private studentSerive dao = new studentSerivelmpl();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    studentFrame frame = new studentFrame(new student());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public studentFrame(student student) {
        initComponents();
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        int i = 0;
        List<lesson> lessons = student.getLessons();
        String[] header = { "学年", "学期", "课程代码", "课程名称", "学分", "成绩", "绩点", "学号", "姓名" };
        for (lesson cur : lessons) {
            Vector v = new Vector<>();
            v.add(cur.getyear());
            v.add(cur.getsemester());
            v.add(cur.getid());
            v.add(cur.getname());
            v.add(cur.getcredit());
            int point = cur.getscore() >= 60 ? ((cur.getscore() / 10) - 5) : 0;
            v.add(cur.getscore());
            v.add(point);
            v.add(student.getid());
            v.add(student.getname());
            model.addRow(v);
        }
    }

    private void initComponents() {
        setTitle("学生系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口
        setBounds(200, 20, 1200, 700);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);

        searchField = new JTextField();
        searchField.setBounds(250, 10, 180, 30);
        getContentPane().add(searchField);
        searchField.setColumns(10);

        searchButton = new JButton("搜索");
        searchButton.setBounds(450, 10, 95, 30);
        getContentPane().add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                searchLesson(ae);
            }
        });

        StuInfo();

    }

    public void StuInfo() {
        String[] header = { "学年", "学期", "课程代码", "课程名称", "学分", "成绩", "绩点", "学号", "姓名" };
        DefaultTableModel model = new DefaultTableModel(header, 0);
        staffListTable = new JTable(model);
        jscrollpane = new JScrollPane(staffListTable);
        jscrollpane.setBounds(10, 100, 800, 600);
        getContentPane().add(jscrollpane);
    }

    public void searchLesson(ActionEvent ae) {

    }

}
