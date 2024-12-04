package com.example.cqupt3g;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button mBtnLogOut;
    private ImageView mIvCquptIcon;
    private TextView mTvThirdGStory;

    // 设置转入此 Activity 的入口
    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initEvent();
    }

    // 初始化布局
    private void initView() {
        mBtnLogOut = findViewById(R.id.btn_home_log_out);
        mIvCquptIcon = findViewById(R.id.iv_home_cqupt_icon);
        mTvThirdGStory = findViewById(R.id.tv_home_third_g_story);
        mTvThirdGStory.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    // 初始化此 Activity 事件
    private void initEvent() {
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
    }

    // 登出，跳回 LoginActivity
    private void logOut() {
        Toast.makeText(this, "登出", Toast.LENGTH_SHORT).show();
        LoginActivity.start(this);
        finish();
    }
}