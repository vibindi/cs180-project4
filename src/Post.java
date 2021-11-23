import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Post Class
 * class to create posts
 *
 * @author Vishnu, Ashvin
 * @version 1
 */
public class Post {

    private Vote vote;
    private ArrayList<Emoji> emojis;
    private String title;
    private String content;
    private int id;
    private int studentId;
    private int forumId;
    private String timeStamp;

    public Post(String title, String content, int studentId, int forumId) {
        this.vote = new Vote();
        this.emojis = new ArrayList<>();
        this.id = IdentificationContainer.getInstance().getPostId();
        IdentificationContainer.getInstance().incPostId();
        this.title = title;
        this.content = content;
        this.studentId = studentId;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date().getTime());
        this.forumId = forumId;

    }

    public Post(String title, String content, int studentId, int forumId, int postId, String timeStamp) {
        this.vote = new Vote();
        this.emojis = new ArrayList<>();
        this.id = postId;
        this.title = title;
        this.content = content;
        this.studentId = studentId;
        this.timeStamp = timeStamp;
        this.forumId = forumId;
    }

    public void addEmoji(String emoji, User user) {
        for (Emoji emoji1 : emojis) {
            if (emoji1.getEmoji().equals(emoji)) {
                emoji1.addVote(user);
                return;
            }
        }
        emojis.add(new Emoji(emoji));
        emojis.get(emojis.size() - 1).addVote(user);
    }

    public void removeEmoji(String emoji, User user) {
        for (Emoji emoji1 : emojis) {
            if (emoji1.getEmoji().equals(emoji)) {
                emoji1.removeVote(user);
                break;
            }
        }
    }

    public int getForumId() {
        return forumId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Comment> getComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : Driver.comments.values()) {
            if (comment.getId() == id) {
                comments.add(comment);
            }
        }
        return comments;
    }

    public Vote getVotes() {
        return vote;
    }

    public ArrayList<Emoji> getEmojis() {
        return emojis;
    }

    public int getStudentId() {
        return studentId;
    }

    public void createComment(String content, User user) {
        Comment comment = new Comment(content, user, id);
        Driver.comments.put(comment.getId(), comment);
        UpdateFiles.writeToFiles();
    }

    public void deleteComment(int commentId) {
        Driver.comments.remove(commentId);
        UpdateFiles.writeToFiles();
    }

    public void editComment(int commentId, String content) {
        for (Comment comment : Driver.comments.values()) {
            if (comment.getId() == commentId) {
                comment.setContent(content);
            }
        }
        UpdateFiles.writeToFiles();
    }

    public String getTimeStamp() {
        return timeStamp;
    }

}