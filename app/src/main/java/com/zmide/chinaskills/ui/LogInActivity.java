package com.zmide.chinaskills.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zmide.chinaskills.R;
import com.zmide.chinaskills.data.UserContract;

public class LogInActivity extends Activity {

    protected static final String LOG = "注册页面";
    protected Context mContext;

    EditText mEditAccount, mEditPassword, mEditRepeatPassword, mEditPhone;
    Button mBtnCancel, mBtnLonIn;

    // 打开注册页面
    public static void start() {
        Intent mIntent = new Intent(BaseApplication.mContext, LogInActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.mContext.startActivity(mIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseApplication.mContext;
        setContentView(R.layout.login_activity);

        initView();
    }

    protected void initView() {
        mEditAccount = findViewById(R.id.ac_login_edit_account);
        mEditPassword = findViewById(R.id.ac_login_edit_password);
        mEditRepeatPassword = findViewById(R.id.ac_login_edit_repeat);
        mEditPhone = findViewById(R.id.ac_login_edit_phone);
        mBtnCancel = findViewById(R.id.ac_login_btn_cancel);
        mBtnLonIn = findViewById(R.id.ac_login_btn_login);

        // 取消按钮点击事件
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 登陆按钮点击事件
        mBtnLonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mEditAccount.getText().toString();
                String password = mEditPassword.getText().toString();
                String repeatPassword = mEditRepeatPassword.getText().toString();
                String phone = mEditPhone.getText().toString();

                if (account.equals("") || password.equals("") || repeatPassword.equals("") || phone.equals("")) {
                    Toast.makeText(mContext, "信息输入不完整！", Toast.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    Toast.makeText(mContext, "密码不得少于 6 位！", Toast.LENGTH_LONG).show();
                } else if (!password.equals(repeatPassword)) {
                    Toast.makeText(mContext, "两次密码输入不一致！", Toast.LENGTH_LONG).show();
                } else {
                    UserContract userContract = UserContract.getInstance(mContext);
                    boolean callback = userContract.apiLogIn(account, password, phone);
                    if (callback) {
                        Toast.makeText(mContext, "注册成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "注册失败，用户名已被注册！", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

    }
}
