package com.credi.fing.publics.service;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitClient {

    private static final String BASE_URL = "https://fingiciel.ngrok.io/";
    private static RetrofitClient instance;
    private ApiService fineractApi;

    private RetrofitClient(String username, String password) {
        // 1. Logging HTTP (optionnel)
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 2. Intercepteur Basic Auth
        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String credential = Credentials.basic(username, password);
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder()
                        .header("Authorization", credential);
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        };

        // 3. Construction du client OkHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)
                .build();

        // 4. Construction de Retrofit avec RxJava2Adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fineractApi = retrofit.create(ApiService.class);
    }

    /**
     * Retourne l’instance unique de RetrofitClient. Si elle n’existe pas, on la crée avec les identifiants passés.
     */
    public static RetrofitClient getInstance(String username, String password) {
       // if (instance == null) {
            instance = new RetrofitClient(username, password);
       // }
        return instance;
    }

    /**
     * Réinitialise l’instance pour pouvoir créer un nouveau RetrofitClient
     * avec d’autres credentials (username/password).
     */
    public static void clearInstance() {
        instance = null;
    }

    public ApiService getFineractApi() {
        return fineractApi;
    }

    private static Retrofit retrofitInstance;

    public static Retrofit getInstance() {
        if (retrofitInstance == null) {
            // 1) Logging Interceptor (optionnel, mais utile en dev)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 2) OkHttpClient (on peut ajouter d'autres Interceptors si besoin)
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            // 3) Construction de Retrofit
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }
}
