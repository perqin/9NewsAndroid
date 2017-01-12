package cn.ninesmart.ninenews.data.network.retrofit;

import cn.ninesmart.ninenews.data.network.retrofit.services.NineNewsService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public final class ApiFactory {
    private static ApiFactory sInstance;

    private NineNewsService mNineNewsService;

    public static ApiFactory getInstance() {
        if (sInstance == null) {
            sInstance = new ApiFactory();
        }
        return sInstance;
    }

    private ApiFactory() {
        // Create client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NineNewsService.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mNineNewsService = retrofit.create(NineNewsService.class);
    }

    public NineNewsService getNineNewsService() {
        return mNineNewsService;
    }
}
