import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * CourseFile Class
 * class to add course info to file
 *
 * @author Noah
 * @version 1
 */
public class CourseFile extends AbstractFile {

    public CourseFile() {
        super("storage/courses.txt");
    }

    public void addCourseInfo(Course course) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path, true));
            pw.print(course.getName() + "," + course.getCourseId() + "," + course.getTeacherId());
            for (int studentId : course.getStudentIds()) {
                pw.print("," + studentId);
            }
            pw.println();
            pw.close();
        } catch (Exception e) {
            System.out.println("An error occured");
        }
    }
}