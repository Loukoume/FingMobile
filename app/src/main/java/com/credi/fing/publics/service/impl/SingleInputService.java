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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Rendu/traitement d'un seul input (un champ) selon son type.
 *
 * NOUVEAUTÉ:
 *  - Mode "injection": withRoot(View rootView [, withIds(...)]) -> aucune inflation; utilise la vue fournie.
 *  - Mode "inflate": comportement identique à la version précédente (auto-inflate du layout selon type).
 *
 * APIs principales (inchangées):
 *  - build(): View
 *  - validate(): boolean
 *  - setValue(Object): void
 *  - getRootView(): View
 *  - getObject(): Object
 */
public class SingleInputService<T> {

    private final Context context;
    private final Class<T> t;
    private Object object;               // instance à mettre à jour
    private final String fieldPath;      // ex: "user:adresse:ville"
    private final Attribut attribut;     // config du champ

    // Vue racine du champ + références internes (si applicable)
    private View rootView;
    private TextInputLayout til;
    private TextInputEditText et;

    // ---- Mode injection (optionnel) ----
    private View providedRoot;           // si fourni -> pas d'inflate
    private Integer tilIdOverride;       // id custom du TextInputLayout (ex: R.id.textInputLayout)
    private Integer editIdOverride;      // id custom du TextInputEditText (ex: R.id.editTextMessage)
    private Integer upsIdOverride;       // id custom de l'ImageView "dropdown" (ex: R.id.ups)
    private Integer switchIdOverride;    // id custom du SwitchMaterial (ex: R.id.id_switch)
    private boolean disableBuiltInSelectHandlersWhenProvided = true; // évite de doubler la logique dans le Fragment

    public SingleInputService(Context context, Class<T> t, Object object, String fieldPath, Attribut attribut) {
        this.context = context;
        this.t = t;
        this.object = object;
        this.fieldPath = fieldPath;
        this.attribut = attribut;
    }

    /**
     * Active le mode injection: on ne gonfle plus, on utilise la vue fournie.
     */
    public SingleInputService<T> withRoot(View rootView) {
        this.providedRoot = rootView;
        return this;
    }

    /**
     * Permet de préciser les IDs (si différents des valeurs par défaut).
     * tilId / editId sont très utiles pour les layouts custom (textarea, etc.).
     * upsId pour l'icône drop-down (si nécessaire), switchId pour le boolean.
     */
    public SingleInputService<T> withIds(Integer tilId, Integer editId, Integer upsId, Integer switchId) {
        this.tilIdOverride = tilId;
        this.editIdOverride = editId;
        this.upsIdOverride = upsId;
        this.switchIdOverride = switchId;
        return this;
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
                    buildStringOrNumber(v, type);
                    break;

                case "text":
                    buildTextArea(v);
                    break;

                case "date":
                    buildPickDate(v, format, /*stringMode*/ false);
                    break;

                case "dateString":
                    buildPickDate(v, format, /*stringMode*/ true);
                    break;

                case "heur":
                    buildPickTime(v);
                    break;

                case "boolean":
                    buildSwitch(v);
                    break;

                default:
                    buildDropInput(v, type);
                    break;
            }
        } else {
            // Si type null -> fallback champ texte
            buildStringOrNumber(v, "string");
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

    /** Retourne la racine (utile après build()). */
    public View getRootView() { return rootView; }

    /** Retourne l'objet mis à jour. */
    public Object getObject() { return object; }

    // -------------------- Construction par type --------------------

    private void buildStringOrNumber(String v, String baseType) {
        if (isProvided()) {
            rootView = providedRoot;
            til = findTil(rootView);
            et  = findEt(rootView);

            if (til != null) til.setHint(attribut.getName());
            if (et != null) {
                if ("number".equals(baseType)) et.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (v != null) et.setText(v);
            }
            // Couleur/états
            applyUtilsInput(rootView);

            if (et != null) {
                et.addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override public void afterTextChanged(Editable s) {
                        ensureConcreteObject();
                        object = Ut.setField(fieldPath, object, s == null ? "" : s.toString());
                    }
                });
            }
            return;
        }

        // Mode inflate (comme avant)
        rootView = LayoutInflater.from(context).inflate(R.layout.input_mt, null, false);
        til = rootView.findViewById(R.id.textField);
        et  = rootView.findViewById(R.id.id);

        if (til != null) til.setHint(attribut.getName());
        if (et != null) {
            if ("number".equals(baseType)) et.setInputType(InputType.TYPE_CLASS_NUMBER);
            if (v != null) et.setText(v);
        }

        applyUtilsInput(rootView);

        if (et != null) {
            et.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override public void afterTextChanged(Editable s) {
                    ensureConcreteObject();
                    object = Ut.setField(fieldPath, object, s == null ? "" : s.toString());
                }
            });
        }
    }

    private void buildTextArea(String v) {
        if (isProvided()) {
            rootView = providedRoot;
            til = findTil(rootView);
            et  = findEt(rootView);

            if (til != null) til.setHint(attribut.getName());
            if (et != null && v != null) et.setText(v);

            applyUtilsInput(rootView);

            if (et != null) {
                et.addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override public void afterTextChanged(Editable s) {
                        object = Ut.setField(fieldPath, object, s == null ? "" : s.toString());
                    }
                });
            }
            return;
        }

        rootView = LayoutInflater.from(context).inflate(R.layout.input_text_area_mt, null, false);
        til = rootView.findViewById(R.id.textInputLayout);
        et  = rootView.findViewById(R.id.editTextMessage);

        if (til != null) til.setHint(attribut.getName());
        if (et != null && v != null) et.setText(v);

        // IDs custom textarea
        UtilsInput.setupMaterialInput(
                rootView, context, attribut.isRequierd(), null, null,
                R.id.textInputLayout, R.id.editTextMessage
        );

        if (et != null) {
            et.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override public void afterTextChanged(Editable s) {
                    object = Ut.setField(fieldPath, object, s == null ? "" : s.toString());
                }
            });
        }
    }

    private void buildPickDate(String v, String format, boolean stringMode) {
        if (isProvided()) {
            rootView = providedRoot;
            til = findTil(rootView);
            et  = findEt(rootView);

            if (til != null) til.setHint(attribut.getName());
            if (et != null) {
                if (v != null) {
                    if (format != null) et.setText(S.date(v, "yyyy-MM-dd'T'HH:mm:ss", format));
                    else et.setText(v);
                }
                final String fmt = (format == null ? "yyyy-MM-dd'T'HH:mm:ss" : format);
                et.setOnClickListener(v1 -> {
                    Composant c = new Composant(object, fieldPath, fmt);
                    if (stringMode) c.setDateString(true);
                    c.showDateTimePicker(context, attribut.getName(), et, "edite", fmt);
                });
            }

            applyUtilsInput(rootView);
            return;
        }

        rootView = LayoutInflater.from(context).inflate(R.layout.pick_date, null, false);
        til = rootView.findViewById(R.id.textField);
        et  = rootView.findViewById(R.id.id);

        if (til != null) til.setHint(attribut.getName());
        if (et != null) {
            if (v != null) {
                if (format != null) et.setText(S.date(v, "yyyy-MM-dd'T'HH:mm:ss", format));
                else et.setText(v);
            }
            final String fmt = (format == null ? "yyyy-MM-dd'T'HH:mm:ss" : format);
            et.setOnClickListener(v1 -> {
                Composant c = new Composant(object, fieldPath, fmt);
                if (stringMode) c.setDateString(true);
                c.showDateTimePicker(context, attribut.getName(), et, "edite", fmt);
            });
        }

        applyUtilsInput(rootView);
    }

    private void buildPickTime(String v) {
        if (isProvided()) {
            rootView = providedRoot;
            til = findTil(rootView);
            et  = findEt(rootView);

            if (til != null) til.setHint(attribut.getName());
            if (et != null) {
                if (v != null) et.setText(v);
                et.setOnClickListener(v12 ->
                        new Composant(object, fieldPath, "yyyy-MM-dd'T'HH:mm:ss")
                                .showTimePicker(context, attribut.getName(), et, "edite"));
            }

            applyUtilsInput(rootView);
            return;
        }

        rootView = LayoutInflater.from(context).inflate(R.layout.pick_date, null, false);
        til = rootView.findViewById(R.id.textField);
        et  = rootView.findViewById(R.id.id);

        if (til != null) til.setHint(attribut.getName());
        if (et != null) {
            if (v != null) et.setText(v);
            et.setOnClickListener(v12 ->
                    new Composant(object, fieldPath, "yyyy-MM-dd'T'HH:mm:ss")
                            .showTimePicker(context, attribut.getName(), et, "edite"));
        }

        applyUtilsInput(rootView);
    }

    private void buildSwitch(String v) {
        if (isProvided()) {
            rootView = providedRoot;
            SwitchMaterial sw = findSwitch(rootView);

            if (sw != null) {
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
            return;
        }

        rootView = LayoutInflater.from(context).inflate(R.layout.switch_material, null, false);
        SwitchMaterial sw = rootView.findViewById(R.id.id_switch);
        if (sw != null) {
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
    }

    private void buildDropInput(String v, String rawType) {
        if (isProvided()) {
            rootView = providedRoot;
            til = findTil(rootView);
            et  = findEt(rootView);
            ImageView ups = findUps(rootView);

            if (til != null) til.setHint(attribut.getName());
            if (et != null && v != null) et.setText(v);

            applyUtilsInput(rootView);

            // En mode injection, on évite d'ajouter nos listeners "select" pour laisser le Fragment décider.
            if (!disableBuiltInSelectHandlersWhenProvided) {
                attachDefaultSelectHandlers(et, ups, rawType);
            }
            return;
        }

        rootView = LayoutInflater.from(context).inflate(R.layout.inpu_drop_mt, null, false);
        til = rootView.findViewById(R.id.textField);
        et  = rootView.findViewById(R.id.id);
        ImageView ups = rootView.findViewById(R.id.ups);

        if (til != null) til.setHint(attribut.getName());
        if (et != null && v != null) et.setText(v);

        applyUtilsInput(rootView);

        // Charger dynamiquement si besoin (request)
        if (attribut.getRequest()!=null &&
                attribut.getRequest().getUrl()!=null &&
                attribut.getRequest().getAttribut()==null &&
                !isSelectType(rawType)) {
            loadValuesFromRequest(attribut);
        }

        attachDefaultSelectHandlers(et, ups, rawType);
    }

    // -------------------- Sélecteurs par défaut (mode inflate) --------------------

    private void attachDefaultSelectHandlers(TextInputEditText etLocal, ImageView ups, String rawType) {
        if (etLocal == null) return;
        final boolean isSelect = isSelectType(rawType);

        etLocal.setOnClickListener(v14 -> {
            EditeService.inputEditText = etLocal; // pour cohérence si SelectActivity est utilisée
            if (isSelect) {
                if (attribut.getValues()!=null && attribut.getValues().size()<=7) {
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
            } else if (attribut.getValues()!=null && !attribut.getValues().isEmpty()) {
                // Popup simple
                List<Object> objects = attribut.getValues();
                PopupMenu pop = S.popupMenu(
                        ups != null ? ups : etLocal,
                        attribut.getLabel()==null
                                ? objects.stream().map(o -> o == null ? "" : String.valueOf(o)).toArray(String[]::new)
                                : objects.stream().filter(Objects::nonNull)
                                .map(o -> String.valueOf(Ut.getValue(o, attribut.getLabel())))
                                .toArray(String[]::new)
                );
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        int idx = item.getItemId() - 1;
                        if (idx < 0 || idx >= objects.size()) return false;
                        Object chosen = objects.get(idx);
                        etLocal.setText(item.getTitle());

                        String fs = fieldPath.contains(":") ? fieldPath.substring(0, fieldPath.indexOf(":")) : fieldPath;
                        Object val = (attribut.getField() == null) ? chosen : Ut.getValue(chosen, attribut.getField());
                        object = Ut.setField(fs, object, val);
                        return true;
                    }
                });
            } else if (attribut.getRequest()!=null && attribut.getRequest().getNavigateClasse()!=null) {
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
            String v = url.substring(url.indexOf("/{") + 1).replace("{", "").replace("}", "");
            String base = url.substring(0, url.indexOf("/{"));
            Object param = getValue(object, v);
            url = base + "/" + (param != null ? String.valueOf(param).replace(".0", "") : "0");
        }
        TextView tmp = new TextView(context);
        HttpApi api = new HttpApi(url, tmp, context);
        TextViewHandler tvh = new TextViewHandler(tmp);
        String finalUrl = url;
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

    // ---- helpers IDs / vues ----
    private boolean isProvided() { return providedRoot != null; }

    private TextInputLayout findTil(View root) {
        int tilId = (tilIdOverride != null) ? tilIdOverride : R.id.textField;
        return root.findViewById(tilId);
    }

    private TextInputEditText findEt(View root) {
        int etId = (editIdOverride != null) ? editIdOverride : R.id.id;
        return root.findViewById(etId);
    }

    private ImageView findUps(View root) {
        int upsId = (upsIdOverride != null) ? upsIdOverride : R.id.ups;
        return root.findViewById(upsId);
    }

    private SwitchMaterial findSwitch(View root) {
        int swId = (switchIdOverride != null) ? switchIdOverride : R.id.id_switch;
        return root.findViewById(swId);
    }

    private void applyUtilsInput(View root) {
        // Utilise l’overload si IDs custom fournis, sinon le standard
        if (tilIdOverride != null || editIdOverride != null) {
            int tilId = (tilIdOverride != null) ? tilIdOverride : R.id.textField;
            int etId  = (editIdOverride != null) ? editIdOverride : R.id.id;
            UtilsInput.setupMaterialInput(root, context, attribut.isRequierd(), null, null, tilId, etId);
        } else {
            UtilsInput.setupMaterialInput(root, context, attribut.isRequierd(), null, null);
        }
    }
}
