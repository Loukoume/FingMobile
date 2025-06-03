package com.credi.fings.publics.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AuthorizationService {

    private static final int REQUEST_STORAGE_PERMISSION = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;

    private final Context context;
    private PermissionCallback permissionCallback;

    public AuthorizationService(Context context) {
        this.context = context;
    }

    /**
     * Vérifie si la permission est accordée.
     *
     * @param permission La permission à vérifier.
     * @return true si la permission est accordée, false sinon.
     */
    public boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Demande la permission d'accès au stockage.
     *
     * @param activity   L'activité appelante.
     * @param callback   Callback pour traiter le résultat.
     */
    public void requestStoragePermission(Activity activity, PermissionCallback callback) {
        this.permissionCallback = callback;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Pas de runtime permissions avant Android 6.0
            permissionCallback.onPermissionGranted();
        } else if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Permission déjà accordée
            permissionCallback.onPermissionGranted();
        } else {
            // Vérifie si une explication est nécessaire
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanationDialog(activity, "Permission de stockage requise",
                        "Cette permission est nécessaire pour accéder aux fichiers de stockage.",
                        Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_STORAGE_PERMISSION);
            } else {
                // Demande directement la permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }
        }
    }

    /**
     * Demande la permission d'accès à la caméra.
     *
     * @param activity   L'activité appelante.
     * @param callback   Callback pour traiter le résultat.
     */
    public void requestCameraPermission(Activity activity, PermissionCallback callback) {
        this.permissionCallback = callback;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionCallback.onPermissionGranted();
        } else if (isPermissionGranted(Manifest.permission.CAMERA)) {
            permissionCallback.onPermissionGranted();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                showExplanationDialog(activity, "Permission de caméra requise",
                        "Cette permission est nécessaire pour accéder à la caméra.",
                        Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            }
        }
    }

    /**
     * Gère le résultat de la demande de permissions.
     *
     * @param requestCode Le code de la demande.
     * @param permissions Les permissions demandées.
     * @param grantResults Les résultats des permissions.
     */
    public void handlePermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionCallback == null) return;

        if (requestCode == REQUEST_STORAGE_PERMISSION || requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionCallback.onPermissionGranted();
            } else {
                permissionCallback.onPermissionDenied();
            }
        }
    }

    /**
     * Affiche une boîte de dialogue pour expliquer pourquoi une permission est requise.
     *
     * @param activity       L'activité appelante.
     * @param title          Titre de la boîte de dialogue.
     * @param message        Message de la boîte de dialogue.
     * @param permission     La permission à demander.
     * @param requestCode    Le code de la demande.
     */
    private void showExplanationDialog(Activity activity, String title, String message, String permission, int requestCode) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(activity,
                        new String[]{permission}, requestCode))
                .setNegativeButton("Annuler", (dialog, which) -> {
                    if (permissionCallback != null) {
                        permissionCallback.onPermissionDenied();
                    }
                })
                .create()
                .show();
    }

    /**
     * Interface de callback pour les permissions.
     */
    public interface PermissionCallback {
        void onPermissionGranted();

        void onPermissionDenied();
    }
}

