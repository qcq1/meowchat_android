package cn.wxl475.meowchat_android.pojo;

public class like_get_count_result {
    private Integer count;
    private Status status;

    public like_get_count_result() {
    }

    public like_get_count_result(Integer count, Status status) {
        this.count = count;
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "like_result{" +
                "count=" + count +
                ", status=" + status +
                '}';
    }
}
