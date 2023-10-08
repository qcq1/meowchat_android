package cn.wxl475.meowchat_android;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.wxl475.meowchat_android.myClass.my_imageView;
import cn.wxl475.meowchat_android.pojo.News;
import cn.wxl475.meowchat_android.pojo.news_result;
import cn.wxl475.meowchat_android.utils.my_utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommunityFragment extends Fragment {
    private View root;
    ViewFlipper flipper;
    private SharedPreferences.Editor editor_CommunityFragment;
    private SharedPreferences getter_CommunityFragment;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_community, container, false);
        flipper = root.findViewById(R.id.flipper);

        getter_CommunityFragment = requireContext().getSharedPreferences("CommunityFragment", MODE_PRIVATE);
        editor_CommunityFragment = getter_CommunityFragment.edit();

        editor_CommunityFragment.putString("获取轮播图", "https://meowchat.xhpolaris.com/notice/get_news");
        editor_CommunityFragment.putString("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6MCwiZGV2aWNlSWQiOiIiLCJleHAiOjE2OTY4MTkyMDAsImlhdCI6MTY5NjczMjgwMCwidXNlcklkIjoiNjQwYzMwMGU3NWJlOTYzNzNlMmU2ODZiIiwid2VjaGF0VXNlck1ldGEiOnsiYXBwSWQiOiJ3eGQzOWNlYmYwNWUyMWQzYjYiLCJvcGVuSWQiOiJvclgwRzQ2WFh2a0dzQ2FFbld3NjRBVDNQOUM0IiwidW5pb25JZCI6Im9WNmtVNTBFbUppR2tHTUFmS2YtRHMxa292TUUifX0.O8P-os_r6_sWonW7mHCRzvUXte44_Hx9-IV_5AJoqko");
        editor_CommunityFragment.putString("school_now", "华东师范大学中山北路校区");
        editor_CommunityFragment.putString("华东师范大学中山北路校区", "637ce159b15d9764c31f9c84");
        editor_CommunityFragment.apply();

        //初始化界面
        TextView textView_school_now = root.findViewById(R.id.textView_school_now);
        textView_school_now.setText(getter_CommunityFragment.getString("school_now", "华东师范大学中山北路校区"));

        //轮播图,启动！
        flipper.setFlipInterval(6500);
        flipper.startFlipping();

        //初始化界面的请求
        try {
            okHttp_init_news();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return root;
    }

    private void okHttp_init_news() throws MalformedURLException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)
                .writeTimeout(8000, TimeUnit.SECONDS)
                .build();

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("communityId",
                getter_CommunityFragment.getString(getter_CommunityFragment.getString("school_now",
                        "华东师范大学中山北路校区"),
                        ""
                )
        );

        String url=my_utils.concat_url_params(getter_CommunityFragment.getString("获取轮播图", ""), params);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", getter_CommunityFragment.getString("token",""))
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
                assert response.body() != null;
                news_result newsResult = new Gson().fromJson(response.body().string(), news_result.class);
                List<News> news = newsResult.getNews();
                ViewFlipper flipper = root.findViewById(R.id.flipper);
                for(int i=0;i<3;i++){
                    my_imageView child = (my_imageView) flipper.getChildAt(i);
                    child.setImageURL(news.get(i).getImageUrl());
                }
            }
        });
    }
}