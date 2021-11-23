import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Comment Class
 * class to create comments
 *
 * @author Vishnu, Ashvin
 * @version 1
 */
public class Comment {
    private String content;
    private User user;
    private int id;
    private String timestamp;
    private int postId;

    public Comment(String content, User user, int postId) {
        this.content = content;
        this.user = user;
        this.id = IdentificationContainer.getInstance().getCommentId();
        IdentificationContainer.getInstance().incCommentId();
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date().getTime());
        this.postId = postId;
    }

    public Comment(String content, User user, int postId, int commentId, String timestamp) {
        this.content = content;
        this.user = user;
        this.id = commentId;
        this.timestamp = timestamp;
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
        UpdateFiles.writeToFiles();
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public int getPostId() {
        return postId;
    }
}