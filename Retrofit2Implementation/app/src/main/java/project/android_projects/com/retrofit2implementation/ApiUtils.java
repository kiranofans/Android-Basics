package project.android_projects.com.retrofit2implementation;

public class ApiUtils {

    public static RetrofitAPIService getService(){
        return RetrofitClient.getClient().create(RetrofitAPIService.class);
    }
}
