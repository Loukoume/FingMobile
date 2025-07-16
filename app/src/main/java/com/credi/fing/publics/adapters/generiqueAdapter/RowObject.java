package com.credi.fing.publics.adapters.generiqueAdapter;

import com.credi.fing.R;

import java.io.Serializable;
import java.util.List;

public class RowObject implements Serializable {
    private int layout;
    private int drawableIcon;
    private Boolean star;
    private SwitchBinder showStar;
    private SwitchBinder showNotif;
    private List<SwitchBinder> drawableIconSwitchBinders;
    private List<SwitchBinder> starSwitchBinders;
    private ElementRow typeMedia;
    private Class<?> navigetActivity;

    public RowObject(Object... attributs){
        for (Object attr :attributs){
            if (attr instanceof Integer) {
                this.layout = (int) attr;
            } else if (attr instanceof ElementRow) {
                this.typeMedia = (ElementRow) attr;
                if(this.layout== R.layout.card_image_verti_row){
                    //   this.typeMedia=ElementRow.CUSTOM_MEDIA;
                }
            }
        }
    }

    public Class<?> getNavigetActivity() {
        return navigetActivity;
    }

    public RowObject setNavigetActivity(Class<?> navigetActivity) {
        this.navigetActivity = navigetActivity;
        return this;
    }

    public SwitchBinder getShowNotif() {
        return showNotif;
    }

    public RowObject setShowNotif(SwitchBinder showNotif) {
        this.showNotif = showNotif;
        return this;
    }

    public SwitchBinder getShowStar() {
        return showStar;
    }

    public RowObject setShowStar(SwitchBinder showStar) {
        this.showStar = showStar;
        return this;
    }

    public List<SwitchBinder> getStarSwitchBinders() {
        return starSwitchBinders;
    }

    public RowObject setStarSwitchBinders(List<SwitchBinder> starSwitchBinders) {
        this.starSwitchBinders = starSwitchBinders;
        return this;
    }

    public List<SwitchBinder> getDrawableIconSwitchBinders() {
        return drawableIconSwitchBinders;
    }

    public RowObject setDrawableIconSwitchBinders(List<SwitchBinder> drawableIconSwitchBinders) {
        this.drawableIconSwitchBinders = drawableIconSwitchBinders;
        return this;
    }

    public Boolean isStar() {
        return star;
    }

    public RowObject setStar(Boolean star) {
        this.star = star;
        return this;
    }

    public int getDrawableIcon() {
        return drawableIcon;
    }
    public RowObject setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
        return this;
    }
    public RowObject putDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
        return this;
    }

    public int getLayout() {
        return layout;
    }

    public ElementRow getTypeMedia() {
        return typeMedia;
    }
}
