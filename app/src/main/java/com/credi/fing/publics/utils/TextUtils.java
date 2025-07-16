package com.credi.fing.publics.utils;

import android.graphics.Paint;
import android.widget.TextView;

import com.credi.fing.publics.Style;
import com.credi.fing.publics.ecouteur.OnTextSplitListener;

public class TextUtils {

    /**
     * Divise un texte pour qu'il s'adapte à la largeur d'un TextView sans être coupé.
     * Cette méthode mesure par mots pour optimiser les performances.
     *
     * @param textView Le TextView cible
     * @param text Le texte à diviser
     * @return Un tableau avec deux chaînes : [texte qui rentre, texte restant]
     */
    public static String[] splitTextToFitTextView(TextView textView, String text, Style style) {
        Paint paint = textView.getPaint(); // Récupère le Paint du TextView
        int availableWidth = 400 - textView.getPaddingLeft() - textView.getPaddingRight(); // Largeur disponible
        System.out.println(" = ==> "+availableWidth+" = "+textView.getWidth()+"  "+textView.getPaddingLeft()+" "+textView.getPaddingRight());
        StringBuilder fittingText = new StringBuilder();
        float currentWidth = 0f;

        // Divise le texte en mots
        String[] words = text.split(" ");

        for (String word : words) {
            float wordWidth = paint.measureText(word + " "); // Mesure la largeur du mot avec un espace

            if (currentWidth + wordWidth + (style==null?0:style.getTaille()*1.1)> availableWidth) {
                break; // Arrête si la largeur dépasse
            }
            fittingText.append(word).append(" "); // Ajoute le mot au texte qui rentre
            currentWidth += wordWidth;
        }

        // Texte restant après celui qui rentre
        String remainingText =fittingText.length()<text.length()? text.substring(fittingText.length()):"";

        return new String[]{fittingText.toString().trim(), remainingText};
    }

    public static void splitTextToFitTextView2(TextView textView, String text, OnTextSplitListener listener) {
        textView.post(() -> {
            Paint paint = textView.getPaint(); // Récupère le Paint du TextView
            int availableWidth = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight(); // Largeur disponible
            System.out.println(" = ==> " + availableWidth + " = " + textView.getWidth() + " " + textView.getPaddingLeft() + " " + textView.getPaddingRight());

            StringBuilder fittingText = new StringBuilder();
            float currentWidth = 0f;

            // Divise le texte en mots
            String[] words = text.split(" ");

            for (String word : words) {
                float wordWidth = paint.measureText(word + " "); // Mesure la largeur du mot avec un espace
                if (currentWidth + wordWidth > availableWidth) {
                    break; // Arrête si la largeur dépasse
                }
                fittingText.append(word).append(" "); // Ajoute le mot au texte qui rentre
                currentWidth += wordWidth;
            }

            // Texte restant après celui qui rentre
            String remainingText =fittingText.length()<text.length()? text.substring(fittingText.length()):"";

            // Retourne les résultats via le callback
            if (listener != null) {
                listener.onTextSplit(fittingText.toString().trim(), remainingText);
            }
        });
    }

}


