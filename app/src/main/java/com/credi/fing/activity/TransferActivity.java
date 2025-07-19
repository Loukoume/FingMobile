package com.credi.fing.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.baoyachi.stepview.VerticalStepView;

import com.credi.fing.R;

import java.util.Arrays;
import java.util.List;

public class TransferActivity extends AppCompatActivity {

    private VerticalStepView stepView;
    private View[] formSteps;
    private final List<String> steps = Arrays.asList(
            "Émetteur", "Bénéficiaire", "Montant", "Observation"
    );
    private int currentStep = 0;
    private Button btnNext, btnPrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        stepView = findViewById(R.id.step_view);

        formSteps = new View[]{
                findViewById(R.id.form_step_0),
                findViewById(R.id.form_step_1),
                findViewById(R.id.form_step_2),
                findViewById(R.id.form_step_3)
        };
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);
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

        /*btnPrev.setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--;
                refreshUI();
            }
        });
        btnNext.setOnClickListener(v -> {
            if (currentStep < steps.size() - 1) {
                currentStep++;
                refreshUI();
            } else {
                submitTransfer();
            }
        });*/

       // refreshUI();
    }

    private void configureStepView() {
        stepView
                .setStepsViewIndicatorComplectingPosition(currentStep)    // position courante
                .reverseDraw(false)                                        // top→bottom
                .setStepViewTexts(steps)                                   // libellés

                // Espacement si besoin
                .setLinePaddingProportion(0.85f)

                // Couleurs de ligne
                .setStepsViewIndicatorUnCompletedLineColor(R.color.black)
                .setStepsViewIndicatorCompletedLineColor(R.color.purple_700)

                // Couleurs de texte
                .setStepViewUnComplectedTextColor(R.color.black)
                .setStepViewComplectedTextColor(R.color.purple_700)

                // Icônes (optionnel)
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.ok_check))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.plus_50));
    }

    private void refreshUI() {
        // StepView
        stepView.setStepsViewIndicatorComplectingPosition(currentStep);
        // Formulaire
        for (int i = 0; i < formSteps.length; i++) {
            formSteps[i].setVisibility(i == currentStep ? View.VISIBLE : View.GONE);
        }
        // Texte du bouton Next
        btnNext.setText(currentStep < steps.size() - 1 ? "Suivant" : "Terminer");
        btnPrev.setEnabled(currentStep > 0);
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