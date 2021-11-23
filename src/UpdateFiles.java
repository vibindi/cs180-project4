import java.util.HashMap;

/**
 * Update Files Class
 * class to read and update files at the start of driver
 *
 * @author Noah, Ashvin
 * @version 1
 */
public class UpdateFiles {
    private static final StudentFile studentFile = new StudentFile();
    private static final TeacherFile teacherFile = new TeacherFile();
    private static final CourseFile courseFile = new CourseFile();
    private static final ForumFile forumFile = new ForumFile();
    private static final PostFile postFile = new PostFile();
    private static final CommentFile commentFile = new CommentFile();

    public static void readFiles() {

        Driver.students = new HashMap<>();
        for (String s : studentFile.readFile()) { //Username,Password,Id
            String[] vals = s.split(",");
            String username = vals[0];
            String password = vals[1];
            int id = Integer.parseInt(vals[2]);
            Student student = new Student(username, password, id);
            Driver.students.put(id, student);
        }

        Driver.teachers = new HashMap<>();
        for (String s : teacherFile.readFile()) { // Username,Password,Id
            if (s == null) {
                break;
            }
            String[] vals = s.split(",");
            String username = vals[0];
            String password = vals[1];
            int id = Integer.parseInt(vals[2]);
            Teacher teacher = new Teacher(username, password, id);
            Driver.teachers.put(id, teacher);
        }

        Driver.courses = new HashMap<>();
        for (String s : courseFile.readFile()) { //Name,CourseId,TeacherId,StudentId...
            if (s == null) {
                break;
            }
            String[] vals = s.split(",");
            String name = vals[0];
            int id = Integer.parseInt(vals[1]);
            int teacherId = Integer.parseInt(vals[2]);
            Course course = new Course(name, teacherId, id);
            for (int i = 3; i < vals.length; i++) {
                course.enrollCourse(Integer.parseInt(vals[i]), false);
            }
            Driver.courses.put(id, course);
        }

        Driver.forums = new HashMap<>();
        for (String s : forumFile.readFile()) { //Topic,ForumId,CourseId,TimeStamp
            if (s == null) {
                break;
            }
            String[] vals = s.split(",");
            String topic = vals[0];
            int id = Integer.parseInt(vals[1]);
            int courseId = Integer.parseInt(vals[2]);
            String timestamp = vals[3];
            Forum forum = new Forum(topic, courseId, id, timestamp);
            Driver.forums.put(id, forum);
        }

        Driver.posts = new HashMap<>();
        for (String s : postFile.readFile()) { //Title,Content,Id,StudentId,ForumId,TimeStamp,VoteId...,"Emoji", EmojiCount, ["Emoji", Emoji Name, EmojiId...]... (note, student id is "S" + "Id", teacher id is "T" + "Id")
            if (s == null) {
                break;
            }
            String[] vals = s.split(",");
            String title = vals[0];
            String content = vals[1];
            int id = Integer.parseInt(vals[2]);
            int studentId = Integer.parseInt(vals[3]);
            int forumId = Integer.parseInt(vals[4]);
            String timestamp = vals[5];
            Post post = new Post(title, content, studentId, forumId, id, timestamp);
            int idx = 6;
            while (!vals[idx].equals("Emoji")) {
                post.getVotes().addVote(Driver.students.get(Integer.parseInt(vals[idx])), false);
                idx++;
            }
            idx++;
            int emojis = Integer.parseInt(vals[idx]);
            idx++;
            for (int i = 0; i < emojis; i++) {
                idx++;
                Emoji emoji = new Emoji(vals[idx]);
                idx++;
                while (!vals[idx].equals("Emoji")) {
                    post.getVotes().addVote(Driver.students.get(Integer.parseInt(vals[idx])), false);
                    idx++;
                }
                post.getEmojis().add(emoji);
            }
            Driver.posts.put(id, post);
        }

        Driver.comments = new HashMap<>();
        for (String s : commentFile.readFile()) { //Content,Id,UserID,PostId,TimeStamp (note, student id is "S" + "Id", teacher id is "T" + "Id")
            if (s == null) {
                break;
            }
            String[] vals = s.split(",");
            String content = vals[0];
            int id = Integer.parseInt(vals[1]);
            User user;
            if (vals[2].charAt(0) == 'S') {
                user = Driver.students.get(Integer.parseInt(vals[2].substring(1)));
            } else {
                user = Driver.teachers.get(Integer.parseInt(vals[2].substring(1)));
            }
            int postId = Integer.parseInt(vals[3]);
            String timestamp = vals[4];
            Comment comment = new Comment(content, user, postId, id, timestamp);
            Driver.comments.put(id, comment);
        }
    }

    public static void writeToFiles() {
        studentFile.reset();
        for (Student student : Driver.students.values()) {
            studentFile.addStudentInfo(student);
        }
        teacherFile.reset();
        for (Teacher teacher : Driver.teachers.values()) {
            teacherFile.addTeacherInfo(teacher);
        }
        courseFile.reset();
        for (Course course : Driver.courses.values()) {
            courseFile.addCourseInfo(course);
        }
        forumFile.reset();
        for (Forum forum : Driver.forums.values()) {
            forumFile.addForumInfo(forum);
        }
        postFile.reset();
        for (Post post : Driver.posts.values()) {
            postFile.addPostInfo(post);
        }
        commentFile.reset();
        for (Comment comment : Driver.comments.values()) {
            commentFile.addCommentInfo(comment);
        }
    }

    public static void wipeFiles() { // For Testing purposes only
        studentFile.reset();
        teacherFile.reset();
        courseFile.reset();
        forumFile.reset();
        postFile.reset();
        commentFile.reset();
        IdentificationContainer.getInstance().reset();
    }
}