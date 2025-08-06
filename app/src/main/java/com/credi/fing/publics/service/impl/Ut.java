package com.credi.fing.publics.service.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.credi.fing.R;
import com.credi.fing.publics.OnClickView;
import com.credi.fing.publics.Style;
import com.credi.fing.publics.ecouteur.CombinedTouchListener;
import com.credi.fing.publics.service.ApiClient;
import com.credi.fing.publics.service.OnDialogViewClick;
import com.credi.fing.publics.utils.DateObject;
import com.credi.fing.publics.utils.Dialogue;
import com.credi.fing.publics.utils.OnSwipeTouchListener;
import com.credi.fing.publics.utils.S;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Ut {
    /**
     * Renvoie la vue correspondant à son nom de ressource.
     *
     * @param context La vue racine (ou une Activity) où chercher.
     * @param viewIdString Le nom de l’ID (par exemple "id_view", sans le "@+id/").
     * @return La View trouvée, ou null si non trouvée.
     */
    public static View findViewByString(Context context, String viewIdString) {
        // 1. Récupérer l’identifiant numérique à partir du nom
        @SuppressLint("DiscouragedApi") int resId = context.getResources()
                .getIdentifier(viewIdString, "id", context.getPackageName());
        if (resId == 0) {
            // pas de telle ressource
            return null;
        }
        // 2. Retourner la vue
        return ((Activity)context).findViewById(resId);
    }

    private static Object setObjectFieldValue(String at, Object objet, Object nouvelleValeur) {
        if(objet==null)return objet;
        String[] segments = at.split(":");
        Field field = null;
        Object currentObject = objet;

        try {
            for (int i = 0; i < segments.length - 1; i++) {
                field = currentObject.getClass().getDeclaredField(segments[i]);
                field.setAccessible(true);
                Object nestedObject = field.get(currentObject);

                if (nestedObject == null) {
                    nestedObject = field.getType().getDeclaredConstructor().newInstance();
                    field.set(currentObject, nestedObject);
                }

                currentObject = nestedObject;
            }

            field = currentObject.getClass().getDeclaredField(segments[segments.length - 1]);
            field.setAccessible(true);
            Object convertedValue = convertValue(field.getType(), nouvelleValeur);
            field.set(currentObject, convertedValue);

            return objet;  // Assurez-vous de retourner l'objet original ici

        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("Erreur dans setObjectFieldValue : " + e.getMessage());
            return null; // Si une exception est levée, retourner null pourrait poser problème
        }
    }

    public static Object createInstance(Class<?> t) {
        try {
            return t.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }
        // Vérifie que tous les caractères sont des espaces ou équivalents
        return s.chars().allMatch(Character::isWhitespace);
    }

    private static final List<String> SPECIAL_DATE_FIELDS = Arrays.asList("createdAt", "modifiedAt", "deletedAt");

    /**
     * Convertit la valeur {@code nouvelleValeur} au type de champ {@code fieldType}.
     *
     * @param fieldType     Type cible vers lequel on veut convertir
     * @param nouvelleValeur Valeur initiale à convertir
     * @return La valeur convertie, ou {@code null} en cas d’échec ou si {@code nouvelleValeur} est null
     */
    public static Object convertValue(Class<?> fieldType, Object nouvelleValeur) {
        // Si la valeur est nulle, on retourne null immédiatement
        if (nouvelleValeur == null) {
            return null;
        }
        //System.out.println(fieldType.getSimpleName()+"   getSimpleName  "+nouvelleValeur.getClass().getSimpleName());
        if(nouvelleValeur.getClass() == fieldType){
            return nouvelleValeur;
        }

        // Conversion pour le type Boolean / boolean
        if (fieldType == boolean.class || fieldType == Boolean.class) {
            String bol = nouvelleValeur.toString().trim();
            if (bol.equalsIgnoreCase("n") || bol.equalsIgnoreCase("non")) {
                bol = "false";
            }
            if (bol.equalsIgnoreCase("o") || bol.equalsIgnoreCase("oui")) {
                bol = "true";
            }
            return Boolean.parseBoolean(bol);
        }
        // Conversion pour le type Integer / int
        else if (fieldType == int.class || fieldType == Integer.class) {
            String strVal = cleanupNumericString(nouvelleValeur.toString());
            return Integer.parseInt(isBlank(strVal) ? "0" : strVal);
        }
        // Conversion pour le type Long / long
        else if (fieldType == long.class || fieldType == Long.class) {
            String strVal = cleanupNumericString(nouvelleValeur.toString());
            return Long.parseLong(isBlank(strVal) ? "0" : strVal);
        }
        // Conversion pour le type Float / float
        else if (fieldType == float.class || fieldType == Float.class) {
            String strVal = cleanupNumericString(nouvelleValeur.toString());
            return Float.parseFloat(isBlank(strVal) ? "0" : strVal);
        }
        // Conversion pour le type Double / double
        else if (fieldType == double.class || fieldType == Double.class) {
            try {
                String strVal = cleanupNumericString(nouvelleValeur.toString().toUpperCase().replace("H", ""));
                return Double.parseDouble(isBlank(strVal) ? "0" : strVal);
            } catch (Exception e) {
                // e.printStackTrace(); // En production, journaliser plutôt que d’afficher la stack
                return null;
            }
        }
        // Conversion pour le type String
        else if (fieldType == String.class) {
            // On nettoie le ".0" éventuel
            return nouvelleValeur.toString().replace(".0", "");
        }
        // Conversion pour Date ou Timestamp
        else if (fieldType == Date.class || fieldType == Timestamp.class) {
            return convertToDateOrTimestamp(fieldType, nouvelleValeur);
        }
        // Conversion pour Enum
        else if (fieldType.isEnum()) {
            return Enum.valueOf((Class<Enum>) fieldType, nouvelleValeur.toString());
        }
        // Conversion pour d’éventuelles Map complexes (LinkedTreeMap ou LinkedHashMap)
        else {
            // Gestion du cas LinkedTreeMap
            if (nouvelleValeur instanceof LinkedTreeMap) {
                //System.out.println("== LinkedTreeMap detected ==");
                return convertLinkedMap(fieldType, (Map<?, ?>) nouvelleValeur);
            }
            // Gestion du cas LinkedHashMap
            if (nouvelleValeur instanceof LinkedHashMap) {
                //System.out.println("== LinkedHashMap detected ==");
                return convertLinkedMap(fieldType, (Map<?, ?>) nouvelleValeur);
            }
            // Dans tous les autres cas, on retourne la valeur telle quelle
            // System.out.println("== Aucune conversion particulière : " + nouvelleValeur);
            return nouvelleValeur;
        }
    }

    /**
     * Nettoie une chaîne numérique (par ex. enlève ".0" en fin).
     */
    private static String cleanupNumericString(String s) {
        return s.replace(".0", "").trim();
    }

    /**
     * Convertit un objet vers un type Date ou Timestamp.
     */
    private static Object convertToDateOrTimestamp(Class<?> fieldType, Object value) {
        // Si c’est déjà une instance de Timestamp ou Date et que le type attendu match,
        // on la retourne telle quelle
        if (fieldType == Timestamp.class && value instanceof Timestamp) {
            return value;
        }
        if (fieldType == Date.class && value instanceof Date) {
            return value;
        }

        // On tente plusieurs formats de dates successivement
        // 1) Format ISO avec ms + timezone : yyyy-MM-dd'T'HH:mm:ss.SSSZ
        // 2) Format par défaut d’un Date.toString() en anglais : EEE MMM dd HH:mm:ss zzz yyyy
        // 3) Format français classique : dd/MM/yyyy
        // 4) Re-chute sur le format ISO si besoin.
        String dateStr = value.toString().trim();
        // Supprimer la dernière occurrence :XX si besoin
        // (exemple : "2023-01-01T10:00:00.000:12" -> "2023-01-01T10:00:00.00012" ??)
        dateStr = dateStr.replaceAll(":(\\d\\d)$", "$1");

        // Formats qu’on va essayer
        List<SimpleDateFormat> formats = new ArrayList<>();
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        formats.add(isoFormat);

        // Format "EEE MMM dd HH:mm:ss zzz yyyy"
        formats.add(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));

        // Format "dd/MM/yyyy"
        formats.add(new SimpleDateFormat("dd/MM/yyyy"));

        // On essaie tour à tour
        Date parsedDate = null;
        for (SimpleDateFormat sdf : formats) {
            try {
                parsedDate = sdf.parse(dateStr);
                break;
            } catch (ParseException e) {
                // On ignore et on tente le suivant
            }
        }

        // Si aucune date n’a été parsée, on retente le format ISO pour la forme
        if (parsedDate == null) {
            try {
                parsedDate = isoFormat.parse(dateStr);
            } catch (ParseException e) {
                // e.printStackTrace(); // En production, journaliser plutôt que d’afficher la stack
                return null; // échec final
            }
        }

        // On convertit en Timestamp si besoin
        if (fieldType == Timestamp.class && parsedDate != null) {
            return new Timestamp(parsedDate.getTime());
        }
        return parsedDate;
    }

    /**
     * Convertit récursivement un {@link Map} (par ex. {@link LinkedTreeMap} ou {@link LinkedHashMap})
     * en une instance du type {@code fieldType}.
     */
    private static Object convertLinkedMap(Class<?> fieldType, Map<?, ?> map) {
        try {
            // Créer une instance du type cible (nécessite un constructeur sans argument)
            Object instance = fieldType.getDeclaredConstructor().newInstance();
            System.out.println("instance = "+instance.getClass().getSimpleName());
            // Récupérer tous les champs du type cible pour pouvoir y écrire
            List<String> fields = Arrays.stream(fieldType.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());

            // On itère sur les entrées de la map
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();

                // Si la clé est de la forme "idNomDeLaClasse", on la remplace par "idServeur"
                if (key.equals("id" + instance.getClass().getSimpleName())||((key+"Dm").equals("id" + instance.getClass().getSimpleName()))) {
                    key = "idServeur";
                }
                //System.out.println(key+"  key "+fields.contains(key)+" fields.contains(key) "+fields);
                // Vérification d’existence du champ
                if (!fields.contains(key)) {
                    // Champ inexistant, on ignore ou on retourne null selon votre besoin
                    // return null;
                    continue;
                }else

                if (value != null) {
                    Field field = fieldType.getDeclaredField(key);
                    field.setAccessible(true);

                    // Si le champ fait partie de SPECIAL_DATE_FIELDS, on suppose que la valeur est un datetime ISO
                    if (SPECIAL_DATE_FIELDS.contains(key)) {
                        String dateString = value.toString();

                        ZonedDateTime ss= DateObject.parseFlexible(dateString);
                        Date dat=DateObject.convertZonedDateTimeToDate(ss);

                        try {
                           // Convertir en Timestamp
                            Timestamp timestamp = DateObject.convertDateToTimestamp(dat);
                            field.set(instance, timestamp);
                        } catch (DateTimeParseException e) {
                            // Gérer l'exception, par exemple logger l'erreur ou utiliser une valeur par défaut
                            //System.err.println("Erreur de parsing de la date : " + dateString);
                            e.printStackTrace();
                        }

                        /*OffsetDateTime odt = OffsetDateTime.parse(value.toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                        // Convertir en Timestamp (exemple via une classe utilitaire perso)
                        Timestamp timestamp = new Timestamp(Date.from(odt.toInstant()).getTime());
                        field.set(instance, timestamp);*/

                    } else {
                        // Sinon, on convertit la valeur selon le type du champ
                        // Correction : comparer field.getType() et non field.getClass()
                        Class<?> targetType;
                        if ("idServeur".equals(key) && (field.getType() == int.class || field.getType() == Integer.class)) {
                            targetType = Integer.class;
                        } else if ("idServeur".equals(key)) {
                            targetType = Long.class;
                        } else {
                            targetType = field.getType();
                        }

                        // System.out.println(targetType+" _targetType_  "+value);
                        Object convertedValue = convertValue(targetType, value);
                        field.set(instance, convertedValue);
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace(); // En production, privilégier un logger
            return null;
        }
    }

    public static Object setField(String attribut, Object objet, Object nouvelleValeur) {
        if (objet == null) return objet;
        if (attribut.contains(":")) {
            return setObjectFieldValue(attribut, objet, nouvelleValeur);
        }
        System.out.println(" _js "+js(objet));
        // Traitement spécifique pour LinkedTreeMap et LinkedHashMap
        if (objet instanceof LinkedTreeMap || objet instanceof LinkedHashMap) {
            Map<Object, Object> map = (Map<Object, Object>) objet;
            System.out.println(" _mp "+map);
            // Si la clé existe déjà, on tente de convertir la nouvelle valeur pour correspondre au type de l'existant
            if (map.containsKey(attribut) && map.get(attribut) != null&&nouvelleValeur!=null) {
                Object existing = map.get(attribut);
                if (nouvelleValeur!=null&&!existing.getClass().getSimpleName().equals(nouvelleValeur.getClass().getSimpleName())) {
                    nouvelleValeur = convertValue(existing.getClass(), nouvelleValeur);
                }
            }
            map.put(attribut, nouvelleValeur);
            System.out.println(" wqp "+map);
            return objet;
        }

        // Traitement classique par réflexion pour les POJO
        Class<?> t = objet.getClass();
        try {
            Field field = t.getDeclaredField(attribut);
            field.setAccessible(true); // Pour accéder à un champ privé
            if (nouvelleValeur!=null&&field.getType().getSimpleName().equals(nouvelleValeur.getClass().getSimpleName())) {
                field.set(objet, nouvelleValeur);
            } else {
                System.out.println(field.getName() + " field.getType() " + field.getType() + "     " + nouvelleValeur);
                Object convertedValue =nouvelleValeur!=null? convertValue(field.getType(), nouvelleValeur):null;
                //System.out.println(" converted " + js(convertedValue));
                field.set(objet, convertedValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return objet; // Retourner objet pour éviter des problèmes ultérieurs
        }

        return objet;
    }

    private static List<String> chaineValue(String fields){
        List<String> list=new ArrayList<>();
        String rest=fields;
        while (!rest.isEmpty()){
            int i=rest.indexOf("\\(");
            if(i!=-1){
                String av=rest.substring(0,i),ap=rest.substring(i);
                if(!av.isEmpty()){
                    list.add(av);
                }
                rest=ap;
                i=ap.indexOf(")");
                if(i!=-1){
                    av=rest.substring(0,i+1);
                    rest=rest.substring(i+1);
                    if(!av.isEmpty()){
                        list.add(av);
                    }
                }else {
                    list.add(ap);
                    rest="";
                }
            }else {
                list.add(rest);
                rest="";
            }
        }
        return list;
    }

    private static String getValuesWithText(Object object, String fieldName){
        List<String> values=chaineValue(fieldName);
        System.out.println(fieldName+"   "+values);
        String value="";
        for (String s:values){
            if(s.startsWith("\\(")){
                String sx=s.replace("\\(","").replace(")","");
                value=value+" "+sx;
            }else {
                value=value+" "+getAllValues(object,s);
            }
        }
        return value.trim();
    }
    public static String formatMontant(double montant) {
        if(montant==0){
            return "";
        }
        // 1. On définit des symboles personnalisés :
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');    // virgule pour la partie décimale
        symbols.setGroupingSeparator('.');   // point pour les milliers

        // 2. On crée un format du style "#,##0.00" :
        //    - #,##0 pour gérer les milliers avec au moins un chiffre à gauche
        //    - .00 pour deux décimales obligatoires
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        // 3. On applique le format
        return df.format(montant);
    }
    public static String getAllValues(Object object, String fieldName) {
        if (fieldName == null) return null;
        if(fieldName.contains("\\(")){
            return getValuesWithText(object,fieldName);
        }
        String value = "";
        if (fieldName.contains("|")) {
            String[] vs = fieldName.split("\\|");
            for (String v : vs) {
                if (v.contains("&")) {
                    String[] cs = v.split("&");
                    for (String c : cs) {
                        String type="string";
                        if (c.contains("?")) {
                            type = c.substring(c.indexOf("?") + 1);
                            c = c.substring(0, c.indexOf("?"));
                        }
                        Object o=getValue(object, c);
                        value = value.isEmpty() ? format(o,type)  : value + " " + format(o,type);
                    }
                } else {
                    String type="string";
                    if (v.contains("?")) {
                        type = v.substring(v.indexOf("?") + 1);
                        v = v.substring(0, v.indexOf("?"));
                    }
                    Object o=getValue(object, v);
                    value = value.isEmpty() ? format(o,type)  : value + " " + format(o,type);
                }
                if(!value.isEmpty()){
                    return value;
                }
            }

        } else {
            if (fieldName.contains("&")) {
                String[] cs = fieldName.split("&");
                for (String c : cs) {
                    String type="string";
                    if (c.contains("?")) {
                        type = c.substring(c.indexOf("?") + 1);
                        c = c.substring(0, c.indexOf("?"));
                    }
                    Object o=getValue(object, c);
                    value = value.isEmpty() ? format(o,type)  : value + " " + format(o,type);
                }
            } else {
                String type="string";
                if (fieldName.contains("?")) {
                    type = fieldName.substring(fieldName.indexOf("?") + 1);
                    fieldName = fieldName.substring(0, fieldName.indexOf("?"));
                }
                //  value = getValue(object, fieldName) + "";
                Object o=getValue(object, fieldName);
                value = value.isEmpty() ? format(o,type)  : value + " " + format(o,type);
            }
        }

        return value.equals("null")?"":value;
    }
    public static String enTel(String s){
        int n=s.length();
        if(n<=3) return s;
        String ss=null,ap=s;
        // x=ap.length();
        while(n>2){
            ss=ss!=null?ap.substring(n-2)+" "+ss:ap.substring(n-2);
            ap=ap.substring(0,n-2);
            n=ap.length();
        }
        if(!ap.equals("")) ss=ap+" "+ss;

        return ss;
    }
    private static String format(Object o, String type) {
        String text=o!=null?o.toString():"";
        switch (type) {
            case "number":
                return S.en3(text)
                        ;
            case "tel":
                return enTel(text);
            default:
                if(type.contains("date")&&text!=null&&!text.isEmpty()){
                    String forma="dd MMM yyyy HH:mm:ss";
                    int i=type.indexOf(":");
                    if(i!=-1){
                        forma=type.substring(i+1);
                    }
                    return S.date(text,"yyyy-MM-dd HH:mm:ss.SSS",forma);
                }
                return text;
        }
    }
    public static Class<?> getValueType(Object object, String fieldName) {
        if (fieldName.contains(":")) {
            fieldName = fieldName.substring(0, fieldName.indexOf(":"));
        }
        Class<?> t = object.getClass();
        //System.out.println(object);
        Field field = null;
        try {
            field = t.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.out.println(t.getSimpleName()+" fieldName "+fieldName);
            throw new RuntimeException(e);
        }
        field.setAccessible(true); // Pour accéder à un champ privé
        return field.getType();

    }

    public static Object getFirstValue(Object object, String fieldNames){
        if(!fieldNames.contains("|")){
            return getValue(object,fieldNames);
        }
        String t[] =fieldNames.split("\\|");
        for (String fieldName:t){
            Object o=getValue(object,fieldName);
            if(o!=null&&!o.toString().isEmpty()){
                return o;
            }
        }
        return null;
    }

    private static Object getValues(Object object, String fieldName) {
        if (fieldName.contains(":")) {
            Object curent = null;
            String[] t = fieldName.split(":");
            int i = 0;
            for (String f : t) {
                curent = getValue(i == 0 ? object : curent, f);
                if (curent == null) {
                    return null;
                }
                i++;
            }
            return curent;
        }
        return null;
    }

    public static Object getValue(Object object, String fieldName) {
        if(object==null)return null;
        //try {
        if(fieldName.contains("|")){
            String fild[]=fieldName.split("\\|");
            for (String f:fild){
                Object os=getValue2(object,f);
                if(os!=null){
                    return os;
                }
            }
        }

        return getValue2(object,fieldName);

    }

    public static Object getValue2(Object object, String fieldName) {
        if(object==null)return null;
        //try {
        if (fieldName.contains(":")) {
            return getValues(object, fieldName);
        }
        // Récupérer la classe de l'objet
        Class<?> clazz = object.getClass();
        List<String> fields = Arrays.stream(clazz.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.toList());
        System.out.println((!fields.contains(fieldName)) + "  " + fieldName + "  " + object.getClass().getName() + "== field== " + fields);
        if (!fields.contains(fieldName)) {
            if (object instanceof LinkedTreeMap) {
                LinkedTreeMap<?, ?> map = (LinkedTreeMap<?, ?>) object;
                try {
                    // Object instance = fieldType.getDeclaredConstructor().newInstance(); // Créer une instance du type cible
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key = (String) entry.getKey();
                        Object value = entry.getValue();
                        if (key.equals(fieldName)) {
                            return value;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
            if (object instanceof LinkedHashMap) {
                LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) object;
                try {
                    // Object instance = fieldType.getDeclaredConstructor().newInstance(); // Créer une instance du type cible
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        String key = (String) entry.getKey();
                        Object value = entry.getValue();
                        if (key.equals(fieldName)) {
                            return value;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
            return null;
        }

        try {
            // Récupérer le champ spécifié
            Field field = clazz.getDeclaredField(fieldName);
            // System.out.println("=fds=" + field.getName());
            // Rendre le champ accessible s'il est privé
            field.setAccessible(true);

            // Récupérer la valeur du champ pour l'objet donné
            Object value = field.get(object);
             System.out.println(value + "==vav==" + object);
            // Vérifier si la valeur est une instance de types primitifs gérables directement
               /* if (value instanceof String || value instanceof Date || value instanceof Enum) {
                    return value;
                }*/
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Vérifier si la valeur est un objet non primitif et non null
          /*  if (value != null && !isPrimitiveOrWrapper(value.getClass())) {
                // Récupérer la valeur de l'attribut dans l'objet imbriqué
                // Remarque : Il faut spécifier l'attribut que vous voulez récupérer dans l'objet imbriqué
                // Pour cela, ajustez `getFieldName` selon votre logique pour déterminer le bon nom d'attribut
                String nestedFieldName = getFieldNameForNestedObject(fieldName, value);
                //  System.out.println(nestedFieldName+"==nestedFieldName=="+value.getClass());
                return getValue(value, nestedFieldName);
            }*/

        // Retourner la valeur sous forme de chaîne de caractères

        return null;

    }

    private static String getFieldNameForNestedObject(String parentFieldName, Object nestedObject) {
        List<String> colon = Arrays.stream(nestedObject.getClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        if (colon.contains(parentFieldName)) return parentFieldName;
        return colon.get(0);
    }

    // Vérifier si la classe est de type primitif ou son équivalent wrapper
    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Double.class ||
                clazz == Float.class ||
                clazz == Boolean.class ||
                clazz == Character.class ||
                clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Date.class ||
                clazz == Timestamp.class ||
                clazz == String.class ||
                clazz == Enum.class;
    }

    public static String js(Object o) {
        if(o==null)return null;
        return ApiClient.gson.toJson(o);
    }

    public static <T> List<T> toObject(String object) {
        Type listType = new TypeToken<List<T>>(){}.getType();
        List<T> list = new Gson().fromJson(object, listType);
        return list;
    }




    public static Object creatObject(Object object, Class<?> t) {
        Object o = createInstance(t);
        List<String> field = Arrays.stream(t.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.toList());
        if (object instanceof LinkedTreeMap) {
            System.out.println(" ici "+o);
            LinkedTreeMap<?, ?> map = (LinkedTreeMap<?, ?>) object;
            // Object instance = fieldType.getDeclaredConstructor().newInstance(); // Créer une instance du type cible
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                try {
                    String key = (String) entry.getKey();
                    System.out.println(key+"  x==> "+entry.getValue());
                    if (field.contains(key) || key.equals("id" + t.getSimpleName())) {
                        Object value = entry.getValue();
                        if (field.contains("idServeur")) {
                            o = setField(key.equals("id" + t.getSimpleName()) ? "idServeur" : key, o, value);
                        } else {
                            o = setField(key, o, value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //return null;
                }
            }
            System.out.println(" rt12 "+js(o));
            return o;
        }
        if (object instanceof LinkedHashMap) {
            System.out.println(" ici2 "+o);
            LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) object;
            // Object instance = fieldType.getDeclaredConstructor().newInstance(); // Créer une instance du type cible
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                try {
                    String key = (String) entry.getKey();
                    if (field.contains(key) || key.equals("id" + t.getSimpleName())) {
                        Object value = entry.getValue();
                        if (field.contains("idServeur")) {
                            o = setField(key.equals("id" + t.getSimpleName()) ? "idServeur" : key, o, value);
                        } else {
                            o = setField(key, o, value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // return null;
                }
            }
            System.out.println(" retour "+js(o));
            return o;
        }
        try {
            return object;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static Object fromJs(String js, Class<?> c) {
        Object object=null;
        try {
            object=ApiClient.gson.fromJson(js, c);
        }catch (Exception ex){
            if(ex.getMessage().contains("JsonSyntaxException")||ex.getMessage().contains("ParseException")||ex.getMessage().contains("Failed parsing")){
                try {
                    object=ApiClient.gson2.fromJson(js, c);
                }catch (Exception x) {
                    x.printStackTrace();
                }
            }else {
                ex.printStackTrace();
                System.out.println(" erreur = "+ex.getMessage());
            }
        }
        //System.out.println(object+" entrer js = "+js);
        return object;
    }

    public static String listJs(List<Object> objects) {
        return js(new Cl(objects));
    }


    public static List<Object> listFromJs(String js) {
        return ((Cl) fromJs(js, Cl.class)).getDatas();
    }

    public static List<Class<?>> classFrmPackag() {
        return Arrays.asList();
    }

    public static List<Attribut> colonnes(Class<?> object) {
        if (object == null) return new ArrayList<>();
        List<Attribut> columns = Arrays.stream(object.getDeclaredFields())
                .filter(f -> !List.class.isAssignableFrom(f.getType())) // Exclut les champs de type List ou sous-types de List
                .peek(f -> f.setAccessible(true)) // Rendre le champ accessible si besoin
                .map(c -> {
                    Attribut attribut = new Attribut(c.getName(), c.getName(), c.getType().getName(), c.getType(), false);
                    return attribut;
                })
                .collect(Collectors.toList());
        System.out.println(object.getSimpleName() + "= getSimpleName===" + columns.stream().map(d -> d.getColonne()).collect(Collectors.toList()));
        return columns;
    }

    public static View appliquerFontFamily(View view, String font, Context context) {
        try {
            String fontName = font.toString().substring(font.toString().indexOf("/") + 1);
            int fontResId = context.getResources().getIdentifier(fontName, "font", context.getPackageName());
            Typeface typeface = ResourcesCompat.getFont(context, fontResId);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTypeface(typeface);
                return textView;
            }
            if (view instanceof TextInputEditText) {
                if (view instanceof EditText) {
                    TextInputEditText textView = (TextInputEditText) view;
                    textView.setTypeface(typeface);
                    return textView;
                }
            }
            if (view instanceof EditText) {
                EditText textView = (EditText) view;
                textView.setTypeface(typeface);
                return textView;
            }
        } catch (Exception e) {
            Dialogue.neutreDialog(font + "   " + e.getMessage(), "erreur", context).show();
        }

        return view;
    }

    private static String codeColor(String color) {
        List<String> cd = Arrays.asList("#FF0000", "#000000", "#0000FF", "#008000", "#FFC0CB", "#6F4E37", "#8B0000");
        List<String> colr = Arrays.asList("ROUGE", "NOIRE", "BLEU", "VERT", "ROSE", "CAFE", "ROUGE SOMBRE");
        return cd.get(colr.indexOf(color));
    }

    public static View appliquerStyle(View view, Style style) {
        if (style == null) return view;
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (style.getFonte() != null) {
                textView = (TextView) appliquerFontFamily(textView, style.getFonte(), view.getContext());
            }
            if (style.getCouleur() != null) {
                textView.setTextColor(Color.parseColor(codeColor(style.getCouleur())));
            }
            textView.setAllCaps(style.isUpercase());
            if (style.getTaille() != null) {
                textView.setTextSize(style.getTaille());
            }
            if (style.getMargeLeft() != null) {
                textView.setPadding(style.getMargeLeft(), 0, 0, 0);
            }
            return textView;
        }
        if (view instanceof TextInputEditText) {
            TextInputEditText textView = (TextInputEditText) view;
            if (style.getFonte() != null) {
                textView = (TextInputEditText) appliquerFontFamily(textView, style.getFonte(), view.getContext());
            }
            if (style.getCouleur() != null) {
                textView.setTextColor(Color.parseColor(codeColor(style.getCouleur())));
            }
            textView.setAllCaps(style.isUpercase());
            if (style.getTaille() != null) {
                textView.setTextSize(style.getTaille());
            }
            if (style.getMargeLeft() != null) {
                textView.setPadding(style.getMargeLeft(), 0, 0, 0);
            }
            return textView;
        }
        if (view instanceof EditText) {
            EditText textView = (EditText) view;
            if (style.getFonte() != null) {
                textView = (EditText) appliquerFontFamily(textView, style.getFonte(), view.getContext());
            }
            if (style.getCouleur() != null) {
                textView.setTextColor(Color.parseColor(codeColor(style.getCouleur())));
            }
            textView.setAllCaps(style.isUpercase());
            if (style.getTaille() != null) {
                textView.setTextSize(style.getTaille());
            }
            if (style.getMargeLeft() != null) {
                textView.setPadding(style.getMargeLeft(), 0, 0, 0);
            }
            return textView;
        }

        return view;
    }

    public static View getView(Context context, int id) {
        return LayoutInflater.from(context).inflate(id, null, false);
    }
    public static int getColor(Context context,int color){
        return ContextCompat.getColor(context,color);
    }

    public static void setBackgroundTint(Context context,View view,int color){
        ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context, color));
        ViewCompat.setBackgroundTintList(view, colorStateList);
    }

    public static void setImageTint(ImageView icone,int color,Context context){
         ColorStateList tint = ContextCompat.getColorStateList(context, color);
         icone.setImageTintList(tint);
    }

    public static boolean equals(Object object1,Object object2,String field){
        if(object1!=null&&object2!=null){
            if(field==null){
                return Objects.equals(object1+"",object2+"");
            }
            Object v1=getValue(object1,field);
            if(v1!=null){
                Object v2=getValue(object2,field);
                //System.out.println("  =v1=  "+v2+"  "+v1+"  =v1=  "+Objects.equals(v2,v1));
                return Objects.equals(v2,v1);
            }
        }
        // System.out.println(object1+"  fls  "+object2+"    === "+field);
        return false;
    }

    public static boolean contient(List<Object> objects,Object object,String field){
        if(objects!=null&&object!=null)
            for (Object o:objects){
                if(equals(o,object,field)){
                    return true;
                }
            }
        return false;
    }
    public static int indexOf(List<Object> objects,Object object,String field){
        if(objects==null||!objects.isEmpty())return -1;
        int j=0;
        for (Object o:objects){
            if(equals(o,object,field)){
                return j;
            }
            j++;
        }
        return -1;
    }

    public static void plier(LinearLayout lmain) {
        List<View> viewList=new ArrayList<>();
        int k=lmain.getChildCount();
        for (int i=0;i<k;i++){
            viewList.add(lmain.getChildAt(i));
        }
        plier(viewList);
    }
    public static void deplier(LinearLayout lmain) {
        List<View> viewList=new ArrayList<>();
        int k=lmain.getChildCount();
        for (int i=0;i<k;i++){
            viewList.add(lmain.getChildAt(i));
        }
        deplier(viewList);
    }
    public static List<View>  allView(LinearLayout lmain) {
        List<View> viewList=new ArrayList<>();
        int k=lmain.getChildCount();
        for (int i=0;i<k;i++){
            viewList.add(lmain.getChildAt(i));
        }
       return viewList;
    }
    public static void sleep(int duration,OnClickView onClickView,View view){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(onClickView!=null)  onClickView.onClick(view,0);
            }
        }, duration);
    }
    public static void plier(List<View> views,int delay) {
        for (int i = 0; i < views.size(); i++) {
            final View view = views.get(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(view!=null)  view.setVisibility(View.VISIBLE);
                }
            }, delay * i);
        }
    }
    public static void plier(List<View> views) {
        int delay = 50; // délai en millisecondes
        for (int i = 0; i < views.size(); i++) {
            final View view = views.get(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(view!=null)  view.setVisibility(View.VISIBLE);
                }
            }, delay * i);
        }
    }
    public static void deplier(List<View> views) {
        int delay = 50; // délai en millisecondes
        int size = views.size();
        // Parcours en sens inverse
        for (int i = size - 1; i >= 0; i--) {
            final View view = views.get(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.GONE);
                }
            }, delay * (size - 1 - i));
        }
    }
    public static void swip(View view, OnClickView onClickView) {
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            public void onSwipeTop() {
                if (onClickView!=null){
                    onClickView.onClick(view,0);
                }
            }

            public void onSwipeRight() {
                if (onClickView!=null){
                    onClickView.onClick(view,1);
                }
            }

            public void onSwipeLeft() {
                if (onClickView!=null){
                    onClickView.onClick(view,2);
                }
            }

            public void onSwipeBottom() {
                if (onClickView!=null){
                    onClickView.onClick(view,3);
                }
            }

        });
    }

    public static void clickOnSwip(View view,int k,OnClickView onClickView){
        view.setOnTouchListener(new CombinedTouchListener(view.getContext(),k,  (v, action) -> {
            if (action == -2) {
                //System.out.println("Clic simple détecté");
                if (onClickView!=null){
                    onClickView.onClick(view,-2);
                }
            } else if (action == -1) {
                // System.out.println("Long press détecté");
                if (onClickView!=null){
                    onClickView.onClick(view,-1);
                }
            } else if (action == 0) {
                // System.out.println("Swipe vers le haut détecté");
                if (onClickView!=null){
                    onClickView.onClick(view,0);
                }
            } else if (action == 1) {
                // System.out.println("Swipe vers la droite détecté");
                if (onClickView!=null){
                    onClickView.onClick(view,1);
                }
            } else if (action == 2) {
                //System.out.println("Swipe vers la gauche détecté");
                if (onClickView!=null){
                    onClickView.onClick(view,2);
                }
            } else if (action == 3) {
                // System.out.println("Swipe vers le bas détecté");
                if (onClickView!=null){
                    onClickView.onClick(view,3);
                }
            }

        }));

    }
    public static View findView(View view, int id) {
        return view.findViewById(id);
    }
    public static void   bind(View binder,View child, int id) {
        child= binder.findViewById(id);
    }
    public static   void showSelectDialogue(final List<Object> data,
                                            Object sec,
                                            String label,
                                            String field,
                                            final Object object,
                                            Context context, Attribut attribut, OnDialogViewClick onDialogViewClick) {
        View dialogView = Ut.getView(context,R.layout.add_layout);
        LinearLayout lm=dialogView.findViewById(R.id.lmain);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();

        List<Object> selection;
        if(sec==null) selection=new ArrayList<>();
        else selection= (List<Object>) sec;

        SelectService selectService= new SelectService(context,data,label,attribut,onDialogViewClick)
                .setTitle("Sélectionnez".toUpperCase())
                .setMultiselect(attribut.getType()!=null&&attribut.getType().equalsIgnoreCase("multiSelect"))
                .setSelect(selection);

        View view=selectService.view(alertDialog);

        View btn1 = Ut.getView(context, R.layout.outline_bouton);
        MaterialButton mtbt1 = btn1.findViewById(R.id.outlinedButton);
        mtbt1.setText("Valider la sélection");
        lm.addView(view);
        lm.addView(btn1);
        if(!selectService.isMultiselect()){
            mtbt1.setVisibility(View.GONE);
        }

        alertDialog.show();
        mtbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                String fs=field.contains(":")?field.substring(0,field.indexOf(":")):field;
                Ut.setField(fs,object,selectService.getSelect());
                //Dialogue.neutreDialog(Ut.js(object),selectService.getSelect().size()+"",context).show();
            }
        });

    }



    static class Cl implements Serializable {
        private List<Object> datas;

        public Cl(List<Object> datas) {
            this.datas = datas;
        }

        public List<Object> getDatas() {
            return datas;
        }

        public void setDatas(List<Object> datas) {
            this.datas = datas;
        }
    }
}
