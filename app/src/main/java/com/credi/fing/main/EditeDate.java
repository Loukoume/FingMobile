package com.credi.fing.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.credi.fing.R;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.S;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditeDate {

    // Valeurs texte affichées
    //private String dateSoumission = S.dateToString(new Date(), "dd MMMM yyyy");
    private String datePaiement  = S.dateToString(new Date(), "dd MMMM yyyy");

    private TextView tvSoumission;
    private TextView tvPaiement;

    // <-- Contiendra la dernière date sélectionnée (soumission ou paiement)
    private Date date = new Date();

    public View view(Context context) {
        View view = Ut.getView(context, R.layout.edite_date_layout);

        tvSoumission = view.findViewById(R.id.txtDateSoumission);
        tvPaiement   = view.findViewById(R.id.txtDatePaiement);

        tvSoumission.setOnClickListener(v -> openCalendar(context, true));
        tvPaiement.setOnClickListener(v -> openCalendar(context, false));

        tvSoumission.setText(this.datePaiement);
        tvPaiement.setText(this.datePaiement);

        return view;
    }

    /**
     * Affiche un DatePickerDialog et met à jour les variables globales,
     * notamment la variable `date` (Date sélectionnée).
     */
    private void openCalendar(Context context, boolean isSoumission) {
        final Calendar c = Calendar.getInstance(Locale.FRANCE);

        int year  = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day   = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(context, (datePicker, y, m, d) -> {

            // 1) Construire une vraie Date Java à partir du choix utilisateur
            Calendar selected = Calendar.getInstance(Locale.FRANCE);
            selected.set(Calendar.YEAR, y);
            selected.set(Calendar.MONTH, m); // m est 0-based (Jan=0)
            selected.set(Calendar.DAY_OF_MONTH, d);
            selected.set(Calendar.HOUR_OF_DAY, 0);
            selected.set(Calendar.MINUTE, 0);
            selected.set(Calendar.SECOND, 0);
            selected.set(Calendar.MILLISECOND, 0);

            // <-- ICI : on met la date sélectionnée dans la variable `date`
            this.date = selected.getTime();

            // 2) Formatage pour affichage (choisissez le format voulu)
            String formattedDate = S.dateToString(this.date, "dd MMMM yyyy");
            // ou si vous préférez: String formattedDate = String.format(Locale.FRANCE, "%02d-%02d-%d", d, (m + 1), y);

            // 3) Affectation selon le champ cliqué
           // if (isSoumission) {
                this.datePaiement = formattedDate;
                tvSoumission.setText(formattedDate);
           // } else {
                this.datePaiement = formattedDate;
                tvPaiement.setText(formattedDate);
            //}

        }, year, month, day);

        picker.show();
    }

    public String getDateSoumission() { return datePaiement; }
    public String getDatePaiement() { return datePaiement; }

    /** Retourne la dernière date sélectionnée (soumission ou paiement) */
    public Date getDate() { return date; }
}
