package cn.wxl475.meowchat_android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class WorldFragment extends Fragment {
    private View root;
    protected WeakReference<View> mRootView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        //在用fragmenttabhost做页面切换的时候，发现只要一来回切换fragment，fragment页面就会重新初始化，也就是执行onCreateView()方法，导致每次Fragment的布局都重绘，无法保持Fragment原有状态
        //解决方案：
        //在Fragment onCreateView方法中缓存View
        // detach/attach can lead to view recreate frequently
        if (mRootView == null || mRootView.get() == null) {
            root=inflater.inflate(R.layout.fragment_world,container,false);
            mRootView = new WeakReference<View>(root);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return root;
    }
}