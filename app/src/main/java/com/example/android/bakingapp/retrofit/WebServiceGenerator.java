package com.example.android.bakingapp.retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceGenerator {

  private static final String HTTP_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";


  private static final Retrofit.Builder builder = new Retrofit.Builder().baseUrl(HTTP_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create());
  private static final Retrofit retrofit = builder.build();


  public static <S> S buildService(Class<S> serviceType) {
    return retrofit.create(serviceType);
  }

}
