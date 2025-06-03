package com.credi.fings.publics.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CustomImageView extends AppCompatImageView {

    private Paint paint;
    private BitmapShader shader;
    private Matrix matrix;
    private Path path;
    private float cornerRadius = 30f; // Rayon des coins arrondis

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG);
        matrix = new Matrix();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        Bitmap bitmap = drawableToBitmap(drawable);
        if (bitmap == null) {
            return;
        }

        // Préserver la clarté en adaptant la résolution du bitmap à la taille du ImageView
        bitmap = scaleBitmapIfNeeded(bitmap);

        // Appliquer le BitmapShader pour remplir l'image
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        matrix.reset();

        // Dimensions du ImageView
        int viewWidth = getWidth();
        int viewHeight = getHeight();

        // Dimensions du bitmap
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // Calcul du ratio pour remplir tout l'espace sans perdre les proportions
        float scale = Math.max((float) viewWidth / bitmapWidth, (float) viewHeight / bitmapHeight);

        // Centrer l'image recadrée
        float dx = (viewWidth - bitmapWidth * scale) / 2;
        float dy = (viewHeight - bitmapHeight * scale) / 2;

        // Appliquer la mise à l'échelle et la translation
        matrix.setScale(scale, scale);
        matrix.postTranslate(dx, dy);

        shader.setLocalMatrix(matrix);
        paint.setShader(shader);

        // Définir les coins arrondis
        RectF rect = new RectF(0, 0, viewWidth, viewHeight);
        path.reset();
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);

        // Appliquer le clip pour les coins arrondis
        canvas.save();
        canvas.clipPath(path);

        // Dessiner l'image avec les coins arrondis
        canvas.drawRect(rect, paint);
        canvas.restore();
    }

    /**
     * Convertit un Drawable en Bitmap.
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        try {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return bitmap;
    }

    /**
     * Adapte la taille du Bitmap à la taille de la vue pour éviter le flou ou la perte de clarté.
     */
    private Bitmap scaleBitmapIfNeeded(Bitmap bitmap) {
        int viewWidth = getWidth();
        int viewHeight = getHeight();

        if (viewWidth <= 0 || viewHeight <= 0) {
            return bitmap; // Dimensions de la vue non valides, retourner le bitmap original
        }

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        // Si le bitmap est déjà suffisamment détaillé, on ne le redimensionne pas
        if (bitmapWidth >= viewWidth && bitmapHeight >= viewHeight) {
            return bitmap;
        }

        // Calcul du ratio d'échelle
        float scale = Math.max((float) viewWidth / bitmapWidth, (float) viewHeight / bitmapHeight);

        // Dimensions ajustées
        int scaledWidth = Math.round(bitmapWidth * scale);
        int scaledHeight = Math.round(bitmapHeight * scale);

        // Création d'un nouveau bitmap redimensionné
        return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
    }

    /**
     * Permet de définir dynamiquement le rayon des coins arrondis.
     */
    public void setCornerRadius(float radius) {
        this.cornerRadius = radius;
        invalidate(); // Redessiner la vue avec les nouvelles valeurs
    }
}

