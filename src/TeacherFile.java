import java.io.*;
import java.util.*;

/**
 * Teacher Class
 * class to write teacher info to file
 *
 * @author Noah
 * @version 1
 */
public class TeacherFile extends AbstractFile {

    public TeacherFile() {
        super("storage/teachers.txt");
    }

    public void addTeacherInfo(Teacher teacher) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path, true));
            pw.println(teacher.getUsername() + "," + teacher.getPassword() + "," + teacher.getTeacherId());
            pw.close();
        } catch (Exception e) {
            System.out.println("An error occured");
        }
    }
}