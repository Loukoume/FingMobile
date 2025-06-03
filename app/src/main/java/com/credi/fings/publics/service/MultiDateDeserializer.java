package com.credi.fings.publics.service;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MultiDateDeserializer implements JsonDeserializer<Date> {

    // Liste des formats acceptés (l'ordre est important : le premier format sera tenté en premier)
    private static final List<SimpleDateFormat> dateFormats = new ArrayList<>();

    static {
        dateFormats.add(new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.ENGLISH)); // Format en lettres (ex: Jan)
        dateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")); // Format ISO avec fuseau horaire
        dateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")); // Format ISO avec millisecondes et fuseau horaire
        dateFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH")); // Format sans millisecondes ni fuseau horaire
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateString = json.getAsString();

        // Essayer tous les formats disponibles
        for (SimpleDateFormat format : dateFormats) {
            try {
                System.out.println(format.toPattern() + " fofrm " + dateString);
                return format.parse(dateString);
            } catch (ParseException e) {
                System.out.println(" excpt " + format.toPattern());
                e.printStackTrace();
                // On ignore et on essaie le format suivant
            }
        }
        // Si aucun format ne convient, lever une exception
        throw new JsonParseException("Impossible d'analyser la date: " + dateString);
    }
}

