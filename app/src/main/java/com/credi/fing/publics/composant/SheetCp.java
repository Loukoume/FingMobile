package com.credi.fing.publics.composant;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.credi.fing.R;
import com.credi.fing.publics.OnClickView;
import com.credi.fing.publics.service.impl.Ut;

public class SheetCp {
    private Context context;
    private OnClickView onClickView;
    private int textColors;
    private int backgrounColors;
    private String title;

    public SheetCp(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public SheetCp setTitle(String title) {
        this.title = title;
        return this;
    }

    public OnClickView getOnClickView() {
        return onClickView;
    }

    public SheetCp setOnClickView(OnClickView onClickView) {
        this.onClickView = onClickView;
        return this;
    }

    public int getTextColors() {
        return textColors;
    }

    public SheetCp setTextColors(int textColors) {
        this.textColors = textColors;
        return this;
    }

    public View view(){
        View view= Ut.getView(context, R.layout.sheet_layout);
        TextView textView=view.findViewById(R.id.title);
        textView.setText(title);
        if(textColors>0){
            textView.setTextColor(Ut.getColor(context,textColors));
        }
        return view;
    }
}
