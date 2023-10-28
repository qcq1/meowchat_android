package cn.wxl475.meowchat_android.pojo;

import java.util.List;

public class MomentAllDetail {
    private get_moment_previews_result getMomentPreviewsResult;
    private List<like_get_count_result> likeGetCountResults;
    private List<get_comments_result> getCommentsResults;

    public MomentAllDetail() {
    }

    public MomentAllDetail(get_moment_previews_result getMomentPreviewsResult, List<like_get_count_result> likeGetCountResults, List<get_comments_result> getCommentsResults) {
        this.getMomentPreviewsResult = getMomentPreviewsResult;
        this.likeGetCountResults = likeGetCountResults;
        this.getCommentsResults = getCommentsResults;
    }

    public get_moment_previews_result getGetMomentPreviewsResult() {
        return getMomentPreviewsResult;
    }

    public void setGetMomentPreviewsResult(get_moment_previews_result getMomentPreviewsResult) {
        this.getMomentPreviewsResult = getMomentPreviewsResult;
    }

    public List<like_get_count_result> getLikeGetCountResults() {
        return likeGetCountResults;
    }

    public void setLikeGetCountResults(List<like_get_count_result> likeGetCountResults) {
        this.likeGetCountResults = likeGetCountResults;
    }

    public List<get_comments_result> getGetCommentsResults() {
        return getCommentsResults;
    }

    public void setGetCommentsResults(List<get_comments_result> getCommentsResults) {
        this.getCommentsResults = getCommentsResults;
    }

    @Override
    public String toString() {
        return "MomentAllDetail{" +
                "getMomentPreviewsResult=" + getMomentPreviewsResult +
                ", likeGetCountResult=" + likeGetCountResults +
                ", get_comments_result=" + getCommentsResults +
                '}';
    }
}
