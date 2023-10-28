package cn.wxl475.meowchat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;

import cn.wxl475.meowchat_android.myClass.my_imageView;
import cn.wxl475.meowchat_android.pojo.Moment;

public class MomentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_detail);
        Intent intent = getIntent();
        Moment moment = new Gson().fromJson(intent.getStringExtra("Moment"),Moment.class);
        my_imageView myImageView = findViewById(R.id.details_img);
        myImageView.setImageURL(moment.getPhotos().get(0));
    }
}