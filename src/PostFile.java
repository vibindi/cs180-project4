import java.io.*;
import java.util.*;

/**
 * PostFile Class
 * class to read Posts and write them to posts.txt
 *
 * @author Noah
 * @version 1
 */
public class PostFile extends AbstractFile {

    public PostFile() {
        super("storage/posts.txt");
    }

    public void addPostInfo(Post post) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path, true));
            pw.print(post.getTitle() + "," + post.getContent() + "," + post.getId() + "," + post.getStudentId() + "," + post.getForumId());
            pw.print("," + post.getTimeStamp());
            for (User user : post.getVotes().getAllVoters()) {
                if (user instanceof Teacher) {
                    pw.print("," + ((Teacher) user).getTeacherId());
                } else {
                    pw.print("," + ((Student) user).getStudentId());
                }
            }
            pw.print(",Emoji," + post.getEmojis().size());
            for (Emoji emoji : post.getEmojis()) {
                pw.print(",Emoji,");
                pw.print(emoji.getEmoji());
                for (User user : emoji.getAllVoters()) {
                    if (user instanceof Teacher) {
                        pw.print("," + ((Teacher) user).getTeacherId());
                    } else {
                        pw.print("," + ((Student) user).getStudentId());
                    }
                }
            }
            pw.println();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}