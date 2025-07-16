package com.credi.fing.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.credi.fing.R;

public class Beneficiaire extends AppCompatActivity {

    LinearLayout nodata;
    TextView text,tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_beneficiaire);
        nodata=findViewById(R.id.nodata);
        nodata.setVisibility(VISIBLE);
        text=findViewById(R.id.text);
        tx=findViewById(R.id.tx_text);
        ImageView back=findViewById(R.id.back);
        ImageView mort=findViewById(R.id.ic_mort);
        mort.setVisibility(GONE);
        String extra=getIntent().getStringExtra("titre");
        tx.setText(extra.toUpperCase());
        text.setText(extra+" s'affichent ici");

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