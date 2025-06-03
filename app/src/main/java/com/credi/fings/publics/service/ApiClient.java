package com.credi.fings.publics.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.credi.fings.publics.utils.MonFichier;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Loukoume on 26 août 2023 21:29:52
 */
//http://dff-itc.ovh/CashAp/Operations/

public class ApiClient {
    public static String BASE_URL = "https://fingiciel.ngrok.io/", hest = "http://192.168.0.112:8081/",
            ipNum2 = "http://192.168.79.1:8081/", ip2 = "http://192.168.137.1:8081/", local = "http://192.168.52.1:8081/",
            BASE_URL_LOCAL = "http://192.168.223.1:8081/", ip = "http://10.10.19.225:8081/",
            BASE_URL_PROD = BASE_URL;

    public static Retrofit retrofit = null, retrofit2 = null, retrofit_dat = null;
    public static Object createInstance(Class<?> t) {
        try {
            return t.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Gson createGsonForClass(Class<?> clazz) {
        GsonBuilder builder = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT);
                builder.registerTypeAdapter(clazz, createInstance(clazz));

        return builder.create();
    }

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(Number.class, new IntegerTypeAdapter())
            .create();
    public static Gson gson2 = new GsonBuilder()
            .setLenient()
            // Enregistrer le désérialiseur personnalisé pour le type Date
            .registerTypeAdapter(Date.class, new MultiDateDeserializer())
            // Exemple d'enregistrement d'un adaptateur personnalisé pour Number
            .registerTypeAdapter(Number.class, new IntegerTypeAdapter())
            .create();
    static OkHttpClient client = new OkHttpClient();

    public static Retrofit getApiClient() {
        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder().baseUrl(BASE_URL_PROD).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit2;
    }




    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    static Retrofit retrofit3;

    public static Retrofit getApiClient3() {
        if (retrofit3 == null) {
            httpClient.connectTimeout(5000, TimeUnit.SECONDS); // Temps maximal pour établir une connexion
            httpClient.readTimeout(5000, TimeUnit.SECONDS);    // Temps maximal pour lire les données
            httpClient.writeTimeout(5000, TimeUnit.SECONDS);   // Temps maximal pour écrire les données

            retrofit3 = new Retrofit.Builder()
                    .baseUrl(BASE_URL_PROD)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit3;
    }

    public static String urlFile(String url){
        int i=url.indexOf("/downloadFile");
        if(i!=-1){
            return BASE_URL_PROD+url.substring(i);
        }
        i=url.lastIndexOf("/");
        return BASE_URL_PROD+url.substring(i);
    }
    public static final String SERVER_FILE_NAME="SERVER_FILE_NAME";
    public static void  getServer(Context context){
        String js= MonFichier.lire(context,SERVER_FILE_NAME);
        if(!js.isEmpty()&&!js.contains("null")){
            BASE_URL_PROD=js;
            retrofit2 = new Retrofit.Builder().baseUrl(BASE_URL_PROD).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
    }
}