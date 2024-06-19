package com.view;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.bean.admin;
import com.bean.student;
import com.service.adminSerive;
import com.service.adminSerivelmpl;
import com.service.studentSerive;
import com.service.studentSerivelmpl;
import com.util.StringUtil;
import com.util.verify;

public class RegisterFrame extends JFrame {

    private studentSerive stuserive = new studentSerivelmpl();
    private adminSerive adminserive = new adminSerivelmpl();
    private JLabel anameJlabel;
    private JLabel passwordJlabel;
    private JTextField anameTextField;
    private JTextField passwordTextField;
    private JLabel titleLabel;
    private JLabel verifyLabel;
    private String verifyCode;
    private JLabel verJLabel;
    private JTextField verifyField;
    private JButton submitButton;
    private JButton resetButton;

    public RegisterFrame() {
        setTitle("注册系统");

        setBounds(500, 150, 630, 450);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);

        titleLabel = new JLabel("注册系统");
        titleLabel.setFont(new Font("华文行楷", Font.PLAIN, 25));
        titleLabel.setBounds(230, 65, 200, 34);
        getContentPane().add(titleLabel);

        anameJlabel = new JLabel("账号");
        anameJlabel.setFont(new Font("宋体", Font.PLAIN, 20));
        anameTextField = new JTextField();
        anameJlabel.setBounds(155, 152, 67, 30);
        anameTextField.setBounds(243, 152, 200, 30);
        getContentPane().add(anameJlabel);
        getContentPane().add(anameTextField);

        passwordJlabel = new JLabel("密码");
        passwordJlabel.setFont(new Font("宋体", Font.PLAIN, 20));
        passwordTextField = new JPasswordField();
        passwordJlabel.setBounds(155, 202, 67, 30);
        passwordTextField.setBounds(243, 202, 200, 30);
        getContentPane().add(passwordJlabel);
        getContentPane().add(passwordTextField);

        verJLabel = new JLabel("验证码");
        verJLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        verifyField = new JPasswordField();
        verJLabel.setBounds(155, 252, 67, 30);
        verifyField.setBounds(243, 252, 100, 30);
        getContentPane().add(verJLabel);
        getContentPane().add(verifyField);
        VerifyCode();

        resetButton = new JButton("重置");
        resetButton.setBounds(340, 315, 90, 30);
        getContentPane().add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resetValue(ae);
            }
        });

        submitButton = new JButton("注册");
        submitButton.setBounds(195, 315, 90, 30);
        getContentPane().add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                register(ae);
            }
        });
    }

    public void VerifyCode() {
        verifyLabel = new JLabel();
        verifyLabel.setBounds(363, 242, 100, 50);
        Object[] ver = verify.createImage();
        ImageIcon img = new ImageIcon((BufferedImage) ver[1]);
        verifyLabel.setIcon((Icon) img);
        verifyCode = ver[0].toString();
        System.out.println(verifyCode);
        verifyLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    Object[] curver = verify.createImage();
                    ImageIcon img = new ImageIcon((BufferedImage) curver[1]);
                    verifyLabel.setIcon((Icon) img);
                    verifyCode = curver[0].toString();
                    System.out.println(verifyCode);
                }
            }
        });
        getContentPane().add(verifyLabel);
    }

    protected void resetValue(ActionEvent ae) {
        anameTextField.setText("");
        passwordTextField.setText("");
        verifyField.setText("");
    }

    protected void register(ActionEvent ae) {
        String id = anameTextField.getText().toString();
        String pwd = passwordTextField.getText().toString();
        String code = verifyField.getText().toString();
        if (StringUtil.isEmpty(id)) {
            JOptionPane.showMessageDialog(this, "id不能为空！");
            return;
        }
        if (StringUtil.isEmpty(pwd)) {
            JOptionPane.showMessageDialog(this, "密码不能为空！");
            return;
        }
        if (StringUtil.isEmpty(code)) {
            JOptionPane.showMessageDialog(this, "验证码不能为空！");
            return;
        }
        admin cur = adminserive.getAdminByname(id);
        if (cur.getname() == null) {
            JOptionPane.showMessageDialog(this, "你不是我们学校学生，暂不允许注册");
            return;
        } else if (cur.gettemp() == 1 || cur.gettemp() == 2) {
            JOptionPane.showMessageDialog(this, "你已经注册过了！");
        } else {
            cur.setpwd(pwd);
            int a = adminserive.updateAdmin(cur);
            if (a != 0) {
                JOptionPane.showMessageDialog(this, "注册成功！");
                return;
            } else {
                JOptionPane.showMessageDialog(this, "注册失败！");
                return;
            }
        }
    }
}
