package cn.wxl475.meowchat_android.pojo;

import java.util.Arrays;
import java.util.List;

public class Moment {
    private String id;
    private Integer createAt;
    private String title;
    private String catId;
    private String communityId;
    private String text;
    private User user;
    private List<String> photos;

    public Moment() {
    }

    public Moment(String id, Integer createAt, String title, String catId, String communityId, String text, User user, List<String> photos) {
        this.id = id;
        this.createAt = createAt;
        this.title = title;
        this.catId = catId;
        this.communityId = communityId;
        this.text = text;
        this.user = user;
        this.photos = photos;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "id='" + id + '\'' +
                ", createAt=" + createAt +
                ", title='" + title + '\'' +
                ", catId='" + catId + '\'' +
                ", communityId='" + communityId + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", photos=" + photos +
                '}';
    }
}
