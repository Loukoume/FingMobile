package com.credi.fings.publics.dataTable;

import android.content.Context;
import android.view.View;

import com.credi.fings.publics.OnClickView;
import com.credi.fings.publics.arrierePlan.Assync;
import com.credi.fings.publics.service.impl.Ut;

import java.util.ArrayList;
import java.util.List;

public class DataTable {
    private Context context;
    private List<Object> objects;
    private String labelle;
    private int index;
    private String id;
    DataLayout rl;
    private List<Head> head;
    private List<Head> head0;

    public List<Head> getHead0() {
        return head0;
    }

    public void setHead0(List<Head> head0) {
        this.head0 = head0;
    }

    private OnClickView onClickView;

    public DataTable(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public DataTable setContext(Context context) {
        this.context = context;
        return this;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public DataTable setObjects(List<Object> objects) {
        this.objects = objects;
        return this;
    }

    public String getLabelle() {
        return labelle;
    }

    public DataTable setLabelle(String labelle) {
        this.labelle = labelle;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public DataTable setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getId() {
        return id;
    }

    public DataTable setId(String id) {
        this.id = id;
        return this;
    }

    public List<Head> getHead() {
        return head;
    }

    public DataTable setHead(List<Head> head) {
        this.head = head;
        return this;
    }

    public OnClickView getOnClickView() {
        return onClickView;
    }

    public void setOnClickView(OnClickView onClickView) {
        this.onClickView = onClickView;
    }

    public static List<String> line(Object o,List<Head> head) {
        List<String> list = new ArrayList<>();
        for (Head h : head) {
            Object v = Ut.getAllValues(o, h.getColonne());
            if (v == null) {
                list.add("");
            } else {
                list.add(v + "");
            }
        }
        return list;
    }
    private List<String> line(Object o,Object b,String field) {
        List<String> list = new ArrayList<>();
        for (Head h : head) {
            Object v;
            if (h.getColonne().equals(field)) {
                v = Ut.getAllValues(b, h.getColonne());
            }else v = Ut.getAllValues(o, h.getColonne());
            if (v == null) {
                list.add("");
            } else {
                list.add(v + "");
            }
        }
        return list;
    }

    public View view() {
        if (head == null) {
            return null;
        }
        if(objects==null){
            objects=new ArrayList<>();
        }
        rl = new DataLayout(context, new ArrayList<>(), head);
        rl.setOnRowClickListener(new DataLayout.OnRowClickListener() {
            @Override
            public void onRowClick(int rowIndex) {
                // Traitez l'index de la ligne cliquée
                // S.toast(context,"ligne "+rowIndex+" clické");
                if(onClickView!=null){
                    onClickView.onClick(null,rowIndex);
                }
            }
        });

        back(head);
        System.out.println(rl+" headhead "+head+"  "+context);
        return rl;
    }
    public void addAll(List<Object> objects){
        for (Object object:objects){
            add(object);
        }
    }
    public void addAll(List<Object> objects,Object ob,String field){
        for (Object object:objects){
            add(object,ob,field);
        }
    }
    public void add(Object object,Object ob,String field) {
        List<String> list = line(object,ob,field);
        if (id != null) {
            int i = Ut.indexOf(objects, object, id);
            System.out.println(" ii = "+i);
            if (i != -1) {
                rl.updateTable(list, i);
            } else {
                objects.add(object);
                rl.updateTable(list);
            }
        } else {
            System.out.println(" non ");
            objects.add(object);
            rl.updateTable(list);
            // System.out.println(id+" cio   "+object+"    "+objects);
        }
        this.index=-1;
    }
    public void add(Object object,int index) {
        List<String> list = line(object,head);
        if (index != -1) {
            rl.updateTable(list, index);
        } else {
            //objects.add(object);
            rl.updateTable(list);
        }
        this.index=-1;
    }

    public void add(Object object) {
        List<String> list = line(object,head);
        //System.out.println(" objects "+Ut.listJs(objects));
        //System.out.println(" object "+Ut.js(object));
        //System.out.println(" id "+id);
        if (id != null) {
            int i = Ut.indexOf(objects, object, id);
            //System.out.println(" ii = "+i);
            if (i != -1) {
                rl.updateTable(list, i);
            } else {
                //objects.add(object);
                rl.updateTable(list);
            }
        } else {
            //System.out.println(" non ");
           // objects.add(object);
            rl.updateTable(list);
            // System.out.println(id+" cio   "+object+"    "+objects);
        }
        this.index=-1;
    }
    public void remove(Object object) {
        if (id != null) {
            int i = Ut.indexOf(objects, object, id);
            if (i != -1) {
                rl.remove(i);
               // objects.remove(i);
            }
        }
        this.index=-1;
    }
    public void remove(int i) {
        if (i != -1) {
            rl.remove(i);
            // if(i<objects.size()){
           // objects.remove(i);
            //}
        }else if(rl!=null){
            rl.updateTable(i);
        }
        this.index=-1;
    }
    public void removeAll() {
        this.index=-1;
        rl.removeAll();
        objects.clear();
    }

    public void add(List<String> list) {
        rl.updateTable(list);
    }
    public void addAlls(List<List<String>> list) {
        for (List<String> line:list){
            rl.updateTable(line);
        }
    }
    public static List<List<String>> ddtas(List<Object> objects,List<Head> head){
        List<List<String>> datas=new ArrayList<>();
        for (Object o:objects){
            datas.add(line(o,head));
        }
        return datas;
    }

    int debut = 0, fin, pas = 1;

    private void back(List<Head> head) {
        fin = objects.size();
        //System.out.println(fin+" fin "+debut+"   "+(debut < fin));
        if (debut < fin&&!objects.isEmpty()) {
            processBatch(head);
        }
    }

    private void processBatch(List<Head> head) {
        if (debut < fin) {
            // Déterminer les éléments à traiter dans ce batch
            final List<List<String>>[] datas = new List[]{null};
            List<Object> currentBatch = objects.subList(debut, Math.min(debut + pas, fin));
            //System.out.println(currentBatch.size()+" currentBatch  ");
            new Assync(
                    (success, code) -> {
                        if (success) {
                            // Vérifier s'il y a encore des éléments à traiter
                            if(datas[0] !=null)
                                addAlls(datas[0]);
                            if (debut < fin) {
                                processBatch(head); // Lancer la prochaine itération
                            }
                        } else {
                            System.out.println("Échec du travail, code: " + code);
                        }
                    },
                    () -> {
                        // Exécuter le traitement de la sous-liste actuelle
                        datas[0] =DataTable.ddtas(currentBatch,head);
                        // Mettre à jour le pointeur pour le prochain lot
                        debut = Math.min(debut + pas, fin);
                        System.out.println(fin+" debut "+debut);
                    }
            ).execute();
        }
    }


}

