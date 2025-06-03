package com.credi.fings.publics.service;

// package : com.votreapp.network

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitClient {

    private static final String BASE_URL = "https://fingiciel.ngrok.io/";
    private static RetrofitClient instance;
    private ApiService fineractApi;

    private RetrofitClient(String username, String password) {
        // 1. Créer l’intercepteur pour le logging HTTP (optionnel mais conseillé en dev)
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        // 2. Créer l’intercepteur pour ajouter le header Basic Auth
        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // Génère "Basic base64(username:password)"
                String credential = Credentials.basic(username, password);
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder()
                        .header("Authorization", credential);
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        };

        // 3. Construire le client OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logging)                // pour afficher les requêtes/réponses en logcat
                .build();

        // 4. Construire Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fineractApi = retrofit.create(ApiService.class);
    }

    /**
     * Singleton pour récupérer l'instance de l'API
     *
     * @param username nom d'utilisateur Basic Auth (ex. "mifos")
     * @param password mot de passe Basic Auth (ex. "password")
     */
    public static RetrofitClient getInstance(String username, String password) {
        if (instance == null) {
            instance = new RetrofitClient(username, password);
        }
        return instance;
    }

    public ApiService getFineractApi() {
        return fineractApi;
    }
}

