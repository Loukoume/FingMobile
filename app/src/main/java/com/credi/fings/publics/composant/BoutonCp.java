package com.credi.fings.publics.composant;

import android.content.Context;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.credi.fings.R;
import com.credi.fings.publics.OnClickView;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.S;

public class BoutonCp {
    private Context context;
    private OnClickView onClickView;
    private int textColors;
    private int backgrounColors;
    private String title;
    private View view;


    public BoutonCp(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public BoutonCp setTitle(String title) {
        this.title = title;
        return this;
    }

    public View getView() {
        return view;
    }

    public BoutonCp setView(View view) {
        this.view = view;
        return this;
    }

    public OnClickView getOnClickView() {
        return onClickView;
    }

    public BoutonCp setOnClickView(OnClickView onClickView) {
        this.onClickView = onClickView;
        return this;
    }

    public int getTextColors() {
        return textColors;
    }

    public BoutonCp setTextColors(int textColors) {
        this.textColors = textColors;
        return this;
    }

    public int getBackgrounColors() {
        return backgrounColors;
    }

    public BoutonCp setBackgrounColors(int backgrounColors) {
        this.backgrounColors = backgrounColors;
        return this;
    }

    public View view(){
         view=view==null? Ut.getView(context, R.layout.outline_bouton):view;
        MaterialButton button=view.findViewById(R.id.outlinedButton);
        if(textColors>0){
            button.setTextColor(Ut.getColor(context,textColors));
        }
        if(backgrounColors>0){
            button.setBackgroundColor(Ut.getColor(context,backgrounColors));
        }
        button.setText(title);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickView!=null){
                    onClickView.onClick(view,0);
                }else {
                    S.toast(context,"Bouton cliqué",Ut.getColor(context,R.color.colorAccent));
                }
            }
        });
        return view;
    }
}
