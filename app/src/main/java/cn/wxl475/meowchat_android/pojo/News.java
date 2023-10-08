package cn.wxl475.meowchat_android.pojo;

public class News {
    private String id;
    private String communityId;
    private String imageUrl;
    private String linkUrl;
    private String type;

    public News() {
    }

    public News(String id, String communityId, String imageUrl, String linkUrl, String type) {
        this.id = id;
        this.communityId = communityId;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", communityId='" + communityId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
