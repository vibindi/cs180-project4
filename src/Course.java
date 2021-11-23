import java.util.ArrayList;

/**
 * Teacher Class
 * class to create teachers
 *
 * @author Vishnu, Ashvin
 * @version 1
 */
public class Course {

    private String name;
    private int teacherId;
    private int courseId;
    private ArrayList<Integer> studentIds;

    public Course(String name, int teacherId) {
        this.name = name;
        this.teacherId = teacherId;
        this.courseId = IdentificationContainer.getInstance().getCourseId();
        IdentificationContainer.getInstance().incCourseId();
        this.studentIds = new ArrayList<>();
    }

    public Course(String name, int teacherId, int courseId) {
        this.name = name;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.studentIds = new ArrayList<>();
    }

    public void createForum(String topic) {
        Forum forum = new Forum(topic, courseId);
        Driver.forums.put(forum.getForumId(), forum);
        UpdateFiles.writeToFiles();
    }

    public void deleteForum(int forumId) {
        Driver.forums.remove(forumId);
        UpdateFiles.writeToFiles();
    }

    public void editForum(int forumId, String topic) {
        for (Forum forum : Driver.forums.values()) {
            if (forum.getForumId() == forumId) {
                forum.setTopic(topic);
            }
        }
        UpdateFiles.writeToFiles();
    }

    public void enrollCourse(int studentId, boolean add) {
        studentIds.add(studentId);
        if (add) {
            UpdateFiles.writeToFiles();
        }
    }

    public void enrollCourse(int studentId) {
        enrollCourse(studentId, true);
    }

    public ArrayList<Forum> getForums() {
        ArrayList<Forum> forums = new ArrayList<>();
        for (Forum forum : Driver.forums.values()) {
            if (forum.getCourseId() == courseId) {
                forums.add(forum);
            }
        }
        return forums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
        UpdateFiles.writeToFiles();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
        UpdateFiles.writeToFiles();
    }

    public ArrayList<Integer> getStudentIds() {
        return studentIds;
    }

    @Override
    public String toString() {
        return name;
    }
}