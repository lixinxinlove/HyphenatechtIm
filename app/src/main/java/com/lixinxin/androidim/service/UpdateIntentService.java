package com.lixinxin.androidim.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lixinxin.androidim.cache.ACache;
import com.lixinxin.androidim.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class UpdateIntentService extends IntentService {


    ACache mACache;
    String username;
    List<User> users;

    public UpdateIntentService() {
        super("UpdateIntentService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mACache = ACache.get(getApplicationContext());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("lxx", "onHandleIntent");
        if (intent != null) {

            username = intent.getStringExtra("username");


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://gank.io/api/data/福利/")
                    .build();

            DiskService diskService = retrofit.create(DiskService.class);
            Call<ResponseBody> call = diskService.getData(1, (int) (1 + Math.random() * (10 - 1 + 1)));

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Gson gson = new Gson();
                        users = gson.fromJson(jsonObject.getString("results"), new TypeToken<List<User>>() {
                        }.getType());


                        mACache.put(username + "Nick", users.get(0).getWho(), 60);

                        if (username.equals("a")) {
                            mACache.put(username + "Avatar", users.get(0).getUrl(), 60 );
                        } else {
                            mACache.put(username + "Avatar", users.get(0).getUrl(), 60);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public interface DiskService {
        @GET("{count}/{page}")
        Call<ResponseBody> getData(
                @Path("count") int count,
                @Path("page") int page
        );
    }

}
