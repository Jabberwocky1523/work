import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.bean.admin;
import com.bean.lesson;
import com.bean.student;
import com.service.adminSerive;
import com.service.adminSerivelmpl;
import com.service.lessonSerive;
import com.service.lessonSerivelmpl;
import com.service.studentSerivelmpl;
import com.service.studentSerive;

public class App {
    public static void main(String[] args) throws Exception {
        // adminSerive serive = new adminSerivelmpl();
        // admin a = new admin();
        // a = serive.getAdminByname("admin");
        // System.out.println(a.getname());
        // System.out.println(a.getpwd());
        // a.setname("jabberwocky");
        // a.setpwd("2004040505ab");
        // int temp = serive.addAdmin(a);
        // System.out.println(temp);
        // a.setpwd("2004040505");
        // temp = serive.updateAdmin(a);
        // System.out.println(temp);
        // temp = serive.addAdmin(a);
        // System.out.println(temp);
        // lessonSerive dao = new lessonSerivelmpl();
        // lesson c = new lesson(2, "math123", 2);
        // dao.addlesson(c);
        // dao.updatelesson(c);
        // studentSerive serive = new studentSerivelmpl();
        // List<student> a = serive.getStudents();
        // for (student b : a) {
        // System.out.println(b.getid());
        // System.out.println(b.getname());
        // for (lesson cur : b.getLessons()) {
        // System.out.println(cur.getname());
        // System.out.println(cur.getcredit());
        // System.out.println(cur.getscore());
        // }
        // }
        // List<lesson> lessons = dao.getLessons();
        // for (lesson cur : lessons) {
        // cur.setscore(99);
        // }
        // student cur = new student(2,"王云",lessons);
        // serive.addstudent(cur);
    }
}
