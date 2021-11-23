import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Forum Class
 * class to create forums
 *
 * @author Vishnu, Ashvin
 * @version 1
 */
public class Forum {

    private String topic;
    private int courseId;
    private int forumId;
    private String timestamp;

    public Forum(String topic, int courseId) {
        this.topic = topic;
        this.courseId = courseId;
        this.forumId = IdentificationContainer.getInstance().getForumId();
        IdentificationContainer.getInstance().incForumId();
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date().getTime());
    }

    public Forum(String topic, int courseId, int forumId, String timestamp) {
        this.topic = topic;
        this.courseId = courseId;
        this.forumId = forumId;
        this.timestamp = timestamp;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
        UpdateFiles.writeToFiles();
    }

    public ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        for (Post post : Driver.posts.values()) {
            if (post.getForumId() == forumId) {
                posts.add(post);
            }
        }
        return posts;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
        UpdateFiles.writeToFiles();
    }

    public int getCourseId() {
        return courseId;
    }

    public void makePost(String title, String content, int studentId) {
        Post p = new Post(title, content, studentId, forumId);
        Driver.posts.put(p.getId(), p);
        UpdateFiles.writeToFiles();
    }

    @Override
    public String toString() {
        return topic;
    }

    public String getTimeStamp() {
        return timestamp;
    }
}
