package com.lixinxin.androidim.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.lixinxin.androidim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void loginEM(View v) {

        EMClient.getInstance().login(etUserName.getText().toString().trim(),
                etPassword.getText().toString().trim(),
                new EMCallBack() {       //回调
                    @Override
                    public void onSuccess() {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager().loadAllConversations();
                                Log.d("main", "登录聊天服务器成功！");
                                Toast.makeText(MainActivity.this, "登录聊天服务器成功", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(MainActivity.this, ConversationListActivity.class);
                                startActivity(intent);

                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                        Toast.makeText(MainActivity.this, "登录聊天服务器失败", Toast.LENGTH_LONG).show();

                    }
                });
    }

    @OnClick(R.id.btn_logout)
    public void logoutEM(View v) {

        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d("main", "退出登录聊天服务器！");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "退出登录聊天服务器ok", Toast.LENGTH_LONG).show();
                    }
                });


            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "退出登录聊天服务器失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @OnClick(R.id.btn_send)
    public void sendMessage(View v) {
        startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "a"));
    }
}

