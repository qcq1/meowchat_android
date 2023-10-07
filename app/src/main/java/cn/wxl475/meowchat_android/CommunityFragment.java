package cn.wxl475.meowchat_android;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import java.util.Objects;

public class CommunityFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_community,container,false);
        //轮播图,启动！
        ViewFlipper flipper = root.findViewById(R.id.flipper);
        flipper.setFlipInterval(6500);
        flipper.startFlipping();

        return root;
    }
}