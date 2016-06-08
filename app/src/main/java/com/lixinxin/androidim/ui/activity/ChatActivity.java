package com.lixinxin.androidim.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.lixinxin.androidim.R;


/**
 * 聊天页面
 */
public class ChatActivity extends AppCompatActivity {

    EaseChatFragment chatFragment = new EaseChatFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        String userName = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userName);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.fl, chatFragment).commit();
    }
}
