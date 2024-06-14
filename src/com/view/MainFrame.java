/*
 * Created by JFormDesigner on Wed Jun 12 21:42:31 HKT 2024
 */

package com.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.bean.lesson;
import com.bean.student;
import com.service.lessonSerive;
import com.service.lessonSerivelmpl;
import com.service.studentSerive;
import com.service.studentSerivelmpl;
import com.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Kurisu
 */
public class MainFrame extends JFrame {
    private student student1 = new student();
    private JTextField[] co;
    private int Xindex1 = 860;
    private int Yindex = 100;
    private int Xindex2 = 950;
    public lessonSerive serive = new lessonSerivelmpl();
    public studentSerive studentSerive = new studentSerivelmpl();
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    private JTable staffListTable;
    private JScrollPane jscrollpane;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel stuLabel;
    private JTextField stuTextField;
    private JButton addButton;
    private JButton resetButton;
    private JButton editButton;
    private JButton deleteButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("高校成绩管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口
        setBounds(200, 20, 1200, 700);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        StuInfo();

        staffListTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me){
                select(me);
            }
        });

        searchField = new JTextField();
        searchField.setBounds(250, 10, 180, 30);
        getContentPane().add(searchField);
        searchField.setColumns(10);

        searchButton = new JButton("搜索");
        searchButton.setBounds(450, 10, 95, 30);
        getContentPane().add(searchButton);
            searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                searchStudent(ae);
            }
        });

        setLabel();

        
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    public void StuInfo() {
        List<lesson> lessonList = serive.getLessons();
        String[] header = new String[lessonList.size() + 2];
        header[0] = "id";
        header[1] = "姓名";
        int i = 2;
        for (lesson cur : lessonList) {
            header[i] = cur.getname();
            i++;
        }
        DefaultTableModel model = new DefaultTableModel(header, 0);
        staffListTable = new JTable(model);
        jscrollpane = new JScrollPane(staffListTable);
        jscrollpane.setBounds(10, 50, 800, 600);
        getContentPane().add(jscrollpane);
        setstuinfo(new student());
    }

    public void setstuinfo(student student) {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<student> students = studentSerive.getStudents();
        for (student curstu : students) {
            Vector v = new Vector<>();
            v.add(curstu.getid());
            v.add(curstu.getname());
            for (lesson curLesson : curstu.getLessons()) {
                v.add(curLesson.getscore());
            }
            model.addRow(v);
        }
    }

    public void searchStudent(ActionEvent ae) {
        student cur = new student();
        if (searchField.getText().matches("^[0-9]*$")) {
            cur.setid(Integer.parseInt(searchField.getText().toString()));
        }
        cur.setname(searchField.getText().toString());
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<student> students = studentSerive.findByName(cur.getname());
        if (students.size() == 0) {
            students = studentSerive.findByid(cur.getid());
        }
        for (student curstu : students) {
            Vector v = new Vector<>();
            v.add(curstu.getid());
            v.add(curstu.getname());
            for (lesson curLesson : curstu.getLessons()) {
                v.add(curLesson.getscore());
            }
            model.addRow(v);
        }
    }

    public void setLabel() {
        int i = 0;
        int index = 100;
        List<lesson> lessons = serive.getLessons();
        co = new JTextField[lessons.size()];
        idLabel = new JLabel("学号：");
        idTextField = new JTextField();
        idLabel.setBounds(860, 50, 100, 30);
        idTextField.setBounds(950, 50, 200, 30);
        getContentPane().add(idLabel);
        getContentPane().add(idTextField);

        nameLabel = new JLabel("姓名：");
        nameTextField = new JTextField();
        nameLabel.setBounds(860, 100, 100, 30);
        nameTextField.setBounds(950, 100, 200, 30);
        getContentPane().add(nameLabel);
        getContentPane().add(nameTextField);

        for (lesson cur : lessons) {
            stuLabel = new JLabel(cur.getname());
            stuTextField = new JTextField();
            stuLabel.setBounds(Xindex1, 50 + index, 100, 30);
            stuTextField.setBounds(Xindex2, 50 + index, 200, 30);
            getContentPane().add(stuLabel);
            getContentPane().add(stuTextField);
            co[i] = stuTextField;
            i++;
            index = index + 50;
        }

        addButton = new JButton("新增学生");
        addButton.setBounds(820, 50 + index, 90, 30);
        getContentPane().add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                studentAddAct(ae);
            }
        });

        editButton = new JButton("修改学生");
        editButton.setBounds(910, 50 + index, 90, 30);
        getContentPane().add(editButton);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                submitEditAct(ae);
            }
        });

        resetButton = new JButton("重置");
        resetButton.setBounds(1000, 50 + index, 90, 30);
        getContentPane().add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resetValue(ae);
            }
        });

        deleteButton = new JButton("删除学生");
        deleteButton.setBounds(1090, 50 + index, 90, 30);
        getContentPane().add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deletestudent(ae);
            }
        });
    }

    public void select(MouseEvent me) {
        int index = 2;
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        int id = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 0).toString());
        String name = model.getValueAt(staffListTable.getSelectedRow(), 1).toString();
        idTextField.setText(model.getValueAt(staffListTable.getSelectedRow(), 0).toString());
        nameTextField.setText(model.getValueAt(staffListTable.getSelectedRow(), 1).toString());
        List<lesson> lessons = serive.getLessons();
        for (int i = 0; i < lessons.size(); i++) {
            co[i].setText(model.getValueAt(staffListTable.getSelectedRow(), index).toString());
            lessons.get(i)
                    .setscore(Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), index).toString()));
            index++;
        }
        student1.setid(id);
        student1.setname(name);
        student1.setlesson(lessons);
    }

    public void resetValue(ActionEvent ae) {
        // TODO 自动生成的方法存根
        idTextField.setText("");
        nameTextField.setText("");
        for (JTextField cur : co) {
            cur.setText("");
        }
    }

    public void deletestudent(ActionEvent ae) {
        int row = staffListTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "请选中要修改的数据！");
            return;
        }
        int id = 0;
        if (idTextField.getText().toString().matches("^[0-9]*$")) {
            id = Integer.parseInt(idTextField.getText().toString());
        } else {
            JOptionPane.showMessageDialog(this, "学生学号必须为数字");
            return;
        }
        String name = nameTextField.getText().toString();
        List<lesson> lessons = serive.getLessons();
        student cur = new student(id, name, lessons);
        int a = studentSerive.deletestudent(cur);
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "删除成功！");
        } else {
            JOptionPane.showMessageDialog(this, "删除失败！");
        }
        setstuinfo(new student());
        resetValue(ae);
    }

    public void studentAddAct(ActionEvent ae) {
        int id = 0;
        if (idTextField.getText().toString().matches("^[0-9]*$")) {
            id = Integer.parseInt(idTextField.getText().toString());
        } else {
            JOptionPane.showMessageDialog(this, "学生学号必须为数字");
            return;
        }
        String name = nameTextField.getText().toString();
        List<lesson> lessons = serive.getLessons();
        int i = 0;
        for (JTextField cur : co) {
            String temp = cur.getText().toString();
            if (temp.matches("^[0-9]*$")) {
                lessons.get(i).setscore(Integer.parseInt(temp));
            } else {
                JOptionPane.showMessageDialog(this, "课程分数必须为数字!");
                return;
            }
            i++;
        }
        if (StringUtil.isEmpty(name)) {
            JOptionPane.showMessageDialog(this, "请填写学生姓名!");
            return;
        }
        if (id == 0) {
            JOptionPane.showMessageDialog(this, "请填写学生学号！");
            return;
        }
        student cur = new student(id, name, lessons);
        int a = studentSerive.addstudent(cur);
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "添加成功!");
            setstuinfo(new student());
        } else {
            JOptionPane.showMessageDialog(this, "添加失败!");
        }
        resetValue(ae);
    }

    public void submitEditAct(ActionEvent ae) {
        int row = staffListTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "请选中要修改的数据！");
            return;
        }
        int id = 0;
        if (idTextField.getText().toString().matches("^[0-9]*$")) {
            id = Integer.parseInt(idTextField.getText().toString());
        } else {
            JOptionPane.showMessageDialog(this, "学生学号必须为数字");
            return;
        }
        String name = nameTextField.getText().toString();
        List<lesson> lessons = serive.getLessons();
        int i = 0;
        for (JTextField cur : co) {
            String temp = cur.getText().toString();
            if (temp.matches("^[0-9]*$")) {
                lessons.get(i).setscore(Integer.parseInt(temp));
            } else {
                JOptionPane.showMessageDialog(this, "课程分数必须为数字!");
                return;
            }
            i++;
        }
        if (StringUtil.isEmpty(name)) {
            JOptionPane.showMessageDialog(this, "请填写学生姓名!");
            return;
        } else if (id == 0) {
            JOptionPane.showMessageDialog(this, "请填写学生学号！");
            return;
        }
        student cur = new student(id, name, lessons);
        int a = studentSerive.updateStudent(student1, cur);
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "修改成功!");
            setstuinfo(new student());
            return;
        } else {
            JOptionPane.showMessageDialog(this, "修改失败!");
        }
    }
}
