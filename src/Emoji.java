import java.util.HashSet;

/**
 * Emoji Class
 * class to create emoji votes
 *
 * @author Ashvin, Vishnu
 * @version 1
 */
public class Emoji {

    private String emoji;

    private HashSet<User> votes;

    public Emoji(String emoji) {
        this.emoji = emoji;
        votes = new HashSet<>();
    }

    public void addVote(User user) {
        addVote(user, true);
    }

    public void addVote(User user, boolean add) {
        votes.add(user);
        if (add) {
            UpdateFiles.writeToFiles();
        }
    }

    public void removeVote(User user) {
        votes.remove(user);
        UpdateFiles.writeToFiles();
    }

    public int getVotes() {
        return votes.size();
    }

    public User[] getAllVoters() {
        return votes.toArray(new User[0]);
    }

    public String getEmoji() {
        return emoji;
    }

}