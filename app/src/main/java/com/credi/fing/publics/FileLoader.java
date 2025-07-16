package com.credi.fing.publics;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileLoader {

    public static InputStream loadFile(Context context, String filePath) {
        try {
            // Convertir le chemin de fichier en URI
            Uri fileUri = Uri.parse(filePath);

            // Obtenir le ContentResolver
            ContentResolver contentResolver = context.getContentResolver();

            if (filePath.startsWith(Environment.getExternalStorageDirectory().getPath())) {
                // Si le chemin est sur un stockage externe classique
                return new FileInputStream(new File(filePath));
            } else {
                // Utilisation de Scoped Storage avec ContentResolver
                return contentResolver.openInputStream(fileUri);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Gestion de l'erreur : journal ou message utilisateur
            return null;
        }
    }
}

