package com.example.cqupt3g.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cqupt3g.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/*
 *  此部分参考了https://github.com/ahuyangdong/DialogCustom
 *  由于对 dialog 不是特别熟悉
 *  此类的注释可能不准确
 */

public class RegisterDialog {
    private boolean flag = false;
    private Context context;
    private Dialog dialog;
    private RegisterDialog.OnRegisterListener listener;
    private TextView mTvTitle;
    private TextInputLayout mTilRegisterUsername;
    private TextInputLayout mTilRegisterPassword;
    private TextInputLayout mTilRegisterPasswordAgain;
    private TextInputEditText mTietRegisterUsername;
    private TextInputEditText mTietRegisterPassword;
    private TextInputEditText mTietRegisterPasswordAgain;
    private Button mBtnCancel;
    private Button mBtnRegister;

    // 构造方法，也是对 dialog 的初始化
    public RegisterDialog(Context context, OnRegisterListener listener) {
        this.context = context;
        this.listener = listener;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_register, null);

        initView(layout);
        initEvent();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setCancelable(false);
        dialog = builder.create();
    }

    // 初始化布局
    private void initView(View layout) {
        mTvTitle = layout.findViewById(R.id.tv_register_hint_text);
        mTilRegisterUsername = layout.findViewById(R.id.til_register_username);
        mTilRegisterPassword = layout.findViewById(R.id.til_register_password);
        mTilRegisterPasswordAgain = layout.findViewById(R.id.til_register_password_again);
        mTietRegisterUsername = layout.findViewById(R.id.tiet_register_username);
        mTietRegisterPassword = layout.findViewById(R.id.tiet_register_password);
        mTietRegisterPasswordAgain = layout.findViewById(R.id.tiet_register_password_again);

        mBtnCancel = layout.findViewById(R.id.btn_register_cancel);
        mBtnRegister = layout.findViewById(R.id.btn_register_register);
    }

    //初始化事件
    private void initEvent() {
        mBtnCancel.setOnClickListener(view -> dialog.dismiss());
        mBtnRegister.setOnClickListener(view -> {
            if (listener != null) {
                register();
            }
        });

        mTietRegisterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String username = mTietRegisterUsername.getText().toString();
                if (username.isEmpty()) {
                    mTilRegisterUsername.setError("用户名不能为空");
                } else {
                    mTilRegisterUsername.setError(null);
                }
            }
        });

        mTietRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = mTietRegisterPassword.getText().toString();
                String passwordAgain = mTietRegisterPasswordAgain.getText().toString();

                if (!flag) return;

                if (passwordAgain.isEmpty()) {
                    mTilRegisterPasswordAgain.setError("密码不能为空");
                } else if (!password.equals(passwordAgain)) {
                    mTilRegisterPasswordAgain.setError("两次密码不一致");
                } else {
                    mTilRegisterPasswordAgain.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = mTietRegisterPassword.getText().toString();

                if (password.isEmpty()) {
                    mTilRegisterPassword.setError("密码不能为空");
                } else if (password.length() <= 6) {
                    mTilRegisterPassword.setError("密码必须大于6位");
                } else {
                    mTilRegisterPassword.setError(null);
                }
            }
        });

        mTietRegisterPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = mTietRegisterPassword.getText().toString();
                String passwordAgain = mTietRegisterPasswordAgain.getText().toString();

                if (passwordAgain.isEmpty()) {
                    mTilRegisterPasswordAgain.setError("密码不能为空");
                } else if (!password.equals(passwordAgain)) {
                    mTilRegisterPasswordAgain.setError("两次密码不一致");
                } else {
                    mTilRegisterPasswordAgain.setError(null);
                }

                flag = true;
            }
        });
    }

    // 注册，调用回调接口方法，将数据传回 LoginActivity 处理
    private void register() {
        String username = mTietRegisterUsername.getText().toString();
        String password = mTietRegisterPassword.getText().toString();
        String passwordAgain = mTietRegisterPasswordAgain.getText().toString();

        if (username.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
            if (username.isEmpty()) {
                mTilRegisterUsername.setError("用户名不能为空");
            } else {
                mTilRegisterUsername.setError(null);
            }
            if (password.isEmpty()) {
                mTilRegisterPassword.setError("密码不能为空");
            } else {
                mTilRegisterPassword.setError(null);
            }
            if (passwordAgain.isEmpty()) {
                mTilRegisterPasswordAgain.setError("密码不能为空");
            } else {
                mTilRegisterPasswordAgain.setError(null);
            }
            return;
        }

        if (password.equals(passwordAgain)) {
            hideKeyBoard();
            mTilRegisterPasswordAgain.setError(null);
            mBtnRegister.setEnabled(false);
            // 通过回调将数据传回
            listener.onRegister(username, password);
            close();
        } else {
            mTilRegisterPasswordAgain.setError("两次密码不一致");
            mTietRegisterPasswordAgain.setText("");
            mTietRegisterPasswordAgain.requestFocus();
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
