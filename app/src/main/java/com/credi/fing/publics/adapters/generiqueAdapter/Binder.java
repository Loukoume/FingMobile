package com.credi.fing.publics.adapters.generiqueAdapter;

import com.credi.fing.publics.service.ApiAction;
import com.credi.fing.publics.service.OnItemViewClick;
import com.credi.fing.publics.service.impl.Visible;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Binder implements Serializable {
    private String title;
    private String title2;
    private String secondry;
    private String secondry2;
    private String font;
    private String url;
    private String fontFamily;
    private int drawableUrl;
    private Boolean multiSelect;
    private Boolean hideIcone;
    private boolean hideRadio;
    private boolean showSwitch;
    private Visible checkSwitch;
    private List<MenuContextuel> menus;
    private ApiAction apiAction;
    private MenuContextuel mediaClick;
    private MenuContextuel rowClick;
    private RowObject rowObject;
    private String extra;

    private Boolean lbotom;

    private OnItemViewClick onItemViewClick;


    public Binder() {
    }

    public Visible getCheckSwitch() {
        return checkSwitch;
    }

    public void setCheckSwitch(Visible checkSwitch) {
        this.checkSwitch = checkSwitch;
    }

    public boolean isHideRadio() {
        return hideRadio;
    }

    public boolean isShowSwitch() {
        return showSwitch;
    }

    public Binder setShowSwitch(boolean showSwitch) {
        this.showSwitch = showSwitch;
        return this;
    }

    public Binder setHideRadio(boolean hideRadio) {
        this.hideRadio = hideRadio;
        return this;
    }

    public Boolean getHideIcone() {
        return hideIcone;
    }

    public Binder setHideIcone(Boolean hideIcone) {
        this.hideIcone = hideIcone;
        return this;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public Binder setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    public Boolean getLbotom() {
        return lbotom;
    }

    public void setLbotom(Boolean lbotom) {
        this.lbotom = lbotom;
    }

    public String getTitle2() {
        return title2;
    }

    public OnItemViewClick getOnItemViewClick() {
        return onItemViewClick;
    }

    public Binder setOnItemViewClick(OnItemViewClick onItemViewClick) {
        this.onItemViewClick = onItemViewClick;
        return this;
    }

    public Binder setTitle2(String title2) {
        this.title2 = title2;
        return this;
    }

    public String getSecondry2() {
        return secondry2;
    }

    public Binder setSecondry2(String secondry2) {
        this.secondry2 = secondry2;
        return this;
    }

    public Boolean getMultiSelect() {
        return multiSelect;
    }

    public Binder setMultiSelect(Boolean multiSelect) {
        this.multiSelect = multiSelect;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public Binder setExtra(String extra) {
        this.extra = extra;
        return this;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getUrl() {
        return url;
    }

    public RowObject getRowObject() {
        return rowObject;
    }

    public void setRowObject(RowObject rowObject) {
        this.rowObject = rowObject;
    }

    public MenuContextuel getMediaClick() {
        return mediaClick;
    }

    public void setMediaClick(MenuContextuel mediaClick) {
        this.mediaClick = mediaClick;
    }

    public MenuContextuel getRowClick() {
        return rowClick;
    }

    public void setRowClick(MenuContextuel rowClick) {
        this.rowClick = rowClick;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDrawableUrl() {
        return drawableUrl;
    }

    public void setDrawableUrl(int drawableUrl) {
        this.drawableUrl = drawableUrl;
    }

    public ApiAction getApiAction() {
        return apiAction;
    }

    public void setApiAction(ApiAction apiAction) {
        this.apiAction = apiAction;
    }

    public Binder(String title, String secondry) {
        this.title = title;
        this.secondry = secondry;
    }

    public Binder(String title, String secondry, List<MenuContextuel> menus) {
        this.title = title;
        this.secondry = secondry;
        this.menus = menus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondry() {
        return secondry;
    }

    public Binder setSecondry(String secondry) {
        this.secondry = secondry;
        return this;
    }

    public List<MenuContextuel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuContextuel> menus) {
        this.menus = menus;
    }

    public void defaultMenu(){
        this.menus= new ArrayList<>(Arrays.asList(new MenuContextuel("Supprimer", ActionMenu.DELETE),new MenuContextuel("Modifier",ActionMenu.UPDATE),
                new MenuContextuel("Quitter",ActionMenu.FINISH)));
    }
    public Binder setDefaultMenu(){
        List<MenuContextuel> ms= new ArrayList<>(Arrays.asList(new MenuContextuel("Supprimer", ActionMenu.DELETE),new MenuContextuel("Modifier",ActionMenu.UPDATE)));
        if(this.menus==null||this.menus.isEmpty()){
            this.menus=ms;
        }else {
            this.menus.addAll(0,ms);
        }
        return this;
    }
    public Binder addMenu(MenuContextuel menu){
        if(this.menus==null)this.menus=new ArrayList<>();
        this.menus.add(menu);
        return this;
    }
}
