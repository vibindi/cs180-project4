import java.io.*;
import java.util.*;

/**
 * CommentFile Class
 * class to write comments to file
 *
 * @author Noah
 * @version 1
 */
public class CommentFile extends AbstractFile {

    public CommentFile() {
        super("storage/comments.txt");
    }

    public void addCommentInfo(Comment comment) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path, true));
            pw.print(comment.getContent() + "," + comment.getId() + ",");
            if (comment.getUser() instanceof Teacher) {
                pw.print("T" + ((Teacher) comment.getUser()).getTeacherId());
            } else {
                pw.print("S" + ((Student) comment.getUser()).getStudentId());
            }
            pw.print("," + comment.getPostId());
            pw.println("," + comment.getTimeStamp());
            pw.close();
        } catch (Exception e) {
            System.out.println("An error occured");
        }
    }
}