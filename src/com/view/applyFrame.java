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
import java.text.SimpleDateFormat;

import com.bean.lesson;
import com.bean.student;
import com.bean.apply;
import com.bean.base;
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

public class applyFrame extends studentFrame {
    private apply curapply = new apply();
    JLabel idLabel;
    JTextField idTextField;
    JLabel scoreLabel;
    JTextField scoreTextField;
    JLabel reasonLabel;
    JTextArea reasonTextField;
    JLabel timeLabel;
    JTextField timeTextField;
    JLabel feedbackLabel;
    JTextArea feedbackTextField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    applyFrame frame = new applyFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public applyFrame() {
        super.applyframe(null);
        setTitle("学生系统");
        setBounds(200, 20, 1200, 700);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);
        getContentPane().removeAll();
        getContentPane().add(jscrollpane);
        refresh();

        staffListTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                select(me);
            }
        });

        idLabel = new JLabel("课程名：");
        idTextField = new JTextField();
        idLabel.setBounds(860, 50, 100, 30);
        idTextField.setBounds(950, 50, 200, 30);
        idTextField.setEditable(false);
        getContentPane().add(idLabel);
        getContentPane().add(idTextField);
        scoreLabel = new JLabel("课程分数：");
        scoreTextField = new JTextField();
        scoreLabel.setBounds(860, 100, 100, 30);
        scoreTextField.setBounds(950, 100, 200, 30);
        getContentPane().add(scoreLabel);
        getContentPane().add(scoreTextField);
        reasonLabel = new JLabel("复查原因：");
        reasonTextField = new JTextArea();
        reasonTextField.setLineWrap(true);
        reasonTextField.setEditable(false);
        reasonLabel.setBounds(860, 150, 100, 30);
        reasonTextField.setBounds(950, 150, 200, 100);
        reasonTextField.setEditable(false);
        getContentPane().add(reasonLabel);
        getContentPane().add(reasonTextField);
        timeLabel = new JLabel("申请时间：");
        timeTextField = new JTextField();
        timeLabel.setBounds(860, 270, 100, 30);
        timeTextField.setBounds(950, 270, 200, 30);
        timeTextField.setEditable(false);
        getContentPane().add(timeLabel);
        getContentPane().add(timeTextField);

        feedbackLabel = new JLabel("反馈结果");
        feedbackTextField = new JTextArea();
        feedbackTextField.setLineWrap(true);
        feedbackLabel.setBounds(860, 320, 100, 30);
        feedbackTextField.setBounds(950, 320, 200, 100);
        getContentPane().add(feedbackLabel);
        getContentPane().add(feedbackTextField);

        JButton submit = new JButton("修改");
        submit.setBounds(950, 500, 70, 30);
        JButton overrule = new JButton("驳回");
        overrule.setBounds(1050, 500, 70, 30);
        getContentPane().add(overrule);
        getContentPane().add(submit);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                submit();
            }
        });

        overrule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                overrule();
            }
        });
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        model.setRowCount(0);
        List<apply> applies = applySerive.getapplie();
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
            a.add(cur.getname());
            a.add(cur.getid());
            model.addRow(a);
        }
    }

    public void select(MouseEvent me) {
        DefaultTableModel model = (DefaultTableModel) staffListTable.getModel();
        idTextField.setText(model.getValueAt(staffListTable.getSelectedRow(), 4).toString());
        scoreTextField.setText(model.getValueAt(staffListTable.getSelectedRow(), 5).toString());
        reasonTextField.setText(model.getValueAt(staffListTable.getSelectedRow(), 8).toString());
        timeTextField.setText(model.getValueAt(staffListTable.getSelectedRow(), 9).toString());
        lesson lesson = new lesson();
        lesson.setname(idTextField.getText().toString());
        curapply.setLesson(lesson);
        curapply.setid(Integer.parseInt(model.getValueAt(staffListTable.getSelectedRow(), 13).toString()));
    }

    public void submit() {
        if (StringUtil.isEmpty(feedbackTextField.getText().toString())) {
            JOptionPane.showMessageDialog(this, "反馈理由不能为空！");
            return;
        }
        curapply.getlesson().setscore(Integer.parseInt(scoreTextField.getText().toString()));
        curapply.setfeedback(feedbackTextField.getText().toString());
        int a = applySerive.update(curapply);
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "修改成功!");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "修改失败!");
        }
    }

    public void overrule() {
        if (StringUtil.isEmpty(feedbackTextField.getText().toString())) {
            JOptionPane.showMessageDialog(this, "反馈理由不能为空！");
            return;
        }
        int a = applySerive.overrule(curapply);
        curapply.setfeedback(feedbackTextField.getText().toString());
        if (a != 0) {
            JOptionPane.showMessageDialog(this, "驳回成功!");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "驳回失败!");
        }
    }
}
