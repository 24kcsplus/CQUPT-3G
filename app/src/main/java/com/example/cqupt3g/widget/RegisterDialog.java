package com.example.cqupt3g.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cqupt3g.R;

/*
    此部分参考了https://github.com/ahuyangdong/DialogCustom
    由于对 dialog 不是特别熟悉
    此类的注释可能不准确
 */

public class RegisterDialog {
    private Context context;
    private Dialog dialog;
    private RegisterDialog.OnRegisterListener listener;
    private TextView mTvTitle;
    private EditText mEtRegisterUsername;
    private EditText mEtRegisterPassword;
    private EditText mEtRegisterPasswordAgain;
    private Button mBtnCancel;
    private Button mBtnRegister;

    // 构造方法，也是对 dialog 的初始化
    public RegisterDialog(Context context, OnRegisterListener listener) {
        this.context = context;
        this.listener = listener;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_register, null);

        initView(layout);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setCancelable(false);
        dialog = builder.create();
    }

    // 初始化布局
    private void initView(View layout) {
        mTvTitle = layout.findViewById(R.id.tv_register_hint_text);
        mEtRegisterUsername = layout.findViewById(R.id.et_register_username);
        mEtRegisterPassword = layout.findViewById(R.id.et_register_password);
        mEtRegisterPasswordAgain = layout.findViewById(R.id.et_register_password_again);

        mBtnCancel = layout.findViewById(R.id.btn_register_cancel);
        mBtnCancel.setOnClickListener(view -> dialog.dismiss());
        mBtnRegister = layout.findViewById(R.id.btn_register_register);
        mBtnRegister.setOnClickListener(view -> {
            if (listener != null) {
                register();
            }
        });
    }

    // 注册，调用回调接口方法，将数据传回 LoginActivity 处理
    private void register() {
        String username = mEtRegisterUsername.getText().toString();
        String password = mEtRegisterPassword.getText().toString();
        String passwordAgain = mEtRegisterPasswordAgain.getText().toString();

        if (username.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
            Toast.makeText(context, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.equals(passwordAgain)) {
            hideKeyBoard();
            mBtnRegister.setEnabled(false);
            // 通过回调将数据传回
            listener.onRegister(username, password);
            close();
        } else {
            Toast.makeText(context, "两次密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
            mEtRegisterPasswordAgain.setText("");
            mEtRegisterPasswordAgain.requestFocus();
        }
    }

    // 关闭此 dialog
    public void close() {
        dialog.dismiss();
    }

    // 显示此 dialog
    public void show() {
        dialog.show();
    }

    // 隐藏软键盘
    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = dialog.getWindow().getDecorView();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // 使用接口回调
    public interface OnRegisterListener {
        void onRegister(String username, String password);
    }
}
