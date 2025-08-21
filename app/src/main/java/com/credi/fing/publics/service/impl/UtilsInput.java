package com.credi.fing.publics.service.impl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.credi.fing.R;

public final class UtilsInput {

    private UtilsInput() {}

    /**
     * Applique les couleurs/états dynamiques à un champ MaterialTextInput
     * standard dont les IDs sont textField (TextInputLayout) et id (TextInputEditText).
     */
    public static void setupMaterialInput(View rootView,
                                          Context context,
                                          boolean required,
                                          @Nullable String initialErr,
                                          @Nullable String helper) {

        final TextInputLayout til = rootView.findViewById(R.id.textField);
        final TextInputEditText et = rootView.findViewById(R.id.id);

        if (til == null || et == null) return;

        applyBehavior(til, et, context, required, initialErr, helper);
    }

    /**
     * Variante lorsque les IDs diffèrent (ex: textInputLayout / editTextMessage).
     */
    public static void setupMaterialInput(View rootView,
                                          Context context,
                                          boolean required,
                                          @Nullable String initialErr,
                                          @Nullable String helper,
                                          int tilId,
                                          int editId) {

        View tilV = rootView.findViewById(tilId);
        View etV  = rootView.findViewById(editId);
        if (!(tilV instanceof TextInputLayout) || !(etV instanceof TextInputEditText)) return;

        final TextInputLayout til = (TextInputLayout) tilV;
        final TextInputEditText et = (TextInputEditText) etV;

        applyBehavior(til, et, context, required, initialErr, helper);
    }

    // ---------- Impl commune ----------

    private static void applyBehavior(TextInputLayout til,
                                      TextInputEditText et,
                                      Context context,
                                      boolean required,
                                      @Nullable String initialErr,
                                      @Nullable String helper) {

        final int cPrimary = safeColor(context, R.color.colorPrimary);
        final int cAccent  = safeColor(context, R.color.colorAccent);
        final int cNeutral = safeColor(context, R.color.gray_500, 0xFF9E9E9E); // fallback gris
        final int cSuccess = safeColor(context, R.color.teal_700, 0xFF018786);
        final int cError   = safeColor(context, R.color.rouge, 0xFFB00020);

        til.setHelperTextEnabled(helper != null);
        til.setHelperText(helper);

        // Icône clear quand du texte
        til.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);

        if (!TextUtils.isEmpty(initialErr)) {
            til.setError(initialErr);
        }

        final ColorStateList hintStates = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_focused},
                        new int[]{}
                },
                new int[]{
                        cNeutral,
                        cAccent,
                        cNeutral
                }
        );
        til.setHintTextColor(hintStates);
        til.setDefaultHintTextColor(hintStates);

        try { til.setBoxStrokeErrorColor(ColorStateList.valueOf(cError)); } catch (Throwable ignored) {}

        et.setOnFocusChangeListener((v, hasFocus) ->
                updateUi(til, et, required, cPrimary, cAccent, cNeutral, cSuccess, cError, hasFocus));

        et.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) til.setError(null); // clear error dès saisie
                updateUi(til, et, required, cPrimary, cAccent, cNeutral, cSuccess, cError, et.hasFocus());
            }
        });

        // Mise à jour initiale
        updateUi(til, et, required, cPrimary, cAccent, cNeutral, cSuccess, cError, false);
    }

    private static void updateUi(TextInputLayout til,
                                 TextInputEditText et,
                                 boolean required,
                                 int cPrimary,
                                 int cAccent,
                                 int cNeutral,
                                 int cSuccess,
                                 int cError,
                                 boolean hasFocus) {

        final boolean hasText = !TextUtils.isEmpty(et.getText());
        final boolean hasError = !TextUtils.isEmpty(til.getError());

        if (required && !hasFocus && !hasText) {
            til.setError("Champs obligatoire");
        }

        if (hasError) {
            til.setHintTextColor(ColorStateList.valueOf(cError));
            safeSetStroke(til, cError);
        } else if (hasFocus) {
            til.setHintTextColor(ColorStateList.valueOf(cAccent));
            safeSetStroke(til, cAccent);
        } else if (hasText) {
            til.setHintTextColor(ColorStateList.valueOf(cSuccess));
            safeSetStroke(til, cSuccess);
        } else {
            til.setHintTextColor(ColorStateList.valueOf(cNeutral));
            safeSetStroke(til, cNeutral);
        }

        til.setEndIconVisible(hasText);
        til.setEndIconActivated(hasText);
    }

    private static void safeSetStroke(TextInputLayout til, int color) {
        try { til.setBoxStrokeColor(color); } catch (Throwable ignored) { }
    }

    private static int safeColor(Context ctx, int resId) {
        return ContextCompat.getColor(ctx, resId);
    }

    private static int safeColor(Context ctx, int resId, int fallback) {
        try { return ContextCompat.getColor(ctx, resId); } catch (Throwable t) { return fallback; }
    }
}
