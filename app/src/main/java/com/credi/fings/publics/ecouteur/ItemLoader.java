package com.credi.fings.publics.ecouteur;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class ItemLoader extends AsyncTaskLoader<List<String>> {

    public ItemLoader(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        return null;
    }


}

