package com.credi.fing.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

public class QRCodeUtil {

    /**
     * Génère un Bitmap QR code à partir d'une chaîne de caractères.
     *
     * @param text      Le texte à encoder dans le QR.
     * @param width     Largeur souhaitée en pixels.
     * @param height    Hauteur souhaitée en pixels.
     * @return          Bitmap du QR code.
     * @throws Exception Si l'encodage échoue.
     */
    public static Bitmap generateQRCode(String text, int width, int height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // hints.put(EncodeHintType.MARGIN, 1); // marge minimale

        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * Sauvegarde un Bitmap dans le cache et retourne son URI via FileProvider.
     */
    public static Uri saveBitmapToCache(Context ctx, Bitmap bmp, String filename) throws Exception {
        File cachePath = new File(ctx.getCacheDir(), "images");
        cachePath.mkdirs();
        File file = new File(cachePath, filename + ".png");
        try (FileOutputStream stream = new FileOutputStream(file)) {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        }
        // authority doit correspondre à android:authorities dans le manifeste
        return FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".fileprovider", file);
    }


    /**
     * Vérifie si une application est installée sur l’appareil.
     * @param ctx           Contexte Android
     * @param packageName   Nom du package (ex. "com.whatsapp")
     * @return              true si installée, false sinon
     */
    public static boolean isAppInstalled(Context ctx, String packageName) {
        try {
            ctx.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Décode un QR code dans une image référencée par une URI.
     * @param ctx     Contexte
     * @param uri     URI de l’image
     * @return        Texte contenu dans le QR ou null si échec
     */
    public static String decodeQrFromUri(Context ctx, Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);
            return decodeQrFromBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Décode un QR code présent dans un Bitmap.
     * @param bitmap  Bitmap à décoder
     * @return        Texte contenu ou null si non trouvé
     */
    public static String decodeQrFromBitmap(Bitmap bitmap) {
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] px = new int[width * height];
        bitmap.getPixels(px, 0, width, 0, 0, width, height);

        LuminanceSource source = new RGBLuminanceSource(width, height, px);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(binaryBitmap);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

