package com.credi.fings.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.credi.fings.R;
import com.credi.fings.entity.User;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imageProfilePhoto,back;
    private TextView textUserName;
    private TextView    textUserEmail;
    private TextView    textUserPhone,tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageProfilePhoto = findViewById(R.id.imageProfilePhoto);
        textUserName      = findViewById(R.id.textUserName);
        textUserEmail     = findViewById(R.id.textUserEmail);
        textUserPhone     = findViewById(R.id.textUserPhone);
        tx=findViewById(R.id.tx_text);
        back=findViewById(R.id.back);
        tx.setText("Mon profile");
        // 2. Remplissage avec les données de l’utilisateur
        User currentUser = Inscription.user; // Votre méthode pour obtenir les données
        if (currentUser != null) {
            // Charger l’image (avec Glide/Picasso ou autre selon votre choix)
            // Exemple avec Glide :
            // Glide.with(this).load(currentUser.getPhotoUrl()).circleCrop().into(imageProfilePhoto);

            textUserName.setText(currentUser.getUsername());
            textUserEmail.setText(currentUser.getOfficeName());
            //textUserPhone.setText(currentUser.get());
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}