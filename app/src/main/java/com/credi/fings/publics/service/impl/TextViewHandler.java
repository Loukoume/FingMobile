package com.credi.fings.publics.service.impl;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
public class TextViewHandler {

    private TextView textView;

    // Constructeur pour initialiser le TextView
    public TextViewHandler(TextView textView) {
        this.textView = textView;
    }

    // Interface fonctionnelle pour accepter une fonction en paramètre
    public interface AfterTextChangedAction {
        void execute();
    }

    // Méthode qui accepte une fonction en paramètre et l'exécute après le changement de texte
    public void setAfterTextChangedAction(AfterTextChangedAction action) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Aucune action ici
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Aucune action ici
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Exécuter l'action passée en paramètre après la modification du texte
                if (action != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            action.execute();
                        }
                    },100);

                }
            }
        });
    }
}

