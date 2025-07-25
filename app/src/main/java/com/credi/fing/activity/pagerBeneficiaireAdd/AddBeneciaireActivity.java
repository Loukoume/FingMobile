package com.credi.fing.activity.pagerBeneficiaireAdd;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.credi.fing.R;
import com.credi.fing.activity.pagerAdapter.SenderFragment;
import com.credi.fing.activity.pagerAdapter.TransferPagerAdapter;
import com.credi.fing.enums.TypeAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.List;

public class AddBeneciaireActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    TextView tx;
    private MaterialButton btnPrev, btnNext;
    private TransferPagerAdapter adapter;
    private TextView tvStepHeader;
    private final List<String> steps = Arrays.asList(
            "Infos générales", "Compte"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneciaire);
        viewPager = findViewById(R.id.view_pager);
        tvStepHeader = findViewById(R.id.tv_step_header);
        btnPrev   = findViewById(R.id.btn_prev);
        btnNext   = findViewById(R.id.btn_next);
        tx=findViewById(R.id.tx_text);

        adapter = new TransferPagerAdapter(this,2, TypeAdapter.BENEFICIAIRE);
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


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pos) {
                tvStepHeader.setText(steps.get(pos));
                btnPrev.setEnabled(pos > 0);
                btnNext.setText(pos < steps.size() - 1 ? "Suivant" : "Terminer");
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
}