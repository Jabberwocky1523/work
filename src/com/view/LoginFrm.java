package com.view;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.dao.adminDao;
import com.service.adminSerive;
import com.service.adminSerivelmpl;
import com.service.studentSerive;
import com.service.studentSerivelmpl;
import com.bean.admin;
import com.bean.student;
import com.util.StringUtil;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import com.view.studentFrame;

@SuppressWarnings({ "serial", "unused" })
public class LoginFrm extends JFrame {

    private JLabel registerLabel;
    private JLabel codeLabel;
    private JPanel contentPane;
    private JLabel titleLabel;
    private JLabel anameJlabel;
    private JTextField anameTextField;
    private JLabel passwordJlabel;
    private JPasswordField passwordTextField;
    private JButton submitButton;
    private JButton resetButton;
    public adminSerive serive = new adminSerivelmpl();
    public studentSerive Dao = new studentSerivelmpl();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrm frame = new LoginFrm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginFrm() {
        setTitle("登录系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口
        setBounds(500, 150, 630, 450);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);

        titleLabel = new JLabel("高校成绩管理系统");
        titleLabel.setFont(new Font("华文行楷", Font.PLAIN, 25));
        titleLabel.setBounds(215, 65, 200, 34);
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
        passwordTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginAct();
                }
            }
        });
        getContentPane().add(passwordJlabel);
        getContentPane().add(passwordTextField);

        registerLabel = new JLabel();
        String path = "src/com/image/register2.jpg";
        ImageIcon registerIcon = new ImageIcon(path);
        registerLabel.setIcon(registerIcon);
        registerLabel.setBounds(60, 300, 50, 50);
        registerLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                new RegisterFrame();
            }

            public void mouseEntered(MouseEvent e) {
                // 处理鼠标移入
                String path = "src/com/image/R-C.jpg";
                ImageIcon registerIcon = new ImageIcon(path);
                registerLabel.setIcon(registerIcon);
            }

            public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
                String path = "src/com/image/register2.jpg";
                ImageIcon registerIcon = new ImageIcon(path);
                registerLabel.setIcon(registerIcon);
            }

            public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }

            public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });
        getContentPane().add(registerLabel);

        codeLabel = new JLabel();
        String cpath = "src/com/image/code.jpg";
        ImageIcon codeIcon = new ImageIcon(cpath);
        codeLabel.setIcon(codeIcon);
        codeLabel.setBounds(500, 300, 60, 60);
        codeLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                loginAct();
            }

            public void mouseEntered(MouseEvent e) {
                // 处理鼠标移
            }

            public void mouseExited(MouseEvent e) {
                // 处理鼠标离开
            }

            public void mousePressed(MouseEvent e) {
                // 处理鼠标按下
            }

            public void mouseReleased(MouseEvent e) {
                // 处理鼠标释放
            }
        });
        getContentPane().add(codeLabel);

        resetButton = new JButton("重置");
        resetButton.setBounds(340, 265, 90, 30);
        getContentPane().add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                resetValue(ae);
            }
        });

        submitButton = new JButton("登录");
        submitButton.setBounds(195, 265, 90, 30);
        getContentPane().add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                loginAct();
            }
        });

    }

    protected void loginAct() {
        String aname = anameTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        if (StringUtil.isEmpty(aname)) {
            JOptionPane.showMessageDialog(this, "用户名不能为空！");
            return;
        }
        if (StringUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(this, "密码不能为空！");
            return;
        }
        admin adminTmp = new admin();
        adminTmp.setname(aname);
        adminTmp.setpwd(password);
        int i = serive.login(adminTmp);
        List<student> ans = new ArrayList<>();
        if (i == 0) {
            ans = Dao.findByid(Integer.parseInt(aname));
            if (ans.size() == 0) {
                JOptionPane.showMessageDialog(this, "你尚未被系统录入");
                return;
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                return;
            }
        } else if (i == 2) {
            JOptionPane.showMessageDialog(this, "欢迎" + adminTmp.getname());
            this.dispose();
            MainFrame operator = new MainFrame();
            operator.setVisible(true);
        } else if (i == 1) {
            ans = Dao.findByid(Integer.parseInt(aname));
            JOptionPane.showMessageDialog(this, "欢迎" + ans.get(0).getname());
            this.dispose();
            studentFrame operator = new studentFrame(ans.get(0));
        } else {
            JOptionPane.showMessageDialog(this, "同学请先注册！");
        }
    }

    protected void resetValue(ActionEvent ae) {
        anameTextField.setText("");
        passwordTextField.setText("");
    }
}
