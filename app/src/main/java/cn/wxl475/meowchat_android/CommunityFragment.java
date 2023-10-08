package cn.wxl475.meowchat_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommunityFragment extends Fragment {
    private View root;
    private ViewFlipper flipper;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp_CommunityFragment;
    private SharedPreferences sp_public;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_community, container, false);
        //editor、sp
        init_sps();

        editor.putString("school_now", "华东师范大学中山北路校区");
        editor.putString("华东师范大学中山北路校区", "637ce159b15d9764c31f9c84");
        editor.apply();

        TextView textView_school_now = root.findViewById(R.id.textView_school_now);
        textView_school_now.setText(sp_CommunityFragment.getString("school_now", "华东师范大学中山北路校区"));

        //初始化界面的请求
        okHttp_init_news();
        return root;
    }

    private void init_sps() {
        editor = requireContext().getSharedPreferences("CommunityFragment", Context.MODE_PRIVATE).edit();
        sp_CommunityFragment = requireContext().getSharedPreferences("CommunityFragment", Context.MODE_PRIVATE);
        sp_public = requireContext().getSharedPreferences("public", Context.MODE_PRIVATE);
    }

    private void okHttp_init_news() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .build();

        FormBody.Builder builder = new FormBody.Builder();
        HashMap<String, String> map = new HashMap<>();
        map.put("communityId", sp_CommunityFragment.getString(sp_CommunityFragment.getString("school_now", "华东师范大学中山北路校区"), ""));
        for (String key : map.keySet()) {
            builder.add(key, Objects.requireNonNull(map.get(key)));
        }
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url("https://meowchat.xhpolaris.com/notice/get_news")
                .addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6MCwiZGV2aWNlSWQiOiIiLCJleHAiOjE2OTY4MTkyMDAsImlhdCI6MTY5NjczMjgwMCwidXNlcklkIjoiNjQwYzMwMGU3NWJlOTYzNzNlMmU2ODZiIiwid2VjaGF0VXNlck1ldGEiOnsiYXBwSWQiOiJ3eGQzOWNlYmYwNWUyMWQzYjYiLCJvcGVuSWQiOiJvclgwRzQ2WFh2a0dzQ2FFbld3NjRBVDNQOUM0IiwidW5pb25JZCI6Im9WNmtVNTBFbUppR2tHTUFmS2YtRHMxa292TUUifX0.O8P-os_r6_sWonW7mHCRzvUXte44_Hx9-IV_5AJoqko")
                .addHeader("X-Xh-Env", "pro")
                .build();
        Call call = okHttpClient.newCall(request);
        //enqueue内部创建子线程，不会阻塞
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(call+"失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                System.out.println(response);
                //轮播图,启动！
                flipper = root.findViewById(R.id.flipper);
                flipper.setFlipInterval(6500);
                flipper.startFlipping();
            }
        });
    }
}