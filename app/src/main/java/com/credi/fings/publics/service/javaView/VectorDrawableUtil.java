package com.credi.fings.publics.service.javaView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

public class VectorDrawableUtil {

    public static Drawable plus(Context context) {
        // Définition du viewport (équivalent à android:viewportWidth="24" android:viewportHeight="24")
        float viewportWidth = 24f;
        float viewportHeight = 24f;

        // Création du Path pour l'icône (équivalent à android:pathData="M19,13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z")
        Path path = new Path();
        try {
            path.moveTo(19, 13);
            path.rLineTo(-6, 0);
            path.rLineTo(0, 6);
            path.rLineTo(-2, 0);
            path.rLineTo(0, -6);
            path.rLineTo(-6, 0);
            path.rLineTo(0, -2);
            path.rLineTo(6, 0);
            path.rLineTo(0, -6);
            path.rLineTo(2, 0);
            path.rLineTo(0, 6);
            path.rLineTo(6, 0);
            path.rLineTo(0, 2);
        } catch (Exception e) {
            Log.e("VectorDrawableUtil", "Erreur lors de la création du path", e);
        }

        // Création d'une ShapeDrawable avec le Path
        ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(path, viewportWidth, viewportHeight));
        shapeDrawable.getPaint().setColor(Color.WHITE); // Équivalent à android:fillColor="@android:color/white"
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.setIntrinsicWidth(dpToPx(context, 24));
        shapeDrawable.setIntrinsicHeight(dpToPx(context, 24));

        return shapeDrawable;
    }

    /**
     * Convertit les valeurs en dp en pixels.
     */
    private static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}

