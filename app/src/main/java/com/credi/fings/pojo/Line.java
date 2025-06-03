package com.credi.fings.pojo;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.credi.fings.R;
import com.credi.fings.publics.Style;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.S;
import com.credi.fings.publics.utils.TextUtils;

public class Line {
    private String texte;
    private String reste;
    private Context context;

    public Line(String texte, Context context) {
        this.texte = texte;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }



    public void setContext(Context context) {
        this.context = context;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getReste() {
        return reste;
    }

    public void setReste(String reste) {
        this.reste = reste;
    }

    static TextView textView;
    public View line(Style style){
        View view= S.findView(context, R.layout.line_layout);
         textView=view.findViewById(R.id.text);

        if(style!=null){
            textView = (TextView) Ut.appliquerStyle(textView, style);
            textView.setPadding(style.getMargeLeft(), 0, 0, 0);
            if(style.getMargeTop()==0){
                String[] fittingText= TextUtils.splitTextToFitTextView(textView,texte,style);
                //System.out.println("Texte qui rentre : " + fittingText[0]);
               // System.out.println("Texte restant : " + fittingText[1]);
                reste=fittingText[1];
                textView.setText(fittingText[0]);
                return view;
            }
        }
        View lim= S.findView(context, R.layout.main);
        LinearLayout lmain=lim.findViewById(R.id.lmain);
        if(style!=null){
            int k=style.getMargeTop();
            for (int i=0;i<k;i++){
                View v= S.findView(context, R.layout.line_layout);
                lmain.addView(v);
            }
            //textView=view.findViewById(R.id.text);

            textView = (TextView) Ut.appliquerStyle(textView, style);
            textView.setPadding(style.getMargeLeft(), 0, 0, 0);

            String[] fittingText= TextUtils.splitTextToFitTextView(textView, texte,style);

            textView.setText(fittingText[0]);
            reste=fittingText[1];

            lmain.addView(view);
            // textView.setText(texte);


            return lim;
        }else{
            String[] fittingText= TextUtils.splitTextToFitTextView(textView,texte,style);
           // System.out.println("Texte qui rentre : 2" + fittingText[0]);
           // System.out.println("Texte restant : 2" + fittingText[1]);
            textView.setText(fittingText[0]);
            reste=fittingText[1];
            return view;
        }
       //
       // System.out.println("=retou3-> "+texte);
       // return lim;
    }

}
