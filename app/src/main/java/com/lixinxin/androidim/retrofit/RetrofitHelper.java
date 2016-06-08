package com.lixinxin.androidim.retrofit;

import retrofit2.Retrofit;

/**
 * Created by lixinxin on 2016/6/8.
 */
public class RetrofitHelper {


    private static Retrofit retrofit;

    private RetrofitHelper() {
    }


    public static Retrofit getInstance() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://gank.io/api/data/福利/")
                    .build();
        }
        return retrofit;
    }
}
