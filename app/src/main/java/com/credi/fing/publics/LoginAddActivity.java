package com.credi.fing.publics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.credi.fing.R;
import com.credi.fing.publics.service.ApiClient;
import com.credi.fing.publics.service.compteService.CompteApiService;
import com.credi.fing.publics.utils.Dialogue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginAddActivity extends AppCompatActivity {
    static Context context;
    TextView tx_text;
    EditText password;
    String password_str;
    CoordinatorLayout cord1;
    AppBarLayout appbar;
    EditText profile;
    String profile_str;
    FloatingActionButton save;
    Toolbar toolbar_personnalise;
    EditText telephone;
    String telephone_str;
    EditText login;
    String login_str;
    EditText nom;
    String nom_str;
    EditText prenom;
    String prenom_str;
    EditText email;
    String email_str;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_compte);
        context = this;
        tx_text = findViewById(R.id.tx_text);
        password = findViewById(R.id.password);
        cord1 = findViewById(R.id.cord1);
        appbar = findViewById(R.id.appbar);
        profile = findViewById(R.id.profile);
        save = findViewById(R.id.save);
        toolbar_personnalise = findViewById(R.id.toolbar_personnalise);
        telephone = findViewById(R.id.telephone);
        login = findViewById(R.id.login);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        tx_text.setText("LoginAddActivity");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity. compte = textValue();
                if (LoginActivity. compte == null) {

                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog(" Veuillez renseigner correctement le formulaire", "Alerte", context).show();
                            });
                        }
                    }

                } else {
                    addCompte(LoginActivity.compte);
                }
            }
        });
        if (getIntent().hasExtra("update")) {
            password.setText(LoginActivity. compte.getPassword());
            if(LoginActivity. compte.getProfile()!=null)
               profile.setText(LoginActivity. compte.getProfile().getLibelle());
            telephone.setText(LoginActivity. compte.getTelephone());
            login.setText(LoginActivity. compte.getLogin());
            nom.setText(LoginActivity. compte.getNom());
            prenom.setText(LoginActivity. compte.getPrenom());
            email.setText(LoginActivity. compte.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Compte textValue() {
        Compte login_vu = new Compte();
        password_str = password.getText().toString();
        if (password_str.isEmpty()) {
            password.setError("Champs obligatoire");
            return null;
        } else {
            login_vu.setPassword(password_str);
        }
        profile_str = profile.getText().toString();
        if (profile_str.isEmpty()) {
            profile.setError("Champs obligatoire");
            return null;
        } else {
           // login_vu.setProfile(profile_str);
        }
        telephone_str = telephone.getText().toString();
        if (telephone_str.isEmpty()) {
            telephone.setError("Champs obligatoire");
            return null;
        } else {
            login_vu.setTelephone(telephone_str);
        }
        login_str = login.getText().toString();
        if (login_str.isEmpty()) {
            login.setError("Champs obligatoire");
            return null;
        } else {
            login_vu.setLogin(login_str);
        }
        nom_str = nom.getText().toString();
        if (nom_str.isEmpty()) {
            nom.setError("Champs obligatoire");
            return null;
        } else {
            login_vu.setNom(nom_str);
        }
        prenom_str = prenom.getText().toString();
        if (prenom_str.isEmpty()) {
            prenom.setError("Champs obligatoire");
            return null;
        } else {
            login_vu.setPrenom(prenom_str);
        }
        email_str = email.getText().toString();
        if (email_str.isEmpty()) {
            email.setError("Champs obligatoire");
            return null;
        } else {
            login_vu.setEmail(email_str);
        }
        if (LoginActivity. compte != null) {
            login_vu.setIdServeur(LoginActivity. compte.getIdServeur());
            login_vu.setIdLocal(LoginActivity. compte.getIdLocal());
        }
        return login_vu;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    ProgressBar pb;
    public void addCompte(final Compte compte) {
        if(pb==null)pb=findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        CompteApiService apiService = ApiClient.getApiClient().create(CompteApiService.class);
        Call<Compte> call = apiService.addCompte(compte);
        call.enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                pb.setVisibility(View.GONE);
                if(response.body()!=null){
                    if(response.body()!=null){
                        finish();
                    }else {
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            if (!activity.isFinishing() && !activity.isDestroyed()) {
                                activity.runOnUiThread(() -> {
                                    Dialogue.neutreDialog("Erreur technique","Alerte", context).show();
                                });
                            }
                        }

                    }

                }else {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        if (!activity.isFinishing() && !activity.isDestroyed()) {
                            activity.runOnUiThread(() -> {
                                Dialogue.neutreDialog("Problème technique","Alerte", context).show();
                            });
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                pb.setVisibility(View.GONE);
                String st=t+"";
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        activity.runOnUiThread(() -> {
                            Dialogue.neutreDialog(t+"","Alerte", context).show();
                        });
                    }
                }

            }
        });
    }}


