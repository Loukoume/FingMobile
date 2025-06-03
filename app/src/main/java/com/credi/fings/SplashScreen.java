package com.credi.fings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.credi.fings.activity.Inscription;
import com.credi.fings.utils.Anim;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreen extends AppCompatActivity {
    TextView m,o,b,is,l,e;
    CircleImageView logo;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        m=findViewById(R.id.m);
        o=findViewById(R.id.o);
        b=findViewById(R.id.b);
        is=findViewById(R.id.is);
        l=findViewById(R.id.l);
        e=findViewById(R.id.e);
        logo=findViewById(R.id.logo);
        context=this;
        logo.setAnimation(Anim.getAnimeBalancoir(context));
        animer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // depuis ta première activité
                Intent intent = new Intent(context, Inscription.class);
                startActivity(intent);

               // Appliquer l'effet fondu
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish();
            }
        }, 2000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        animer();
    }

    private void animer(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                m.setVisibility(View.VISIBLE);
                m.setAnimation(Anim.getAnimeGD(context));
            }
        }, 100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                o.setVisibility(View.VISIBLE);
                o.setAnimation(Anim.getAnimeHB(context));
            }
        }, 200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setVisibility(View.VISIBLE);
                b.setAnimation(Anim.getAnimeBH(context));
            }
        }, 300);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                is.setVisibility(View.VISIBLE);
                is.setAnimation(Anim.getAnimeDG(context));
            }
        }, 100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                l.setVisibility(View.VISIBLE);
                l.setAnimation(Anim.getAnimeHB(context));
            }
        }, 200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                e.setVisibility(View.VISIBLE);
                e.setAnimation(Anim.getAnimeBH(context));
            }
        }, 300);
    }
}