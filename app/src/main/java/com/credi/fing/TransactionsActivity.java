package com.credi.fing;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.entity.LoanAccount;
import com.credi.fing.entity.SavingsAccount;
import com.credi.fing.entity.Transaction;
import com.credi.fing.publics.adapters.generiqueAdapter.AdapterViewHolder;
import com.credi.fing.publics.composant.RecyclierViewCp;
import com.credi.fing.publics.service.impl.Ut;

import java.util.List;

public class TransactionsActivity extends AppCompatActivity {
    SavingsAccount compte;
    TextView accountNumber,soldeDisponible,text_close,
    texpand,type_compte;
    LinearLayout lexpand,balanceSection,recycler_lineair;
    Context context;
    RecyclierViewCp recyclierViewCp;
    RecyclerView recyclerView;
    List<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        if(getIntent().hasExtra("compte")){
            context=this;
            String js=getIntent().getStringExtra("compte");
            compte= (SavingsAccount) Ut.fromJs(js,SavingsAccount.class);
            soldeDisponible=findViewById(R.id.soldeDisponible);
            accountNumber=findViewById(R.id.accountNumber);
            lexpand=findViewById(R.id.lexpand);
            texpand=findViewById(R.id.text_expand);
            type_compte=findViewById(R.id.type_compte);
            text_close=findViewById(R.id.text_close);
            balanceSection=findViewById(R.id.balanceSection);
            recycler_lineair=findViewById(R.id.recycler_lineair);
            recyclerView=findViewById(R.id.transactionList);
            accountNumber.setText("Compte N° "+compte.getAccountNo());
            soldeDisponible.setText(Html.fromHtml(Ut.formatMontant(compte.getAccountBalance().doubleValue())+" <sup>XOF</sup>"));
            type_compte.setText(compte.getAccountType().getValue());
            lexpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(balanceSection.getVisibility()==View.VISIBLE){
                        balanceSection.setVisibility(View.GONE);
                        text_close.setVisibility(View.VISIBLE);
                        texpand.setVisibility(View.GONE);
                    }else {
                        balanceSection.setVisibility(View.VISIBLE);
                        text_close.setVisibility(View.GONE);
                        texpand.setVisibility(View.VISIBLE);
                    }
                }
            });


          displayData();
        }
    }

    private void displayData(){
        list=List.of(new Transaction("Intérêt postés","Depot","187"),
                new Transaction("Dépôt","Depot","190000"),
                new Transaction("Retrait","Retrait","24500"),
                new Transaction("Intérêt postés","Depot","187"),
                new Transaction("Dépôt","Depot","190000"),
                new Transaction("Retrait","Retrait","24500"));
        recyclierViewCp=new RecyclierViewCp(context,R.layout.item_transaction,
                list,(h,o,i)->{
             setText(h,o);
        }).setRecyclerView(recyclerView);
        recyclierViewCp.view();
    }
    private void setText(AdapterViewHolder h,Object o){
       Transaction t= (Transaction) o;
       View view=h.view;
       View statusStripe=view.findViewById(R.id.statusStripe);
        TextView montantText=view.findViewById(R.id.montantText);
        TextView labelText=view.findViewById(R.id.labelText);
        if(t.getType().equals("Depot")){
           statusStripe.setBackgroundColor(Ut.getColor(context,R.color.green));
            montantText.setTextColor(Ut.getColor(context,R.color.green));
       }else {
           statusStripe.setBackgroundColor(Ut.getColor(context,R.color.red));
            montantText.setTextColor(Ut.getColor(context,R.color.red));
       }
        montantText.setText(Ut.formatMontant(Double.parseDouble(t.getMontant())));
        labelText.setText(t.getMotif());
    }
}