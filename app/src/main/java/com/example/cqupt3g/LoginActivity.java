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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private String username, password;

    private ImageView mIvCquptIcon;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPassword;
    private Button mBtnLogin;
    private CheckBox mCbRememberPassword;

    public static void start(Context context){
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

    private void initView() {
        mIvCquptIcon = findViewById(R.id.iv_login_cqupt_icon);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mEtLoginPassword = findViewById(R.id.et_login_password);
        mBtnLogin = findViewById(R.id.btn_login_login);
        mCbRememberPassword = findViewById(R.id.cb_login_remember_password);
    }

    private void initEvent() {
        isRemembered();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        readText();
        rememberPassword();
        if (username.equals("24k") && password.equals("123456")) {
            loginSuccess();
        } else {
            loginFailed();
        }
    }

    private void loginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        HomeActivity.start(this);
        finish();;
    }

    private void loginFailed() {
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

        // 错误时清除密码
        mEtLoginPassword.setText("");
    }

    private void readText(){
        username = mEtLoginUsername.getText().toString();
        password = mEtLoginPassword.getText().toString();
    }

    // 检查是否记住过密码
    private void isRemembered(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);

        if (sharedPreferences.getBoolean("remember_password", false)) {
            username = sharedPreferences.getString("username", "");
            password = sharedPreferences.getString("password", "");
            mEtLoginUsername.setText(username);
            mEtLoginPassword.setText(password);
            mCbRememberPassword.setChecked(true);
            Log.d(TAG,"(LoginActivity:79)-->>曾经记住过密码");
        }else {
            Log.d(TAG,"(LoginActivity:86)-->>曾经未记住过密码");
        }
    }

    // 记住密码
    private void rememberPassword() {
        SharedPreferences.Editor editor = getSharedPreferences("user", 0).edit();

        if (mCbRememberPassword.isChecked()) {
            editor.putBoolean("remember_password", true);
            editor.putString("username", this.username);
            editor.putString("password", this.password);
            Log.d(TAG,"(LoginActivity:92)-->>选中了记住密码，已记住密码");
        } else {
            editor.clear();
            Log.d(TAG,"(LoginActivity:97)-->>未选中记住密码，清除！");
        }

        editor.apply();
    }
}