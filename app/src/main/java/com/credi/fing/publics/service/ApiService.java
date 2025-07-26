package com.credi.fing.publics.service;

import com.credi.fing.entity.Beneficiary;
import com.credi.fing.entity.BeneficiaryTemplate;
import com.credi.fing.entity.Client;
import com.credi.fing.pojo.LoanPojo;
import com.credi.fing.pojo.LoanProductResponse;
import com.credi.fing.publics.UploadFileResponse;

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
    //fineract-provider/api/v1/self/clients/1?&tenantIdentifier=default
    @GET("fineract-provider/api/v1/self/clients/{id}")
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
    @GET("fineract-provider/api/v1/self/clients/{id}/accounts")
    Call<Client> getClientAccountsById(
            @Header("Authorization") String authHeader,
            @Path("id") long clientId,
            @Query("tenantIdentifier") String tenantIdentifier
    );
    /**
     * Récupère la liste des prêts et comptes d’épargne pour un client donné.
     * Ex. GET /fineract-provider/api/v1/clients/{id}/accounts?tenantIdentifier=default
     *
     * @param authHeader       header "Authorization: Basic <tokenBase64>"
     * @param templateType         l’ID du client (par ex. 8)
     * @param tenantIdentifier ("default" par défaut)
     * @return une réponse sérialisée dans ClientAccountsResponse
     */
    @GET("fineract-provider/api/v1/self/loans/template")
    Call<LoanProductResponse> getTemplatePret(
            @Header("Authorization") String authHeader,
            @Query("templateType") String templateType,
            @Query("tenantIdentifier") String tenantIdentifier
    );
    @POST("fineract-provider/api/v1/self/loans")
    Call<Object> saveLoan(
            @Header("Authorization") String authHeader,
            @Body LoanPojo loanAccount,
            @Query("tenantIdentifier") String tenantIdentifier
    );
    //fineract-provider/api/v1/self/beneficiaries/tpt?&tenantIdentifier=default
    @POST("fineract-provider/api/v1/self/beneficiaries/tpt")
    Call<Object> saveBeneF(
            @Header("Authorization") String authHeader,
            @Body Beneficiary loanAccount,
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
    Call<Client> authenticate(
            @Query("tenantIdentifier") String tenantIdentifier
    );

    /**
     * GET /fineract-provider/api/v1/self/beneficiaries/tpt
     * - Authorization: Basic …
     * - Fineract-Platform-TenantId: default
     */
    @GET("fineract-provider/api/v1/self/beneficiaries/tpt")
    Call<List<Beneficiary>> getBeneficiaries(
            @Header("Authorization") String authorization,
            @Query("tenantIdentifier") String tenantIdentifier
    );

    @GET("fineract-provider/api/v1/self/beneficiaries/tpt")
    Call<List<Beneficiary>> getTemplate(
            @Header("Authorization") String authorization,
            @Query("tenantIdentifier") String tenantIdentifier
    );

    /**
     * GET /fineract-provider/api/v1/self/beneficiaries/tpt/template
     * - Authorization: Basic …
     * - tenantIdentifier en query
     */
    @GET("fineract-provider/api/v1/self/beneficiaries/tpt/template")
    Call<BeneficiaryTemplate> getBeneficiariesTemplate(
            @Header("Authorization") String authorization,
            @Query("tenantIdentifier") String tenantIdentifier
    );

    //https://fingiciel.ngrok.io/fineract-provider/api/v1/self/beneficiaries/tpt/template?&tenantIdentifier=default

}
