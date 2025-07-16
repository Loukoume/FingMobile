package com.credi.fing.publics.composant;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.credi.fing.R;
import com.credi.fing.publics.OnClickView;
import com.credi.fing.publics.service.impl.Ut;

public class TwoBoutonCp {
    private Context context;
    private OnClickView onClickView1;
    private int textColors1;
    private int backgrounColors1;
    private String title1;
     private String orientation;
    private OnClickView onClickView2;
    private int textColors2;
    private int backgrounColors2;
    private String title2;
    View view;


    public TwoBoutonCp(Context context) {
        this.context = context;
    }

    public String getOrientation() {
        return orientation;
    }

    public TwoBoutonCp setOrientation(String orientation) {
        this.orientation = orientation;
        return this;
    }

    public OnClickView getOnClickView1() {
        return onClickView1;
    }

    public TwoBoutonCp setOnClickView1(OnClickView onClickView1) {
        this.onClickView1 = onClickView1;
        return this;
    }

    public int getTextColors1() {
        return textColors1;
    }

    public TwoBoutonCp setTextColors1(int textColors1) {
        this.textColors1 = textColors1;
        return this;
    }

    public int getBackgrounColors1() {
        return backgrounColors1;
    }

    public TwoBoutonCp setBackgrounColors1(int backgrounColors1) {
        this.backgrounColors1 = backgrounColors1;
        return this;
    }

    public String getTitle1() {
        return title1;
    }

    public TwoBoutonCp setTitle1(String title1) {
        this.title1 = title1;
        return this;
    }
    public View getView() {
        return view;
    }

    public TwoBoutonCp setView(View view) {
        this.view = view;
        return this;
    }
    public OnClickView getOnClickView2() {
        return onClickView2;
    }

    public void setOnClickView2(OnClickView onClickView2) {
        this.onClickView2 = onClickView2;
    }

    public int getTextColors2() {
        return textColors2;
    }

    public void setTextColors2(int textColors2) {
        this.textColors2 = textColors2;
    }

    public int getBackgrounColors2() {
        return backgrounColors2;
    }

    public void setBackgrounColors2(int backgrounColors2) {
        this.backgrounColors2 = backgrounColors2;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }
    public View view(){
        if(orientation!=null&&orientation.equalsIgnoreCase("v")){
            View view= Ut.getView(context, R.layout.main_vide);
            LinearLayout linearLayout=view.findViewById(R.id.main);
            linearLayout.addView(bouton1());
            linearLayout.addView(bouton2());
            return view;
        }
        View view= Ut.getView(context, R.layout.two_view);
        TableRow r1=view.findViewById(R.id.view1);
        r1.addView(bouton1());
        TableRow r2=view.findViewById(R.id.view2);
        r2.addView(bouton2());
        return view;
    }
    private View bouton1(){
        BoutonCp boutonCp=new BoutonCp(context)
                .setBackgrounColors(backgrounColors1)
                .setOnClickView(onClickView1)
                .setTextColors(textColors1)
                .setTitle(title1);
        return boutonCp.view();
    }
    private View bouton2(){
        BoutonCp boutonCp=new BoutonCp(context)
                .setBackgrounColors(backgrounColors2)
                .setOnClickView(onClickView2)
                .setTextColors(textColors2)
                .setTitle(title2);
        return boutonCp.view();
    }
}
