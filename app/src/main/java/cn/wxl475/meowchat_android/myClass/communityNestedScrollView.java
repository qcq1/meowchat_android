package cn.wxl475.meowchat_android.myClass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class communityNestedScrollView extends NestedScrollView {
    private int mHeaderHeight = 0;
    private int originScroll=0;

    public communityNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public communityNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public communityNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeaderHeight(int headerHeight){
        this.mHeaderHeight=headerHeight;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(originScroll<mHeaderHeight){
            scrollBy(dx,dy);
            consumed[0]=dx;
            consumed[1]=dy;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.originScroll=t;
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
