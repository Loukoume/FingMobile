package com.credi.fing.repository;

import com.credi.fing.entity.User;
import com.credi.fing.pojo.LoginRequest;

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

