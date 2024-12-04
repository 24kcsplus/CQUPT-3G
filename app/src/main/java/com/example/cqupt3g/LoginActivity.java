package com.example.cqupt3g;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cqupt3g.widget.RegisterDialog;

public class LoginActivity extends AppCompatActivity implements RegisterDialog.OnRegisterListener {

    private static final String TAG = "LoginActivity";
    private String username, password;

    private ImageView mIvCquptIcon;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private CheckBox mCbRememberPassword;

    // 设置转入此 Activity 的入口
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    // 初始化布局
    private void initView() {
        mIvCquptIcon = findViewById(R.id.iv_login_cqupt_icon);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mEtLoginPassword = findViewById(R.id.et_login_password);
        mBtnLogin = findViewById(R.id.btn_login_login);
        mBtnRegister = findViewById(R.id.btn_login_register);
        mCbRememberPassword = findViewById(R.id.cb_login_remember_password);
    }

    // 初始化此 Activity 事件
    // 检查是否记住过密码
    private void initEvent() {
        isRemembered();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerView();
            }
        });
    }

    // 启动自定义 dialog
    private void registerView() {
        RegisterDialog registerDialog = new RegisterDialog(this, this);
        registerDialog.show();
    }

    // 重写回调接口
    @Override
    public void onRegister(String username, String password) {
        registerUser(username, password);
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    // 用户注册，并将数据持久化
    private void registerUser(String regUsername, String regPassword) {
        SharedPreferences.Editor editor = getSharedPreferences("reg_user", 0).edit();
        editor.putString("reg_username", regUsername);
        editor.putString("reg_password", regPassword);
        editor.apply();
    }

    // 用户登录
    private void login() {
        // 读取 EditView 内容
        readText();

        // 记住密码
        rememberPassword();

        // 读取持久化数据
        SharedPreferences sharedPreferences = getSharedPreferences("reg_user", 0);
        String cmpUsername = sharedPreferences.getString("reg_username", "");
        String cmpPassword = sharedPreferences.getString("reg_password", "");

        // 登录检查逻辑
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (username.equals(cmpUsername) && password.equals(cmpPassword)) {
                loginSuccess();
            } else {
                loginFailed();
            }
        }
    }

    // 登录成功进入 3G 宣传 Activity
    private void loginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        HomeActivity.start(this);
        finish();
    }

    // 登陆失败提醒
    private void loginFailed() {
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

        // 错误时清除密码，并聚焦到密码框
        mEtLoginPassword.setText("");
        mEtLoginPassword.requestFocus();
    }

    // 读取 EditView 内容
    private void readText() {
        username = mEtLoginUsername.getText().toString();
        password = mEtLoginPassword.getText().toString();
    }

    // 检查是否记住过密码
    private void isRemembered() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);

        username = sharedPreferences.getString("username", "");
        mEtLoginUsername.setText(username);

        if (sharedPreferences.getBoolean("remember_password", false)) {
            password = sharedPreferences.getString("password", "");
            mEtLoginPassword.setText(password);
            mCbRememberPassword.setChecked(true);
            Log.d(TAG, "(LoginActivity:79)-->>曾经记住过密码");
        } else {
            Log.d(TAG, "(LoginActivity:86)-->>曾经未记住过密码");
        }
    }

    // 记住密码
    private void rememberPassword() {
        SharedPreferences.Editor editor = getSharedPreferences("user", 0).edit();

        // 没有选中记住密码也要记住用户名
        editor.putString("username", this.username);

        if (mCbRememberPassword.isChecked()) {
            editor.putBoolean("remember_password", true);
            editor.putString("password", this.password);
            Log.d(TAG, "(LoginActivity:92)-->>选中了记住密码，已记住密码");
        } else {
            editor.remove("remember_password");
            editor.remove("password");
            Log.d(TAG, "(LoginActivity:97)-->>未选中记住密码，清除！");
        }

        editor.apply();
    }
}