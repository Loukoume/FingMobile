package com.credi.fing.publics.service.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.credi.fing.R;
import com.credi.fing.publics.service.ClickHandler;
import com.credi.fing.publics.service.OnDialogViewClick;

import java.util.ArrayList;
import java.util.List;

public class SelectService {
    private Context context;
    private List<Object> data;
    private List<Object> select;
    private String label;
    private boolean multiselect;
    Attribut attribut;
    private String title;
    OnDialogViewClick onClickView;
    // private String action;

    public SelectService(Context context, List<Object> data, String label, Attribut attribut, OnDialogViewClick onClickView) {
        this.context = context;
        this.data = data;
        this.label = label;
        this.attribut=attribut;
        this.onClickView=onClickView;
        this.select=new ArrayList<>();
    }

    public boolean isMultiselect() {
        return multiselect;
    }

    public SelectService setMultiselect(boolean multiselect) {
        this.multiselect = multiselect;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SelectService setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public SelectService setLabel(String label) {
        this.label = label;
        return this;
    }

    public List<Object> getSelect() {
        return select;
    }
    public String getStringSelect() {
        String v="";
        for (Object o:select){
            v=v+", "+(label!=null?Ut.getValue(o,label):o.toString());
        }
        if (!v.isEmpty()){
            v=v.substring(2);
        }
        return v;
    }

    public SelectService setSelect(List<Object> select) {
        this.select = select;
        return this;
    }

    public void add(Object x,String field,View view){
        if(ClickHandler.getOnItemViewClick()!=null){
            ClickHandler.getOnItemViewClick().onItemeClick(view,x,-1);
        }else {
            int j=Ut.indexOf(select,x,field);
            if(j!=-1){
                this.select.remove(j);
            }else this.select.add(x);
        }
    }
    public View view(AlertDialog alertDialog){
        View view= LayoutInflater.from(context).inflate(R.layout.lyn,null,false);
        LinearLayout lm=view.findViewById(R.id.main);
        TextView id=view.findViewById(R.id.id);
        id.setText(title);
        for(int i=0;i<data.size();i++){
            View vw=LayoutInflater.from(context).inflate(R.layout.row_jrs,null,false);
            final TextView tv=vw.findViewById(R.id.title),
                    scd=vw.findViewById(R.id.secondre),
                    num=vw.findViewById(R.id.num);
            final CheckBox chex=vw.findViewById(R.id.checkbox);
            final RadioButton radioButton=vw.findViewById(R.id.radio);
            vw.findViewById(R.id.icone).setVisibility(View.GONE);
            if(multiselect)
            {
                chex.setVisibility(View.VISIBLE);
                chex.setChecked(Ut.contient(select,data.get(i),label));
                radioButton.setVisibility(View.GONE);
            }else {
                chex.setVisibility(View.GONE);
                radioButton.setChecked(Ut.contient(select,data.get(i),label));
                radioButton.setVisibility(View.GONE);
            }
            if(attribut!=null&&!attribut.isIcone()){
                chex.setVisibility(View.GONE);
                radioButton.setVisibility(View.GONE);
                vw.findViewById(R.id.icone).setVisibility(View.GONE);
            }
            String affo=label!=null?Ut.getAllValues(data.get(i),label):data.get(i)+"";
            String txtscd=attribut!=null&&attribut.getSubLabel()!=null?Ut.getAllValues(data.get(i),attribut.getSubLabel()):"";
            tv.setText(affo);
            if(attribut!=null&&attribut.getSubLabel()!=null){
                scd.setVisibility(View.VISIBLE);
                scd.setText(txtscd);
            }
            num.setText(String.valueOf(i));
            chex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            chex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            int finalI = i;
            vw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickView!=null){
                        if(ClickHandler.getOnItemViewClick()!=null){
                            ClickHandler.getOnItemViewClick().onItemeClick(view,data.get(finalI),-1);
                        }
                        onClickView.hideDialogue(alertDialog,v, finalI);
                    }else {
                        chex.setChecked(!chex.isChecked());
                        add(data.get(Integer.parseInt(num.getText().toString())),label,v);
                    }

                }
            });

            lm.addView(vw);
        }

        return view;
    }
}
