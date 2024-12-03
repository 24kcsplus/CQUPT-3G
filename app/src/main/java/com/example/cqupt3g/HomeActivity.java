package com.example.cqupt3g;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    private Button mBtnClickMe;
    private ImageView mIvCquptIcon;
    private TextView mTvThirdGStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initEvent();
    }

    private void initView() {
        mBtnClickMe = findViewById(R.id.btn_home_click_me);
        mIvCquptIcon = findViewById(R.id.iv_home_cqupt_icon);
        mTvThirdGStory = findViewById(R.id.tv_home_third_g_story);
        mTvThirdGStory.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void initEvent() {
        mBtnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMe();
            }
        });
    }

    private void clickMe() {
        Toast.makeText(this, "不知道为什么题目演示没有btn还要写btn，姑且先写一下", Toast.LENGTH_SHORT).show();
    }
}