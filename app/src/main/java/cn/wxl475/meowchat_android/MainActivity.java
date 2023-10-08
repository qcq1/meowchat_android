package cn.wxl475.meowchat_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //发布按钮跳转
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View nav_release = findViewById(R.id.navigation_release);
        nav_release.setOnClickListener(v -> releaseOnClick());

        // 获取页面上的底部导航栏控件
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void releaseOnClick() {
        startActivity(new Intent(MainActivity.this, ReleaseActivity.class));
    }
}