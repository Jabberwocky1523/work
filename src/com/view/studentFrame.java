package com.view;

import java.awt.EventQueue;
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
    private student curstudent;
    private JTable staffListTable;
    private JScrollPane jscrollpane;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> comboBox = new JComboBox<>();
    private JComboBox<String> semester = new JComboBox<>();
    public lessonSerive serive = new lessonSerivelmpl();
    public studentSerive studentSerive = new studentSerivelmpl();

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
        curstudent = student;
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        int i = 0;
        List<lesson> lessons = student.getLessons();
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
        setyear();
        setsemester();

        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                selectsemesteAndyear(ie);
            }
        });

        semester.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                selectsemesteAndyear(ie);
            }
        });
    }

    public void selectsemesteAndyear(ItemEvent me) {
        if (me.getStateChange() == ItemEvent.SELECTED) {
            int a = Integer.parseInt(semester.getSelectedItem().toString());
            String b = comboBox.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
            if (b.equals("全部")) {
                model.setRowCount(0);
                List<lesson> lessons = curstudent.getLessons();
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
                    v.add(curstudent.getid());
                    v.add(curstudent.getname());
                    model.addRow(v);
                }
                return;
            }
            model.setRowCount(0);
            List<lesson> lessons = curstudent.getLessons();
            for (lesson cur : lessons) {
                Vector v = new Vector<>();
                if (cur.getsemester() == a && cur.getyear().equals(b)) {
                    v.add(cur.getyear());
                    v.add(cur.getsemester());
                    v.add(cur.getid());
                    v.add(cur.getname());
                    v.add(cur.getcredit());
                    int point = cur.getscore() >= 60 ? ((cur.getscore() / 10) - 5) : 0;
                    v.add(cur.getscore());
                    v.add(point);
                    v.add(curstudent.getid());
                    v.add(curstudent.getname());
                    model.addRow(v);
                }
            }
        }
    }

    public void StuInfo() {
        String[] header = { "学年", "学期", "课程代码", "课程名称", "学分", "成绩", "绩点", "学号", "姓名" };
        DefaultTableModel model = new DefaultTableModel(header, 0);
        staffListTable = new JTable(model);
        jscrollpane = new JScrollPane(staffListTable);
        jscrollpane.setBounds(10, 100, 800, 600);
        getContentPane().add(jscrollpane);
    }

    public void setyear() {
        HashMap<String, Integer> map = new HashMap<>();
        comboBox.addItem("全部");
        List<lesson> lessons = serive.getLessons();
        for (lesson cur : lessons) {
            map.put(cur.getyear(), 0);
        }
        for (lesson cur : lessons) {
            if (map.get(cur.getyear()) == 0) {
                comboBox.addItem(cur.getyear());
                map.replace(cur.getyear(), 1);
            }
        }
        comboBox.setBounds(150, 50, 100, 30);
        getContentPane().add(comboBox);
    }

    public void setsemester() {

        semester.addItem("1");
        semester.addItem("2");
        semester.setBounds(550, 50, 100, 30);
        getContentPane().add(semester);
    }

    public void searchLesson(ActionEvent ae) {

    }

}
