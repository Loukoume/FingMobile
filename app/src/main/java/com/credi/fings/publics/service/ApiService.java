package com.credi.fings.publics.service;

import android.database.Observable;

import com.credi.fings.entity.Client;
import com.credi.fings.entity.LoanAccount;
import com.credi.fings.publics.UploadFileResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST
    Call<Object> postData(@Url String url, @Body Object body);

    @GET
    Call<Object> getData(@Url String url);
    @POST
    Call<List<Object>> postDataList(@Url String url, @Body Object body);


    /**
     * Récupère les détails d'un client par son ID.
     * Ex. GET /fineract-provider/api/v1/clients/{id}?tenantIdentifier=default
     *
     * @param authHeader       header "Authorization: Basic <tokenBase64>"
     * @param clientId         l'ID du client (ici 8)
     * @param tenantIdentifier ("default" par défaut)
     */
    @GET("fineract-provider/api/v1/clients/{id}")
    Call<Client> getClientById(
            @Header("Authorization") String authHeader,
            @Path("id") long clientId,
            @Query("tenantIdentifier") String tenantIdentifier
    );

    /**
     * Récupère la liste des prêts et comptes d’épargne pour un client donné.
     * Ex. GET /fineract-provider/api/v1/clients/{id}/accounts?tenantIdentifier=default
     *
     * @param authHeader       header "Authorization: Basic <tokenBase64>"
     * @param clientId         l’ID du client (par ex. 8)
     * @param tenantIdentifier ("default" par défaut)
     * @return une réponse sérialisée dans ClientAccountsResponse
     */
    @GET("fineract-provider/api/v1/clients/{id}/accounts")
    Call<Client> getClientAccountsById(
            @Header("Authorization") String authHeader,
            @Path("id") long clientId,
            @Query("tenantIdentifier") String tenantIdentifier
    );
    @GET
    Call<List<Object>> getDataList(@Url String url);
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
    @POST
    Call<Object> postListeData(@Url String url, @Body List<Object> body);

    @Multipart
    @POST("files/media")
    Call<UploadFileResponse> uploadCompteProfil2(
            @Part MultipartBody.Part body,
            @Part("id") RequestBody id,
            @Part("tables") RequestBody table,
            @Part("designation") RequestBody designation);
    @Multipart
    @POST
    Call<UploadFileResponse> uploadCompteProfil(
            @Url String url,
            @Part MultipartBody.Part body,
            @PartMap Map<String, RequestBody> designations
    );

    @POST("fineract-provider/api/v1/self/authentication")
    Observable<Client> authenticate(
            @Query("username") String username,
            @Query("password") String password
    );

}
