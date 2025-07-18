package com.credi.fing.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.binder.OperationBinder;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.adapters.generiqueAdapter.AdapterViewHolder;
import com.credi.fing.publics.composant.RecyclierViewCp;
import com.credi.fing.publics.service.impl.EditeObject;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.utils.S;

import java.util.List;

public class Beneficiaire extends AppCompatActivity {

    LinearLayout nodata;
    TextView text,tx;
    RecyclerView recyclerView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_beneficiaire);
        nodata=findViewById(R.id.nodata);
        nodata.setVisibility(VISIBLE);
        text=findViewById(R.id.text);
        tx=findViewById(R.id.tx_text);
        recyclerView=findViewById(R.id.liste);
        context=this;
        ImageView back=findViewById(R.id.back);
        ImageView mort=findViewById(R.id.ic_mort);
        mort.setVisibility(GONE);
        String extra=getIntent().getStringExtra("titre");
        tx.setText(extra.toUpperCase());
        text.setText(extra+" s'affichent ici");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        RecyclierViewCp recyclierViewCp = new RecyclierViewCp(context, R.layout.row_compte, PagerActivity.savingsAccounts, (h, o, i) -> {
            setText(o, h, 1);
        }).setNumberItems(1).setBackground(R.color.colorSendre);


        recyclierViewCp.view(recyclerView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void setText(Object object, AdapterViewHolder holder, int k) {
        TextView title = holder.title, second = holder.secondre,
                title2 = holder.title2, secondre2 = holder.secondre2, date = holder.date, value = holder.textePourcentage;
        Object productN_ob = Ut.getValue(object, "accountNo");
        Object productName_ob = Ut.getValue(object, "productName");
        Object loanBalance_ob = Ut.getValue(object, "accountBalance");
        Object currency_ob = Ut.getValue(object, "currency");

        View view = holder.view;
        ImageView plus = view.findViewById(R.id.plus),
                mort = view.findViewById(R.id.menu);

        if (productN_ob != null) {
            String productName = productN_ob.toString();
            title.setText(productName);
        }
        if (productName_ob != null) {
            second.setText(productName_ob.toString());
        }
        //Object loanBalance_ob=Ut.getValue(v,"loanBalance");
        if (k == 2) {
            //System.out.println(" values v = "+Ut.js(v));
            loanBalance_ob = Ut.getValue(object, "loanBalance");
            Object initial = Ut.getValue(object, "originalLoan");
            if (loanBalance_ob != null) {
                title2.setText("CFA "+Ut.formatMontant(Double.parseDouble(loanBalance_ob.toString())));
            }
            if (initial != null) {
                value.setText("CFA "+Ut.formatMontant(Double.parseDouble(initial.toString())));
            }
        }

    }

}