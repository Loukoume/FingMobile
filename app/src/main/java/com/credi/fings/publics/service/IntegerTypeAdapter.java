package com.credi.fings.publics.service;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IntegerTypeAdapter extends TypeAdapter<Number> {
    @Override
    public void write(JsonWriter out, Number value) throws IOException {
        // Vérifiez si le nombre est un entier (int ou long)
        if (value instanceof Integer || value instanceof Long) {
            out.value(value.longValue());
        } else {
            out.value(value);
        }
    }

    @Override
    public Number read(JsonReader in) throws IOException {
        // Ici, vous pouvez implémenter la logique de désérialisation si nécessaire
        return in.nextLong();
    }
}

