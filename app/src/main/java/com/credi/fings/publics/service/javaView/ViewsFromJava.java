package com.credi.fings.publics.service.javaView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.credi.fings.R;
import com.credi.fings.publics.service.impl.ShapeUtils;
import com.credi.fings.publics.service.impl.Ut;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewsFromJava {
    public Context context;

    public ViewsFromJava(Context context) {
        this.context = context;
    }

    public View twoView() {
        // Création du TableLayout
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.setStretchAllColumns(true);

        // Création d'un TableRow
        TableRow tableRow = new TableRow(context);

        // Création et ajout des LinearLayout via la méthode privée
        tableRow.addView(createLinearLayout("view1",LinearLayout.VERTICAL));
        tableRow.addView(createLinearLayout("view2",LinearLayout.VERTICAL));

        // Ajout du TableRow dans le TableLayout
        tableLayout.addView(tableRow);

        return tableLayout;
    }
    public View twoView(View view1,View view2) {
        View view=twoView();
        LinearLayout l1=view.findViewWithTag("view1");
        l1.setPadding(0,0,10,0);
        LinearLayout l2=view.findViewWithTag("view2");
        l2.setPadding(10,0,0,0);
        l1.addView(view1);
        l2.addView(view2);
        return view;
    }

    public LinearLayout createLinearLayout(String tag,int orientation) {
        LinearLayout layout = new LinearLayout(context);
        layout.setTag(tag);
        layout.setOrientation(orientation);
        return layout;
    }

    /**
     * Construit la vue correspondant au layout "cardImageHorizRow"
     */
    public View cardImageVertiRow() {
        // Création du MaterialCardView et configuration de ses marges, élévation et rayon
        MaterialCardView cardView = createMaterialCardView();

        // Création du CoordinatorLayout qui contiendra l'ensemble des vues
        CoordinatorLayout coordinatorLayout = createCoordinatorLayout();

        // Ajout de la ligne horizontale principale
        LinearLayout horizontalRow = createHorizontalRow();
        coordinatorLayout.addView(horizontalRow);

        // Ajout des vues superposées dans le CoordinatorLayout
        coordinatorLayout.addView(createIconImageView());
        coordinatorLayout.addView(createProgressBar());
        coordinatorLayout.addView(createNotSenteImageView());
        coordinatorLayout.addView(createNotificationLayout());
        coordinatorLayout.addView(createStarView());

        // Ajout du CoordinatorLayout dans le MaterialCardView
        cardView.addView(coordinatorLayout);

        return cardView;
    }
    public View cardImageHorizRow() {
        MaterialCardView cardView = createMaterialCardView(8, 6, 8);

        CoordinatorLayout coordinatorLayout = createCoordinatorLayout();
        LinearLayout mainVerticalLayout = createLinearLayout("mainVerticalLayout", LinearLayout.VERTICAL);
        mainVerticalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // Section contenant les fichiers et vidéos
        mainVerticalLayout.addView(createFileSection());

        // Ajout du CircleImageView
        mainVerticalLayout.addView(createCircleImageView(150, R.color.colorAccent));

        // Ajout du layout horizontal contenant l'image et les textes
        mainVerticalLayout.addView(createTextSection());

        // Ajout des icônes superposées
        coordinatorLayout.addView(mainVerticalLayout);
        coordinatorLayout.addView(createIconImageView());
        coordinatorLayout.addView(createProgressBar());
        coordinatorLayout.addView(createNotSenteImageView());

        cardView.addView(coordinatorLayout);
        return cardView;
    }
    public CircleImageView createCircleImageView(int size, int borderColor) {
        CircleImageView circleImageView = new CircleImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(size));
        params.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        circleImageView.setLayoutParams(params);
        circleImageView.setTag("circ_img");
        circleImageView.setVisibility(View.GONE);
        circleImageView.setImageResource(R.drawable.user);
        circleImageView.setBorderColor(context.getResources().getColor(borderColor));
        circleImageView.setBorderWidth(dpToPx(1));
        return circleImageView;
    }

    // -------------------- Méthodes de création des conteneurs principaux --------------------

    /**
     * Crée le MaterialCardView avec les marges horizontales (8dp), marges verticales (2dp),
     * une élévation de 5dp et un coin arrondi de 0dp.
     */

    public LinearLayout createFileSection() {
        LinearLayout fileSection = createLinearLayout("fileSection", LinearLayout.VERTICAL);
        fileSection.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        fileSection.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));

        CoordinatorLayout fileCoordinator = createCoordinatorLayout();

        // CustomImageView
        View customImage = new View(context);
        customImage.setLayoutParams(new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(200)
        ));
        customImage.setBackgroundColor(context.getResources().getColor(R.color.white));
        customImage.setTag("custom_image");

        // CustomVideoView
        View customVideo = new View(context);
        CoordinatorLayout.LayoutParams videoParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(200));
        customVideo.setLayoutParams(videoParams);
        customVideo.setBackgroundColor(context.getResources().getColor(R.color.white));
        customVideo.setVisibility(View.GONE);
        customVideo.setTag("custom_video");

        // Vue transparente pour superposition
        View vi = new View(context);
        CoordinatorLayout.LayoutParams viParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(200));
        vi.setLayoutParams(viParams);
        vi.setBackgroundColor(context.getResources().getColor(R.color.black_transparant));
        vi.setVisibility(View.GONE);
        vi.setElevation(dpToPx(500));
        vi.setTag("vi");

        // Texte sélection de fichier
        TextView vis = new TextView(context);
        CoordinatorLayout.LayoutParams visParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        visParams.gravity = Gravity.CENTER;
        vis.setLayoutParams(visParams);
        vis.setText("Sélectionner le fichier");
        vis.setTextSize(17);
        vis.setTextAppearance(android.R.style.TextAppearance_Material_Widget_Button_Borderless_Colored);
        vis.setVisibility(View.GONE);
        vis.setTag("vis");

        fileCoordinator.addView(customImage);
        fileCoordinator.addView(customVideo);
        fileCoordinator.addView(vi);
        fileCoordinator.addView(vis);

        fileSection.addView(fileCoordinator);
        return fileSection;
    }

    public LinearLayout createTextSection() {
        LinearLayout textSection = createLinearLayout("textSection", LinearLayout.HORIZONTAL);
        textSection.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ImageView im = new ImageView(context);
        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(
                dpToPx(60), ViewGroup.LayoutParams.MATCH_PARENT);
        imParams.setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
        im.setLayoutParams(imParams);
        im.setTag("im");
        im.setVisibility(View.GONE);

        LinearLayout verticalTextLayout = createLinearLayout("verticalTextLayout", LinearLayout.VERTICAL);
        verticalTextLayout.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));

        // TextView Title
        TextView title = new TextView(context);
        title.setTag("title");
        title.setText("Title");
        title.setTextSize(18);
        title.setTextAppearance(android.R.style.TextAppearance_Material_Widget_Button_Borderless_Colored);

        // TextView Secondre
        TextView secondre = new TextView(context);
        secondre.setTag("secondre");
        secondre.setText("Secondary text");

        // TextInputLayout + EditText
        TextInputLayout textField = new TextInputLayout(context);
        textField.setTag("textField");
        textField.setHint("Designation du document");
        textField.setVisibility(View.GONE);

        TextInputEditText textInput = new TextInputEditText(context);
        textInput.setTag("id");
        textField.addView(textInput);

        verticalTextLayout.addView(title);
        verticalTextLayout.addView(secondre);
        verticalTextLayout.addView(textField);

        textSection.addView(im);
        textSection.addView(verticalTextLayout);

        return textSection;
    }

    public MaterialCardView createMaterialCardView() {
        MaterialCardView cardView = new MaterialCardView(context);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginHor = dpToPx(8);
        int marginVert = dpToPx(2);
        params.setMargins(marginHor, marginVert, marginHor, marginVert);
        cardView.setLayoutParams(params);
        cardView.setCardElevation(dpToPx(5));
        cardView.setRadius(0);
        return cardView;
    }
    public MaterialCardView createMaterialCardView(int margin, int elevation, int radius) {
        MaterialCardView cardView = new MaterialCardView(context);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginPx = dpToPx(margin);
        params.setMargins(marginPx, marginPx, marginPx, marginPx);
        cardView.setLayoutParams(params);
        cardView.setCardElevation(dpToPx(elevation));
        cardView.setRadius(dpToPx(radius));
        return cardView;
    }

    /**
     * Crée le CoordinatorLayout qui accueille l'ensemble des vues.
     */
    public CoordinatorLayout createCoordinatorLayout() {
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(context);
        coordinatorLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return coordinatorLayout;
    }

    // -------------------- Ligne horizontale principale --------------------

    /**
     * Construit la ligne horizontale principale qui contient :
     * - Une ImageView ("image")
     * - Un séparateur (View)
     * - Un CircleImageView ("circ_img")
     * - Un LinearLayout vertical contenant 3 TextView ("title", "secondre", "secondre2")
     */
    public LinearLayout createHorizontalRow() {
        LinearLayout horizontalRow = new LinearLayout(context);
        horizontalRow.setOrientation(LinearLayout.HORIZONTAL);
        horizontalRow.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        horizontalRow.addView(createImageView());
        horizontalRow.addView(createDividerView());
        horizontalRow.addView(createCircleImageView());
        horizontalRow.addView(createVerticalTextLayout());

        return horizontalRow;
    }

    // -------------------- Méthodes utilitaires de création des vues internes --------------------


    /**
     * Crée l'ImageView ayant pour tag "image" avec une taille de 50dp x 40dp
     * et des marges verticales de 13dp.
     */
    public ImageView createImageView() {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(50), dpToPx(40));
        params.topMargin = dpToPx(13);
        params.bottomMargin = dpToPx(13);
        imageView.setLayoutParams(params);
        imageView.setTag("image");
        imageView.setVisibility(View.VISIBLE);
        // Vous pouvez définir une source avec imageView.setImageResource(R.drawable.votre_image);
        return imageView;
    }

    /**
     * Crée une vue séparatrice (divider) de 1dp de largeur et hauteur égale à celle du parent,
     * avec un fond coloré.
     */
    public View createDividerView() {
        View divider = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(1), ViewGroup.LayoutParams.MATCH_PARENT);
        divider.setLayoutParams(params);
        divider.setBackgroundColor(context.getResources().getColor(R.color.colorSendre));
        return divider;
    }

    /**
     * Crée le CircleImageView ayant pour tag "circ_img", de taille 50dp x 50dp,
     * avec des marges (5dp horizontal, 8dp vertical), une image source et des attributs de bordure.
     */
    public CircleImageView createCircleImageView() {
        CircleImageView circleImageView = new CircleImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(50), dpToPx(50));
        int margin = dpToPx(5);
        int marginVertical = dpToPx(8);
        params.setMargins(margin, marginVertical, margin, marginVertical);
        circleImageView.setLayoutParams(params);
        circleImageView.setTag("circ_img");
        circleImageView.setVisibility(View.GONE);
        circleImageView.setImageResource(R.drawable.user);
        circleImageView.setBorderColor(context.getResources().getColor(R.color.colorSendre2));
        circleImageView.setBorderWidth(dpToPx(1));
        return circleImageView;
    }

    /**
     * Crée un LinearLayout vertical contenant trois TextView :
     * - "title" : texte simple, taille 17sp, police "exo_medium"
     * - "secondre" : texte avec marge haute de 4dp et couleur spécifique
     * - "secondre2" : texte similaire mais caché (visibility GONE) et marge haute de 8dp, taille 15sp
     */
    public LinearLayout createVerticalTextLayout() {
        LinearLayout verticalLayout = createLinearLayout("verticalTextLayout", LinearLayout.VERTICAL);
        verticalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        verticalLayout.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));

        // TextView "title"
        TextView title = new TextView(context);
        title.setTag("title");
        title.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setSingleLine(true);
        title.setTextSize(17);
        title.setTypeface(Typeface.create("exo_medium", Typeface.NORMAL));
        title.setText("Title");

        // TextView "secondre"
        TextView secondre = new TextView(context);
        secondre.setTag("secondre");
        LinearLayout.LayoutParams secondParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        secondParams.topMargin = dpToPx(4);
        secondre.setLayoutParams(secondParams);
        secondre.setText("Secondry text");
        secondre.setTextColor(context.getResources().getColor(R.color.colorSendre2));
        secondre.setTypeface(null, Typeface.NORMAL);

        // TextView "secondre2"
        TextView secondre2 = new TextView(context);
        secondre2.setTag("secondre2");
        LinearLayout.LayoutParams thirdParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        thirdParams.topMargin = dpToPx(8);
        secondre2.setLayoutParams(thirdParams);
        secondre2.setText("Secondry text");
        secondre2.setTextColor(context.getResources().getColor(R.color.colorSendre2));
        secondre2.setTextSize(15);
        secondre2.setVisibility(View.GONE);
        secondre2.setTypeface(null, Typeface.NORMAL);

        verticalLayout.addView(title);
        verticalLayout.addView(secondre);
        verticalLayout.addView(secondre2);

        return verticalLayout;
    }

    // -------------------- Vues superposées dans le CoordinatorLayout --------------------

    /**
     * Crée l'ImageView "ic_mort" placée en haut à droite, avec une élévation de 10dp,
     * une marge de 8dp et initialement masquée.
     */
    public ImageView createIconImageView() {
        ImageView icon = new ImageView(context);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        icon.setLayoutParams(params);
        icon.setElevation(dpToPx(10));
        icon.setVisibility(View.GONE);
        icon.setTag("ic_mort");
        icon.setImageResource(R.drawable.ic_mort_noir);
        return icon;
    }

    /**
     * Crée le ProgressBar "pbc" de 30dp x 30dp, placé en haut à droite avec des marges de 5dp,
     * et initialement masqué.
     */
    public ProgressBar createProgressBar() {
        ProgressBar progressBar = new ProgressBar(context);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                dpToPx(30), dpToPx(30));
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        progressBar.setLayoutParams(params);
        progressBar.setVisibility(View.GONE);
        progressBar.setTag("pbc");
        return progressBar;
    }

    /**
     * Crée l'ImageView "not_sente" de 16dp x 16dp, placée en bas à droite avec des marges de 6dp,
     * une élévation de 5dp, et initialement masquée.
     */
    public ImageView createNotSenteImageView() {
        ImageView notSente = new ImageView(context);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                dpToPx(16), dpToPx(16));
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params.setMargins(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
        notSente.setLayoutParams(params);
        notSente.setElevation(dpToPx(5));
        notSente.setVisibility(View.GONE);
        notSente.setTag("not_sente");
        notSente.setImageResource(R.drawable.ic_note_sent);
        return notSente;
    }

    /**
     * Crée le layout de notification "lnotif" qui est un LinearLayout vertical
     * (initialement masqué) placé en haut à droite. Il contient un CoordinatorLayout
     * qui lui-même regroupe une ImageView ("notif") et un TextView ("textnotif").
     */
    public LinearLayout createNotificationLayout() {
        LinearLayout lnotif = new LinearLayout(context);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.topMargin = dpToPx(6);
        int fabMargin = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
        params.rightMargin = fabMargin;
        lnotif.setLayoutParams(params);
        lnotif.setOrientation(LinearLayout.VERTICAL);
        lnotif.setVisibility(View.GONE);
        lnotif.setElevation(dpToPx(5));
        lnotif.setTag("lnotif");

        // Layout interne pour la notification
        CoordinatorLayout notifCoordinator = new CoordinatorLayout(context);
        notifCoordinator.setLayoutParams(new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // ImageView "notif"
        ImageView notif = new ImageView(context);
        CoordinatorLayout.LayoutParams notifParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        notifParams.topMargin = dpToPx(8);
        notif.setLayoutParams(notifParams);
        notif.setTag("notif");
        notif.setElevation(dpToPx(5));
        notif.setImageResource(R.drawable.ic_notifications_black_24dp);
        notif.setColorFilter(context.getResources().getColor(R.color.colorSendre2));

        // TextView "textnotif"
        TextView textnotif = new TextView(context);
        CoordinatorLayout.LayoutParams textNotifParams = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, dpToPx(20));
        textNotifParams.gravity = Gravity.RIGHT | Gravity.TOP;
        textNotifParams.leftMargin = dpToPx(10);
        textNotifParams.bottomMargin = fabMargin;
        textnotif.setLayoutParams(textNotifParams);
        textnotif.setTag("textnotif");
        textnotif.setGravity(Gravity.CENTER);
        textnotif.setPadding(dpToPx(5), 0, dpToPx(5), 0);
        textnotif.setText("0");
        textnotif.setTextColor(Color.parseColor("#FFFFFF"));
        textnotif.setBackground(ShapeUtils.createCircleBackground("#FF0000"));
        textnotif.setElevation(dpToPx(10));

        notifCoordinator.addView(notif);
        notifCoordinator.addView(textnotif);
        lnotif.addView(notifCoordinator);
        return lnotif;
    }

    /**
     * Crée une petite vue ("star") de 10dp x 10dp, placée en haut à gauche avec
     * une marge gauche de 35dp et marge haute de 8dp, ayant pour fond un drawable circulaire,
     * et initialement masquée.
     */
    public View createStarView() {
        View star = new View(context);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                dpToPx(10), dpToPx(10));
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.leftMargin = dpToPx(35);
        params.topMargin = dpToPx(8);
        star.setLayoutParams(params);
        star.setBackground(ShapeUtils.createCircleBackground("#FF0000"));
        star.setVisibility(View.GONE);
        star.setTag("star");
        return star;
    }

    public View cardSimpleRow() {
        // Création du MaterialCardView avec marges, élévation et coins arrondis
        MaterialCardView cardView = createMaterialCardView(4, 6, 0);

        // Création du CoordinatorLayout
        CoordinatorLayout coordinatorLayout = createCoordinatorLayout();

        // Création du LinearLayout principal (orientation verticale)
        LinearLayout mainVerticalLayout = createLinearLayout("mainVerticalLayout", LinearLayout.VERTICAL);
        mainVerticalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Ajout du bloc principal avec les TextView
        mainVerticalLayout.addView(createTextSection2());

        // Ajout du LinearLayout "l_bottom" qui est initialement caché
        LinearLayout bottomLayout = createLinearLayout("l_bottom", LinearLayout.VERTICAL);
        bottomLayout.setPadding(dpToPx(8), 0, dpToPx(8), dpToPx(4));
        bottomLayout.setVisibility(View.GONE);
        mainVerticalLayout.addView(bottomLayout);

        // Ajout du mainVerticalLayout dans le CoordinatorLayout
        coordinatorLayout.addView(mainVerticalLayout);

        // Ajout des icônes superposées
        coordinatorLayout.addView(createIconImageView());
        coordinatorLayout.addView(createProgressBar());
        coordinatorLayout.addView(createNotSenteImageView());

        // Ajout de la section secondaire alignée à droite
        coordinatorLayout.addView(createRightAlignedTextSection());

        // Ajout du CoordinatorLayout dans le MaterialCardView
        cardView.addView(coordinatorLayout);

        return cardView;
    }

// -------------------- Sections de texte --------------------

    /**
     * Crée la section de texte principale contenant "title" et "secondre"
     */
    public LinearLayout createTextSection2() {
        LinearLayout textSection = createLinearLayout("textSection", LinearLayout.VERTICAL);
        textSection.setPadding(dpToPx(8), dpToPx(4), dpToPx(8), 0);
        textSection.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // Title
        TextView title = new TextView(context);
        title.setTag("title");
        title.setText("Title");
        title.setTextSize(17);
        title.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Secondary Text
        TextView secondre = new TextView(context);
        secondre.setTag("secondre");
        secondre.setText("Secondry text");
        secondre.setTextColor(context.getResources().getColor(R.color.colorSendre2));
        LinearLayout.LayoutParams secondreParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        secondreParams.topMargin = dpToPx(2);
        secondre.setLayoutParams(secondreParams);

        textSection.addView(title);
        textSection.addView(secondre);

        return textSection;
    }

    /**
     * Crée la section de texte alignée à droite contenant "title2" et "secondre2"
     */
    public LinearLayout createRightAlignedTextSection() {
        LinearLayout rightTextSection = createLinearLayout("rightTextSection", LinearLayout.VERTICAL);
        rightTextSection.setPadding(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(4));
        rightTextSection.setGravity(Gravity.END);
        rightTextSection.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // Title2
        TextView title2 = new TextView(context);
        title2.setTag("title2");
        title2.setText("Title");
        title2.setTextSize(17);
        title2.setVisibility(View.INVISIBLE);
        title2.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Secondary Text 2
        TextView secondre2 = new TextView(context);
        secondre2.setTag("secondre2");
        secondre2.setText("Secondry text");
        secondre2.setTextColor(context.getResources().getColor(R.color.colorSendre2));
        secondre2.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams secondre2Params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        secondre2Params.topMargin = dpToPx(2);
        secondre2.setLayoutParams(secondre2Params);

        rightTextSection.addView(title2);
        rightTextSection.addView(secondre2);

        return rightTextSection;
    }

    public View classe() {
        // SwipeRefreshLayout
        SwipeRefreshLayout swipeRefreshLayout = new SwipeRefreshLayout(context);
        swipeRefreshLayout.setLayoutParams(new SwipeRefreshLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        swipeRefreshLayout.setId(View.generateViewId());
        swipeRefreshLayout.setBackgroundColor(context.getResources().getColor(R.color.colorSendre));

        // CoordinatorLayout
        CoordinatorLayout coordinatorLayout = createCoordinatorLayout();
        coordinatorLayout.setId(View.generateViewId());

        // LinearLayout principal contenant les RecyclerView
        LinearLayout mainLayout = createMainLinearLayout();
        coordinatorLayout.addView(mainLayout);

        // Ajout du ProgressBar centré
        coordinatorLayout.addView(createCenteredProgressBar("pb", 50));

        // Ajout du texte "Données vides"
        coordinatorLayout.addView(createCenteredTextView("vide", "Données vides", 30, View.GONE));

        // Floating Action Button (plus)
        coordinatorLayout.addView(createFloatingActionButton());

        // Section No Data
        coordinatorLayout.addView(createNoDataSection());

        // Section "Chargement en cours"
        coordinatorLayout.addView(createWaitSection());

        // Bouton en bas de l'écran
        coordinatorLayout.addView(createBottomButtonSection());

        // Ajout du CoordinatorLayout dans le SwipeRefreshLayout
        swipeRefreshLayout.addView(coordinatorLayout);

        return swipeRefreshLayout;
    }

    // -------------------- Création des vues principales --------------------

    /**
     * Crée le LinearLayout principal qui contient les RecyclerView.
     */
    public LinearLayout createMainLinearLayout() {
        LinearLayout mainLayout = createLinearLayout("lmain", LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        mainLayout.setPadding(0, 0, 0, dpToPx(1));
        mainLayout.setBackgroundColor(context.getResources().getColor(R.color.white));

        // RecyclerView principale
        RecyclerView recyclerView1 = new RecyclerView(context);
        recyclerView1.setId(View.generateViewId());
        recyclerView1.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        recyclerView1.setBackgroundColor(context.getResources().getColor(R.color.white));
        recyclerView1.setTag("liste");

        // RecyclerView secondaire
        RecyclerView recyclerView2 = new RecyclerView(context);
        recyclerView2.setId(View.generateViewId());
        recyclerView2.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        recyclerView2.setBackgroundColor(context.getResources().getColor(R.color.white));
        recyclerView2.setTag("liste2");
        recyclerView2.setVisibility(View.GONE);

        mainLayout.addView(recyclerView1);
        mainLayout.addView(recyclerView2);

        return mainLayout;
    }

    /**
     * Crée le FloatingActionButton avec les styles correspondants.
     */
    public FloatingActionButton createFloatingActionButton() {
        FloatingActionButton fab = new FloatingActionButton(context);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params.setMargins(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(8));

        fab.setLayoutParams(params);
        fab.setId(View.generateViewId());
        fab.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.plus_argant);
        fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
        fab.setTag("fab");

        return fab;
    }

    /**
     * Crée la section "No Data".
     */
    public LinearLayout createNoDataSection() {
        LinearLayout noDataLayout = createLinearLayout("nodata", LinearLayout.VERTICAL);
        noDataLayout.setGravity(Gravity.CENTER);
        noDataLayout.setBackgroundColor(context.getResources().getColor(R.color.black_transparant));
        noDataLayout.setVisibility(View.GONE);

        // Icône
        ImageView noDataIcon = new ImageView(context);
        noDataIcon.setImageResource(R.drawable.baseline_browser_not_supported_24);
        noDataIcon.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(100), dpToPx(100)));

        // Texte principal
        TextView noDataText = createCenteredTextView("text", "No data found", 16, View.VISIBLE);

        // Texte supplémentaire
        TextView noDataNews = createCenteredTextView("news", "Créer de nouvelles sections", 16, View.GONE);

        noDataLayout.addView(noDataIcon);
        noDataLayout.addView(noDataText);
        noDataLayout.addView(noDataNews);

        return noDataLayout;
    }

    /**
     * Crée la section "Chargement en cours".
     */
    public LinearLayout createWaitSection() {
        LinearLayout waitLayout = createLinearLayout("waite", LinearLayout.VERTICAL);
        waitLayout.setGravity(Gravity.CENTER);
        waitLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        waitLayout.setVisibility(View.GONE);

        // ProgressBar
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Texte
        TextView waitText = createCenteredTextView(null, "Chargement en cours, veuillez patienter", 16, View.VISIBLE);

        waitLayout.addView(progressBar);
        waitLayout.addView(waitText);

        return waitLayout;
    }

    /**
     * Crée la section du bouton en bas.
     */
    public LinearLayout createBottomButtonSection() {
        LinearLayout bottomLayout = createLinearLayout("lbouton", LinearLayout.VERTICAL);
        bottomLayout.setGravity(Gravity.BOTTOM);
        bottomLayout.setVisibility(View.GONE);

        // Ajout du layout "bouton"
        View boutonView = new View(context);
        boutonView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        boutonView.setId(View.generateViewId()); // Simule l'inclusion du layout XML "@layout/bouton"

        bottomLayout.addView(boutonView);
        return bottomLayout;
    }

    // -------------------- Méthodes Utilitaires --------------------

    /**
     * Crée un ProgressBar centré avec une taille donnée.
     */
    public ProgressBar createCenteredProgressBar(String tag, int size) {
        ProgressBar progressBar = new ProgressBar(context);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                dpToPx(size), dpToPx(size));
        params.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(params);
        progressBar.setVisibility(View.GONE);
        progressBar.setTag(tag);
        return progressBar;
    }

    /**
     * Crée un TextView centré avec une taille de texte personnalisée.
     */
    public TextView createCenteredTextView(String tag, String text, int textSize, int visibility) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setVisibility(visibility);
        textView.setLayoutParams(new CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        if (tag != null) {
            textView.setTag(tag);
        }

        return textView;
    }

    public void setFont(TextView textView,String fontName){
        if (fontName!=null) {
            int fontResId = context.getResources().getIdentifier(fontName, "font", context.getPackageName());
            Typeface typeface = ResourcesCompat.getFont(context, fontResId);
            textView.setTypeface(typeface);
        }
    }



    // -------------------- Méthode utilitaire --------------------

    /**
     * Convertit une valeur en dp en pixels.
     */
    public int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}