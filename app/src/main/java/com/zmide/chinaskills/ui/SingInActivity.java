package com.zmide.chinaskills.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zmide.chinaskills.R;
import com.zmide.chinaskills.data.UserContract;

public class SingInActivity extends Activity {

    // 打开登陆页面
    public static void start() {
        Intent mIntent = new Intent(BaseApplication.mContext, SingInActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.mContext.startActivity(mIntent);
    }

    public String TAG = "SingIn Activity";
    protected Context mContext;

    protected EditText mEditAccount, mEditPassword;
    protected Button mBtnSingIn, mBtnLogIn;
    protected CheckBox mCboxRemPassword, mCboxAutoLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseApplication.mContext;
        setContentView(R.layout.singin_activity);
        initView();
    }

    // 初始化 View
    protected void initView() {
        mEditAccount = findViewById(R.id.ac_singin_edit_account);
        mEditPassword = findViewById(R.id.ac_singin_edit_password);
        mBtnSingIn = findViewById(R.id.ac_singin_btn_singin);
        mBtnLogIn = findViewById(R.id.ac_singin_btn_login);
        mCboxRemPassword = findViewById(R.id.ac_singin_cbox_password);
        mCboxAutoLogin = findViewById(R.id.ac_singin_cbox_autosingin);

        // 登陆按钮点击事件
        mBtnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取账号密码
                String account = mEditAccount.getText().toString();
                String password = mEditPassword.getText().toString();

                // 判断账号密码格式
                if (account.equals("") || password.equals("")) {
                    Toast.makeText(mContext, "账号密码不得为空！", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 5) {
                    Toast.makeText(mContext, "账号或密码格式不正确！", Toast.LENGTH_SHORT).show();
                } else {
                    goSingIn(account, password);
                }

            }
        });

        // 注册按钮点击事件
        mBtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转注册页面
                LogInActivity.start();
            }
        });



    }

    protected void goSingIn(String account, String password) {
        Log.d(TAG, "账号: " + account + "  密码：" + password);

        UserContract userContract = UserContract.getInstance(mContext);
        boolean callback = userContract.apiSingIn(account, password);

        if(callback) {
            Toast.makeText(mContext, "登陆成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "密码错误或账号不存在！", Toast.LENGTH_SHORT).show();
        }

    }


}
