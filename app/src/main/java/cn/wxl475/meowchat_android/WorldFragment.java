package cn.wxl475.meowchat_android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WorldFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_world,container,false);
        return root;
    }
}