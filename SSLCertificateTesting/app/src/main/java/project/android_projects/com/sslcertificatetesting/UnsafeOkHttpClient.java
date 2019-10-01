package project.android_projects.com.sslcertificatetesting;

import android.text.TextUtils;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UnsafeOkHttpClient {
    /** It's called UnsafeOKHttpClient because it trusts all sort of SSL certificates
     * (Expired, badSSLs, and so on )*/
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originRequest = chain.request();

                    String userAgent = System.getProperty("http.agent");

                    Request request = originRequest.newBuilder()
                            .header("Content-Type","application/json")
                            .header("Authorization","")
                            .method(originRequest.method(),originRequest.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient okHttpClient = okHttpBuilder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void generateRequestHeader(RetrofitApi apiService, final String tokenRecords){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originRequest = chain.request();

                String userAgent = System.getProperty("http.agent");

                if(!TextUtils.isEmpty(tokenRecords)){
                    AuthenticationInterceptor
                }

                Request request = originRequest.newBuilder()
                        .header("Content-Type","application/json")
                        .header("User-Agent",tokenRecords.getTokenType() + " "+
                                tokenRecords.getToken()+" "+ tokenRecords.getSecret())
                        .header("Authorization","")
                        .method(originRequest.method(),originRequest.body())
                        .build();

                return chain.proceed(request);
            }
        });
    }*/
}
