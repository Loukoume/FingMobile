package com.credi.fing.publics.adapters.generiqueAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.publics.AddActivity;
import com.credi.fing.publics.LoginActivity;
import com.credi.fing.publics.repository.Repository;
import com.credi.fing.publics.repository.sqlite.Data;
import com.credi.fing.publics.service.ApiAction;
import com.credi.fing.publics.service.ApiClient;
import com.credi.fing.publics.service.ApiService;
import com.credi.fing.publics.service.FindObject;
import com.credi.fing.publics.service.HttpApi;
import com.credi.fing.publics.service.OnBindViewHolderAction;
import com.credi.fing.publics.service.OnDelete;
import com.credi.fing.publics.service.impl.Attribut;
import com.credi.fing.publics.service.impl.EditeObject;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.service.impl.Visible;
import com.credi.fing.publics.service.interfacs.CrudInterface;
import com.credi.fing.publics.utils.S;
import com.credi.fing.utils.Anim;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<AdapterViewHolder> {
    Context context;
    List<Object> liste,selection;
    int id = R.layout.card_simple_row;
    Binder binder;
    String url;
    Adapter adapter;
    private Object data;
    int p;
    Class<?> aClass;
    List<Attribut> attributs;
    private boolean save;

    OnBindViewHolderAction onBindViewHolderAction;
    OnDelete onDelete;
    private CrudInterface crudInterface;

    public void setCrudInterface(CrudInterface crudInterface) {
        this.crudInterface = crudInterface;
    }

    public OnDelete getOnDelete() {
        return onDelete;
    }

    public void setOnDelete(OnDelete onDelete) {
        this.onDelete = onDelete;
    }

    public void setOnBindViewHolderAction(OnBindViewHolderAction onBindViewHolderAction) {
        this.onBindViewHolderAction = onBindViewHolderAction;
    }

    public List<Object> getSelection() {
        return selection;
    }

    public void setSelection(List<Object> selection) {
        this.selection = selection;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setAttributs(List<Attribut> attributs) {
        this.attributs = attributs;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }


    public Adapter(Context context, List<Object> liste, int id, OnBindViewHolderAction onBindViewHolderAction) {
        this.context = context;
        this.liste = liste;
        this.id = id;
        this.onBindViewHolderAction = onBindViewHolderAction;
    }

    public Adapter(Context context, List<Object> liste, String url, Binder binder,boolean save) {
        this.context = context;
        this.liste = liste;
        this.binder = binder;
        if (binder != null && binder.getRowObject() != null) {
            this.id = binder.getRowObject().getLayout();
        }
        this.url = url;
        this.save=save;
    }

    public Adapter(Context context, List<Object> liste, String url, Binder binder, int id, Class<?> aClass,boolean save) {
        this.context = context;
        this.liste = liste;
        this.binder = binder;
        if (binder != null && binder.getRowObject() != null) {
            this.id = binder.getRowObject().getLayout();
        } else
            this.id = id;
        this.url = url;
        this.aClass = aClass;
        this.save=save;
    }
    public Adapter(Context context, List<Object> liste, String url, Binder binder, int id, Class<?> aClass,boolean save,CrudInterface crudInterface,
                   OnBindViewHolderAction onBindViewHolderAction) {
        this.context = context;
        this.liste = liste;
        this.binder = binder;
        this.onBindViewHolderAction=onBindViewHolderAction;
        if (binder != null && binder.getRowObject() != null) {
            this.id = binder.getRowObject().getLayout();
        } else
            this.id = id;
        this.url = url;
        this.aClass = aClass;
        this.save=save;
        this.crudInterface=crudInterface;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
        return new AdapterViewHolder(itemView, id);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final AdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Object object = liste.get(position);

        if (onBindViewHolderAction != null) {
            onBindViewHolderAction.bind(holder, object, position);
        } else {
            if (Ut.getValue(object, "idServeur") == null&&save) {
                if (aClass != null && Ut.getValue(object, "id" + aClass.getSimpleName()) == null) {
                    saveData(holder.not_sente, url, ApiAction.save.name(), object, context, position);
                } else if (aClass == null)
                    saveData(holder.not_sente, url, ApiAction.save.name(), object, context, position);
            }
            // System.out.println((aClass!=null&&Ut.getValue(object,"id"+aClass.getSimpleName())==null)+"=objs="+(Ut.getValue(object, "idServeur") == null));
            // System.out.println((aClass==null)+"==aClass=="+aClass);
            // System.out.println(Ut.js(object));
            if (binder != null) {
                //System.out.println("binder "+binder.getMultiSelect());
                String tile = binder.getTitle(),
                        font = binder.getFontFamily(),
                        secd = binder.getSecondry();
               /* String type = "string";
                if (secd!=null&&secd.contains("?")) {
                    type = secd.substring(secd.indexOf("?") + 1);
                    secd = secd.substring(0, secd.indexOf("?"));
                }*/

                Object ts = Ut.getAllValues(object, tile);
                holder.title.setText(ts != null ? ts.toString() : null);

                if(holder.secondre!=null){
                    Object sd = Ut.getAllValues(object, secd);
                    holder.secondre.setText(sd != null ? sd.toString() : null);
                }else {
                    if(position%2==0){
                        holder.view.setBackgroundColor(Ut.getColor(context,R.color.colorSendre));
                    }else {
                        holder.view.setBackgroundColor(Ut.getColor(context,R.color.white));
                    }
                }

                if(binder.getSecondry2()!=null&&holder.secondre2!=null){
                    holder.secondre2.setVisibility(View.VISIBLE);
                    String second2 = binder.getSecondry2();
                    Object ts2 = Ut.getAllValues(object, second2);
                    holder.secondre2.setText(ts2 != null ? ts2.toString() : null);
                }
                if(binder.getTitle2()!=null&&holder.title2!=null){
                    holder.title2.setVisibility(View.VISIBLE);
                    String title2 = binder.getTitle2();
                    Object ts2 = Ut.getAllValues(object, title2);
                    holder.title2.setText(ts2 != null ? ts2.toString() : null);
                }

                Object fnt = font != null ? Ut.getAllValues(object, font) : null;

                if (fnt != null && fnt.toString().contains("font/")) {
                    String fontName = fnt.toString().substring(fnt.toString().indexOf("/") + 1);
                    int fontResId = context.getResources().getIdentifier(fontName, "font", context.getPackageName());
                    Typeface typeface = ResourcesCompat.getFont(context, fontResId);
                    holder.title.setTypeface(typeface);
                    holder.secondre.setTypeface(typeface);
                }

                LinearLayout lbotom=holder.lbotom;
                if(binder.getOnItemViewClick()!=null&&lbotom!=null){
                    lbotom.setVisibility(View.VISIBLE);
                    binder.getOnItemViewClick().onItemeClick(lbotom,object,position);
                    if(binder.isShowSwitch()){
                        holder.aSwitch.setVisibility(View.VISIBLE);
                        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                binder.getOnItemViewClick().onItemeClick(holder.aSwitch,object,position);
                            }
                        });
                        if(binder.getCheckSwitch()!=null){
                            Visible vi=binder.getCheckSwitch();
                            Object v1=Ut.getValue(object,vi.getAttribut().getColonne());
                            holder.aSwitch.setChecked(Objects.equals(v1,vi.getValeurs()));
                        }
                    }
                }

                if (binder.getRowObject() != null && binder.getRowObject().getTypeMedia() != null) {
                    switch (binder.getRowObject().getTypeMedia()) {
                        case CIRC_MEDIA:
                            holder.circ_img.setVisibility(View.VISIBLE);
                            holder.image.setVisibility(View.GONE);
                            break;
                        case ICON_MEDIA:
                            holder.circ_img.setVisibility(View.GONE);
                            holder.image.setVisibility(View.VISIBLE);
                            holder.image.setImageResource(binder.getRowObject().getDrawableIcon());
                            break;
                        case SIMPL_MEDIA:
                            if(holder.circ_img!=null)
                              holder.circ_img.setVisibility(View.GONE);
                            if(holder.image!=null)
                              holder.image.setVisibility(View.VISIBLE);
                            if (binder.getRowObject().getDrawableIcon() > 0)
                                holder.image.setImageResource(binder.getRowObject().getDrawableIcon());
                            else if(binder.getRowObject().getDrawableIconSwitchBinders()!=null){
                                if(binder.getRowObject().getDrawableIconSwitchBinders().size()==1){
                                    String field=binder.getRowObject().getDrawableIconSwitchBinders().get(0).getField();
                                    Object val=binder.getRowObject().getDrawableIconSwitchBinders().get(0).getValue();
                                    Object dft=binder.getRowObject().getDrawableIconSwitchBinders().get(0).getDefaultValue();
                                    Object reps=binder.getRowObject().getDrawableIconSwitchBinders().get(0).getRespons();
                                    Object rp=Ut.getValue(object,field);
                                    if(rp!=null&&reps!=null&& Objects.equals(reps,rp)){
                                        int dw=Integer.parseInt(val.toString());
                                        holder.image.setImageResource(dw);
                                    }else {
                                        int dw=Integer.parseInt(dft.toString());
                                        holder.image.setImageResource(dw);
                                    }
                                }
                            }
                            if (binder.getRowObject().isStar()!=null)
                            {
                                holder.star.setVisibility(View.VISIBLE);
                            }
                            else if(binder.getRowObject().getStarSwitchBinders()!=null){
                                if(binder.getRowObject().getShowStar()!=null){
                                    String field=binder.getRowObject().getShowStar().getField();
                                    // Object val=binder.getRowObject().getShowStar().getValue();
                                    Object reps=binder.getRowObject().getShowStar().getRespons();
                                    Object rp=Ut.getFirstValue(object,field);
                                    if(reps!=null){
                                        switch (reps.toString()){
                                            case "null":
                                                if(rp==null){
                                                    holder.star.setVisibility(View.VISIBLE);
                                                }else {
                                                    holder.star.setVisibility(View.GONE);
                                                }
                                                break;
                                            case "notNull":
                                                if(rp==null){
                                                    holder.star.setVisibility(View.GONE);
                                                }else {
                                                    holder.star.setVisibility(View.VISIBLE);
                                                }
                                                break;
                                            case "true":
                                                if(Objects.equals(rp,true)){
                                                    holder.star.setVisibility(View.VISIBLE);
                                                }else if(rp!=null){
                                                    holder.star.setVisibility(View.GONE);
                                                }
                                                break;
                                            case "false":
                                                if(Objects.equals(rp,false)){
                                                    holder.star.setVisibility(View.VISIBLE);
                                                }else {
                                                    holder.star.setVisibility(View.GONE);
                                                }
                                                break;

                                        }
                                    }

                                }else
                                    holder.star.setVisibility(View.VISIBLE);
                                if(binder.getRowObject().getStarSwitchBinders().size()==1){
                                    String field=binder.getRowObject().getStarSwitchBinders().get(0).getField();
                                    Object val=binder.getRowObject().getStarSwitchBinders().get(0).getValue();
                                    Object dft=binder.getRowObject().getStarSwitchBinders().get(0).getDefaultValue();
                                    Object reps=binder.getRowObject().getStarSwitchBinders().get(0).getRespons();
                                    Object rp=Ut.getFirstValue(object,field);
                                    if(rp!=null&&reps!=null&& Objects.equals(reps,rp)){
                                        int dw= (int) val;
                                        holder.star.setBackgroundResource(dw);
                                    }else {
                                        int dw= (int) dft;
                                        holder.star.setBackgroundResource(dw);
                                    }
                                }
                            }else {
                                if(holder.star!=null)
                                  holder.star.setVisibility(View.GONE);
                            }
                            if(binder.getRowObject().getShowNotif()!=null){
                                String field=binder.getRowObject().getShowNotif().getField();
                                Object val=binder.getRowObject().getShowNotif().getValue();
                                Object reps=binder.getRowObject().getShowNotif().getRespons();
                                Object rp=Ut.getFirstValue(object,field);
                                if(reps!=null&&holder.lnotif!=null){
                                    switch (reps.toString()){
                                        case "null":
                                            if(rp==null){
                                                holder.lnotif.setVisibility(View.VISIBLE);
                                                holder.notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.icon_notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.notif.setText(S.en3(rp.toString()));
                                            }else {
                                                holder.lnotif.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "notNull":
                                            if(rp==null&&holder.lnotif!=null){
                                                holder.lnotif.setVisibility(View.GONE);
                                            }else if(holder.lnotif!=null){
                                                holder.lnotif.setVisibility(View.VISIBLE);
                                                holder.notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.icon_notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.notif.setText(S.en3(rp.toString()));
                                            }
                                            break;
                                        case "=":
                                            if(rp!=null&&(Integer.parseInt(rp.toString().replace(".0",""))==Integer.parseInt(val.toString()))){
                                                holder.lnotif.setVisibility(View.VISIBLE);
                                                holder.notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.icon_notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.notif.setText(S.en3(rp.toString()));
                                            }else {
                                                holder.lnotif.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "<":
                                            if(rp!=null&&(Integer.parseInt(rp.toString().replace(".0",""))<Integer.parseInt(val.toString()))){
                                                holder.lnotif.setVisibility(View.VISIBLE);
                                                holder.notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.icon_notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.notif.setText(S.en3(rp.toString()));
                                            }else {
                                                holder.lnotif.setVisibility(View.GONE);
                                            }
                                            break;
                                        case ">":
                                            if(rp!=null&&(Integer.parseInt(rp.toString().replace(".0",""))>Integer.parseInt(val.toString()))){
                                                holder.lnotif.setVisibility(View.VISIBLE);
                                                holder.notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.icon_notif.setAnimation(Anim.getAnimeBalancoir(context));
                                                holder.notif.setText(S.en3(rp.toString()));
                                            }else {
                                                holder.lnotif.setVisibility(View.GONE);
                                            }
                                            break;

                                    }
                                }

                            }else if(binder.getRowObject()!=null&&binder.getRowObject().getStarSwitchBinders()!=null&&binder.getRowObject().getStarSwitchBinders().size()==1){
                                String field=binder.getRowObject().getStarSwitchBinders().get(0).getField();
                                Object val=binder.getRowObject().getStarSwitchBinders().get(0).getValue();
                                Object dft=binder.getRowObject().getStarSwitchBinders().get(0).getDefaultValue();
                                Object reps=binder.getRowObject().getStarSwitchBinders().get(0).getRespons();
                                Object rp=Ut.getFirstValue(object,field);
                                if(rp!=null&&reps!=null&& Objects.equals(reps,rp)){
                                    int dw= (int) val;
                                    holder.star.setBackgroundResource(dw);
                                }else {
                                    int dw= (int) dft;
                                    holder.star.setBackgroundResource(dw);
                                }

                            }else if(holder.star!=null){
                                holder.star.setVisibility(View.GONE);
                            }
                            break;
                        case CUSTOM_MEDIA_IMAGE:
                            holder.circ_img.setVisibility(View.GONE);
                            holder.customImageView.setVisibility(View.VISIBLE);
                            holder.customVideoView.setVisibility(View.GONE);
                            holder.secondre.setVisibility(View.GONE);
                            if (binder.getRowObject().getDrawableIcon() > 0)
                                holder.customImageView.setImageResource(binder.getRowObject().getDrawableIcon());
                            break;
                        case CUSTOM_MEDIA_VIDEO:
                            holder.circ_img.setVisibility(View.GONE);
                            holder.customImageView.setVisibility(View.GONE);
                            holder.customVideoView.setVisibility(View.VISIBLE);
                            holder.secondre.setVisibility(View.GONE);
                            if(Ut.getValue(object,"path")==null){
                                View vi=holder.view.findViewById(R.id.vi);
                                TextView vis=holder.view.findViewById(R.id.vis);
                                vis.setVisibility(View.VISIBLE);
                                vi.setVisibility(View.VISIBLE);
                                holder.view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //  new MediaUtils(context).saveImage()
                                    }
                                });
                            }
                            break;
                        case SELECT_ROW:

                            if(binder.getMultiSelect()!=null){
                                if(binder.getMultiSelect()){
                                    boolean boo=Ut.contient(selection,object,binder.getTitle());
                                    holder.checkbox.setVisibility(View.VISIBLE);
                                    holder.radioButton.setVisibility(View.GONE);
                                    if(boo){
                                        holder.icon.setImageResource(R.drawable.baseline_add_task_24_2);
                                    }else {
                                        holder.icon.setImageResource(R.drawable.baseline_add_task_24);
                                    }
                                    if(selection!=null){
                                        holder.checkbox.setChecked(boo);
                                    }
                                }else {
                                    boolean boo=Ut.contient(selection,object,binder.getTitle());
                                    holder.radioButton.setVisibility(View.VISIBLE);
                                    holder.checkbox.setVisibility(View.GONE);
                                    if(boo){
                                        holder.icon.setImageResource(R.drawable.baseline_add_task_24_2);
                                    }else {
                                        holder.icon.setImageResource(R.drawable.baseline_add_task_24);
                                    }
                                    if(selection!=null){
                                        holder.radioButton.setChecked(boo);
                                    }
                                }
                            }
                            if(binder.getHideIcone()){
                                holder.icon.setVisibility(View.GONE);
                                holder.checkbox.setVisibility(View.GONE);
                                holder.radioButton.setVisibility(View.GONE);
                            }else {
                                if(binder.isHideRadio()){
                                    holder.radioButton.setVisibility(View.GONE);
                                }
                            }
                            break;
                    }
                }
                if (LoginActivity.compte != null) {
                    if(binder==null||binder.getRowObject()==null||binder.getRowObject().getTypeMedia()!=ElementRow.SELECT_ROW)
                        clickMenu(holder, object,position);
                } else {
                    holder.ic_mort.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return liste==null?0:liste.size();
    }

    public void saveData(final ImageView not_sent, String url, String action, final Object object, Context context, int p) {
        //ChapitreActivity.pbc.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        String bas=ApiClient.BASE_URL_PROD.endsWith("/")?ApiClient.BASE_URL_PROD:ApiClient.BASE_URL_PROD+"/";

        Call<Object> call = apiService.postData(bas +  url + "/" + action, object);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //ChapitreActivity.pbc.setVisibility(View.GONE);
                if (response.body() != null) {
                    if(not_sent!=null) not_sent.setVisibility(View.GONE);
                    liste.set(p, response.body());
                    if (data != null) {
                        Repository repository = new Repository(Data.class, context);
                        Object oj = repository.delete(data);
                        data = null;
                    } else {
                        Repository repository = new Repository(object.getClass(), context);
                        Object oj = repository.delete(object);
                    }
                    //  Dialogue.neutreDialog(Ut.js(oj),"Suppression succès",context).show();
                } else {
                   // Dialogue.neutreDialog("Erreur technique " + url + "/" + action + "   " + Ut.js(object), "Alerte", context).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                //ChapitreActivity.pbc.setVisibility(View.GONE);

                //Dialogue.neutreDialog("Echec " + t, "Alerte", context).show();


            }
        });

    }


    private void clickMenu(AdapterViewHolder holder, Object object,int position) {
        if (binder.getMenus() != null && !binder.getMenus().isEmpty()&&holder.ic_mort!=null) {
            holder.ic_mort.setVisibility(View.VISIBLE);
            holder.ic_mort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] array = binder.getMenus().stream().map(m -> m.getLabel())
                            .collect(Collectors.toList())
                            .toArray(new String[0]);
                    PopupMenu popupMenu = S.popupMenu(v, array);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId() - 1;
                            switch (binder.getMenus().get(id).getActionMenu()) {
                                case DELETE:
                                    if(crudInterface!=null&&crudInterface.getOnDelete()!=null){
                                        crudInterface.getOnDelete().delete(object,position);
                                    }else {
                                    if(Ut.getValue(object, "idServeur") == null){
                                        liste.remove(p);
                                        adapter.notifyItemRemoved(p);
                                        if(onDelete!=null){
                                            onDelete.delete(object,p);
                                        }
                                    }else {
                                    TextView textView = new TextView(context);
                                    HttpApi httpApi = new HttpApi(url + "/delete", textView, context);
                                    httpApi.getDatas(object);
                                    textView.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {
                                            if (s.toString().equals("Error")) {
                                                S.toast(context, httpApi.getMessage());
                                            } else if (s.toString().equals("Ok")) {
                                                S.toast(context, "Suppression effectué avec succès");
                                                if (adapter != null) {
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            liste.remove(p);
                                                            adapter.notifyItemRemoved(p);
                                                        }
                                                    }, 500);

                                                }
                                            } else if (s.toString().equals("technique")) {
                                                S.toast(context, "Erreur technique");
                                            }
                                        }
                                    });
                                    }}
                                    break;
                                case UPDATE:
                                    if(crudInterface!=null&&crudInterface.getOnSave()!=null){
                                        crudInterface.getOnSave().save(object,position);
                                    }else {
                                        EditeObject editeObject = new EditeObject();
                                        editeObject.setObject(Ut.creatObject(object, aClass));
                                        editeObject.setaClass(aClass);
                                        editeObject.setAttribute(attributs);
                                        editeObject.setDesignation(("Modifier " + aClass.getSimpleName()).toUpperCase());
                                        if (adapter != null) {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    // editeObject.set
                                                    context.startActivity(new Intent(
                                                            context, AddActivity.class
                                                    ).putExtra("object", editeObject));
                                                }
                                            }, 100);

                                        }
                                    }
                                    break;
                                case NAVIGATE:
                                    FindObject findObject = binder.getMenus().get(id).getFindObject();
                                    Intent intent=new Intent(
                                            context, binder.getMenus().get(id).getT()
                                    ).putExtra("object", findObject != null ? (Serializable) findObject.find(object,null) : (Serializable) Ut.creatObject(object, aClass));
                                    if(binder.getExtra()!=null){
                                        intent.putExtra("extra",binder.getExtra());
                                    }
                                    context.startActivity(intent);
                                    break;
                                case FINISH:
                                    ((Activity) context).finish();
                                    break;
                            }
                            return false;
                        }
                    });
                }
            });
        }
        if (binder.getMediaClick() != null && binder.getRowObject() != null&&binder.getRowObject().getTypeMedia()!=null) {
            switch (binder.getRowObject().getTypeMedia()) {
                case CIRC_MEDIA:
                    holder.circ_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickMedia(object);
                        }
                    });
                    break;
                case SIMPL_MEDIA:
                    if(holder.image!=null)
                        holder.image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clickMedia(object);
                            }
                        });
                    break;
            }
        }
    }

    private void clickMedia(Object object) {
        switch (binder.getMediaClick().getActionMenu()) {
            case DELETE:
                TextView textView = new TextView(context);
                HttpApi httpApi = new HttpApi(url + "/delete", textView, context);
                httpApi.getDatas(object);
                textView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().equals("Error")) {
                            S.toast(context, httpApi.getMessage());
                        } else if (s.toString().equals("Ok")) {
                            S.toast(context, "Suppression effectué avec succès");
                            if (adapter != null) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        liste.remove(p);
                                        adapter.notifyItemRemoved(p);
                                    }
                                }, 500);

                            }
                        } else if (s.toString().equals("technique")) {
                            S.toast(context, "Erreur technique");
                        }
                    }
                });
                break;
            case UPDATE:
                EditeObject editeObject = new EditeObject();
                editeObject.setObject(Ut.creatObject(object, aClass));
                editeObject.setaClass(aClass);
                editeObject.setAttribute(attributs);
                editeObject.setDesignation("Modifier " + aClass.getSimpleName());
                if (adapter != null && binder.getMediaClick().getT() != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // editeObject.set
                            context.startActivity(new Intent(
                                    context, binder.getMediaClick().getT()
                            ).putExtra("object", editeObject));
                        }
                    }, 100);

                }

                break;
            case NAVIGATE:
                if (binder.getMediaClick().getT() != null)
                    context.startActivity(new Intent(
                            context, binder.getMediaClick().getT()
                    ).putExtra("data", (Serializable) Ut.creatObject(object, aClass)));
                break;
            case FINISH:
                ((Activity) context).finish();
                break;
        }
    }



}

