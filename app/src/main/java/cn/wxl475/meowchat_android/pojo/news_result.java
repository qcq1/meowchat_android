package cn.wxl475.meowchat_android.pojo;

import java.util.ArrayList;
import java.util.List;

public class news_result {
    private List<News> news;
    private Status status;

    public news_result(ArrayList<News> news, Status status) {
        this.news = news;
        this.status = status;
    }

    public news_result() {
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "news_result{" +
                "news=" + news +
                ", status=" + status +
                '}';
    }
}
