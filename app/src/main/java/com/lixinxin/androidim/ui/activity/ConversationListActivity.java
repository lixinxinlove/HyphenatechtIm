package com.lixinxin.androidim.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.lixinxin.androidim.R;

public class ConversationListActivity extends AppCompatActivity {


    private EaseConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);


        conversationListFragment = new EaseConversationListFragment();

        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(ConversationListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
            }


        });


        conversationListFragment.refresh();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl, conversationListFragment, "chat");
        ft.addToBackStack(null);
        ft.commit();

    }
}
