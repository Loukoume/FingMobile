package com.credi.fings.publics.service.impl;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.service.GetDate;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.publics.utils.S;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Composant {
    private Object object;
    private String field;
    private String dateFormat;
    private boolean withHour;

    public Composant(Object object, String field, String dateFormat) {
        this.object = object;
        this.field = field;
        this.dateFormat = dateFormat;
        this.withHour = dateFormat != null && dateFormat.contains("HH");
    }

    public void showTimePicker(Context context, String title, View view, String typeView) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText(title)
                .build();

        timePicker.show(fragmentManager, "MATERIAL_TIME_PICKER");

        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedTime = String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute());
                if (object != null && field != null)
                    object = Ut.setField(field, object, selectedTime);
                switch (typeView) {
                    case "TextView":
                        ((TextView) view).setText(selectedTime);
                        break;
                    case "edite":
                        ((TextInputEditText) view).setText(selectedTime);
                        break;
                }
            }
        });
    }

    public void showDatePicker(Context context, String title, View view, String typeView, String format) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        // Création du MaterialDatePicker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        // Affichage du sélecteur de date
        datePicker.show(fragmentManager, "MATERIAL_DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            // "selection" est un Long représentant la date sélectionnée en millisecondes UTC
            // On convertit ce timestamp en date puis on formate la date.
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            String selectedDate = sdf.format(new Date(selection));
            Date date = S.date(selectedDate, dateFormat);
            if (object != null && field != null)
                object = Ut.setField(field, object, date);
            switch (typeView) {
                case "TextView":
                    ((TextView) view).setText(selectedDate);
                    break;
                case "edite":
                    ((TextInputEditText) view).setText(selectedDate);
                    break;
            }
        });
    }

    public void showDateTimePicker(Context context, String titleDate, View view, String typeView, String format) {
        if (withHour) {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

            // 1. Créer le sélecteur de date
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(titleDate)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            // Afficher le sélecteur de date
            datePicker.show(fragmentManager, "MATERIAL_DATE_PICKER");

            // Lorsque la date est sélectionnée
            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Conversion du timestamp UTC en date
                final long dateInMillis = selection;
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dateInMillis);

                // 2. Créer et afficher le sélecteur d'heure
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY))  // On peut initialiser avec l’heure actuelle
                        .setMinute(calendar.get(Calendar.MINUTE))
                        .setTitleText("Date et heur " + S.phrase(titleDate))
                        .build();

                timePicker.show(fragmentManager, "MATERIAL_TIME_PICKER");

                // Lorsque l’heure est sélectionnée
                timePicker.addOnPositiveButtonClickListener(v -> {
                    // Récupération de l'heure et de la minute
                    int hour = timePicker.getHour();
                    int minute = timePicker.getMinute();

                    // Mettre à jour le calendrier avec l'heure sélectionnée
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Formatage de la date et de l'heure (ex : "dd/MM/yyyy HH:mm")
                    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
                    String selectedDateTime = sdf.format(calendar.getTime());

                    Date date = S.date(selectedDateTime, dateFormat);
                    if (object != null && field != null)
                        object = Ut.setField(field, object, date);

                    sdf = new SimpleDateFormat(format, Locale.getDefault());
                    selectedDateTime = sdf.format(calendar.getTime());
                    // Mise à jour de la vue
                    switch (typeView) {
                        case "TextView":
                            ((TextView) view).setText(selectedDateTime);
                            break;
                        case "edite":
                            ((TextInputEditText) view).setText(selectedDateTime);
                            break;
                    }
                });
            });
        } else {
            showDatePicker(context, titleDate, view, typeView, format);
        }

    }

    public void showTimePicker2Date1Date2(Context context, String title, View view, String typeView,
                                          Date debut, Date fin, GetDate getDate) {

        // On utilise un Calendar pour extraire heure et minute de la date "debut"
        Calendar calendarDebut = Calendar.getInstance();
        calendarDebut.setTime(debut);
        int heureDebut  = calendarDebut.get(Calendar.HOUR_OF_DAY);
        int minuteDebut = calendarDebut.get(Calendar.MINUTE);

        // Même chose pour la date "fin"
        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(fin);
        int heureFin  = calendarFin.get(Calendar.HOUR_OF_DAY);
        int minuteFin = calendarFin.get(Calendar.MINUTE);

        // On réutilise la méthode showTimePicker2(...) en lui passant les heures/minutes calculées
        showTimePicker2(context, title, view, typeView, heureDebut, minuteDebut, heureFin, minuteFin,debut,getDate);
    }

    public void showTimePicker2(Context context, String title, View view, String typeView,
                                int heureDebut, int minuteDebut,
                                int heureFin, int minuteFin, Date debut, GetDate getDate) {

        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        // Construction du timePicker
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText(title)
                .build();

        timePicker.show(fragmentManager, "MATERIAL_TIME_PICKER_2");

        timePicker.addOnPositiveButtonClickListener(v -> {
            int pickedHour = timePicker.getHour();
            int pickedMinute = timePicker.getMinute();

            // Conversion des heures/minutes en nombre total de minutes depuis minuit
            int pickedTotalMinutes = pickedHour * 60 + pickedMinute;
            int debutTotalMinutes = heureDebut * 60 + minuteDebut;
            int finTotalMinutes   = heureFin   * 60 + minuteFin;

            // Vérifie si la plage horaire chevauche minuit
            boolean chevaucheMinuit = (debutTotalMinutes > finTotalMinutes);

            boolean estValide;

            if (!chevaucheMinuit) {
                // CAS STANDARD : heureDebut <= heureFin
                // => L'heure sélectionnée doit être comprise entre [debutTotalMinutes ... finTotalMinutes]
                estValide = (pickedTotalMinutes >= debutTotalMinutes && pickedTotalMinutes <= finTotalMinutes);
            } else {
                // CAS CHEVAUCHEMENT : heureDebut > heureFin
                // => Plage valide : [debutTotalMinutes ... 23:59] OU [00:00 ... finTotalMinutes]
                // Autrement dit : pickedTotalMinutes >= debutTotalMinutes ou pickedTotalMinutes <= finTotalMinutes
                estValide = (pickedTotalMinutes >= debutTotalMinutes || pickedTotalMinutes <= finTotalMinutes);
            }

            if (estValide) {
                Date dateHHmm=null;
                if(debut!=null){
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.setTime(debut);
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, pickedHour);
                    selectedCalendar.set(Calendar.MINUTE, pickedMinute);
                    selectedCalendar.set(Calendar.SECOND, 0);
                    selectedCalendar.set(Calendar.MILLISECOND, 0);

                    if (chevaucheMinuit && (pickedTotalMinutes <= finTotalMinutes)) {
                        // On bascule au jour suivant
                        selectedCalendar.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    // Voilà la date qui représente la sélection
                    dateHHmm = selectedCalendar.getTime();

                }

                // On réutilise l'heure sélectionnée telle quelle
                // Formatage de l'heure en chaîne "HH:mm"
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", pickedHour, pickedMinute);

                // Mise à jour du champ de l'objet si nécessaire
                if (object != null && field != null) {
                    object = Ut.setField(field, object, selectedTime);
                    EditeService.object=object;
                    AddActivity.object=object;
                }

                // Mise à jour de la vue
                switch (typeView) {
                    case "TextView":
                        ((TextView) view).setText(selectedTime);
                        break;
                    case "edite":
                        ((TextInputEditText) view).setText(selectedTime);
                        break;
                }
                if(getDate!=null)
                    getDate.get(dateHHmm);
            } else {
                // Si la sélection n'est pas valide, on affiche un dialogue d’alerte
                Dialogue.neutreDialog("L'heure sélectionnée n'est pas valide. L'heur valide doit doit être entre "+heureDebut+":"+minuteDebut+" et "+heureFin+":"+minuteFin, "Alerte", context).show();
            }
        });
    }


    /**
     * Contrainte la sélection d’une date entre deux bornes (startDateInMillis et endDateInMillis).
     * Les deux paramètres doivent être des timestamps (en millisecondes) correspondant aux bornes.
     * Par exemple, vous pouvez utiliser calendar.getTimeInMillis() pour générer ces valeurs.
     */
    public void showDatePickerConstrained(Context context, String title, View view, String typeView,
                                          String format,
                                          long startDateInMillis,
                                          long endDateInMillis) {

        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        // Builder pour le DatePicker
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds());

        // Configuration des contraintes de dates
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(startDateInMillis);
        constraintsBuilder.setEnd(endDateInMillis);

        // Appliquer les contraintes dans le builder du DatePicker
        builder.setCalendarConstraints(constraintsBuilder.build());

        // Construction du DatePicker
        MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.show(fragmentManager, "MATERIAL_DATE_PICKER_CONSTRAINED");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            // "selection" est un Long représentant la date sélectionnée en millisecondes UTC
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            String selectedDate = sdf.format(new Date(selection));

            // Conversion en Date selon le dateFormat défini
            Date date = S.date(selectedDate, dateFormat);

            if (object != null && field != null) {
                object = Ut.setField(field, object, date);
            }

            // Mise à jour de la vue
            switch (typeView) {
                case "TextView":
                    ((TextView) view).setText(selectedDate);
                    break;
                case "edite":
                    ((TextInputEditText) view).setText(selectedDate);
                    break;
            }
        });
    }

}
