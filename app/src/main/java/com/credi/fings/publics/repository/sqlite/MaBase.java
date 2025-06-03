package com.credi.fings.publics.repository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class MaBase extends SQLiteOpenHelper {
    private String CREATE_BDD = null,table;
     List<Attribut> attributs;
     List<Pj> allAttributs;
    public MaBase(Context context, String name, SQLiteDatabase.CursorFactory factory, List<Attribut> attributs, String nom_table) {
        super(context, name, factory, 1);
        this.attributs=attributs;
        this.table=nom_table;
        CREATE_BDD=requette(attributs);
    }
    public MaBase(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
    }
    public MaBase(Context context, String name, SQLiteDatabase.CursorFactory factory, List<Pj> attributs) {
        super(context, name, factory, 1);
        this.allAttributs=attributs;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if(allAttributs!=null){
            for (Pj p:allAttributs){
                table=p.getTable();
                CREATE_BDD=requette(p.getAttributs());
                db.execSQL(CREATE_BDD);
            }
        }else if(attributs!=null){
            CREATE_BDD=requette(attributs);
            db.execSQL(CREATE_BDD);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE IF EXISTS " + table + ";");
        onCreate(db);
    }

    private String requette(List<Attribut> l){
        String q="", rq="CREATE TABLE " + table ;
        for(Attribut a:l){
            q=q+a.getNom()+" "+a.getType()+a.getPrivilege()+", ";
        }
        q=q.substring(0,q.length()-2);
        rq=rq+" ("+q+");";
        return rq;
    }
}
