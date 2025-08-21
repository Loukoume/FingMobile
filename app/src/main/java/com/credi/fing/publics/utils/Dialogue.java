package com.credi.fing.publics.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;

import com.credi.fing.R;
import com.credi.fing.pojo.err.ApiErrorResponse;
import com.credi.fing.pojo.err.ErrorUtils;
import com.credi.fing.publics.service.DialogAction;
import com.credi.fing.publics.service.impl.Ut;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


/**
 * Created by Loukoume on 05/02/2018.
 */

public class Dialogue {


    public static AlertDialog neutreDialog(String titre, String T, final Context context, DialogAction dialogInterface, Object object) {
        // Création d'un boite de dialogue
        // if(titre.contains("java.net")||titre.contains("javax.net"))titre="Vérifier votre connexion internet et réessayer";
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(false);
        builder.setMessage(titre);
        builder.setTitle(T);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogInterface != null) {
                            dialogInterface.execut(object, context);
                        }
                        dialog.cancel();
                    }
                });

        dialog = builder.create();
        return dialog;
    }


    public static Dialog neutreDialogF(final String titre, String T, final Context context, final Activity activity) {
        // Création d'un boite de dialogue
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(T);
        builder.setCancelable(false);
        builder.setMessage(titre);

        builder.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        activity.finish();
                    }
                });

        dialog = builder.create();
        return dialog;
    }


    public static void showDialog(Context context, AlertDialog alertDialog) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                activity.runOnUiThread(alertDialog::show);
            }
        }

    }


    public enum Type {INFO, WARNING, ERROR, SUCCESS}


    // Détection simple réseau
    private static boolean looksLikeNetworkIssue(String t, String m) {
        String s = (t + " " + m).toLowerCase();
        return s.contains("unable to resolve host") || s.contains("java.net") || s.contains("javax.net") || s.contains("unknownhost") || s.contains("timeout");
    }

    // imports conseillés
// import androidx.appcompat.app.AlertDialog;
// import com.google.android.material.dialog.MaterialAlertDialogBuilder;

    public static AlertDialog show(
            @NonNull Context context,
            @NonNull Type type,
            @Nullable String title,
            @NonNull String message,
            @Nullable Runnable onOk
    ) {
        // 1) Choix icône / couleur / titre par défaut
        @DrawableRes int iconRes;
        @ColorInt int accent;
        String defaultTitle;

        if (looksLikeNetworkIssue(title == null ? "" : title, message)) {
            title = "Problème de connexion";
            message = "Veuillez vérifier votre connexion Internet et réessayer.";
            type = Type.WARNING;
        }

        switch (type) {
            case WARNING:
                iconRes = R.drawable.ic_round_warning_24;
                accent = ContextCompat.getColor(context, R.color.warningColor);
                defaultTitle = "Avertissement";
                break;
            case ERROR:
                iconRes = R.drawable.ic_round_error_24;
                accent = ContextCompat.getColor(context, R.color.errorColor);
                defaultTitle = "Erreur";
                break;
            case SUCCESS:
                iconRes = R.drawable.ic_round_check_circle_24;
                accent = ContextCompat.getColor(context, R.color.successColor);
                defaultTitle = "Succès";
                break;
            default:
                iconRes = R.drawable.ic_round_info_24;
                accent = ContextCompat.getColor(context, R.color.infoColor);
                defaultTitle = "Information";
        }

        // 2) Inflate du layout custom qui contient le titre et le message
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_alert, null, false);

        ImageView ivIcon = view.findViewById(R.id.ivIcon);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvMessage = view.findViewById(R.id.tvMessage);
        FrameLayout iconBg = view.findViewById(R.id.flIconContainer);

        ivIcon.setImageResource(iconRes);
        ivIcon.setImageTintList(ColorStateList.valueOf(accent));
        // Halo léger derrière l’icône
        iconBg.setBackgroundTintList(ColorStateList.valueOf(adjustAlpha(accent, 0.15f)));

        // >>> ICI on affiche titre et message dans la vue custom
        tvTitle.setText((title == null || title.trim().isEmpty()) ? defaultTitle : title);
        tvMessage.setText(message);

        // 3) Construction du dialog (le layout custom remplace le contenu standard)
        MaterialAlertDialogBuilder builder =
                new MaterialAlertDialogBuilder(new ContextThemeWrapper(context, R.style.AppAlertDialogTheme))
                        .setView(view)
                        .setCancelable(false)
                        .setPositiveButton("OK", (d, w) -> {
                            if (onOk != null) onOk.run();
                        });

        if (type == Type.ERROR) {
            builder.setNegativeButton("Détails", (d, w) -> { /* ouvrir logs / copier erreur */ });
        }

        AlertDialog dialog = builder.create();

        // 4) Styliser les boutons une fois qu’ils existent (évite le NPE)
        dialog.setOnShowListener(dlg -> {
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (positive != null) positive.setTextColor(accent);

            Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            if (negative != null) negative.setTextColor(accent);
        });

        // dialog.show();
        return dialog;
    }

    // Overload simple (compat avec ton ancienne signature)
    public static AlertDialog neutreDialog(String titre, String T, final Context context) {
        return show(context, Type.INFO, T, titre, null);
    }

    public static AlertDialog dialog(String message, int code, final Context context) {
        DialogConfig dg = getDialogConfigFromCode(code);
        return show(context, dg.type, dg.title, message, null);
    }

    private static int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static class DialogConfig {
        public final Type type;
        public final String title;

        public DialogConfig(Type type, String title) {
            this.type = type;
            this.title = title;
        }
    }

    /**
     * Retourne le type et le titre adaptés en fonction du code d'erreur HTTP ou interne.
     */
    public static DialogConfig getDialogConfigFromCode(int code) {
        switch (code) {
            case 200:
            case 201:
                return new DialogConfig(Type.SUCCESS, "Succès");
            case 400:
                return new DialogConfig(Type.WARNING, "Requête invalide");
            case 401:
                return new DialogConfig(Type.WARNING, "Non autorisé");
            case 403:
                return new DialogConfig(Type.WARNING, "Accès refusé");
            case 404:
                return new DialogConfig(Type.WARNING, "Ressource introuvable");
            case 408:
                return new DialogConfig(Type.WARNING, "Délai dépassé");
            case 500:
                return new DialogConfig(Type.ERROR, "Erreur interne du serveur");
            case 502:
                return new DialogConfig(Type.ERROR, "Mauvaise passerelle");
            case 503:
                return new DialogConfig(Type.ERROR, "Service indisponible");
            case 504:
                return new DialogConfig(Type.ERROR, "Délai de réponse dépassé");
            default:
                // Codes hors HTTP → traiter comme erreur générique
                if (code >= 200 && code < 300) {
                    return new DialogConfig(Type.SUCCESS, "Opération réussie");
                } else if (code >= 400 && code < 500) {
                    return new DialogConfig(Type.WARNING, "Problème côté client");
                } else if (code >= 500) {
                    return new DialogConfig(Type.ERROR, "Problème côté serveur");
                } else {
                    return new DialogConfig(Type.INFO, "Information");
                }
        }
    }

    public static void showNetWork(Context context, String msg, int code) {
        dialog(msg, code, context).show();
    }

    public static void erreurTechnique(Context context, String errorContent, int code) {
        // Affiche le message d’erreur et le code HTTP

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                String finalErrorContent = errorContent;
                if (finalErrorContent.contains("{")) {
                    try {
                        ApiErrorResponse apiErrorResponse =
                                new ApiErrorResponse().fromJs(finalErrorContent);
                        finalErrorContent = ErrorUtils.buildErrorMessage(apiErrorResponse);
                    } catch (Exception e) {

                    }
                }
                String finalErrorContent1 = finalErrorContent;
                activity.runOnUiThread(() -> {
                    showNetWork(context, finalErrorContent1, code);
                });
            }
        }
    }
}
