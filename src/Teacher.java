import java.lang.reflect.Array;
import java.util.*;

//TODO make more specifics for teacher
/**
 * Teacher Class
 * class to create teachers
 *
 * @author Vishnu
 * @version 1
 */
public class Teacher extends User {

    private int teacherId;

    public Teacher(String username, String password) {
        super(username, password);
        this.teacherId = IdentificationContainer.getInstance().getTeacherId();
        IdentificationContainer.getInstance().incTeacherId();
    }

    public Teacher(String username, String password, int teacherId) {
        super(username, password);
        this.teacherId = teacherId;
    }

    public void createCourse(String name) {
        Course c = new Course(name, teacherId);
        Driver.courses.put(c.getCourseId(), c);
        UpdateFiles.writeToFiles();
    }

    public void deleteCourse(int courseId) {
        Driver.courses.remove(courseId);
        UpdateFiles.writeToFiles();
    }

    public void editCourse(int courseId, String name) {
        Driver.courses.get(courseId).setName(name);
        UpdateFiles.writeToFiles();
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> c = new ArrayList<>();
        for (int i = 0; i < Driver.courses.size(); i++) {
            if (Driver.courses.get(i) != null && Driver.courses.get(i).getTeacherId() == teacherId) {
                c.add(Driver.courses.get(i));
            }
        }
        return c;
    }

    /* DASHBOARD (default is normal order, ascending, descending) */

    /**
     * @param sortNum 1-Default, 2-Asc, 3-Desc
     */
    public void teacherDashboard(int sortNum, int forumId) {
        ArrayList<Post> posts = new ArrayList<>();
        posts.addAll(Driver.forums.get(forumId).getPosts());
        if (sortNum == 2) {
            posts.sort(new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return Integer.compare(o1.getVotes().getVotes(), o2.getVotes().getVotes());
                }
            });
        } else if (sortNum == 3) {
            posts.sort(new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return Integer.compare(o2.getVotes().getVotes(), o1.getVotes().getVotes());
                }
            });
        }

        for (Post post : posts) {
            System.out.printf("Student: %s\nVotes: %d\n",
                    Driver.courses.get(post.getStudentId()).getName(),
                    post.getVotes().getVotes());
        }
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
        UpdateFiles.writeToFiles();
    }

    @Override
    public String toString() {
        return "Teacher|" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Teacher) {
            Teacher t = (Teacher) o;
            return t.teacherId == this.teacherId && super.equals(o);
        }
        return false;
    }

}