import java.util.ArrayList;

/**
 * Student Class
 * class to create student
 *
 * @author Vishnu, Ashvin
 * @version 1
 */
//TODO make more specifics for teacher
public class Student extends User {

    private int studentId;

    public Student(String username, String password) {
        super(username, password);
        this.studentId = IdentificationContainer.getInstance().getStudentId();
        IdentificationContainer.getInstance().incStudentId();
    }

    public Student(String username, String password, int studentId) {
        super(username, password);
        this.studentId = studentId;
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> c = new ArrayList<>();
        for (int i = 0; i < Driver.courses.size(); i++) {
            if (Driver.courses.get(i).getStudentIds().contains(studentId)) {
                c.add(Driver.courses.get(i));
            }
        }
        return c;
    }

    public int getStudentId() {
        return this.studentId;
    }

    @Override
    public String toString() {
        return "Student|" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Student) {
            Student s = (Student) o;
            return s.studentId == this.studentId && super.equals(o);
        }
        return false;
    }
}
