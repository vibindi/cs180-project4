import java.io.*;
import java.util.*;

/**
 * ForumFile Class
 * class to write forums to file
 *
 * @author Noah
 * @version 1
 */
public class ForumFile extends AbstractFile {

    public ForumFile() {
        super("storage/forums.txt");
    }

    public void addForumInfo(Forum forum) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path, true));
            pw.println(forum.getTopic() + "," + forum.getForumId() + "," + forum.getCourseId() + "," + forum.getTimeStamp());
            pw.close();
        } catch (Exception e) {
            System.out.println("An error occured");
        }
    }
}