package cn.ninesmart.ninenews.data.network.retrofit.services;

import cn.ninesmart.ninenews.data.network.body.GetArticlesRes;
import cn.ninesmart.ninenews.data.network.body.PostUserRes;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public interface NineNewsService {
    String API_HOST = "http://api2.9smart.cn/";

    @GET("articles")
    Observable<GetArticlesRes> getArticles();

    @FormUrlEncoded
    @POST("user")
    Observable<PostUserRes> login(@Field("email") String email, @Field("password") String password);
}
