package com.credi.fing.publics.service.impl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.credi.fing.R;

/**
 * Utilitaires d'habillage pour TextInputLayout + TextInputEditText.
 * - Hint & contour changent de couleur: vide vs texte, focus, erreur, désactivé.
 * - S'installe via setupMaterialInput(...) et écoute focus + text change.
 *
 * Utilisation simple:
 * UtilsInput.setupMaterialInput(root, ctx, required, null, null);
 *
 * Ou avec IDs spécifiques (ex textarea):
 * UtilsInput.setupMaterialInput(root, ctx, required, null, null, R.id.textInputLayout, R.id.editTextMessage);
 */
public final class UtilsInput {

    private UtilsInput() {}

    // ===== API publique =====

    /** Version IDs par défaut (R.id.textField / R.id.id) */
    public static void setupMaterialInput(View root, Context ctx, boolean required, String errorText, String initialValue) {
        setupMaterialInput(root, ctx, required, errorText, initialValue, R.id.textField, R.id.id);
    }

    /** Version avec IDs personnalisés */
    public static void setupMaterialInput(
            View root,
            Context ctx,
            boolean required,
            String errorText,
            String initialValue,
            @IdRes int tilId,
            @IdRes int editId
    ) {
        if (root == null || ctx == null) return;

        TextInputLayout til = root.findViewById(tilId);
        TextInputEditText et = root.findViewById(editId);
        if (til == null || et == null) return;

        // Appliquer une valeur initiale si fournie (utile pour forcer l'état "rempli")
        if (initialValue != null) et.setText(initialValue);

        // Palette par défaut (avec fallback si une couleur n'existe pas dans colors.xml)
        Palette p = Palette.from(ctx);

        // Installer les écouteurs
        installListeners(til, et, p, required);

        // Poser une éventuelle erreur initiale
        if (errorText != null && !errorText.trim().isEmpty()) {
            til.setError(errorText);
            til.setErrorEnabled(true);
        }

        // Premier rendu
        refreshState(til, et, p);
    }

    // ===== Logique d'état & rendu =====

    private static void installListeners(TextInputLayout til, TextInputEditText et, Palette p, boolean required) {
        // Sur changement de focus -> rafraîchit
        et.setOnFocusChangeListener((v, hasFocus) -> refreshState(til, et, p));

        // Sur saisie -> efface l'erreur requise et rafraîchit
        et.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                if (til.isErrorEnabled() && s != null && s.length() > 0) {
                    til.setError(null);
                    til.setErrorEnabled(false);
                }
                refreshState(til, et, p);
            }
        });

        // Si le champ est requis, on affiche une erreur quand il perd le focus et qu'il est vide
        if (required) {
            et.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    if (isEmpty(et)) {
                        til.setError("Champs obligatoire");
                        til.setErrorEnabled(true);
                    }
                }
                refreshState(til, et, p);
            });
        }
    }

    /** Calcule l'état et applique les couleurs hint + contour. */
    private static void refreshState(TextInputLayout til, EditText et, Palette p) {
        boolean hasError = til.isErrorEnabled() && til.getError() != null;
        boolean hasFocus = et.hasFocus();
        boolean disabled = !et.isEnabled();
        boolean filled = !isEmpty(et);

        if (hasError) {
            applyHintColor(til, p.hintError);
            applyStrokeColor(til, p.strokeError);
            return;
        }

        if (disabled) {
            applyHintColor(til, p.hintDisabled);
            applyStrokeColor(til, p.strokeDisabled);
            return;
        }

        if (hasFocus) {
            // Focus prioritaire : on force les couleurs de focus
            applyHintColor(til, p.hintFocused);
            applyStrokeColor(til, p.strokeFocused);
            return;
        }

        // Pas focus -> on distingue vide vs rempli
        if (filled) {
            applyHintColor(til, p.hintFilled);
            applyStrokeColor(til, p.strokeFilled);
        } else {
            applyHintColor(til, p.hintEmpty);
            applyStrokeColor(til, p.strokeEmpty);
        }
    }

    private static boolean isEmpty(EditText et) {
        CharSequence s = et.getText();
        return s == null || s.toString().trim().isEmpty();
    }

    // ===== Appliers (compatibles toutes versions Material) =====

    private static void applyHintColor(TextInputLayout til, @ColorInt int color) {
        ColorStateList csl = ColorStateList.valueOf(color);
        // Certaines versions utilisent defaultHintTextColor pour l'état "collapsed"
        til.setDefaultHintTextColor(csl);
        til.setHintTextColor(csl);
    }

    private static void applyStrokeColor(TextInputLayout til, @ColorInt int color) {
        try {
            // Mat components récents
            til.setBoxStrokeColor(color);
        } catch (Throwable ignore) {
            // au cas où, aucune autre API nécessaire : on laisse la couleur par défaut
        }
    }

    // ===== Palette =====

    private static final class Palette {
        // Hints
        @ColorInt int hintEmpty;
        @ColorInt int hintFilled;
        @ColorInt int hintFocused;
        @ColorInt int hintDisabled;
        @ColorInt int hintError;

        // Contours
        @ColorInt int strokeEmpty;
        @ColorInt int strokeFilled;
        @ColorInt int strokeFocused;
        @ColorInt int strokeDisabled;
        @ColorInt int strokeError;

        static Palette from(Context ctx) {
            Palette p = new Palette();

            // Fallbacks (Material light par défaut)
            @ColorInt int FALLBACK_PRIMARY   = parse("#3F51B5");
            @ColorInt int FALLBACK_ACCENT    = parse("#FF4081");
            @ColorInt int FALLBACK_ERROR     = parse("#B00020");
            @ColorInt int FALLBACK_GRAY_500  = parse("#9E9E9E");
            @ColorInt int FALLBACK_GRAY_300  = parse("#E0E0E0");

            // Récupérations sûres (si la res n'existe pas, on prend le fallback)
            @ColorInt int colorPrimary = colorOr(ctx, R.color.colorPrimary, FALLBACK_PRIMARY);
            @ColorInt int colorAccent  = colorOr(ctx, R.color.colorAccent,  FALLBACK_ACCENT);
            @ColorInt int colorError   = colorOr(ctx, R.color.red, FALLBACK_ERROR);
            @ColorInt int gray500      = colorOr(ctx, R.color.gray_500, FALLBACK_GRAY_500);
            @ColorInt int gray300      = colorOr(ctx, R.color.gray_300, FALLBACK_GRAY_300);

            // Règles:
            // - Vide: hint & stroke plus neutres (gris)
            // - Rempli: hint accentué, stroke plus affirmé (primary)
            // - Focus: couleurs fortes (primary)
            // - Erreur: rouge
            // - Désactivé: gris clair
            p.hintEmpty     = gray500;
            p.strokeEmpty   = gray300;

            p.hintFilled    = colorAccent;   // hint color quand il y a du texte
            p.strokeFilled  = colorPrimary;  // contour quand il y a du texte

            p.hintFocused   = colorPrimary;  // hint en focus
            p.strokeFocused = colorPrimary;  // contour en focus

            p.hintError     = colorError;
            p.strokeError   = colorError;

            p.hintDisabled  = adjustAlpha(gray500, 0.7f);
            p.strokeDisabled= gray300;

            return p;
        }
    }

    // ===== Helpers couleurs =====

    @ColorInt
    private static int colorOr(Context ctx, int resId, @ColorInt int fallback) {
        try {
            return ContextCompat.getColor(ctx, resId);
        } catch (Exception ignore) {
            return fallback;
        }
    }

    @ColorInt
    private static int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        return Color.argb(
                alpha,
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

    @ColorInt
    private static int parse(String hex) {
        try {
            return Color.parseColor(hex);
        } catch (Exception ignore) {
            return Color.BLACK;
        }
    }
}
