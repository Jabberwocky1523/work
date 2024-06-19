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

public class LessonFrame extends JFrame {
    private int temp2 = 0;
    private int temp = 0;
    private student student1 = new student();
    private int Xindex1 = 860;
    private int Yindex = 100;
    private int Xindex2 = 950;
    private JMenuBar MenuBar = new JMenuBar();
    private JMenu lessonMJMenu;
    public lessonSerive serive = new lessonSerivelmpl();
    public studentSerive studentSerive = new studentSerivelmpl();
    private List<lesson> curLessons = serive.getLessons();
    private JTextField[] co = new JTextField[serive.getLessons().size()];
    private JLabel[] coLabels = new JLabel[serive.getLessons().size()];
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
    private JLabel creditLabel;
    private JTextField creditField;
    private JTextField nameTextField;
    private JLabel yearLabel;
    private JTextField yearfField;
    private JLabel semesterLabel;
    private JLabel stuLabel;
    private JTextField stuTextField;
    private JButton addButton;
    private JButton resetButton;
    private JButton editButton;
    private JButton deleteButton;
    private JRadioButton MRadioButton;
    private JRadioButton FRadioButton;
    private ButtonGroup semesterButtonGroup;
    private MainFrame MainFrame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LessonFrame frame = new LessonFrame(new MainFrame());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LessonFrame(MainFrame Main) {
        MainFrame = Main;
        initComponents();
    }

    public void initComponents() {
        setTitle("课程管理");
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
        LessonInfo();
        setLabel();
    }

    public void searchLesson(ActionEvent ae) {
        lesson cur = new lesson();
        if (searchField.getText().matches("^[0-9]*$")) {
            cur.setid(Integer.parseInt(searchField.getText().toString()));
        }
        cur.setname(searchField.getText().toString());
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<lesson> lessons = serive.findByName(cur.getname());
        if (lessons.size() == 0) {
            lessons = serive.findById(cur.getid());
        }
        for (lesson curlLesson : lessons) {
            Vector<String> a = new Vector<>();
            a.add(curlLesson.getid() + "");
            a.add(curlLesson.getname());
            a.add(curlLesson.getcredit() + "");
            a.add(curlLesson.getyear());
            a.add(curlLesson.getsemester() + "");
            model.addRow(a);
        }
    }

    public void LessonInfo() {
        List<lesson> lessonList = serive.getLessons();
        curLessons = lessonList;
        String[] header = { "课程号", "课程名", "学分", "开启学年", "开启学期" };
        DefaultTableModel model = new DefaultTableModel(header, 0);
        staffListTable = new JTable(model);
        jscrollpane = new JScrollPane(staffListTable);
        jscrollpane.setBounds(10, 100, 800, 600);
        getContentPane().add(jscrollpane);
        setlesson(new lesson());
        staffListTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                select(me);
            }
        });
    }

    public void select(MouseEvent me) {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        int id = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 0).toString());
        String name = model.getValueAt(staffListTable.getSelectedRow(), 1).toString();
        int credit = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 2).toString());
        String year = model.getValueAt(staffListTable.getSelectedRow(), 3).toString();
        int semester = Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 4).toString());
        idTextField.setText(id + "");
        nameTextField.setText(name + "");
        creditField.setText(credit + "");
        yearfField.setText(year + "");
        MRadioButton.setSelected(semester == 1 ? true : false);
        FRadioButton.setSelected(semester == 2 ? true : false);
    }

    public void setlesson(lesson lesson) {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<lesson> lessons = serive.getLessons();
        for (lesson cur : lessons) {
            Vector<String> a = new Vector<>();
            a.add(cur.getid() + "");
            a.add(cur.getname());
            a.add(cur.getcredit() + "");
            a.add(cur.getyear());
            a.add(cur.getsemester() + "");
            model.addRow(a);
        }
    }

    public void setLabel() {
        idLabel = new JLabel("课程号：");
        idTextField = new JTextField();
        idLabel.setBounds(860, 50, 100, 30);
        idTextField.setBounds(950, 50, 200, 30);
        getContentPane().add(idLabel);
        getContentPane().add(idTextField);

        nameLabel = new JLabel("课程名：");
        nameTextField = new JTextField();
        nameLabel.setBounds(860, 100, 100, 30);
        nameTextField.setBounds(950, 100, 200, 30);
        getContentPane().add(nameLabel);
        getContentPane().add(nameTextField);

        creditLabel = new JLabel("学分: ");
        creditField = new JTextField();
        creditLabel.setBounds(860, 150, 100, 30);
        creditField.setBounds(950, 150, 200, 30);
        getContentPane().add(creditLabel);
        getContentPane().add(creditField);

        yearLabel = new JLabel("开启学年: ");
        yearfField = new JTextField();
        yearLabel.setBounds(860, 200, 100, 30);
        yearfField.setBounds(950, 200, 200, 30);
        getContentPane().add(yearLabel);
        getContentPane().add(yearfField);

        semesterLabel = new JLabel("开启学期");
        semesterLabel.setBounds(860, 250, 100, 30);
        getContentPane().add(semesterLabel);
        MRadioButton = new JRadioButton("1");
        MRadioButton.setBounds(975, 250, 74, 23);
        getContentPane().add(MRadioButton);
        FRadioButton = new JRadioButton("2");
        FRadioButton.setBounds(1051, 250, 74, 23);
        getContentPane().add(FRadioButton);
        semesterButtonGroup = new ButtonGroup();
        semesterButtonGroup.add(MRadioButton);
        semesterButtonGroup.add(FRadioButton);
        MRadioButton.setSelected(true);

        addButton = new JButton("新增课程");
        addButton.setBounds(820, 300, 90, 30);
        getContentPane().add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                lessonAddAct(ae);
            }
        });

        editButton = new JButton("修改课程");
        editButton.setBounds(910, 300, 90, 30);
        getContentPane().add(editButton);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                submitEditAct(ae);
            }
        });

        resetButton = new JButton("重置");
        resetButton.setBounds(1000, 300, 90, 30);
        getContentPane().add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resetValue(ae);
            }
        });

        deleteButton = new JButton("删除课程");
        deleteButton.setBounds(1090, 300, 90, 30);
        getContentPane().add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deletelesson(ae);
            }
        });

    }

    public void resetValue(ActionEvent ae) {
        idTextField.setText("");
        nameTextField.setText("");
        creditField.setText("");
        yearfField.setText("");
        MRadioButton.setSelected(true);
    }

    public void submitEditAct(ActionEvent ae) {
        int row = staffListTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "请选中要修改的数据！");
            return;
        }
        int id = Integer.parseInt(idTextField.getText().toString());
        String name = nameTextField.getText().toString();
        int credit = Integer.parseInt(creditField.getText().toString());
        String year = yearfField.getText().toString();
        int semester = Integer.parseInt(MRadioButton.isSelected() ? MRadioButton.getText() : FRadioButton.getText());
        lesson cur = new lesson(id, name, credit, semester, year);
        if (StringUtil.isEmpty(name)) {
            JOptionPane.showMessageDialog(this, "请填写学生姓名!");
            return;
        } else if (id == 0) {
            JOptionPane.showMessageDialog(this, "请填写学生学号！");
            return;
        }
        int a = serive.updatelesson(cur);
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "修改成功!");
            setlesson(new lesson());
            MainFrame.getContentPane().removeAll();
            MainFrame.repaint();
            MainFrame.initComponents();
            this.transferFocus();
            return;
        } else {
            JOptionPane.showMessageDialog(this, "修改失败!");
        }

    }

    public void lessonAddAct(ActionEvent ae) {
        int id = Integer.parseInt(idTextField.getText().toString());
        String name = nameTextField.getText().toString();
        int credit = Integer.parseInt(creditField.getText().toString());
        String year = yearfField.getText().toString();
        int semester = Integer.parseInt(MRadioButton.isSelected() ? MRadioButton.getText() : FRadioButton.getText());
        if (!(year.matches("[0-9]{4}-[0-9]{4}"))) {
            JOptionPane.showMessageDialog(this, "开启年月必须以如下格式，如2023-2024");
            return;
        }
        lesson cur = new lesson(id, name, credit, semester, year);
        int a = serive.addlesson(cur);
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "添加成功!");
            setlesson(cur);
            MainFrame.getContentPane().removeAll();
            MainFrame.repaint();
            MainFrame.initComponents();
        } else {
            JOptionPane.showMessageDialog(this, "添加失败!");
        }
        resetValue(ae);
    }

    public void deletelesson(ActionEvent ae) {
        int row = staffListTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "请选中要修改的数据！");
            return;
        }
        int id = 0;
        if (idTextField.getText().toString().matches("^[0-9]*$")) {
            id = Integer.parseInt(idTextField.getText().toString());
        } else {
            JOptionPane.showMessageDialog(this, "课程号必须为数字");
            return;
        }
        String name = nameTextField.getText().toString();
        int credit = Integer.parseInt(creditField.getText().toString());
        lesson cur = new lesson(id, name, credit);
        int a = serive.deletelesson(cur);
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "删除成功！");
        } else {
            JOptionPane.showMessageDialog(this, "删除失败！");
        }
        setlesson(new lesson());
        MainFrame.getContentPane().removeAll();
        MainFrame.repaint();
        MainFrame.initComponents();

        resetValue(ae);
    }
}
