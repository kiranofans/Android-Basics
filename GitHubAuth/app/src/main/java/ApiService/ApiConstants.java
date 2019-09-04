package ApiService;

public class ApiConstants {

    //Github
    public static final String GITHUB_BASE_URL="https://api.github.com";
    public static final String GITHUB_CLIENT_SECRET = "f7f18015bae2a9b40e7c5de46548ec13c5b13910";
    public static final String GITHUB_CLIENT_ID = "b16413aa46b046411c28";
    public static final String GITHUB_CALLBACK_URL = "MVP_Pattern_App_Demo://callback";

    public static final String GITHUB_REPO_URL = "/v3/repos/kiranofans/:repo/branches";
    public static final String GITHUB_BRNCHES_URL = "/v3/repos/kiranofans/:repo/branches";
    public static final String GITHUB_USERS_URL = "/v3/users/kiranofans";
    public static final String GITHUB_USER_IDENTITY_URL = "/v3/login/oauth/authorize";
    public static final String GITHUB_ACCESS_TOKEN_URL = "/v3/login/oauth/access_token";

    //Github response code
    public static final String GITHUB_RC_OK = "";

    //News API
    public static final String NEWS_API_BASE_URL = "https://newsapi.org";
    public static final String NEWS_API_KEY = "be21da26680240de86c2f9a587dc49a6";

    public static final String END_POINT_TOP_HEADLINES = "/v2/top-headlines";
    public static final String END_POINT_EVERYTHING = "/v2/everything";
    public static final String END_POINT_SOURCES = "/v2/sources";
}
