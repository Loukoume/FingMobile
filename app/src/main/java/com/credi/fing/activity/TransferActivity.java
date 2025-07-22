package com.credi.fing.activity;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.baoyachi.stepview.VerticalStepView;

import com.credi.fing.R;
import com.credi.fing.activity.pagerAdapter.SenderFragment;
import com.credi.fing.activity.pagerAdapter.TransferPagerAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class TransferActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    ImageView mort;
    TextView tx;
    private MaterialButton btnPrev, btnNext;
    private TransferPagerAdapter adapter;
    private TextView tvStepHeader,numero_etape,un_sur_total;
    private final List<String> steps = Arrays.asList(
            "Émetteur", "Bénéficiaire", "Montant"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        viewPager = findViewById(R.id.view_pager);
        tvStepHeader = findViewById(R.id.tv_step_header);
        btnPrev   = findViewById(R.id.btn_prev);
        btnNext   = findViewById(R.id.btn_next);
        tx=findViewById(R.id.tx_text);
        //back=findViewById(R.id.back);
       // mort=findViewById(R.id.ic_mort);
        //numero_etape=findViewById(R.id.numero_etape);
        //un_sur_total=findViewById(R.id.un_sur_total);

        tx.setText("Transfert d'argent".toUpperCase());
       /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });*/
        //mort.setVisibility(GONE);
        // 1) Pager + Adapter
        adapter = new TransferPagerAdapter(this);
        viewPager.setAdapter(adapter);
//        configureStepView();


        // 3) Navigation des boutons
        btnPrev.setOnClickListener(v -> {
            int p = viewPager.getCurrentItem();
            if (p > 0) viewPager.setCurrentItem(p - 1, true);
        });
        btnNext.setOnClickListener(v -> {
            int p = viewPager.getCurrentItem();
            if (p < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(p + 1, true);
            } else {
                submitAllData();
            }
        });

        // 4) Mise à jour des boutons selon la page
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pos) {
                tvStepHeader.setText(steps.get(pos));
                btnPrev.setEnabled(pos > 0);
                btnNext.setText(pos < steps.size() - 1 ? "Suivant" : "Terminer");
                //numero_etape.setText((pos+1)+"");
                //un_sur_total.setText((pos+1)+"/"+steps.size());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    private void submitAllData() {
        // Exemple : récupération des fragments
        SenderFragment sf = (SenderFragment)getSupportFragmentManager()
                .findFragmentByTag("f" + 0);
        // ... ou mieux : partager un ViewModel pour collecter les saisies
        Toast.makeText(this, "Transfert soumis !", Toast.LENGTH_LONG).show();
    }

    private void submitTransfer() {
        // Récupérer et valider les données de chaque EditText
        String senderName    = ((EditText)findViewById(R.id.et_sender_name)).getText().toString();
        String senderAccount = ((EditText)findViewById(R.id.et_sender_account)).getText().toString();
        String benName       = ((EditText)findViewById(R.id.et_beneficiary_name)).getText().toString();
        String benAccount    = ((EditText)findViewById(R.id.et_beneficiary_account)).getText().toString();
        String amount        = ((EditText)findViewById(R.id.et_amount)).getText().toString();
        String note          = ((EditText)findViewById(R.id.et_note)).getText().toString();
        // TODO : appel API ici
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        //.makeText(this, "Transfert soumis !", Toast.LENGTH_LONG).show();
    }
}