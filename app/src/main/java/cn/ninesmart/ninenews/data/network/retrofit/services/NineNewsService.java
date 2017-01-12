package cn.ninesmart.ninenews.data.network.retrofit.services;

import cn.ninesmart.ninenews.data.network.body.GetArticlesRes;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public interface NineNewsService {
    String API_HOST = "http://api2.9smart.cn/";

    @GET("articles")
    Observable<GetArticlesRes> getArticles();
}
