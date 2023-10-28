package cn.wxl475.meowchat_android.pojo;

import java.util.List;

public class get_comments_result {
    private List<Comment> comments;
    private Integer total;

    public get_comments_result() {
    }

    public get_comments_result(List<Comment> comments, Integer total) {
        this.comments = comments;
        this.total = total;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "get_comments_result{" +
                "comments=" + comments +
                ", status=" + total +
                '}';
    }
}
