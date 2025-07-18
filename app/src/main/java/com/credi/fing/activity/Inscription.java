package com.credi.fing.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.credi.fing.entity.User;
import com.credi.fing.pojo.LoginRequest;
import com.credi.fing.publics.service.RetrofitClient;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.LesConnectes;
import com.credi.fing.publics.utils.MonFichier;
import com.credi.fing.repository.AuthService;
import com.credi.fing.utils.Json;
import com.google.android.material.button.MaterialButton;
import com.credi.fing.MainActivity;
import com.credi.fing.R;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

public class Inscription extends AppCompatActivity {
    private ConstraintLayout layoutCreationCompte;
    private TextView tvTitle;
    ImageView cadena;
    private LinearLayout limage;
    private LinearLayout layoutPhone,lnouveau;
    private TextView tvPhoneTitle;
    private EditText etPhoneNumber;
    private EditText motDePasse;
    private View view;
    private ProgressBar bp;
    private TextView tvSeparator;
    private MaterialButton btnRegisterGoogle,save,ajouter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_inscription);
        // Récupération des vues avec findViewById
        layoutCreationCompte = findViewById(R.id.layout_creation_compte);
        limage = findViewById(R.id.limage);
        layoutPhone = findViewById(R.id.layout_phone);
        tvPhoneTitle = findViewById(R.id.tvPhoneTitle);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        motDePasse = findViewById(R.id.mot_de_passe);
        view = findViewById(R.id.vide);
        lnouveau=findViewById(R.id.lnouveau);
        cadena=findViewById(R.id.cadena);
        bp = findViewById(R.id.pb);
        tvSeparator = findViewById(R.id.tvSeparator);
        btnRegisterGoogle = findViewById(R.id.btnRegisterGoogle);
        save=findViewById(R.id.saves);
        ajouter=findViewById(R.id.ajouter);
        context=this;

         checkBox=findViewById(R.id.checked_politique);
         connexion();

       // souiM();

        TextView tvAuth = findViewById(R.id.tvAuth);
        // Convertit le HTML en Spannable et rend les liens cliquables
        //tvAuth.setText(Html.fromHtml(getString(R.string.auth_text), Html.FROM_HTML_MODE_LEGACY));
        tvAuth.setMovementMethod(LinkMovementMethod.getInstance());
    }
    CheckBox checkBox;
    private void inscrition(){
         tvPhoneTitle.setText("Inscription");
        lnouveau.setVisibility(View.VISIBLE);
        motDePasse.setVisibility(View.GONE);
        cadena.setVisibility(View.GONE);
        save.setText("J'ai déjà un compte");
        ajouter.setText("Créer le compte");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connexion();
            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  startActivity(new Intent(Inscription.this, MainActivity.class));
            }
        });
    }
    private void connexion(){
        tvPhoneTitle.setText("Authentification");
        lnouveau.setVisibility(View.GONE);
        cadena.setVisibility(View.VISIBLE);
        motDePasse.setVisibility(View.VISIBLE);
        save.setText("Je n'ai pas encore de compte");
        ajouter.setText("S'authentifier");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inscrition();
            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    String mdp=motDePasse.getText().toString();
                    String login=etPhoneNumber.getText().toString();
                    if(mdp.equalsIgnoreCase("fingiciel")
                            &&login.equalsIgnoreCase("fingiciel")){
                        body = new LoginRequest("fingiciel", "fingiciel");
                        go((User) Ut.fromJs(Json.inscriptionUser,User.class),"fingiciel");
                    }else {
                        if(login.isEmpty()||mdp.isEmpty()){
                          // motDePasse.setError("Champ obligatoir");
                          // etPhoneNumber.setError("Champ obligatoir");
                            body = new LoginRequest("fingiciel", "fingiciel");
                            go((User) Ut.fromJs(Json.inscriptionUser,User.class),"fingiciel");
                        }else {
                            authenticateUser(login,mdp);
                        }
                    }
                }else {
                    Dialogue.neutreDialog("Veuillez accepter la politique de confidentialité","Information",context).show();
                }

               // Dialogue.neutreDialog(" mtp = "+mdp,"login = "+login,view.getContext()).show();

            }
        });
    }

    //https://fingiciel.ngrok.io/fineract-provider/api/v1/clients/8/accounts?&tenantIdentifier=default
    //https://fingiciel.ngrok.io/fineract-provider/api/v1/self/clients/1?&tenantIdentifier=default

    //https://fingiciel.ngrok.io/fineract-provider/api/v1/self/clients/1/accounts?&tenantIdentifier=default
//https://fingiciel.ngrok.io//fineract-provider/api/v1/self/authentication

    private void go(User user,String password){
        Inscription.user=user;
        System.out.println(" user => "+Ut.js(user));
        MonFichier.ecrire(context,"password",password);
        new LesConnectes().add(context, user);
        startActivity(new Intent(Inscription.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        hide();
    }

    private  AuthService authService;
    private final CompositeDisposable disposables = new CompositeDisposable();


    public static User user;
    public static LoginRequest body;
    public void authenticateUser(String username, String password) {
        show();
        // 1) Récupération du service Retrofit
        authService = RetrofitClient.getInstance().create(AuthService.class);

        // 2) Construction du corps JSON pour l’authentification
         body = new LoginRequest(username, password);

        // 3) Génération du header Basic Auth (optionnel si votre instance Fineract l'exige)
        String authorizationHeader = Credentials.basic(username, password);

        // 4) Tenant identifier requis par Fineract
        String tenantIdentifier = "default";

        // 5) Appel Retrofit mis à jour pour inclure le @Body
        Disposable d = authService.authenticate(
                        authorizationHeader,
                        tenantIdentifier,
                        body
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                             Inscription.user=user;
                             System.out.println(" user => "+Ut.js(user));
                             MonFichier.ecrire(context,"password",password);
                             new LesConnectes().add(context, user);
                             startActivity(new Intent(Inscription.this, MainActivity.class));
                             overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                             finish();
                             hide();
                        },
                        throwable -> {
                            hide();
                            Dialogue.neutreDialog(throwable.getMessage(),"Echèc d'authentifaction",context).show();
                            // Gestion de l’erreur
                            Log.e("AuthRepo", "Erreur d'auth : " + throwable.getMessage());
                        }
                );

        disposables.add(d);
    }


    public void clear() {
        disposables.clear();
    }

    private void show() {
       view.setVisibility(View.VISIBLE);
       bp.setVisibility(View.VISIBLE);
    }
    private void hide() {
        view.setVisibility(View.GONE);
        bp.setVisibility(View.GONE);
    }

    private void souiM() {
        final List<User> l = new LesConnectes().comptes(context);
        if (l.size() == 1) {
            Inscription.user = l.get(0);
            System.out.println(" =user=> "+Ut.js(Inscription.user));
            Dialogue.neutreDialog(Ut.js(Inscription.user),"",context).show();
            body = new LoginRequest(Inscription.user.getUsername(), MonFichier.lire(context,"password"));
            Intent intent = new Intent(context, MainActivity.class);
           // intent.putExtra("compte", user);
           // startActivity(intent);
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
           // finish();

        } else if (l.size() > 1) {

            String m[] = new String[l.size() + 1];
            for (int i = 0; i < l.size(); i++) {

            }
        } else {
            return;
        }
    }

}