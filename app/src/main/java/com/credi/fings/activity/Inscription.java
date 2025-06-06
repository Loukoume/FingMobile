package com.credi.fings.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.credi.fings.publics.utils.Dialogue;
import com.google.android.material.button.MaterialButton;
import com.credi.fings.MainActivity;
import com.credi.fings.R;

public class Inscription extends AppCompatActivity {
    private ConstraintLayout layoutCreationCompte;
    private TextView tvTitle;
    ImageView cadena;
    private LinearLayout limage;
    private LinearLayout layoutPhone,lnouveau;
    private TextView tvPhoneTitle;
    private EditText etPhoneNumber;
    private EditText motDePasse;
    private EditText etPassWord;
    private EditText confirmeetPassWord;
    private TextView tvSeparator;
    private MaterialButton btnRegisterGoogle,save,ajouter;
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
        etPassWord = findViewById(R.id.etPassWord);
        lnouveau=findViewById(R.id.lnouveau);
        cadena=findViewById(R.id.cadena);
        confirmeetPassWord = findViewById(R.id.confirmeetPassWord);
        tvSeparator = findViewById(R.id.tvSeparator);
        btnRegisterGoogle = findViewById(R.id.btnRegisterGoogle);
        save=findViewById(R.id.saves);
        ajouter=findViewById(R.id.ajouter);
        connexion();
    }
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
                String mdp=motDePasse.getText().toString();
                String login=etPhoneNumber.getText().toString();
                Dialogue.neutreDialog(" mtp = "+mdp,"login = "+login,view.getContext()).show();
               // startActivity(new Intent(Inscription.this, MainActivity.class));
               // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

}