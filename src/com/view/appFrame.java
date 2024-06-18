package com.view;

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
import java.text.SimpleDateFormat;

import com.bean.apply;
import com.bean.lesson;
import com.bean.student;
import com.service.applySerive;
import com.service.applySerivelmpl;
import com.service.lessonSerive;
import com.service.lessonSerivelmpl;
import com.service.studentSerive;
import com.service.studentSerivelmpl;
import com.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class appFrame extends JFrame {
    private SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private apply curApply = new apply();
    private student curstudent;
    private studentFrame superFrame;
    private JLabel reasonlLabel;
    private JTextField reasonField;
    private JTable staffListTable;
    private JScrollPane jscrollpane;
    public JButton submitButton;
    private JComboBox<String> comboBox = new JComboBox<>();
    private JComboBox<String> semester = new JComboBox<>();
    public lessonSerive serive = new lessonSerivelmpl();
    public studentSerive studentSerive = new studentSerivelmpl();
    public applySerive applySerive = new applySerivelmpl();
    public JMenu applyMenu;

    public appFrame(student student, studentFrame studentFrame) {
        curstudent = student;
        superFrame = studentFrame;
        this.setTitle("成绩复查申请提交");
        setBounds(200, 20, 830, 700);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);
        StuInfo();
        setyear();
        setsemester();
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
        reasonlLabel = new JLabel("复查理由：");
        reasonField = new JTextField();
        reasonlLabel.setBounds(180, 10, 70, 30);
        reasonField.setBounds(250, 10, 300, 30);
        submitButton = new JButton("提交");
        submitButton.setBounds(600, 10, 70, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                submit(curApply);
            }
        });
        getContentPane().add(reasonField);
        getContentPane().add(reasonlLabel);
        getContentPane().add(submitButton);
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
        staffListTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                select(me);
            }
        });
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
            comboBox.setBounds(150, 50, 100, 30);
        }
        getContentPane().add(comboBox);
    }

    public void setsemester() {
        semester.addItem("1");
        semester.addItem("2");
        semester.setBounds(550, 50, 100, 30);
        getContentPane().add(semester);
    }

    public void submit(apply apply) {
        if (StringUtil.isEmpty(reasonField.getText().toString())) {
            JOptionPane.showMessageDialog(this, "复查理由不能为空！");
            return;
        }
        apply.setreason(reasonField.getText().toString());
        int b = applySerive.addapply(apply);
        DefaultTableModel model = (DefaultTableModel) superFrame.staffListTable.getModel();
        if (b != 0) {
            JOptionPane.showMessageDialog(this, "提交成功!");
            model.setRowCount(0);
            List<apply> applies = applySerive.getAppliesByid(curstudent.getid());
            for (apply cur : applies) {
                Vector a = new Vector<String>();
                if (cur.gettemp() == 1) {
                    a.add("已通过");
                } else {
                    a.add("未通过");
                }
                a.add(cur.getlesson().getyear());
                a.add(cur.getlesson().getsemester());
                a.add(cur.getlesson().getid() + "");
                a.add(cur.getlesson().getname());
                a.add(cur.getlesson().getscore() + "");
                a.add(cur.getlesson().getcredit() + "");
                int point = cur.getlesson().getscore() >= 60 ? ((cur.getlesson().getscore() / 10) - 5) : 0;
                a.add(point + "");
                a.add(cur.getreason());
                a.add(ft.format(cur.getapplytime()));
                a.add(cur.getfeedback());
                a.add(ft.format(cur.getendtime()));
                model.addRow(a);
            }

        } else {
            JOptionPane.showMessageDialog(this, "提交失败!");
        }

    }

    public void select(MouseEvent me) {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        String year = model.getValueAt(staffListTable.getSelectedRow(), 0).toString();
        int semester = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 1).toString());
        int lessonid = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 2).toString());
        String lessonname = model.getValueAt(staffListTable.getSelectedRow(), 3).toString();
        int credit = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 4).toString());
        int id = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 7).toString());
        String name = model.getValueAt(staffListTable.getSelectedRow(), 8).toString();
        lesson lesson = new lesson(lessonid, lessonname, credit, semester, year);
        curApply.setLesson(lesson);
        curApply.setapplytime(null);
        curApply.setendtime(null);
        curApply.setfeedback(null);
        curApply.setid(id);
        curApply.setname(name);
        if (!reasonField.getText().toString().equals("")) {
            curApply.setreason(reasonField.getText().toString());
        }
        curApply.settemp(0);
        System.out.println(curApply);
    }
}
