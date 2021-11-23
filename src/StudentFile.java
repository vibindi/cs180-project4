import java.io.*;

/**
 * StudentFile Class
 * class to add student info to file
 *
 * @author Noah
 * @version 1
 */
public class StudentFile extends AbstractFile {

    public StudentFile() {
        super("storage/students.txt");
    }

    public void addStudentInfo(Student student) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path, true));
            pw.println(student.getUsername() + "," + student.getPassword() + "," + student.getStudentId());
            pw.close();
        } catch (Exception e) {
            System.out.println("An error occured");
        }
    }
}