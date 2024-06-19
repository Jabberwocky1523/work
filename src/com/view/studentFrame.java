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

import com.bean.admin;
import com.bean.apply;
import com.bean.base;
import com.bean.lesson;
import com.bean.student;
import com.service.adminSerive;
import com.service.adminSerivelmpl;
import com.service.applySerive;
import com.service.applySerivelmpl;
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
    private int index = 0;
    private int curtemp = 0;
    private student curstudent = new student();
    private String curapply;
    public JTable staffListTable;
    public JScrollPane jscrollpane;
    private JTextField searchField;
    public JButton searchButton;
    private JComboBox<String> comboBox = new JComboBox<>();
    private JComboBox<String> semester = new JComboBox<>();
    public lessonSerive serive = new lessonSerivelmpl();
    public studentSerive studentSerive = new studentSerivelmpl();
    public applySerive applySerive = new applySerivelmpl();
    public static adminSerive adminSerive = new adminSerivelmpl();
    public JMenu applyMenu;
    JButton applyButton = new JButton("申请");
    JButton deleteButton = new JButton("撤销");
    JMenu lessonmeMenu;
    public JMenuBar menuBar = new JMenuBar();

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

    public studentFrame() {

    }

    public studentFrame(student student) {
        curstudent = student;
        setTitle("学生系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口
        setBounds(200, 20, 830, 700);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);
        initComponents();

    }

    protected void initComponents() {
        searchField = new JTextField();
        searchField.setBounds(250, 10, 180, 30);
        getContentPane().add(searchField);
        searchField.setColumns(10);

        lessonmeMenu = new JMenu("查看成绩");
        applyMenu = new JMenu("成绩复查申请");
        Font t = new Font("宋体", Font.PLAIN, 15);
        applyMenu.setFont(t);
        lessonmeMenu.setFont(t);

        if (index == 0) {
            menuBar.add(lessonmeMenu);
            menuBar.add(applyMenu);
        }
        this.setJMenuBar(menuBar);
        applyMenu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                applyframe(me);
            }
        });
        lessonmeMenu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (curtemp == 0) {
                    return;
                }
                getContentPane().removeAll();
                initComponents();
                repaint();
                curtemp = 0;
            }
        });

        searchButton = new JButton("搜索");
        searchButton.setBounds(450, 10, 95, 30);
        getContentPane().add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                searchLesson(ae);
            }
        });

        StuInfo();
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        int i = 0;
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
            lesson.map.put(cur.getid(), cur);
            model.addRow(v);
        }
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
        if (index == 0) {
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
        }
        getContentPane().add(comboBox);
    }

    public void setsemester() {
        if (index == 0) {
            semester.addItem("1");
            semester.addItem("2");
            semester.setBounds(550, 50, 100, 30);
            index++;
        }
        getContentPane().add(semester);
    }

    public void searchLesson(ActionEvent ae) {
        base cur = new lesson();
        if (searchField.getText().toString().matches("^[0-9]*$") &&
                !searchField.getText().toString().equals("")) {
            cur.setid(Integer.parseInt(searchField.getText().toString()));
        }
        lesson curLesson = lesson.map.get(cur.getid());
        cur.setname(searchField.getText().toString());
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<lesson> lessons = serive.findByName(cur.getname());
        if (lessons.size() == 0) {
            lessons.add(curLesson);
        }
        for (lesson CUR : lessons) {
            Vector v = new Vector<>();
            v.add(CUR.getyear());
            v.add(CUR.getsemester());
            v.add(CUR.getid());
            v.add(CUR.getname());
            v.add(CUR.getcredit());
            v.add(CUR.getscore());
            int point = CUR.getscore() >= 60 ? ((CUR.getscore() / 10) - 5) : 0;
            v.add(point);
            v.add(curstudent.getid());
            v.add(curstudent.getname());
            model.addRow(v);
        }

    }

    public void applyframe(MouseEvent me) {
        if (curtemp == 1) {
            return;
        }
        getContentPane().removeAll();
        this.repaint();
        studentFrame b = this;
        applyButton.setBounds(450, 10, 70, 30);
        deleteButton.setBounds(310, 10, 70, 30);
        getContentPane().add(applyButton);
        getContentPane().add(deleteButton);
        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new appFrame(curstudent, b);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int a = applySerive.deleteapply(curapply, curstudent.getid());
                if (a != 0) {
                    JOptionPane.showMessageDialog(null, "撤销成功!");
                    refresh();
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "撤销失败!");
                }
            }
        });
        String[] header = { "审核状态", "学年", "学期", "课程代码", "课程名", "课程分数", "学分", "绩点", "复查理由", "申请时间", "结果反馈", "反馈时间",
                "申请人", "学号" };
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
        refresh();
        curtemp = 1;
    }

    public void select(MouseEvent me) {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        curapply = model.getValueAt(staffListTable.getSelectedRow(), 4).toString();
        System.out.println(curapply);
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<apply> applies = applySerive.getAppliesByid(curstudent.getid());
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (apply cur : applies) {
            Vector a = new Vector<String>();
            if (cur.gettemp() == 1) {
                a.add("已通过");
            } else if (cur.gettemp() == 0) {
                a.add("未审核");
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
            a.add(curstudent.getname());
            a.add(curstudent.getid());
            model.addRow(a);
        }
    }
}
