package com.credi.fings.publics.service.impl;

import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;

public class ShapeUtils {
    public static GradientDrawable createCircleBackground(String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL); // Définit la forme comme ovale
        drawable.setColor(Color.parseColor(color)); // Définit la couleur du fond

        return drawable;
    }

}

