package com.example.android.bakingapp.retrofit;





import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class WebServiceGenerator {
    private static final  String HTTP_BASE_URL ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl(HTTP_BASE_URL)
                                                        .client(okHttpClientBuilder.build())
                                                        .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();


      public static <S> S buildService(Class<S> serviceType){
          return retrofit.create(serviceType);
      }

}
