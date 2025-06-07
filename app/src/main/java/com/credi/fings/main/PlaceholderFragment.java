package com.credi.fings.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.credi.fings.R;
import com.credi.fings.activity.PagerActivity;
import com.credi.fings.binder.OperationBinder;
import com.credi.fings.binder.PretBinder;
import com.credi.fings.publics.AddActivity;
import com.credi.fings.publics.adapters.generiqueAdapter.AdapterViewHolder;
import com.credi.fings.publics.carousel.CarouselItem;
import com.credi.fings.publics.composant.RecyclierViewCp;
import com.credi.fings.publics.service.ClickHandler;
import com.credi.fings.publics.service.impl.EditeObject;
import com.credi.fings.publics.service.impl.ListActivity;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.service.pojo.NavigateObject;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.publics.utils.S;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public PlaceholderFragment fragment;
    private PageViewModel pageViewModel;

    // private FragmentMainBinding binding;
    //List<Data> datas,data2;
    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    RecyclerView recyclerView;
    static TextView menu;
    LinearLayout main;
    Context context;
    TextView entree, creance, dette, depense, reste;
    static View roots;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        roots = root;
        int index = getArguments().getInt(ARG_SECTION_NUMBER);
        // recyclerView=root.findViewById(R.id.liste);
        main = root.findViewById(R.id.main);
        main.removeAllViews();
        //Dialogue.neutreDialog(index+" ","",context).show();
        //recyclerView.setNestedScrollingEnabled(false);
        switch (index) {
            case 1:
                RecyclierViewCp recyclierViewCp = new RecyclierViewCp(context, R.layout.row_compte, PagerActivity.savingsAccounts, (h, o, i) -> {
                    setText(o, h, 1);
                }).setNumberItems(1).setBackground(R.color.colorSendre);
                main.addView(recyclierViewCp.view());
                break;
            case 2:
                recyclierViewCp = new RecyclierViewCp(context, R.layout.row_epargne, PagerActivity.loanAccounts, (h, o, i) -> {
                    setText(o, h, 2);
                }).setNumberItems(1).setBackground(R.color.colorSendre);
                main.addView(recyclierViewCp.view());
                break;
            case 3:
                break;
        }

        fragment = this;
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        int index = getArguments().getInt(ARG_SECTION_NUMBER);

    }


    void setText(Object v, AdapterViewHolder holder, int k) {
        TextView title = holder.title, second = holder.secondre,
                title2 = holder.title2, secondre2 = holder.secondre2, date = holder.date, value = holder.textePourcentage;
        Object productN_ob = Ut.getValue(v, "accountNo");
        Object productName_ob = Ut.getValue(v, "productName");
        Object loanBalance_ob = Ut.getValue(v, "accountBalance");
        Object currency_ob = Ut.getValue(v, "currency");

        /*
         values v = {"accountNo":"000000078",
         "dateFormat":"dd MMMM yyyy","inArrears":false,
         "loanType":{"code":"accountType.individual",
         "id":1,"value":"Individual"},"locale":"en","productId":1,
         "productName":"Prêt conso","shortProductName":"CONS",
         "status":{"active":false,"closed":false,"closedObligationsMet":false,"closedRescheduled":false,"closedWrittenOff":false,"code":"loanStatusType.submitted.and.pending.approval","overpaid":false,"pendingApproval":true,"value":"Soumis et en attente d\u0027approbation","waitingForDisbursal":false},
         "timeline":{"expectedDisbursementDate":[2025,6,3],"expectedMaturityDate":[2027,6,3],"submittedByFirstname":"App","submittedByLastname":"Administrator","submittedByUsername":"mifos","submittedOnDate":[2025,6,3]}}
2025-06-06 18:41:00.459 18898-18898 System.out              com.credi.fings
        * */

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
            loanBalance_ob = Ut.getValue(v, "loanBalance");
            Object initial = Ut.getValue(v, "originalLoan");
            if (loanBalance_ob != null) {
                title2.setText(Ut.formatMontant(Double.parseDouble(loanBalance_ob.toString())));
            }
            if (initial != null) {
                value.setText(Ut.formatMontant(Double.parseDouble(initial.toString())));
            }
            Object last_ob = Ut.getValue(v, "timeline:expectedDisbursementDate");
            if (last_ob != null) {
                List<Object> obs = (List<Object>) last_ob;
                String sdate = obs.get(2) + " " + S.en2(Integer.parseInt(obs.get(1).toString())) + " " + obs.get(0);
                String dat = S.date(sdate, "dd MM yyyy", "dd MMM yyyy");
                date.setText(dat);
            }
        } else if (loanBalance_ob != null && currency_ob != null) {
            Object symb = Ut.getValue(currency_ob, "displaySymbol");
            String sb = symb == null ? "" : symb.toString();
            title2.setText(sb + " " + Ut.formatMontant(Double.parseDouble(loanBalance_ob.toString())));

            Object displ = Ut.getValue(currency_ob, "displayLabel");
            String displsb = displ == null ? "" : displ.toString();
            secondre2.setText(displsb);

            Object last_ob = Ut.getValue(v, "lastActiveTransactionDate");
            if (last_ob != null) {
                List<Object> obs = (List<Object>) last_ob;
                String sdate = obs.get(2) + " " + S.en2(Integer.parseInt(obs.get(1).toString())) + " " + obs.get(0);
                String dat = S.date(sdate, "dd MM yyyy", "dd MMM yyyy");
                date.setText(dat);
            }
            Object type = Ut.getValue(v, "depositType");
            if (type != null) {
                Object vl = Ut.getValue(type, "value");
                if (vl != null) {
                    if (vl.toString().toLowerCase().contains("sav")) {
                        value.setText("Dépôt");
                        value.setTextColor(Ut.getColor(context, R.color.green));
                    } else {
                        value.setText("Retrait");
                        value.setTextColor(Ut.getColor(context, R.color.rouge));
                    }
                }
            }
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OperationBinder operationBinder = new OperationBinder();
                    EditeObject editeObject = operationBinder.editeObject();
                    startActivity(new Intent(context, AddActivity.class)
                            .putExtra("object", editeObject));
                    if (getActivity() != null)
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            mort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] m = {"Transactions", "Opération"};
                    PopupMenu popupMenu = S.popupMenu(view, m);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case 1:
                                   setOperation();
                                    break;
                                case 2:
                                    break;
                            }
                            return false;
                        }
                    });
                }
            });
        }
    }

    void setOperation() {
        OperationBinder compteBinder = new OperationBinder();
        NavigateObject nvgdp = compteBinder.navigateObject();
       /* if (loanAccounts != null && savingsAccounts != null) {
            List<Object> lit = new ArrayList<>(loanAccounts);
            lit.addAll(savingsAccounts);
            nvgdp.setValues(lit);
        }*/
       // nvgdp.setObject(o);
        //ClickHandler.setCrudInterface(crudInterface("idCompteBancaire", "comptabilite_compte_bancaire", context));
        ClickHandler.setOnBindViewHolderAction((vo, v, j) -> {
            TextView title = vo.title, second = vo.secondre,
                    title2 = vo.title2, secondre2 = vo.secondre2, date = vo.date, value = vo.textePourcentage;
            Object productN_ob = Ut.getValue(v, "accountNo");
            Object productName_ob = Ut.getValue(v, "productName");

            if (productN_ob != null) {
                String productName = productN_ob.toString();
                title.setText(productName);
            }
            if (productName_ob != null) {
                second.setText(productName_ob.toString());
            }
            Object loanBalance_ob = Ut.getValue(v, "accountBalance");
            Object currency_ob = Ut.getValue(v, "currency");
            if (loanBalance_ob != null && currency_ob != null) {
                Object symb = Ut.getValue(currency_ob, "displaySymbol");
                String sb = symb == null ? "" : symb.toString();
                title2.setText(sb + " " + Ut.formatMontant(Double.parseDouble(loanBalance_ob.toString())));

                Object displ = Ut.getValue(currency_ob, "displayLabel");
                String displsb = displ == null ? "" : displ.toString();
                secondre2.setText(displsb);

                Object last_ob = Ut.getValue(v, "lastActiveTransactionDate");
                if (last_ob != null) {
                    List<Object> obs = (List<Object>) last_ob;
                    String sdate = obs.get(2) + " " + S.en2(Integer.parseInt(obs.get(1).toString())) + " " + obs.get(0);
                    String dat = S.date(sdate, "dd MM yyyy", "dd MMM yyyy");
                    date.setText(dat);
                }
                Object type = Ut.getValue(v, "depositType");
                if (type != null) {
                    Object vl = Ut.getValue(type, "value");
                    if (vl != null) {
                        if (vl.toString().toLowerCase().contains("sav")) {
                            value.setText("Dépôt");
                            value.setTextColor(Ut.getColor(context, R.color.green));
                        } else {
                            value.setText("Retrait");
                            value.setTextColor(Ut.getColor(context, R.color.rouge));
                        }
                    }
                }
            }

        });
        startActivity(new Intent(context, ListActivity.class)
                .putExtra("id", "idCompteBancaire")
                .putExtra("navigateObject", nvgdp));

    }

    private int dpToPx(int dp) {
        Resources r = getActivity().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}