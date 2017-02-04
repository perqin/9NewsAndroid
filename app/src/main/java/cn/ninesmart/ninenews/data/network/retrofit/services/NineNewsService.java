package cn.ninesmart.ninenews.data.network.retrofit.services;

import cn.ninesmart.ninenews.data.network.body.GetArticleIdRes;
import cn.ninesmart.ninenews.data.network.body.GetArticlesRes;
import cn.ninesmart.ninenews.data.network.body.GetCommentsIdRes;
import cn.ninesmart.ninenews.data.network.body.GetUserIdRes;
import cn.ninesmart.ninenews.data.network.body.PostCommentsIdRes;
import cn.ninesmart.ninenews.data.network.body.PostUserRes;
import cn.ninesmart.ninenews.data.network.body.PostUsersRes;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public interface NineNewsService {
    String API_HOST = "http://api2.9smart.cn/";

    @GET("articles")
    Observable<GetArticlesRes> getArticles();

    @GET("articles/{dateline}/next/{page}")
    Observable<GetArticlesRes> getMoreArticles(@Path("dateline") long lastDateline, @Path("page") int nextPage);

    @FormUrlEncoded
    @POST("user")
    Observable<PostUserRes> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("users")
    Observable<PostUsersRes> register(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("repassword") String confirmPassword,
                                      @Field("nickname") String nickname);

    @GET("user/{id}")
    Observable<GetUserIdRes> getUserById(@Path("id") String userId);

    @GET("article/{id}")
    Observable<GetArticleIdRes> getArticleById(@Path("id") String articleId);

    @GET("comments/{article_id}")
    Observable<GetCommentsIdRes> getCommentsByArticleId(@Path("article_id") String articleId);

    @GET("comments/{article_id}/{dateline}/next/{page}")
    Observable<GetCommentsIdRes> getMoreCommentsByArticleId(@Path("article_id") String articleId, @Path("dateline") long lastDateline, @Path("page") int nextPage);

    @FormUrlEncoded
    @POST("comments/{target_id}")
    Observable<PostCommentsIdRes> postCommentToTarget(@Path("target_id") String targetId,
                                                      @Query("auth") String token,
                                                      @Field("type") String type,
                                                      @Field("content") String content,
                                                      @Field("model") String model);
}
