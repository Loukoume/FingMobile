package com.credi.fings.publics.service;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.VideoView;

import androidx.annotation.Nullable;

public class CustomVideoView extends VideoView {

    private Paint paint;
    private Path path;
    private float cornerRadius = 30f; // Rayon des coins arrondis

    public CustomVideoView(Context context) {
        super(context);
        init();
    }

    public CustomVideoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomVideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewWidth = getWidth();
        int viewHeight = getHeight();

        // Définir les coins arrondis
        RectF rect = new RectF(0, 0, viewWidth, viewHeight);
        path.reset();
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);

        // Appliquer un clip pour les coins arrondis
        canvas.save();
        canvas.clipPath(path);

        // Dessiner la vidéo
        super.onDraw(canvas);

        canvas.restore();
    }

    /**
     * Permet de définir dynamiquement le rayon des coins arrondis.
     */
    public void setCornerRadius(float radius) {
        this.cornerRadius = radius;
        invalidate(); // Redessiner la vue avec les nouvelles valeurs
    }
}

