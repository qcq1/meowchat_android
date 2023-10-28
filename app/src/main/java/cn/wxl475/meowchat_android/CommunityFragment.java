package cn.wxl475.meowchat_android;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.wxl475.meowchat_android.myClass.my_moment_RecyclerViewAdapter;
import cn.wxl475.meowchat_android.myClass.my_imageView;
import cn.wxl475.meowchat_android.pojo.Moment;
import cn.wxl475.meowchat_android.pojo.MomentAllDetail;
import cn.wxl475.meowchat_android.pojo.News;
import cn.wxl475.meowchat_android.pojo.get_comments_result;
import cn.wxl475.meowchat_android.pojo.get_moment_previews_result;
import cn.wxl475.meowchat_android.pojo.like_get_count_result;
import cn.wxl475.meowchat_android.pojo.news_result;
import cn.wxl475.meowchat_android.utils.my_utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommunityFragment extends Fragment{
    private View root;
    private ViewFlipper flipper;
    private RecyclerView waterfall;
    private Integer page;
    private my_moment_RecyclerViewAdapter myMomentRecyclerViewAdapter;
    private SharedPreferences.Editor editor_CommunityFragment;
    private SharedPreferences getter_CommunityFragment;
    protected WeakReference<View> mRootView;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                news_result newsResult = (news_result) msg.obj;
                List<News> news = newsResult.getNews();
                for (int i = 0; i < news.size(); i++) {
                    my_imageView myImageView = new my_imageView(requireContext());
                    myImageView.setImageURL(news.get(i).getImageUrl());
                    int finalI = i;
                    myImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), WebActivity.class);
                            intent.putExtra("LinkUrl", news.get(finalI).getLinkUrl());
                            startActivity(intent);
                        }
                    });
                    flipper.addView(myImageView);
                }
            }
            else if(msg.what==2){
                MomentAllDetail momentAllDetail= (MomentAllDetail) msg.obj;

                //新建一个RecyclerView的适配器，并传入数据
                myMomentRecyclerViewAdapter = new my_moment_RecyclerViewAdapter(requireContext(), momentAllDetail);
                //将适配器设置给recyclerview控件
                waterfall.setAdapter(myMomentRecyclerViewAdapter);
                //给适配器添加我们暴露的监听方法
                myMomentRecyclerViewAdapter.setOnRecyclerViewItemClickListener(this::onItemClick);
                //新建一个StaggeredGridLayoutManager布局管理器，设置参数：1.显示的列数   2.显示布局的方向（水平或垂直）
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                //将布局管理器设置给recyclerveiw控件
                waterfall.setLayoutManager(staggeredGridLayoutManager);
            }
        }
        //实现我们的监听接口里的方法，在这里获得数据，对数据进行操作
        public void onItemClick(View view, Moment moment) {
            //创建一个intent，指明跳转目标类
            Intent intent = new Intent(getActivity(), MomentDetailActivity.class);
            //拿到数据传给intent
            intent.putExtra("Moment", new Gson().toJson(moment,Moment.class));
            //启动Activity
            startActivity(intent);
        }
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //在用fragmenttabhost做页面切换的时候，发现只要一来回切换fragment，fragment页面就会重新初始化，也就是执行onCreateView()方法，导致每次Fragment的布局都重绘，无法保持Fragment原有状态
        //解决方案：
        //在Fragment onCreateView方法中缓存View
        // detach/attach can lead to view recreate frequently
        if (mRootView == null || mRootView.get() == null) {
            root=inflater.inflate(R.layout.fragment_community,container,false);
            mRootView = new WeakReference<View>(root);
            //初始化内容
            flipper = root.findViewById(R.id.flipper);
            waterfall = root.findViewById(R.id.waterfall);
            page=0;

            getter_CommunityFragment = requireContext().getSharedPreferences("CommunityFragment", MODE_PRIVATE);
            editor_CommunityFragment = getter_CommunityFragment.edit();

            editor_CommunityFragment.putString("获取轮播图", "https://meowchat.xhpolaris.com/notice/get_news");
            editor_CommunityFragment.putString("获取一些动态预览","https://meowchat.xhpolaris.com/moment/get_moment_previews");
            editor_CommunityFragment.putString("获取点赞数量","https://meowchat.xhpolaris.com/like/get_count");
            editor_CommunityFragment.putString("获取评论","https://meowchat.xhpolaris.com/comment/get_comments");
            editor_CommunityFragment.putString("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6MCwiZGV2aWNlSWQiOiIiLCJleHAiOjE2OTY4MTkyMDAsImlhdCI6MTY5NjczMjgwMCwidXNlcklkIjoiNjQwYzMwMGU3NWJlOTYzNzNlMmU2ODZiIiwid2VjaGF0VXNlck1ldGEiOnsiYXBwSWQiOiJ3eGQzOWNlYmYwNWUyMWQzYjYiLCJvcGVuSWQiOiJvclgwRzQ2WFh2a0dzQ2FFbld3NjRBVDNQOUM0IiwidW5pb25JZCI6Im9WNmtVNTBFbUppR2tHTUFmS2YtRHMxa292TUUifX0.O8P-os_r6_sWonW7mHCRzvUXte44_Hx9-IV_5AJoqko");
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
                okHttp_init_moment();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }

        return root;
    }
    private void okHttp_init_moment(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)
                .writeTimeout(8000, TimeUnit.SECONDS)
                .build();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("page",page.toString());
        params.put("communityId",
                getter_CommunityFragment.getString(getter_CommunityFragment.getString("school_now",
                                "华东师范大学中山北路校区"),
                        ""
                )
        );

        String url = my_utils.concat_url_params(getter_CommunityFragment.getString("获取一些动态预览", ""), params);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", getter_CommunityFragment.getString("token", ""))
                .addHeader("X-Xh-Env", "pro")
                .build();
        Call call = okHttpClient.newCall(request);
        //enqueue内部创建子线程，不会阻塞
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(call + "失败");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    get_moment_previews_result momentResult = new Gson().fromJson(response.body().string(), get_moment_previews_result.class);
                    List<Moment> moments = momentResult.getMoments();
                    List<like_get_count_result> likeGetCountResults = new ArrayList<>();
                    ArrayList<get_comments_result> getCommentsResults = new ArrayList<>();
                    for(Moment moment : moments){
                        likeGetCountResults.add(okhttp_get_moment_like_num(moment, "1"));
                        getCommentsResults.add(okhttp_get_comments(moment,"moment","0"));
                    }
                    MomentAllDetail momentAllDetail = new MomentAllDetail(momentResult,likeGetCountResults,getCommentsResults);
                    Message msg = new Message();
                    msg.what=2;
                    msg.obj = momentAllDetail;
                    handler.sendMessage(msg);
                }
            }
        });
    }
    private like_get_count_result okhttp_get_moment_like_num(Moment moment,String targetType) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)
                .writeTimeout(8000, TimeUnit.SECONDS)
                .build();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("targetId",moment.getId());
        params.put("targetType",targetType);

        String url = my_utils.concat_url_params(getter_CommunityFragment.getString("获取点赞数量", ""), params);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", getter_CommunityFragment.getString("token", ""))
                .addHeader("X-Xh-Env", "pro")
                .build();
        Call call = okHttpClient.newCall(request);
        //enqueue内部创建子线程，不会阻塞
        Response response = call.execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return new Gson().fromJson(response.body().string(), like_get_count_result.class);
        }
        return null;
    }

    private get_comments_result okhttp_get_comments(Moment moment,String scope,String page) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(8000, TimeUnit.SECONDS)
                .readTimeout(8000, TimeUnit.SECONDS)
                .writeTimeout(8000, TimeUnit.SECONDS)
                .build();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("id",moment.getId());
        params.put("scope",scope);
        params.put("page",page);

        String url = my_utils.concat_url_params(getter_CommunityFragment.getString("获取点赞数量", ""), params);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", getter_CommunityFragment.getString("token", ""))
                .addHeader("X-Xh-Env", "pro")
                .build();
        Call call = okHttpClient.newCall(request);
        //enqueue内部创建子线程，不会阻塞
        Response response = call.execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return new Gson().fromJson(response.body().string(), get_comments_result.class);
        }
        return null;
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

        String url = my_utils.concat_url_params(getter_CommunityFragment.getString("获取轮播图", ""), params);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", getter_CommunityFragment.getString("token", ""))
                .addHeader("X-Xh-Env", "pro")
                .build();
        Call call = okHttpClient.newCall(request);
        //enqueue内部创建子线程，不会阻塞
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(call + "失败");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    news_result newsResult = new Gson().fromJson(response.body().string(), news_result.class);
                    Message msg = new Message();
                    msg.what=1;
                    msg.obj = newsResult;
                    handler.sendMessage(msg);
                }
            }
        });
    }
}