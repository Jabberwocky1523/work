package com.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.bean.lesson;
import com.bean.student;
import com.bean.admin;
import com.bean.base;
import com.service.adminSerive;
import com.service.adminSerivelmpl;
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

/**
 * @author Kurisu
 */
public class MainFrame extends JFrame {
    private JMenu lessonMJMenu;
    private JMenu FileMenu;
    private JMenu inputMenu;
    public lessonSerive serive = new lessonSerivelmpl();
    public studentSerive studentSerive = new studentSerivelmpl();
    public adminSerive adminSerive = new adminSerivelmpl();
    private List<lesson> curLessons = serive.getLessons();
    private int temp2 = 0;
    private int temp = 0;
    private int Xindex1 = 860;
    private int Xindex2 = 950;
    private student student1 = new student();
    private JMenuBar MenuBar = new JMenuBar();
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
    private JTextField nameTextField;
    private JLabel stuLabel;
    private JTextField stuTextField;
    private JButton addButton;
    private JButton resetButton;
    private JButton editButton;
    private JButton deleteButton;
    private JComboBox<String> comboBox = new JComboBox<>();
    private JComboBox<String> semester = new JComboBox<>();

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

    protected void initComponents() {
        temp = 0;
        Xindex1 = 860;
        Xindex2 = 950;
        student1 = new student();
        MenuBar = new JMenuBar();
        JTextField[] co = new JTextField[serive.getLessons().size()];
        JLabel[] coLabels = new JLabel[serive.getLessons().size()];
        if (temp2 == 0) {
            setTitle("高校成绩管理系统");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口
            setBounds(200, 20, 1200, 700);// 设置页面大小
            setResizable(false);// 设置页面不可拖拽改变大小
            getContentPane().setLayout(null);
            setVisible(true);
            temp2++;
        }

        FileMenu = new JMenu("导出");
        inputMenu = new JMenu("导入");
        Font t = new Font("宋体", Font.PLAIN, 15);
        FileMenu.setFont(t);
        inputMenu.setFont(t);
        lessonMJMenu = new JMenu("课程管理");
        lessonMJMenu.setFont(t);
        JMenu stumenu = new JMenu("学生申请");
        stumenu.setFont(t);
        MenuBar.add(lessonMJMenu);
        MenuBar.add(stumenu);
        MenuBar.add(FileMenu);
        MenuBar.add(inputMenu);
        this.setJMenuBar(MenuBar);

        FileMenu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                dealExportbtn();
            }
        });

        inputMenu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                importact();
            }
        });

        stumenu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                new applyFrame();
            }
        });

        MainFrame curFrame = this;
        lessonMJMenu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                LessonFrame lessonFrame = new LessonFrame(curFrame);
                lessonFrame.setVisible(true);
            }
        });

        StuInfo();

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

    public void StuInfo() {
        List<lesson> lessonList = serive.getLessons();
        curLessons = lessonList;
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
        jscrollpane.setBounds(10, 100, 800, 600);
        getContentPane().add(jscrollpane);
        setstuinfo(new student());
        staffListTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                select(me);
            }
        });
    }

    public void setstuinfo(student student) {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<student> students = studentSerive.getStudents();
        for (student curstu : students) {
            int i = 0;
            Vector v = new Vector<>();
            v.add(curstu.getid());
            v.add(curstu.getname());
            for (lesson curLesson : curstu.getLessons()) {
                if (curLesson.getname().equals(curLessons.get(i).getname())) {
                    v.add(curLesson.getscore());
                    i++;
                    if (i == curLessons.size()) {
                        break;
                    }
                }
            }
            model.addRow(v);
        }
    }

    public void searchStudent(ActionEvent ae) {
        base cur = new student();
        if (searchField.getText().toString().matches("^[0-9]*$") && !searchField.getText().toString().equals("")) {
            cur.setid(Integer.parseInt(searchField.getText().toString()));
        }
        student a = student.map.get(cur.id);
        cur.setname(searchField.getText().toString());
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<student> students = studentSerive.findByName(cur.getname());
        if (students.size() == 0) {
            students.add(a);
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
        if (temp != 0) {
            for (JTextField curco : co) {
                getContentPane().remove(curco);
            }
            for (JLabel curJLabel : coLabels) {
                getContentPane().remove(curJLabel);
            }
            getContentPane().remove(addButton);
            getContentPane().remove(editButton);
            getContentPane().remove(deleteButton);
            getContentPane().remove(resetButton);
            getContentPane().remove(idTextField);
            getContentPane().remove(nameTextField);
        }
        temp++;
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

        for (lesson cur : curLessons) {
            stuLabel = new JLabel(cur.getname());
            stuTextField = new JTextField();
            stuLabel.setBounds(Xindex1, 50 + index, 100, 30);
            stuTextField.setBounds(Xindex2, 50 + index, 200, 30);
            getContentPane().add(stuLabel);
            getContentPane().add(stuTextField);
            co[i] = stuTextField;
            coLabels[i] = stuLabel;
            i++;
            index = index + 50;
        }
        this.repaint();

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
        List<lesson> lessons = curLessons;
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
        student.map.remove(id);
        int a = studentSerive.deletestudent(cur);
        admin curAdmin = new admin(id + "", "0");
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "删除成功！");
            adminSerive.deleteAdmin(curAdmin);
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
        for (int i = 0; i < curLessons.size(); i++) {
            String temp = co[i].getText().toString();
            if (temp.matches("^[0-9]*$")) {
                int j = i;
                while (true) {
                    if (lessons.get(j).getname().equals(coLabels[i].getText().toString())) {
                        lessons.get(j).setscore(Integer.parseInt(temp));
                        break;
                    }
                    j++;
                }
            } else {
                JOptionPane.showMessageDialog(this, "课程分数必须为数字!");
                return;
            }
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
            admin b = new admin();
            b.setname(id + "");
            adminSerive.addAdmin(b);
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
        List<student> cur1 = studentSerive.findByid(id);
        List<lesson> lessons = cur1.get(0).getLessons();
        for (int i = 0; i < curLessons.size(); i++) {
            String temp = co[i].getText().toString();
            if (temp.matches("^[0-9]*$")) {
                int j = i;
                while (true) {
                    if (lessons.get(j).getname().equals(coLabels[i].getText().toString())) {
                        lessons.get(j).setscore(Integer.parseInt(temp));
                        break;
                    }
                    j++;
                }
            } else {
                JOptionPane.showMessageDialog(this, "课程分数必须为数字!");
                return;
            }
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

    public void selectsemesteAndyear(ItemEvent me) {
        int b = Integer.parseInt(semester.getSelectedItem().toString());
        List<lesson> lessons = serive.getLessons();
        String[] header = new String[lessons.size() + 2];
        header[0] = "id";
        header[1] = "姓名";
        int i = 2;
        DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getModel();
        if (me.getStateChange() == ItemEvent.SELECTED) {
            String a = model.getSelectedItem().toString();
            if (a == "全部") {
                getContentPane().remove(staffListTable);
                getContentPane().remove(jscrollpane);
                StuInfo();
                setLabel();
                return;
            }
            curLessons.clear();
            for (lesson cur : lessons) {
                if (cur.getyear().equals(a) && cur.getsemester() == b) {
                    header[i] = cur.getname();
                    curLessons.add(cur);
                    i++;
                }
            }
            getContentPane().remove(staffListTable);
            getContentPane().remove(jscrollpane);
            DefaultTableModel Tmodel = new DefaultTableModel(header, 0);
            staffListTable = new JTable(Tmodel);
            jscrollpane = new JScrollPane(staffListTable);
            jscrollpane.setBounds(10, 100, 800, 600);
            staffListTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    select(me);
                }
            });
            getContentPane().add(jscrollpane);
            setLabel();
            Tmodel.setRowCount(0);
            List<student> students = studentSerive.getStudents();
            for (student curstu : students) {
                Vector v = new Vector<>();
                v.add(curstu.getid());
                v.add(curstu.getname());
                for (lesson curLesson : curstu.getLessons()) {
                    if (curLesson.getyear().equals(a) && curLesson.getsemester() == b) {
                        v.add(curLesson.getscore());
                    }
                }
                Tmodel.addRow(v);
            }
        }
    }

    public void importact() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showSaveDialog(this);// 显示保存对话框
            String fi = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(fi);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fi), "GBK"));
            List<student> curstu = new ArrayList<>();
            String cur;
            int i = 0;
            while ((cur = reader.readLine()) != null) {
                String[] a = cur.split("\\s+");
                if (i == 0) {
                    i++;
                    continue;
                } else {
                    int id = Integer.parseInt(a[0]);
                    String name = a[1];
                    int index = 2;
                    List<lesson> curList = serive.getLessons();
                    while (index < a.length) {
                        curList.get(index - 2).setscore(Integer.parseInt(a[index]));
                        index++;
                    }
                    student b = new student(id, name, curList);
                    curstu.add(b);
                }
            }
            DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
            List<admin> admins = new ArrayList<>();
            model.setRowCount(0);
            for (student stu : curstu) {
                int d = 0;
                Vector v = new Vector<>();
                v.add(stu.getid());
                v.add(stu.getname());
                for (lesson curLesson : stu.getLessons()) {
                    if (curLesson.getname().equals(curLessons.get(d).getname())) {
                        v.add(curLesson.getscore());
                        d++;
                        if (d == curLessons.size()) {
                            break;
                        }
                    }
                }
                admin curadmin = new admin(stu.getid() + "", "0");
                admins.add(curadmin);
                model.addRow(v);
            }
            reader.close();
            JOptionPane.showMessageDialog(null, "文件上传成功");
            if (JOptionPane.showConfirmDialog(this, "您要刷新数据库吗？") == JOptionPane.OK_OPTION) {
                studentSerive.update(curstu);
                adminSerive.update(admins);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    protected void dealExportbtn() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showSaveDialog(this);
            StringBuffer fi = new StringBuffer(fileChooser.getSelectedFile().getAbsolutePath());
            int result = fi.toString().indexOf(".xls");
            if (result == -1) {
                fi.append(".xls");
            }
            System.out.println(fi);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(fi.toString(), true), "GBK"));
            for (int i = 0; i < staffListTable.getColumnCount(); i++) {
                writer.write(staffListTable.getColumnName(i) + "\t");
            }
            writer.write("\n");
            for (int i = 0; i < staffListTable.getRowCount(); i++) {
                for (int j = 0; j < staffListTable.getColumnCount(); j++) {
                    try {
                        writer.write(staffListTable.getValueAt(i, j).toString() + "\t");
                    } catch (NullPointerException e) {
                        writer.write("" + "\t");
                        e.printStackTrace();
                    }
                }
                writer.write("\n");
            }
            writer.close();
            JOptionPane.showMessageDialog(null, "文件导出成功");
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }
}
