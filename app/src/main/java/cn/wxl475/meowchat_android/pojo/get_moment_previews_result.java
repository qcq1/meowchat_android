package cn.wxl475.meowchat_android.pojo;

import java.util.List;

public class get_moment_previews_result {
    private List<Moment> moments;
    private Status status;

    public get_moment_previews_result(List<Moment> moments, Status status) {
        this.moments = moments;
        this.status = status;
    }

    public get_moment_previews_result() {
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "moment_result{" +
                "moments=" + moments +
                ", status=" + status +
                '}';
    }
}
