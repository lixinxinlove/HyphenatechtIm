package com.lixinxin.androidim.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lixinxin on 2016/6/8.
 */
public interface UserService {
    @GET("{page}/{count}")
    Call<ResponseBody> getUser(@Path("page") int page,@Path("page") int count);
}
