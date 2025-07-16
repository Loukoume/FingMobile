package com.credi.fing.publics.service.compteService;

import com.credi.fing.publics.Compte;
import com.credi.fing.publics.service.RetourData;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Loukoume on 28 oct. 2024 17:41:35.
 */

public interface CompteApiService {
    @POST("compte/login")
    Call<Compte> login(@Body Compte compte);

    @POST("compte/save")
    Call<Compte> addCompte(@Body Compte id);


    @POST("compte/update")
    Call<Compte> updateCompte(@Body Compte id);


    @GET("compte/all")
    Call<List<Compte>> allCompte();


    @POST("compte/delete")
    Call<Compte> deleteCompte(@Body Compte id);


    @Multipart
    @POST("compte/photoupload")
    Call<RetourData<String>> uploadCompteProfil(
            @Part MultipartBody.Part body,
            @Part("id") String id);


    @POST("compte/delete_cascade")
    Call<List<Compte>> deleteCascadeCompte(@Body Compte id);


    @POST("compte/find_by_id")
    Call<Compte> findByIdCompte(@Body Compte id);


    @POST("compte/compte_by_nom")
    Call<List<Compte>> compteByNom(@Body String nom);


    @POST("compte/compte_by_prenom")
    Call<List<Compte>> compteByPrenom(@Body String prenom);


    @POST("compte/compte_by_login")
    Call<List<Compte>> compteByLogin(@Body String login);


    @POST("compte/compte_by_email")
    Call<List<Compte>> compteByEmail(@Body String email);


    @POST("compte/compte_by_password")
    Call<List<Compte>> compteByPassword(@Body String password);


    @POST("compte/compte_by_telephone")
    Call<List<Compte>> compteByTelephone(@Body String telephone);


    @POST("compte/compte_by_profile")
    Call<List<Compte>> compteByProfile(@Body String profile);


    @POST("compte/id_media")
    Call<List<Compte>> idMedia(@Body Compte compte);


}
