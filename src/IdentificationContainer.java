import java.io.*;
/**
 * IdentificationContainer Class
 * class to create id's for each object
 *
 * @version 1
 * @author Vishnu
 */

/**
 * Keeps track of the latest id numbers that can be added to
 * // TODO: fix all stack trace prints
 */
public class IdentificationContainer {

    public int teacherId;
    public int studentId;
    public int courseId;
    public int forumId;
    public int postId;
    public int commentId;

    private static IdentificationContainer instance;

    public static IdentificationContainer getInstance() {
        if (instance == null) {
            instance = new IdentificationContainer();
        }
        return instance;
    }

    private IdentificationContainer() {
        // first check for id container file
        // if there, read from the file and set the values
        // if not there, create the file and initialize the values

        // remove the "src/" portion later, it's there just for testing (issue with my intellij)
        File f = new File("storage/" + "idcontainer.txt");
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                int lineNum = 0;
                while (line != null) {
                    // no need for try catch when parsing int, because we are writing the file
                    // there should be no error
                    switch (lineNum) {
                        case 0 -> teacherId = Integer.parseInt(line);
                        case 1 -> studentId = Integer.parseInt(line);
                        case 2 -> courseId = Integer.parseInt(line);
                        case 3 -> forumId = Integer.parseInt(line);
                        case 4 -> postId = Integer.parseInt(line);
                        case 5 -> commentId = Integer.parseInt(line);
                    }
                    lineNum++;
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                f.createNewFile();
                teacherId = 0;
                studentId = 0;
                courseId = 0;
                forumId = 0;
                postId = 0;
                commentId = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateFile() {
        File f = new File("storage/" + "idcontainer.txt");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, false))) {
            pw.println(teacherId);
            pw.println(studentId);
            pw.println(courseId);
            pw.println(forumId);
            pw.println(postId);
            pw.println(commentId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        File f = new File("storage/" + "idcontainer.txt");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, false))) {
            pw.println(0);
            pw.println(0);
            pw.println(0);
            pw.println(0);
            pw.println(0);
            pw.println(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // INCREMENT TEACHER ID
    public void incTeacherId() {
        teacherId++;
        updateFile();
    }

    public void incStudentId() {
        studentId++;
        updateFile();
    }

    public void incCourseId() {
        courseId++;
        updateFile();
    }

    public void incForumId() {
        forumId++;
        updateFile();
    }

    public void incPostId() {
        postId++;
        updateFile();
    }

    public void incCommentId() {
        commentId++;
        updateFile();
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getForumId() {
        return forumId;
    }

    public int getPostId() {
        return postId;
    }

    public int getCommentId() {
        return commentId;
    }
}