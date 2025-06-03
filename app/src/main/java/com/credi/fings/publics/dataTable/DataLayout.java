package com.credi.fings.publics.dataTable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import com.credi.fings.R;
import com.credi.fings.publics.ecouteur.AdjustResizeHelper;
import com.credi.fings.publics.service.impl.Ut;
import com.credi.fings.publics.utils.Dialogue;
import com.credi.fings.publics.utils.S;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataLayout extends RelativeLayout {

    public interface OnRowClickListener {
        void onRowClick(int rowIndex);
    }
    private OnRowClickListener rowClickListener;
    public void setOnRowClickListener(OnRowClickListener listener) {
        this.rowClickListener = listener;
    }

    // Remplacement de List<String> headers par List<Head> heads
    private List<Head> heads;
    // Données du tableau : une liste de lignes, chaque ligne étant une liste de valeurs (String)
    private List<List<String>> sampleObjects;

    // Différents TableLayout pour composer le tableau
    private TableLayout tableA; // Première colonne - en-tête
    private TableLayout tableB; // Autres colonnes - en-tête
    private TableLayout tableC; // Première colonne - données
    private TableLayout tableD; // Autres colonnes - données

    private HorizontalScrollView horizontalScrollViewB;
    private HorizontalScrollView horizontalScrollViewD;
    private ScrollView scrollViewC;
    private ScrollView scrollViewD;

    private Context context;
    private int[] headerCellsWidth; // Largeur de chaque colonne
    private int x, y,index=-1;               // Dimensions de l'écran

    private int top,btm;

    /**
     * Constructeur principal
     * @param context contexte Android
     * @param data    liste de lignes, chaque ligne = liste de valeurs
     * @param heads   liste d'objets Head décrivant chaque colonne (libellé, colonne, width, etc.)
     */
    public DataLayout(Context context, List<List<String>> data, List<Head> heads) {
        super(context);

        this.context = context;
        top=S.dpToPx(4,context.getResources());
        btm=S.dpToPx(5,context.getResources());
        this.sampleObjects = data;
        this.heads =heads==null?new ArrayList<>(): heads
                .stream().peek(h->{
                    int w=h.getWidth();
                    h.setWidth(S.dpToPx(w,context.getResources()));
                }).collect(Collectors.toList());

        // On crée un tableau de la même taille que le nombre de colonnes
        this.headerCellsWidth = new int[heads.size()];

        // Initialisation des composants
        initComponents();
        setComponentsId();
        setScrollViewAndHorizontalScrollViewTag();

        // Récupération des dimensions de l'écran (si besoin)
        Point point = AdjustResizeHelper.getAppUsableScreenSize(context);
        x = point.x;
        y = point.y;
        // Vous pouvez afficher un dialogue de test si nécessaire
        // Dialogue.neutreDialog(x + "  " + y, " layout ", context).show();

        // Construction de la hiérarchie des vues
        horizontalScrollViewB.addView(tableB);
        scrollViewC.addView(tableC);
        scrollViewD.addView(horizontalScrollViewD);
        horizontalScrollViewD.addView(tableD);

        // Ajout au layout principal
        addComponentToMainLayout();
        setBackgroundColor(Color.WHITE);

        // Ajout des en-têtes
        addTableRowToTableA(); // en-tête de la première colonne
        addTableRowToTableB(); // en-tête des autres colonnes

        // Ajustement de la hauteur des en-têtes
        resizeHeaderHeight();

        // Calcul ou application des largeurs de colonnes
        getTableRowHeaderCellWidth();

        // Génération du contenu (lignes de données)
        generateTableC_AndTable_D();

        // Ajustement final des hauteurs de lignes de données
        resizeBodyTableRowHeight();
    }
    private int dpToPx(int x){
        return S.dpToPx(x,context.getResources());
    }

    // ---------------------------------------------------------------------------------------------
    // Initialisation et configuration de base
    // ---------------------------------------------------------------------------------------------

    private void initComponents() {
        tableA = new TableLayout(context);
        tableB = new TableLayout(context);
        tableC = new TableLayout(context);
        tableD = new TableLayout(context);

        horizontalScrollViewB = new MyHorizontalScrollView(context);
        horizontalScrollViewD = new MyHorizontalScrollView(context);

        scrollViewC = new MyScrollView(context);
        scrollViewD = new MyScrollView(context);

        // Exemples de couleurs
        // tableA.setBackgroundColor(Color.WHITE);
        horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);

        scrollViewC.setElevation(0f);
        scrollViewD.setElevation(0f);
        horizontalScrollViewB.setElevation(0f);
        horizontalScrollViewD.setElevation(0f);

        // Si c'est un fading edge qui pose problème, vous pouvez aussi le désactiver :
        scrollViewC.setVerticalFadingEdgeEnabled(false);
        scrollViewD.setVerticalFadingEdgeEnabled(false);
        horizontalScrollViewB.setHorizontalFadingEdgeEnabled(false);
        horizontalScrollViewD.setHorizontalFadingEdgeEnabled(false);

        scrollViewC.setVerticalScrollBarEnabled(false);
        scrollViewD.setVerticalScrollBarEnabled(false);
        horizontalScrollViewB.setHorizontalScrollBarEnabled(false);
        horizontalScrollViewD.setHorizontalScrollBarEnabled(false);


    }

    @SuppressLint("ResourceType")
    private void setComponentsId() {
        tableA.setId(1);
        horizontalScrollViewB.setId(2);
        scrollViewC.setId(3);
        scrollViewD.setId(4);
    }

    private void setScrollViewAndHorizontalScrollViewTag() {
        horizontalScrollViewB.setTag("horizontal scroll view b");
        horizontalScrollViewD.setTag("horizontal scroll view d");
        scrollViewC.setTag("scroll view c");
        scrollViewD.setTag("scroll view d");
    }

    private void addComponentToMainLayout() {
        LayoutParams componentB_Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentB_Params.addRule(RelativeLayout.RIGHT_OF, tableA.getId());

        LayoutParams componentC_Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentC_Params.addRule(RelativeLayout.BELOW, tableA.getId());

        LayoutParams componentD_Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        componentD_Params.addRule(RelativeLayout.RIGHT_OF, scrollViewC.getId());
        componentD_Params.addRule(RelativeLayout.BELOW, horizontalScrollViewB.getId());

        addView(tableA);
        addView(horizontalScrollViewB, componentB_Params);
        addView(scrollViewC, componentC_Params);
        addView(scrollViewD, componentD_Params);
    }

    // ---------------------------------------------------------------------------------------------
    // Gestion des en-têtes
    // ---------------------------------------------------------------------------------------------

    /**
     * Construit et ajoute la ligne d'en-tête pour la première colonne (tableA)
     */
    private void addTableRowToTableA() {
        tableA.addView(componentATableRow());
    }

    /**
     * Construit et ajoute la ligne d'en-tête pour les colonnes suivantes (tableB)
     */
    private void addTableRowToTableB() {
        tableB.addView(componentBTableRow());
    }

    /**
     * Retourne un TableRow représentant l'en-tête de la première colonne
     */
    private TableRow componentATableRow() {
        TableRow row = new TableRow(context);

        // heads.get(0) correspond à la première colonne
        Head headFirst = heads.get(0);
        TextView textView = headerTextView(headFirst.getLibelle());

        //textView.setTextColor(ContextCompat.getColorStateList(context, R.color.textColor));
        //textView.setTextSize(dpToPx(5));

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins(dpToPx(2), top, 0, btm);

        //textView.setLayoutParams(params);
        //textView.setBackground(sp(R.color.colorSendre));
        // Application du style identique à tableB
        row.setBackground(sp(R.color.gray,0.5f));
        row.addView(textView,params);
        return row;
    }

    /**
     * Retourne un TableRow représentant l'en-tête des colonnes 2..n
     */
    private TableRow componentBTableRow() {
        TableRow row = new TableRow(context);

        // On commence à 1 car la 0 est déjà gérée par tableA
        for (int i = 1; i < heads.size(); i++) {
            Head head = heads.get(i);

            TextView textView = headerTextView(head.getLibelle());
            //textView.setTextColor(ContextCompat.getColorStateList(context, R.color.textColor));
            // textView.setTextSize(dpToPx(5));

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            params.setMargins(dpToPx(2), top, 0, btm);

            //textView.setLayoutParams(params);
            //textView.setBackground(sp(R.color.colorSendre));

            row.addView(textView,params);
        }
        row.setBackground(sp(R.color.gray,0.5f));
        return row;
    }

    /**
     * Crée un TextView pour l'en-tête (couleur de fond, style, etc.)
     */
    private TextView headerTextView(String label) {
        TextView headerTextView = new TextView(context);
        headerTextView.setBackgroundColor(0);
        headerTextView.setText(label);
        headerTextView.setTextSize(dpToPx(5));
        headerTextView.setTextColor(Color.parseColor("#425e6a"));
        headerTextView.setGravity(Gravity.START);
        headerTextView.setPadding(dpToPx(2), top, dpToPx(2), btm);
        Ut.appliquerFontFamily(headerTextView,"poppins_emi_bold",context);
        return headerTextView;
    }
    /**
     * Crée un TextView pour les cellules du corps (valeurs)
     */
    private TextView bodyTextView(String label) {
        TextView bodyTextView = new TextView(context);
        bodyTextView.setBackgroundColor(0);
        bodyTextView.setTextColor(ContextCompat.getColor(context,R.color.colorBlack));
        bodyTextView.setText(label);
        bodyTextView.setGravity(Gravity.START);
        bodyTextView.setPadding(dpToPx(2), top, dpToPx(2), btm);
        Ut.appliquerFontFamily(bodyTextView,"poppins_regular",context);
        //bodyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        //bodyTextView.setTypeface(null, Typeface.BOLD);

        //bodyTextView.setLetterSpacing(0.1f);
        return bodyTextView;
    }

    // ---------------------------------------------------------------------------------------------
    // Gestion du contenu (lignes de données)
    // ---------------------------------------------------------------------------------------------

    /**
     * Génère toutes les lignes dans tableC (colonne fixe) et tableD (colonnes défilables)
     */
    private void generateTableC_AndTable_D() {
        for (int i = 0; i < sampleObjects.size(); i++) {
            final int rowIndex = i;
            List<String> rowData = sampleObjects.get(i);

            // Construit les deux lignes
            TableRow rowC = tableRowForTableC(rowData, i % 2,rowIndex);
            TableRow rowD = tableRowForTableD(rowData, i % 2,rowIndex);

            // Listener de clic sur les deux parties de la ligne
            rowC.setOnClickListener(v -> {
                if (rowClickListener != null) {
                    rowClickListener.onRowClick(rowIndex);
                }
            });
            rowD.setOnClickListener(v -> {
                if (rowClickListener != null) {
                    rowClickListener.onRowClick(rowIndex);
                }
            });

            tableC.addView(rowC);
            tableD.addView(rowD);
        }
    }

    /**
     * Construit une TableRow pour la première colonne (tableC)
     */
    private TableRow tableRowForTableC(List<String> rowData, int parity, int rowIndex) {
        TableRow row = new TableRow(context);

        // Largeur = headerCellsWidth[0]
        TableRow.LayoutParams params = new TableRow.LayoutParams(headerCellsWidth[0], LayoutParams.MATCH_PARENT);
        params.setMargins(dpToPx(2), top, 0, btm);

        // rowData.get(0) = valeur de la première colonne
        TextView textView = bodyTextView(rowData.get(0));
        if(rowIndex==this.index){
            //textView.setBackground(sp(parity == 0 ? R.color.colordIndex : R.color.colordIndex));
            row.setBackground(sp(parity == 0 ? R.color.colordIndex : R.color.colordIndex,0.5f));
        }else {
            // textView.setBackground(sp(parity == 0 ? R.color.white : R.color.colorSendre));
            row.setBackground(sp(parity == 0 ? R.color.white : R.color.colorSendre,0.5f));
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);

        row.addView(textView, params);
        row.setOnClickListener(v -> {
            Dialogue.neutreDialog(this.index+" ",""+rowIndex,context).show();
            if (rowClickListener != null) {
                int old=this.index;
                this.index=rowIndex;
                this.updateTable(old);
                this.updateTable(rowIndex);
                rowClickListener.onRowClick(rowIndex);
            }
        });
        return row;
    }
    private void nouveau(int rowIndex){
        int old=this.index;
        this.index=rowIndex;
        this.updateTable(rowIndex);
        this.updateTable(old);
    }

    /**
     * Construit une TableRow pour les colonnes 2..n (tableD)
     */
    private TableRow tableRowForTableD(List<String> rowData, int parity, int rowIndex) {
        TableRow row = new TableRow(context);

        // Nombre de colonnes (dans tableB, on a heads.size()-1 colonnes)
        int loopCount = ((TableRow) tableB.getChildAt(0)).getChildCount();
        for (int i = 0; i < loopCount; i++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    headerCellsWidth[i + 1], LayoutParams.MATCH_PARENT);
            params.setMargins(dpToPx(2), top, 0, btm);

            // rowData.get(i+1) = valeur de la colonne i+1
            TextView textViewB = bodyTextView(rowData.get(i + 1));

            if(rowIndex==this.index){
                //textViewB.setBackground(sp(parity == 0 ? R.color.colordIndex : R.color.colordIndex));
                row.setBackground(sp(parity == 0 ? R.color.colordIndex : R.color.colordIndex,0.5f));
            }else{
                // textViewB.setBackground(sp(parity == 0 ? R.color.white : R.color.colorSendre));
                row.setBackground(sp(parity == 0 ? R.color.white : R.color.colorSendre,0.5f));
            }

            textViewB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            textViewB.setLayoutParams(params);
            row.addView(textViewB);
            // Listener de clic sur les deux parties de la ligne

            row.setOnClickListener(v -> {
                if (rowClickListener != null) {
                    int old=this.index;
                    this.index=rowIndex;
                    this.updateTable(old);
                    this.updateTable(rowIndex);
                    rowClickListener.onRowClick(rowIndex);
                }
            });
        }

        return row;
    }



    // ---------------------------------------------------------------------------------------------
    // Gestion des largeurs
    // ---------------------------------------------------------------------------------------------

    /**
     * Calcule la largeur de chaque colonne.
     * - Si heads.get(i).getWidth() > 0, on l'utilise directement
     * - Sinon, on mesure la vue dans l'en-tête
     * - Si la somme des largeurs est inférieure à x, on répartit l'espace libre
     */
    /**
     * Calcule la largeur de chaque colonne en utilisant directement la valeur de head.getWidth().
     * Si la somme des largeurs est inférieure à la largeur de l'écran, on répartit l'espace restant.
     */
    private void getTableRowHeaderCellWidth() {
        // Les TableRow rowA (colonne 0) et rowB (colonnes suivantes) sont toujours utiles
        // pour appliquer ensuite les LayoutParams, même si on ne mesure plus leur contenu.
        TableRow rowA = (TableRow) tableA.getChildAt(0);
        TableRow rowB = (TableRow) tableB.getChildAt(0);

        int totalWidth = 0;

        for (int i = 0; i < heads.size(); i++) {
            Head head = heads.get(i);

            // On utilise la valeur de head.getWidth() directement.
            // Vous pouvez prévoir un comportement si getWidth() est à 0 (par ex. valeur par défaut).
            headerCellsWidth[i] = head.getWidth();

            // Additionne la largeur calculée
            totalWidth += headerCellsWidth[i];
        }

        // Si la somme des largeurs est inférieure à la largeur de l'écran,
        // on répartit l'espace libre pour que le tableau occupe toute la largeur.
        if (totalWidth < x && heads.size() > 0) {
            int delta = (x - totalWidth) / heads.size();
            for (int i = 0; i < heads.size(); i++) {
                headerCellsWidth[i] += delta;
            }
        }

        // Enfin, on applique ces largeurs aux TextView des en-têtes (rowA pour la 1ère colonne, rowB pour les colonnes suivantes)
        applyHeaderWidths(rowA, rowB);
    }


    /**
     * Applique les largeurs calculées aux TextView dans tableA et tableB
     */
    private void applyHeaderWidths(TableRow rowA, TableRow rowB) {
        for (int i = 0; i < heads.size(); i++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    headerCellsWidth[i], LayoutParams.MATCH_PARENT);
            params.setMargins(dpToPx(2), top, 0, btm);

            if (i == 0) {
                TextView textView = (TextView) rowA.getChildAt(0);
                textView.setLayoutParams(params);
            } else {
                TextView textView = (TextView) rowB.getChildAt(i - 1);
                textView.setLayoutParams(params);
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Gestion de la hauteur des en-têtes et des lignes
    // ---------------------------------------------------------------------------------------------

    private void resizeHeaderHeight() {
        TableRow rowA = (TableRow) tableA.getChildAt(0);
        TableRow rowB = (TableRow) tableB.getChildAt(0);

        int heightA = viewHeight(rowA);
        int heightB = viewHeight(rowB);
        int finalHeight = Math.max(heightA, heightB);

        matchLayoutHeight(heightA < heightB ? rowA : rowB, finalHeight);
    }

    private void resizeBodyTableRowHeight() {
        int count = tableC.getChildCount(); // même nombre de lignes que tableD
        for (int i = 0; i < count; i++) {
            TableRow rowC = (TableRow) tableC.getChildAt(i);
            TableRow rowD = (TableRow) tableD.getChildAt(i);

            int heightC = viewHeight(rowC);
            int heightD = viewHeight(rowD);
            int finalHeight = Math.max(heightC, heightD);

            if (heightC < heightD) {
                matchLayoutHeight(rowC, finalHeight);
            } else if (heightC > heightD) {
                matchLayoutHeight(rowD, finalHeight);
            }
            int finalI = i;
            rowC.setOnClickListener(v -> {
                if (rowClickListener != null) {
                    int old=this.index;
                    this.index= finalI;
                    this.updateTable(finalI);
                    this.updateTable(old);
                    rowClickListener.onRowClick(finalI);
                }
            });
        }
    }

    private void matchLayoutHeight(TableRow tableRow, int height) {
        int childCount = tableRow.getChildCount();

        if (childCount == 1) {
            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = height - (params.bottomMargin + params.topMargin);
            view.setLayoutParams(params);
            return;
        }

        for (int i = 0; i < childCount; i++) {
            View view = tableRow.getChildAt(i);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            if (!isTheHeighestLayout(tableRow, i)) {
                params.height = height - (params.bottomMargin + params.topMargin);
                view.setLayoutParams(params);
            }
        }
    }

    private boolean isTheHeighestLayout(TableRow tableRow, int layoutPosition) {
        int childCount = tableRow.getChildCount();
        int maxPos = -1;
        int maxHeight = 0;

        for (int i = 0; i < childCount; i++) {
            int h = viewHeight(tableRow.getChildAt(i));
            if (h > maxHeight) {
                maxHeight = h;
                maxPos = i;
            }
        }
        return (maxPos == layoutPosition);
    }

    // ---------------------------------------------------------------------------------------------
    // Lecture des dimensions d'une vue
    // ---------------------------------------------------------------------------------------------

    private int viewHeight(View view) {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    private int viewWidth(View view) {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    // ---------------------------------------------------------------------------------------------
    // Exemple de MaterialShapeDrawable pour un fond arrondi ou stylé
    // ---------------------------------------------------------------------------------------------

    private MaterialShapeDrawable sp(int bk,float width) {
        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable();
        shapeDrawable.setShapeAppearanceModel(
                ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.CUT, 0)
                        .build()
        );
        // Appliquer la couleur de fond blanche
        shapeDrawable.setFillColor(ContextCompat.getColorStateList(context, bk));
        // Convertir 0.1dp en pixels
        float strokeWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        // Définir le contour (stroke) avec la largeur convertie et la couleur colorPrimary
        shapeDrawable.setStroke((int) strokeWidth, ContextCompat.getColor(context, R.color.icon));
        // Padding par défaut à 0, coins déjà à 0 et aucune élévation
        shapeDrawable.setElevation(0f);
        return shapeDrawable;
    }



    // ---------------------------------------------------------------------------------------------
    // ScrollViews personnalisées pour synchroniser le défilement
    // ---------------------------------------------------------------------------------------------

    class MyHorizontalScrollView extends HorizontalScrollView {
        public MyHorizontalScrollView(Context context) {
            super(context);
        }
        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) getTag();
            if (tag.equalsIgnoreCase("horizontal scroll view b")) {
                horizontalScrollViewD.scrollTo(l, 0);
            } else {
                horizontalScrollViewB.scrollTo(l, 0);
            }
        }
    }

    class MyScrollView extends ScrollView {
        public MyScrollView(Context context) {
            super(context);
        }
        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) getTag();
            if (tag.equalsIgnoreCase("scroll view c")) {
                scrollViewD.scrollTo(0, t);
            } else {
                scrollViewC.scrollTo(0, t);
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Méthodes supplémentaires (updateTable, remove, etc.) si besoin
    // ---------------------------------------------------------------------------------------------

    /**
     * Exemple de méthode pour ajouter une ligne et rafraîchir l'affichage
     */
    public void updateTable(List<String> newData) {
        int z = tableC.getChildCount();
        TableRow rowC = tableRowForTableC(newData, z % 2, z);
        TableRow rowD = tableRowForTableD(newData, z % 2, z);


        tableC.addView(rowC);
        tableD.addView(rowD);

        // resizeHeaderHeight();
        resizeBodyTableRowHeight();
    }

    public void updateTable(List<String> data, int index) {

        // Vérifier que l'index est valide
        if (index < 0 || index >= tableC.getChildCount() || index >= tableD.getChildCount()) {
            throw new IndexOutOfBoundsException("L'index fourni est invalide");
        }

        // Récupérer la TableRow de la tableC (première colonne) et mettre à jour le texte
        TableRow rowC = (TableRow) tableC.getChildAt(index);
        if (rowC.getChildCount() > 0) {
            TextView cell0 = (TextView) rowC.getChildAt(0);
            cell0.setText(data.get(0));
        }

        // Récupérer la TableRow de la tableD (colonnes restantes)
        TableRow rowD = (TableRow) tableD.getChildAt(index);
        int cellCount = rowD.getChildCount();
        for (int i = 0; i < cellCount; i++) {
            // On met à jour chaque cellule avec data à partir de l'élément 1 (car data.get(0) est dans tableC)
            if (data.size() > (i + 1)) {
                TextView cell = (TextView) rowD.getChildAt(i);
                cell.setText(data.get(i + 1));
            }
        }

        // Mettre à jour la couleur de fond selon la parité du rang pour conserver le style (similaire à generateTableC_AndTable_D)

        /*int backgroundColor = (index % 2 == 0) ? Color.LTGRAY : Color.DKGRAY;
        rowC.setBackgroundColor(backgroundColor);
        rowD.setBackgroundColor(backgroundColor);*/

        int backgroundColor = (index % 2 == 0) ? R.color.white : R.color.colorSendre;
        rowC.setBackground(sp(backgroundColor,0.5f));
        rowD.setBackground(sp(backgroundColor,0.5f));

        // Réajuster la hauteur des lignes du tableau si nécessaire
        resizeBodyTableRowHeight();

        // ajusterLeText();
    }
    public void updateTable(int index) {

        // Vérifier que l'index est valide
        if (index < 0 || index >= tableC.getChildCount() || index >= tableD.getChildCount()) {
            if(!(this.index < 0 || this.index >= tableC.getChildCount() || this.index >= tableD.getChildCount())){
                TableRow rowC = (TableRow) tableC.getChildAt(this.index);
                TableRow rowD = (TableRow) tableD.getChildAt(this.index);
                /*int backgroundColor = (this.index % 2 == 0) ? Color.WHITE : Color.parseColor("#E9E9E9");
                rowC.setBackgroundColor(backgroundColor);
                rowD.setBackgroundColor(backgroundColor);*/

                int backgroundColor = (this.index % 2 == 0) ? R.color.white : R.color.colorSendre;
                rowC.setBackground(sp(backgroundColor,0.5f));
                rowD.setBackground(sp(backgroundColor,0.5f));
            }
            return;
            //throw new IndexOutOfBoundsException("L'index fourni est invalide");
        }

        // Récupérer la TableRow de la tableC (première colonne) et mettre à jour le texte
        TableRow rowC = (TableRow) tableC.getChildAt(index);
        if (rowC.getChildCount() > 0) {
            TextView cell0 = (TextView) rowC.getChildAt(0);
        }

        // Récupérer la TableRow de la tableD (colonnes restantes)
        TableRow rowD = (TableRow) tableD.getChildAt(index);
        int cellCount = rowD.getChildCount();
        for (int i = 0; i < cellCount; i++) {
            // On met à jour chaque cellule avec data à partir de l'élément 1 (car data.get(0) est dans tableC)
            TextView cell = (TextView) rowD.getChildAt(i);
        }

        // Mettre à jour la couleur de fond selon la parité du rang pour conserver le style (similaire à generateTableC_AndTable_D)

       /* int backgroundColor = (index % 2 == 0) ? R.color.white : R.color.colorSendre;
        rowC.setBackground(sp(backgroundColor,0.5f));
        rowD.setBackground(sp(backgroundColor,0.5f));*/

        int parity=index % 2;
        if(index==this.index){
            //textView.setBackground(sp(parity == 0 ? R.color.colordIndex : R.color.colordIndex));
            rowC.setBackground(sp(parity == 0 ? R.color.colordIndex : R.color.colordIndex,0.5f));
            rowD.setBackground(sp(parity == 0 ? R.color.colordIndex : R.color.colordIndex,0.5f));
        }else {
            // textView.setBackground(sp(parity == 0 ? R.color.white : R.color.colorSendre));
            rowD.setBackground(sp(parity == 0 ? R.color.white : R.color.colorSendre,0.5f));
            rowC.setBackground(sp(parity == 0 ? R.color.white : R.color.colorSendre,0.5f));
        }
    }

    /**
     * Supprime une ligne
     */
    public void remove(int index) {
        if (index < 0 || index >= tableC.getChildCount() || index >= tableD.getChildCount()) {
            S.toast(context,"erreur technique");
            // return;
            throw new IndexOutOfBoundsException("Index invalide");
        }
        tableC.removeViewAt(index);
        tableD.removeViewAt(index);

        // Recolorer les lignes si nécessaire
        for (int i = index; i < tableC.getChildCount(); i++) {
            TableRow rowC = (TableRow) tableC.getChildAt(i);
            TableRow rowD = (TableRow) tableD.getChildAt(i);


            int backgroundColor = (index % 2 == 0) ? R.color.white : R.color.colorSendre;
            rowC.setBackground(sp(backgroundColor,0.5f));
            rowD.setBackground(sp(backgroundColor,0.5f));

            int finalI = i;
            rowC.setOnClickListener(v -> {
                if (rowClickListener != null) {
                    rowClickListener.onRowClick(finalI);
                }
            });
            rowD.setOnClickListener(v -> {
                if (rowClickListener != null) {
                    rowClickListener.onRowClick(finalI);
                }
            });

        }
        resizeBodyTableRowHeight();
    }

    public void remove2(int i) {
        // Vérifier la validité de l’index
        if (i < 0 || i >= tableC.getChildCount() || i >= tableD.getChildCount()) {
            throw new IndexOutOfBoundsException("Index invalide");
        }

        // Ajuster la sélection si nécessaire
        if (this.index == i) {
            // La ligne sélectionnée est celle qu’on supprime : on la désélectionne
            this.index = -1;
        } else if (i < this.index) {
            // Si on supprime une ligne au-dessus de la ligne sélectionnée,
            // la sélection se décale d’une ligne vers le haut
            this.index--;
        }

        // Suppression effective dans les tables
        tableC.removeViewAt(i);
        tableD.removeViewAt(i);

        // Recoloration des lignes restantes à partir de i
        for (int k = i; k < tableC.getChildCount(); k++) {
            TableRow rowC = (TableRow) tableC.getChildAt(k);
            TableRow rowD = (TableRow) tableD.getChildAt(k);

            // Détermine la couleur de base par alternance
            int backgroundColor = (k % 2 == 0) ? R.color.white : R.color.colorSendre;

            // Si la ligne k correspond à this.index (ligne actuellement sélectionnée), on applique la couleur spéciale
            if (k == this.index) {
                backgroundColor = R.color.colordIndex;
            }

            // On applique le "MaterialShapeDrawable" ou le fond que vous utilisez
            rowC.setBackground(sp(backgroundColor, 0.5f));
            rowD.setBackground(sp(backgroundColor, 0.5f));
        }

        // Réajustement des hauteurs si nécessaire
        resizeBodyTableRowHeight();
    }


    /**
     * Supprime toutes les lignes de données du tableau,
     * en laissant les en-têtes intacts.
     */
    public void removeAll() {
        // Supprime toutes les lignes de la 1ère colonne
        tableC.removeAllViews();
        // Supprime toutes les lignes des colonnes restantes
        tableD.removeAllViews();

        // Si vous maintenez une liste de données en interne (sampleObjects, par exemple),
        // vous pouvez également la vider :
        if (sampleObjects != null) {
            sampleObjects.clear();
        }

        // Ré-ajuster l’affichage si nécessaire (hauteurs, etc.)
        resizeBodyTableRowHeight();
    }

}
