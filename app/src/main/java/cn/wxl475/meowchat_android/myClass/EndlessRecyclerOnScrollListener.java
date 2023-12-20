package cn.wxl475.meowchat_android.myClass;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(!recyclerView.canScrollVertically(1)){
            onLoadMore();
        }
    }
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
}


