package com.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class studentFrame extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    studentFrame frame = new studentFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private studentFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("学生系统s");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口
        setBounds(200, 20, 1200, 700);// 设置页面大小
        setResizable(false);// 设置页面不可拖拽改变大小
        getContentPane().setLayout(null);
        setVisible(true);

        
    }

}
