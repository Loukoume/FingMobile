package com.credi.fing.publics.service.impl;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.credi.fing.R;
import com.credi.fing.publics.acty.SelectActivity;
import com.credi.fing.publics.service.HttpApi;
import com.credi.fing.publics.utils.S;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Rendu/traitement d'un seul input (un champ) selon son type.
 * - Gère les layout: input_mt, input_text_area_mt, pick_date, inpu_drop_mt, switch_material
 * - Colore automatiquement hint/contour via UtilsInput
 * - Valide "required" et remonte l'erreur dans le TextInputLayout
 * - Met à jour l'objet "object" via Ut.setField(fieldPath, object, value)
 *
 * Dépendances présentes dans le projet:
 *  - Attribut, Ut, S, Composant, HttpApi, TextViewHandler, SelectService
 *  - EditeService.field / EditeService.inputEditText pour les sélections "navigate"
 */
public class SingleInputService<T> {

    private final Context context;
    private final Class<T> t;
    private Object object;               // instance à mettre à jour
    private final String fieldPath;      // ex: "user:adresse:ville" ou "libelle"
    private final Attribut attribut;     // config du champ (type, name, required, values, request...)

    private View rootView;               // view gonflée pour ce champ
    private TextInputLayout til;         // si applicable
    private TextInputEditText et;        // si applicable

    public SingleInputService(Context context, Class<T> t, Object object, String fieldPath, Attribut attribut) {
        this.context = context;
        this.t = t;
        this.object = object;
        this.fieldPath = fieldPath;
        this.attribut = attribut;
    }

    /** Construit la vue pour ce champ et branche tous les comportements. */
    public View build() {
        String type = attribut.getType();
        String format = null;
        Object raw = getValue(object, fieldPath);
        String v = raw != null ? String.valueOf(raw) : null;

        if (type != null) {
            int idx = type.indexOf("|");
            if (idx > 0) {
                format = type.substring(idx + 1);
                type = type.substring(0, idx);
            }
            switch (type) {
                case "string":
                case "number":
                    inflateInputMt(v, type);
                    break;

                case "text":
                    inflateTextArea(v);
                    break;

                case "date":
                    inflatePickDate(v, format, /*stringMode*/ false);
                    break;

                case "dateString":
                    inflatePickDate(v, format, /*stringMode*/ true);
                    break;

                case "heur":
                    inflatePickTime(v);
                    break;

                case "boolean":
                    inflateSwitch(v);
                    break;

                default:
                    // Sélecteur (dropdown / multiselect / oneSelect / request values)
                    inflateDropInput(v, type);
                    break;
            }
        } else {
            // Si type null -> fallback champ texte
            inflateInputMt(v, "string");
        }

        return rootView;
    }

    /** Valide uniquement ce champ (required). Retourne true si OK. */
    public boolean validate() {
        if (til == null) return true; // pas de til (switch par ex.), rien à valider ici
        Object raw = getValue(object, fieldPath);
        boolean empty = (raw == null || String.valueOf(raw).trim().isEmpty());
        if (attribut.isRequierd() && empty) {
            til.setError("Champs obligatoire");
            return false;
        }
        til.setError(null);
        return true;
    }

    /** Met à jour la valeur (ex: résultat d'une sélection externe) et la vue. */
    public void setValue(Object newValue) {
        object = Ut.setField(fieldPath, object, newValue);
        if (et != null) {
            et.setText(newValue == null ? "" : String.valueOf(newValue));
        }
    }

    /** Retourne la racine gonflée (utile si besoin après build()) */
    public View getRootView() {
        return rootView;
    }

    /** Retourne l'objet mis à jour. */
    public Object getObject() {
        return object;
    }

    // -------------------- Inflatages par type --------------------

    private void inflateInputMt(String v, String baseType) {
        rootView = LayoutInflater.from(context).inflate(R.layout.input_mt, null, false);
        til = rootView.findViewById(R.id.textField);
        et = rootView.findViewById(R.id.id);

        til.setHint(attribut.getName());
        if ("number".equals(baseType)) {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (v != null) et.setText(v);

        // Couleur/états
        UtilsInput.setupMaterialInput(rootView, context, attribut.isRequierd(), null, null);

        et.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                ensureConcreteObject();
                object = Ut.setField(fieldPath, object, s == null ? "" : s.toString());
            }
        });
    }

    private void inflateTextArea(String v) {
        rootView = LayoutInflater.from(context).inflate(R.layout.input_text_area_mt, null, false);
        til = rootView.findViewById(R.id.textInputLayout);
        et = rootView.findViewById(R.id.editTextMessage);

        til.setHint(attribut.getName());
        if (v != null) et.setText(v);

        UtilsInput.setupMaterialInput(
                rootView, context, attribut.isRequierd(), null, null,
                R.id.textInputLayout, R.id.editTextMessage
        );

        et.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                object = Ut.setField(fieldPath, object, s == null ? "" : s.toString());
            }
        });
    }

    private void inflatePickDate(String v, String format, boolean stringMode) {
        rootView = LayoutInflater.from(context).inflate(R.layout.pick_date, null, false);
        til = rootView.findViewById(R.id.textField);
        et = rootView.findViewById(R.id.id);

        til.setHint(attribut.getName());
        if (v != null) {
            if (format != null) {
                et.setText(S.date(v, "yyyy-MM-dd'T'HH:mm:ss", format));
            } else {
                et.setText(v);
            }
        }

        String fmt = (format == null ? "yyyy-MM-dd'T'HH:mm:ss" : format);
        et.setOnClickListener(v1 -> {
            Composant c = new Composant(object, fieldPath, fmt);
            if (stringMode) c.setDateString(true);
            c.showDateTimePicker(context, attribut.getName(), et, "edite", fmt);
        });

        UtilsInput.setupMaterialInput(rootView, context, attribut.isRequierd(), null, null);
    }

    private void inflatePickTime(String v) {
        rootView = LayoutInflater.from(context).inflate(R.layout.pick_date, null, false);
        til = rootView.findViewById(R.id.textField);
        et = rootView.findViewById(R.id.id);

        til.setHint(attribut.getName());
        if (v != null) et.setText(v);

        et.setOnClickListener(v12 ->
                new Composant(object, fieldPath, "yyyy-MM-dd'T'HH:mm:ss")
                        .showTimePicker(context, attribut.getName(), et, "edite"));

        UtilsInput.setupMaterialInput(rootView, context, attribut.isRequierd(), null, null);
    }

    private void inflateSwitch(String v) {
        rootView = LayoutInflater.from(context).inflate(R.layout.switch_material, null, false);
        SwitchMaterial sw = rootView.findViewById(R.id.id_switch);
        sw.setText(attribut.getName());
        boolean checked = "true".equalsIgnoreCase(v);
        sw.setChecked(checked);
        object = Ut.setField(fieldPath, object, checked);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                object = Ut.setField(fieldPath, object, isChecked);
            }
        });
    }

    private void inflateDropInput(String v, String rawType) {
        rootView = LayoutInflater.from(context).inflate(R.layout.inpu_drop_mt, null, false);
        til = rootView.findViewById(R.id.textField);
        et  = rootView.findViewById(R.id.id);
        ImageView ups = rootView.findViewById(R.id.ups);

        til.setHint(attribut.getName());
        if (v != null) et.setText(v);

        UtilsInput.setupMaterialInput(rootView, context, attribut.isRequierd(), null, null);

        // Charger dynamiquement si besoin (request)
        if (attribut.getRequest() != null &&
                attribut.getRequest().getUrl() != null &&
                attribut.getRequest().getAttribut() == null &&
                !isSelectType(rawType)) {
            loadValuesFromRequest(attribut);
        }

        final boolean isSelect = isSelectType(rawType);
        et.setOnClickListener(v14 -> {
            EditeService.inputEditText = et; // pour cohérence avec SelectActivity
            if (isSelect) {
                if (attribut.getValues() != null && attribut.getValues().size() <= 7) {
                    Object selected = getValue(object, fieldPath);
                    showSelectDialog(attribut.getValues(), selected, attribut.getLabel());
                } else {
                    // Navigation vers SelectActivity (réutilise la convention EditeService.field)
                    EditeService.field = attribut;
                    Object lb = getValue(object, fieldPath);
                    context.startActivity(new Intent(context, SelectActivity.class)
                            .putExtra("attribut", attribut)
                            .putExtra("selected", Ut.js(lb)));
                }
            } else if (attribut.getValues() != null && !attribut.getValues().isEmpty()) {
                // Popup simple
                List<Object> objects = attribut.getValues();
                PopupMenu pop = S.popupMenu(ups,
                        attribut.getLabel() == null
                                ? objects.stream().map(o -> o == null ? "" : String.valueOf(o)).toArray(String[]::new)
                                : objects.stream().filter(Objects::nonNull)
                                .map(o -> String.valueOf(Ut.getValue(o, attribut.getLabel())))
                                .toArray(String[]::new));

                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        int idx = item.getItemId() - 1;
                        if (idx < 0 || idx >= objects.size()) return false;
                        Object chosen = objects.get(idx);
                        et.setText(item.getTitle());

                        String fs = fieldPath.contains(":") ? fieldPath.substring(0, fieldPath.indexOf(":")) : fieldPath;
                        Object val = (attribut.getField() == null) ? chosen : Ut.getValue(chosen, attribut.getField());
                        object = Ut.setField(fs, object, val);
                        return true;
                    }
                });
            } else if (attribut.getRequest() != null && attribut.getRequest().getNavigateClasse() != null) {
                // Navigation custom
                EditeService.field = attribut;
                context.startActivity(new Intent(context, attribut.getRequest().getNavigateClasse())
                        .putExtra("select", "true"));
            }
        });
    }

    // -------------------- Aides & utilitaires --------------------

    private boolean isSelectType(String rawType) {
        return "multiSelect".equals(rawType) || "oneSelect".equals(rawType);
    }

    private void ensureConcreteObject() {
        if (t != null && !"LinkedTreeMap".equals(t.getSimpleName())) {
            object = Ut.creatObject(object, t);
        }
    }

    private Object getValue(Object obj, String fieldName) {
        if (obj == null || fieldName == null) return null;
        if (!fieldName.contains(":")) {
            return Ut.getValue(obj, fieldName);
        }
        Object current = obj;
        for (String f : fieldName.split(":")) {
            if (current == null) return null;
            current = Ut.getValue(current, f);
        }
        return current;
    }

    private void loadValuesFromRequest(Attribut at) {
        String url = at.getRequest().getUrl();
        if (url.contains("/{")) {
            // prise en charge d'un path variable basé sur la valeur courante
            String v = url.substring(url.indexOf("/{") + 1).replace("{", "").replace("}", "");
            String base = url.substring(0, url.indexOf("/{"));
            Object param = getValue(object, v);
            url = base + "/" + (param != null ? String.valueOf(param).replace(".0", "") : "0");
        }
        TextView tmp = new TextView(context);
        HttpApi api = new HttpApi(url, tmp, context);
        TextViewHandler tvh = new TextViewHandler(tmp);
        tvh.setAfterTextChangedAction(() -> {
            switch (tmp.getText().toString()) {
                case "Ok":
                case "local":
                case "technique":
                    at.setValues(copyToTargetType(api.getList(), Ut.getValueType(object, at.getColonne())));
                    break;
            }
        });
        api.getAllDatas();
    }

    private List<Object> copyToTargetType(List<Object> src, Class<?> target) {
        List<Object> out = new ArrayList<>();
        for (Object o : src) {
            out.add(Ut.creatObject(o, target));
        }
        return out;
    }

    private void showSelectDialog(final List<Object> data, Object selected, String label) {
        View dialogView = Ut.getView(context, R.layout.add_layout);
        LinearLayout lm = dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        final AlertDialog alert = builder.create();

        List<Object> selection = (selected == null) ? new ArrayList<>() : (List<Object>) selected;

        SelectService selectService = new SelectService(context, data, label, attribut, null)
                .setTitle("SÉLECTIONNEZ")
                .setMultiselect(true)
                .setSelect(selection);

        View content = selectService.view(alert);

        View btn1 = Ut.getView(context, R.layout.outline_bouton);
        MaterialButton mtbt1 = btn1.findViewById(R.id.outlinedButton);
        mtbt1.setText("Valider la sélection");

        lm.addView(content);
        lm.addView(btn1);

        alert.show();
        mtbt1.setOnClickListener(v -> {
            alert.cancel();
            String fs = fieldPath.contains(":") ? fieldPath.substring(0, fieldPath.indexOf(":")) : fieldPath;
            object = Ut.setField(fs, object, selectService.getSelect());
            if (et != null) et.setText(selectService.getStringSelect());
        });
    }
}
