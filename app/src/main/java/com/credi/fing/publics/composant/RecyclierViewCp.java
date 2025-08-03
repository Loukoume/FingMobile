package com.credi.fing.publics.composant;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.credi.fing.R;
import com.credi.fing.publics.OnClickView;
import com.credi.fing.publics.adapters.generiqueAdapter.Adapter;
import com.credi.fing.publics.ecouteur.GridSpacingItemDecoration;
import com.credi.fing.publics.ecouteur.RecyclerTouchListener;
import com.credi.fing.publics.service.OnBindViewHolderAction;
import com.credi.fing.publics.service.impl.RecyclerHandler;
import com.credi.fing.publics.service.impl.Ut;
import com.credi.fing.publics.service.interfacs.OnSelect;
import com.credi.fing.publics.utils.S;

import java.util.List;

public class RecyclierViewCp {
    private Context context;
    private List<Object> objects;
    private Adapter adapter;
    private int row_layout;
    private int numberItems = 1;
    private RecyclerView recyclerView;
    private int background;
    private OnSelect onSelect;
    private OnBindViewHolderAction onBindViewHolderAction;
    private OnSelect onLongClick;
    private OnSelect onClick;
    private OnClickView onClickView;
    private RecyclerHandler.OnSwipeRight onSwipeRight;
    private RecyclerHandler.OnSwipeLeft onSwipeLeft;

    /** Orientation du RecyclerView : LinearLayoutManager.VERTICAL ou LinearLayoutManager.HORIZONTAL */
    private int orientation = RecyclerView.VERTICAL;

    /** Indique si on est en train de charger plus d’items */
    private boolean isLoading = false;

    /** Délai simulé pour loadMore (en ms) */
    private int time = 1000;


    private int lastVisibleItem = -1;

    public RecyclierViewCp(Context context,
                           int row_layout,
                           List<Object> objects,
                           OnBindViewHolderAction onBindViewHolderAction) {
        this.context = context;
        this.row_layout = row_layout;
        this.objects = objects;
        this.onBindViewHolderAction = onBindViewHolderAction;
    }

    /**
     * Constructeur avec orientation
     * @param orientation LinearLayoutManager.VERTICAL ou LinearLayoutManager.HORIZONTAL
     */
    public RecyclierViewCp(Context context,
                           int row_layout,
                           List<Object> objects,
                           OnBindViewHolderAction onBindViewHolderAction,
                           int orientation) {
        this(context, row_layout, objects, onBindViewHolderAction);
        this.orientation = orientation;
    }

    /** Setter fluide pour l’orientation */
    public RecyclierViewCp setOrientation(int orientation) {
        this.orientation = orientation;
        return this;
    }

    public OnSelect getOnSelect() {
        return onSelect;
    }

    public void setOnSelect(OnSelect onSelect) {
        this.onSelect = onSelect;
    }

    public int getBackground() {
        return background;
    }

    public RecyclierViewCp setBackground(int background) {
        this.background = background;
        return this;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclierViewCp setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public OnClickView getOnClickView() {
        return onClickView;
    }

    public void setOnClickView(OnClickView onClickView) {
        this.onClickView = onClickView;
    }

    public int getNumberItems() {
        return numberItems;
    }

    public RecyclierViewCp setNumberItems(int numberItems) {
        this.numberItems = numberItems;
        return this;
    }

    public void setOnLongClick(OnSelect onLongClick) {
        this.onLongClick = onLongClick;
    }

    public void setOnClick(OnSelect onClick) {
        this.onClick = onClick;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RecyclerHandler.OnSwipeRight getOnSwipeRight() {
        return onSwipeRight;
    }

    public void setOnSwipeRight(RecyclerHandler.OnSwipeRight onSwipeRight) {
        this.onSwipeRight = onSwipeRight;
    }

    public RecyclerHandler.OnSwipeLeft getOnSwipeLeft() {
        return onSwipeLeft;
    }

    public void setOnSwipeLeft(RecyclerHandler.OnSwipeLeft onSwipeLeft) {
        this.onSwipeLeft = onSwipeLeft;
    }

    /** Génère la vue en interne, si non déjà fournie par setRecyclerView */
    public View view() {
        View view = recyclerView;
        if (recyclerView == null) {
            view = Ut.getView(context, R.layout.recyclier_layout);
            recyclerView = view.findViewById(R.id.recycler_view);
            if (background > 0) {
                recyclerView.setBackgroundResource(background);
            }
        }
        preparerDatas();
        return view;
    }

    /** Utiliser quand on passe un RecyclerView déjà existant */
    public void view(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        preparerDatas();
    }

    /** Prépare et configure le RecyclerView (LayoutManager, Adapter, Listener…) */
    private void preparerDatas() {
        adapter = new Adapter(context, objects, row_layout, onBindViewHolderAction);

        GridLayoutManager layoutManager =
                new GridLayoutManager(
                        context,
                        numberItems,
                        orientation,
                        false  // reverseLayout = false
                );
        recyclerView.setLayoutManager(layoutManager);

        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(
                    new GridSpacingItemDecoration(
                            numberItems,
                            S.dpToPx(2, context.getResources()),
                            true
                    )
            );
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                context,
                recyclerView,
                new RecyclerTouchListener.SwipeClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (onClick != null) {
                            onClick.select(objects.get(position), position);
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        if (onLongClick != null) {
                            onLongClick.select(objects.get(position), position);
                        }
                    }

                    @Override
                    public void onSwipeLeft(View view, int position) {
                        if (onSwipeLeft != null) {
                            view.animate().translationX(-600f).setDuration(300).start();
                            onSwipeLeft.execute(position, view);
                        }
                    }

                    @Override
                    public void onSwipeRight(View view, int position) {
                        if (onSwipeRight != null) {
                            view.animate().translationX(600f).setDuration(300).start();
                            onSwipeRight.execute(position, view);
                        }
                    }
                }
        ));
    }

    /** Met à jour la liste et notifie l’Adapter */
    public void updateList(List<Object> list) {
        if (adapter != null && list != null) {
            this.objects = list;
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Adapter ou données non initialisés", Toast.LENGTH_SHORT).show();
        }
    }

    /** Supprime un item à la position p */
    public void remove(int p) {
        if (this.objects.size() > p) {
            this.objects.remove(p);
            adapter.notifyItemRemoved(p);
        } else {
            Toast.makeText(context, "Position invalide", Toast.LENGTH_SHORT).show();
        }
    }

    /** Insère un objet à la position p */
    public void inserer(int p, Object object) {
        if (this.objects.size() >= p) {
            this.objects.add(p, object);
            adapter.notifyItemInserted(p);
        } else {
            Toast.makeText(context, "Position invalide", Toast.LENGTH_SHORT).show();
        }
    }

    public View getCurentView(){
        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (lm == null) return null;
        int firstVisible = lm.findFirstVisibleItemPosition();
        View rowView = lm.findViewByPosition(lastVisibleItem);
        return rowView;
    }

    /**
     * Exemple de scroll fixe sur un FloatingActionButton
     */
    public void fixedScrol(View fab) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                if (dy > 0) {
                    if (fab != null) fab.setVisibility(View.GONE);
                } else {
                    if (fab != null) fab.setVisibility(View.VISIBLE);
                }
                LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
                if (lm == null) return;
               /* if (!isLoading  && lm.findLastCompletelyVisibleItemPosition() == objects.size() - 1) {
                    isLoading = true;
                    loadMore();
                }*/
                int firstVisible = lm.findFirstVisibleItemPosition();
                // ou bien : int firstVisible = lm.findFirstCompletelyVisibleItemPosition();
                // ou : int lastVisible = lm.findLastVisibleItemPosition();

                // 3) Si différente de la précédente, c’est un nouvel item visible
                if (firstVisible != lastVisibleItem) {
                    lastVisibleItem = firstVisible;
                    View rowView = lm.findViewByPosition(lastVisibleItem);
                    if (rowView != null) {
                      if(onClickView!=null){
                          onClickView.onClick(rowView,lastVisibleItem);
                      }
                    }

                }
            }
        });
    }

    /**
     * Exemple de scroll fixe avec deux vues (fab et top)
     */
    public void fixedScrol(View fab, View top) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                if (dy > 0) {
                    fab.setVisibility(View.GONE);
                    top.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                    top.setVisibility(View.GONE);
                }
            }
        });
    }

    /** Ajoute un placeholder null et notifie l’adaptateur */
    private void loadMore() {
        objects.add(null);
        adapter.notifyItemInserted(objects.size() - 1);
        if (onClick != null) {
            onClick.select(null, objects.size() - 1);
        }
        // Vous pouvez décommenter et adapter ce Handler pour charger réellement plus de données
        /*
        new Handler().postDelayed(() -> {
            objects.remove(objects.size() - 1);
            adapter.notifyItemRemoved(objects.size());
            // Charger et insérer de nouvelles données ici…
            isLoading = false;
        }, time);
        */
    }

    /**
     * Définit la position de l'item à afficher en instantané.
     * @param position index de l'item dans l'adapter
     */
    public void scrollToPosition(int position) {
        if (recyclerView != null) {
            recyclerView.scrollToPosition(position);
        }
    }

    /**
     * Définit la position de l'item à afficher avec un défilement animé.
     * @param position index de l'item dans l'adapter
     */
    public void smoothScrollToPosition(int position) {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(position);
        }
    }

}
