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
        studentSerive dao = new studentSerivelmpl();
        List<student> a = dao.getStudents();
        System.out.println(student.map.get(1));
        System.out.println(student.map.remove(1));
        System.out.println(student.map.get(1));
        System.out.println(student.map.remove(2));
    }
}
