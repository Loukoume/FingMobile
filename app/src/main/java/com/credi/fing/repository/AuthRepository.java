package com.credi.fing.repository;

import android.util.Log;

import com.credi.fing.publics.service.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.Credentials;

public class AuthRepository {
    private final AuthService authService;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public AuthRepository() {
        authService = RetrofitClient.getInstance().create(AuthService.class);
    }

    public void authenticateUser() {
        // Exemple avec tenantIdentifier en Query (voir section 2.1)
        String tenantIdentifier = "default";
        String authorizationHeader = Credentials.basic("mifos", "password");

        Disposable d = authService.authenticate(authorizationHeader, tenantIdentifier,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            // Succès : on reçoit un User désérialisé depuis JSON
                            // Par exemple, user.getUsername(), etc.
                            Log.d("AuthRepo", "Connecté en tant que: " + user);
                        },
                        throwable -> {
                            // Gestion de l’erreur
                            Log.e("AuthRepo", "Erreur d'auth: " + throwable.getMessage());
                        }
                );
        disposables.add(d);
    }

    public void clear() {
        disposables.clear();
    }
}

