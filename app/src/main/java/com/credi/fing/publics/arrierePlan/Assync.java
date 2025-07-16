package com.credi.fing.publics.arrierePlan;

import android.os.AsyncTask;

public class Assync extends AsyncTask<Void, Void, Boolean> {
    private final OnWorkFinish onWorkFinish;
    private final OnWorking onWorking;

    public Assync(OnWorkFinish onWorkFinish,OnWorking onWorking) {
        this.onWorkFinish = onWorkFinish;
        this.onWorking=onWorking;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if(onWorking!=null){
                onWorking.work();
            }
            return true; // Indique que la tâche est réussie
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Indique un échec
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (onWorkFinish != null) {
            String code = success ? "200" : "500";
            onWorkFinish.onFinished(success, code);
        }
    }
}
