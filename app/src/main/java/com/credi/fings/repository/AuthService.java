package com.credi.fings.repository;

import com.credi.fings.entity.Client;
import com.credi.fings.entity.User;
import com.credi.fings.pojo.LoginRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {
    /**
     * Appel POST https://…/fineract-provider/api/v1/self/authentication
     * - Basic Auth dans l’en-tête "Authorization"
     * - TenantId dans l’en-tête "Fineract-Platform-TenantId"
     */
    @POST("fineract-provider/api/v1/self/authentication")
    Observable<User> authenticate(
            @Header("Authorization") String authorizationHeader,
            @Header("Fineract-Platform-TenantId") String tenantId,
            @Body LoginRequest loginBody
    );
}

