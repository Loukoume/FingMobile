package com.credi.fing.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.baoyachi.stepview.VerticalStepView;

import com.credi.fing.R;

import java.util.Arrays;
import java.util.List;

public class TransferActivity extends AppCompatActivity {

    private VerticalStepView stepView;
    private final List<String> steps = Arrays.asList(
            "Émetteur", "Bénéficiaire", "Montant", "Observation"
    );
    private int currentStep = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        stepView = findViewById(R.id.step_view);
        configureStepView();

        findViewById(R.id.btn_next).setOnClickListener(v -> {
            if (currentStep < steps.size() - 1) {
                currentStep++;
                configureStepView();
            }
        });
        findViewById(R.id.btn_prev).setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--;
                configureStepView();
            }
        });
    }

    private void configureStepView() {
        stepView
                .setStepsViewIndicatorComplectingPosition(currentStep)    // position courante
                .reverseDraw(false)                                        // top→bottom
                .setStepViewTexts(steps)                                   // libellés

                // Espacement si besoin
                .setLinePaddingProportion(0.85f)

                // Couleurs de ligne
                .setStepsViewIndicatorUnCompletedLineColor(android.R.color.black)
                .setStepsViewIndicatorCompletedLineColor(R.color.purple_700)

                // Couleurs de texte
                .setStepViewUnComplectedTextColor(android.R.color.black)
                .setStepViewComplectedTextColor(R.color.purple_700)

                // Icônes (optionnel)
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.baseline_done_all_24))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.aide_24));
    }

}