package ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {
    private static Retrofit retrofit = null;

    public static Retrofit2ApiService getGitRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(ApiConstants.GITHUB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(Retrofit2ApiService.class);
    }

    public static Retrofit2ApiService getNewsRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(ApiConstants.NEWS_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(Retrofit2ApiService.class);
    }
}
