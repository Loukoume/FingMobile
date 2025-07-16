package com.credi.fing.publics.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.credi.fing.publics.repository.sqlite.Attribut;
import com.credi.fing.publics.repository.sqlite.DB;
import com.credi.fing.publics.repository.sqlite.MaBase;
import com.credi.fing.publics.repository.sqlite.Pj;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.S;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Repository{
    private Class<?> t;

    private Context context;

    private SQLiteDatabase bdd;
    private MaBase memoireDB;
    List<Attribut> attributs;
    List<Pj> allAttributs;
    private String entityPackage;
    List<com.credi.fing.publics.service.impl.Attribut> coln;
    List<String> columns;
    public Repository(Class<?> t, Context context) {
        this.t = t;
        this.context = context;
        attributs = new ArrayList<>();
        coln= Ut.colonnes(t);
        attributs.add(new Attribut("idLocal", "INTEGER", "PRIMARY KEY AUTOINCREMENT"));
        for (com.credi.fing.publics.service.impl.Attribut a:coln) {
            if(!a.getColonne().equals("idLocal")) attributs.add(new Attribut(a.getColonne(), a.getT().getSimpleName().toUpperCase(), ""));
        }
        memoireDB = new MaBase(context, DB.ENONCE_BDD, null,attributs,tableName(t));
    }
    public Repository(Class<?> t) {
        this.t = t;
        columns=Ut.colonnes(t).stream().map(c->c.getColonne())
                .collect(Collectors.toList());
        memoireDB = new MaBase(context, DB.ENONCE_BDD, null);
        coln= Ut.colonnes(t);
    }

    private String tableName(){
        return "TABLE_"+t.getSimpleName().toUpperCase();
    }
    private String tableName(Class<?> t){
        return "TABLE_"+t.getSimpleName().toUpperCase();
    }

    public void init(){
        allAttributs=new ArrayList<>();
        List<Class<?>> classList=Ut.classFrmPackag();
        for (Class<?> ts:classList){
            coln= Ut.colonnes(ts);
            attributs = new ArrayList<>();
            attributs.add(new Attribut("idLocal", "INTEGER", "PRIMARY KEY AUTOINCREMENT"));
            for (com.credi.fing.publics.service.impl.Attribut a:coln) {
                if(!a.getColonne().equals("idLocal")) attributs.add(new Attribut(a.getColonne(), a.getT().getSimpleName().toUpperCase(), ""));
            }
            allAttributs.add(new Pj(attributs,tableName(ts)));
        }
        memoireDB = new MaBase(context, DB.ENONCE_BDD, null, allAttributs);
    }

    public void open() {
        //on ouvre la BDD en écriture
        bdd = memoireDB.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        bdd.close();
    }

    private void addColonn(String err) {
        err = err.substring(0, err.indexOf("(code"));
        for (com.credi.fing.publics.service.impl.Attribut coln : coln) {
            String x=coln.getColonne();
            if (err.contains(x)) {
                bdd.execSQL("ALTER TABLE " + tableName() + " ADD COLUMN " + x + " "+coln.getType()+";");
                break;
            }
        }
    }
    private ContentValues putValues(Object o) throws Exception{
        ContentValues values = new ContentValues();
        for (com.credi.fing.publics.service.impl.Attribut a:coln) {
            Object v=Ut.getValue(o,a.getColonne());
            if(v!=null&&!Ut.isPrimitiveOrWrapper(v.getClass())){
                List<String> cs= Arrays.stream(v.getClass().getDeclaredFields())
                        .map(c->c.getName()).collect(Collectors.toList());
                if(!cs.contains("idLocal")){
                    //Faire un traitement si possible
                    Object id=Ut.getValue(v,"idServeur");
                    if(id!=null){
                        Repository repository=new Repository(v.getClass(),context);
                        v=repository.save(v);
                        id=Ut.getValue(v,"idLocal");
                        values.put(a.getColonne(),id.toString());
                    }
                }else {
                    Object id=Ut.getValue(v,"idLocal");
                    if(id==null){
                        id=Ut.getValue(v,"idServeur");
                        if(id!=null){
                            Repository repository=new Repository(v.getClass(),context);
                            v=repository.save(v);
                            id=Ut.getValue(v,"idLocal");
                        }else
                         throw new Exception("Trensien object "+v.getClass().getName()+" in "+o.getClass().getName()+" could not be saved. " +
                                "Persiste "+v.getClass().getName()+" first.");
                    }
                    values.put(a.getColonne(),id.toString());
                }
            }else if(v!=null){
                if(v.getClass() == Timestamp.class){
                    Timestamp timestamp = (Timestamp) v;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                    String formattedDate = sdf.format(timestamp);
                    values.put(a.getColonne(), formattedDate);
                }else
                    values.put(a.getColonne(),v!=null?v.toString():null);
            }
        }
         return values;
    }
    private List<Object> attributObject(Object o){
        List<Object> attribut=new ArrayList<>();
        for (com.credi.fing.publics.service.impl.Attribut a:coln) {
            Object v=Ut.getValue(o,a.getColonne());
            if(v!=null&&!isEntity(v.getClass())){
               Object  id=Ut.getValue(v,"idServeur");
                if(id!=null){
                    attribut.add(id);
                }
            }
        }
        return attribut;
    }

    public Object save(Object o) {
        open();
        ContentValues values = null;
        try {
            values = putValues(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long i;
        try {
            if(values.containsKey("idLocal")){
                Object v=values.get("idLocal");
                if(v!=null){
                    return update(v);
                }
                values.remove("idLocal");
            }
            i = bdd.insertOrThrow(tableName(), null, values);
            o=Ut.setField("idLocal",o,i);
        } catch (Exception ex) {
            if (ex.getMessage().contains("no such table")) {
                memoireDB.onCreate(bdd);
            } else {
                if (ex.getMessage().contains("no such column")) {
                    addColonn(ex.getMessage());
                } else memoireDB.onUpgrade(bdd, 0, 1);
            }

        }
        close();
       return o;
    }
    public Object update(Object o) {
        open();
        ContentValues values = null;
        try {
            values = putValues(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long i;
        try {
            Object v=values.get("idLocal");
            if(!values.containsKey("idLocal")|| Objects.equals(v,null)){
                throw new Exception("idLocal of "+o.getClass().getName()+" should not be null ");
            }
            i = bdd.update(tableName(), values,  "idLocal = " + v, null);
            o=Ut.setField("idLocal",o,i);
        } catch (Exception ex) {
            if (ex.getMessage().contains("no such table")) {
                memoireDB.onCreate(bdd);
            } else {
                if (ex.getMessage().contains("no such column")) {
                    addColonn(ex.getMessage());
                } else memoireDB.onUpgrade(bdd, 0, 1);
            }
        }
        close();
        return o;
    }
    public Object delete(Object o) {
        open();
        Object v=Ut.getValue(o,"idLocal");
        if(Objects.equals(v,null)){
            try {
                throw new Exception("idLocal of "+o.getClass().getName()+" should not be null");
            } catch (Exception e) {
               return o;
            }
         }
         List<Object> as=attributObject(o);
         for (Object b:as){
             delete(b);
         }
         bdd.delete(tableName(),   "idLocal = " + v, null);
         close();
        return o;
    }

    public List<Object> findAll(){
        open();
        List<Object> list = new ArrayList<>();
        try {
            Cursor cursor = bdd.query(tableName(), columns.toArray(new String[0]), null, null, null, null, null);
            Object o;
            if (cursor.moveToFirst()) {
                do {
                    o = cursorToObject(cursor);
                    list.add(o);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception ex) {
            if (ex.getMessage().contains("no such table")) {
                memoireDB.onCreate(bdd);
            } else {
                if (ex.getMessage().contains("no such column")) {
                    addColonn(ex.getMessage());
                } else memoireDB.onUpgrade(bdd, 0, 1);
            }
        }
        close();
        return list;
    }
    public Object findById(long id) {
        open();
        List<Object> os = new ArrayList<>();
        try {
            Cursor cursor = bdd.query(tableName(), columns.toArray(new String[0]),  "idLocal =" + id, null, null, null, null);
            Object o;
            if (cursor.moveToFirst() && cursor.isFirst()) {
                do {
                    o = cursorToObject(cursor);
                    os.add(o);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            if (e.getMessage().contains("no such column")) {
                addColonn(e.getMessage());
            } else memoireDB.onUpgrade(bdd, 0, 1);
        }
        close();
        return os.isEmpty() ? null : os.get(0);
    }

    public List<Object> findByAttribut(String attribut,String value) {
        open();
        List<Object> os = new ArrayList<>();
        try {
            String vlu=" like "+value;
            if (S.isInt(value)){
                vlu=" = "+value;
            }
            Cursor cursor = bdd.query(tableName(), columns.toArray(new String[0]),  attribut+vlu, null, null, null, null);
            Object o;
            if (cursor.moveToFirst() && cursor.isFirst()) {
                do {
                    o = cursorToObject(cursor);
                    if(o!=null) os.add(o);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
          /*  if (e.getMessage().contains("no such column")) {
                addColonn(e.getMessage());
            } else memoireDB.onUpgrade(bdd, 0, 1);*/
        }
        close();
        return  os;
    }
    public List<Object> findByAttribut(String attribut,String value,String condition) {
        open();
        List<Object> os = new ArrayList<>();
        try {
            String vlu=" "+condition+" "+value;
            Cursor cursor = bdd.query(tableName(), columns.toArray(new String[0]),  attribut+vlu, null, null, null, null);
            Object o;
            if (cursor.moveToFirst() && cursor.isFirst()) {
                do {
                    o = cursorToObject(cursor);
                    if(o!=null) os.add(o);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
          /*  if (e.getMessage().contains("no such column")) {
                addColonn(e.getMessage());
            } else memoireDB.onUpgrade(bdd, 0, 1);*/
        }
        close();
        return os;
    }

     private boolean isEntity(Class<?> c){
        List<Class<?>> classes=Ut.classFrmPackag();
        return classes.stream().filter(cs->Objects.equals(cs,c)).findFirst().isPresent();
     }
    private Object cursorToObject(Cursor c) {
        Object o = Ut.createInstance(t);
        int i=0;
        for (com.credi.fing.publics.service.impl.Attribut a:coln){
            Class<?> fieldType=a.getT();
            if (fieldType == boolean.class || fieldType == Boolean.class) {
                o=Ut.setField(a.getColonne(),o,(c.getInt(i) == 1));
            } else if (fieldType == int.class || fieldType == Integer.class) {
                o=Ut.setField(a.getColonne(),o,c.getInt(i));
            } else if (fieldType == long.class || fieldType == Long.class) {
                o=Ut.setField(a.getColonne(),o,c.getLong(i));
            } else if (fieldType == float.class || fieldType == Float.class) {
                o=Ut.setField(a.getColonne(),o,c.getFloat(i));
            } else if (fieldType == double.class || fieldType == Double.class) {
                o=Ut.setField(a.getColonne(),o,c.getDouble(i));
            } else if (fieldType == String.class) {
                o=Ut.setField(a.getColonne(),o,c.getString(i));
            } else if (fieldType == Date.class || fieldType == Timestamp.class) {
                if(fieldType == Timestamp.class){
                    String  dateStr = c.getString(i).toString().replaceAll(":(\\d\\d)$", "$1");
                    // Définir le format
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC")); // Pour s'assurer du fuseau horaire
                    try {
                        Date date = formatter.parse(dateStr);
                        Timestamp timestamp = new Timestamp(date.getTime());
                        o=Ut.setField(a.getColonne(),o,timestamp);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }
                }else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    try {
                        Date date= dateFormat.parse(c.getString(i));
                        o=Ut.setField(a.getColonne(),o,date);
                    } catch (ParseException e) {
                        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date date= dateFormat.parse(c.getString(i).toString().trim());
                            o=Ut.setField(a.getColonne(),o,date);
                        } catch (ParseException ex) {

                            String dateStr = c.getString(i).toString().replaceAll(":(\\d\\d)$", "$1");
                            // Définir le format
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                            try {
                                Date date= dateFormat.parse(dateStr);
                                o=Ut.setField(a.getColonne(),o,date);
                            } catch (ParseException exx) {

                            }

                            // throw new RuntimeException("Erreur lors de la conversion de la date : " + nouvelleValeur, e);
                        }
                    }
                }

            } else if (fieldType.isEnum()) {
                 o=Ut.setField(a.getColonne(),o,Enum.valueOf((Class<Enum>) fieldType, c.getString(i)));
            }else if(isEntity(a.getT())){
               Long id=c.getLong(i);
               Repository repository=new Repository(a.getT(),context);
               Object ob=repository.findById(id);
                o=Ut.setField(a.getColonne(),o,ob);
            }

            i++;
        }
        return o;
    }
}
