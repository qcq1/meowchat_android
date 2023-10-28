package cn.wxl475.meowchat_android.pojo;

public class Comment {
    private String id;
    private Integer likes;
    private Integer createAt;
    private String text;
    private User user;
    private Integer comments;
    private String replyName;

    public Comment() {
    }

    public Comment(String id, Integer likes, Integer createAt, String text, User user, Integer comments, String replyName) {
        this.id = id;
        this.likes = likes;
        this.createAt = createAt;
        this.text = text;
        this.user = user;
        this.comments = comments;
        this.replyName = replyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", likes=" + likes +
                ", createAt=" + createAt +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", comments=" + comments +
                ", replyName='" + replyName + '\'' +
                '}';
    }
}
